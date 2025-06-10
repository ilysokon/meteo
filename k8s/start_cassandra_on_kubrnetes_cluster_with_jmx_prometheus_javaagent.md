start cassandra on kubrnetes cluster with jmx_prometheus_javaagent

Step-by-Step Setup
✅ 1. Download the JMX Exporter Agent & Config

First, you need two things:

jmx_prometheus_javaagent-<version>.jar (e.g. jmx_prometheus_javaagent-0.20.0.jar)

A JMX config file (cassandra.yml) for monitoring specific metrics

✅ 2. Add These as Kubernetes ConfigMaps

You can store both the agent JAR and config in a shared volume (like an emptyDir) or better, mount them via a ConfigMap.

Example: `cassandra-jmx-configmap.yaml`
```
apiVersion: v1
kind: ConfigMap
metadata:
    name: cassandra-jmx-config
data:
    cassandra.yml: |
    startDelaySeconds: 0
    jmxUrl: ""
    lowercaseOutputName: true
    lowercaseOutputLabelNames: true
    whitelistObjectNames:
        - org.apache.cassandra.metrics:*
```
You'll also need to mount the `.jar` file from a custom image or init container (more below).

✅ 3. Modify Cassandra Container in Your StatefulSet

Add:

* Mounts for the JMX agent and config
* Set JAVA_OPTS to include the agent
* Set LOCAL_JMX=no

Example patch to `containers`:
```
env:
    - name: LOCAL_JMX
        value: "no"
    - name: JAVA_OPTS
        value: >
            -javaagent:/jmx-exporter/jmx_prometheus_javaagent.jar=7070:/jmx-exporter/cassandra.yml

volumeMounts:
- name: jmx-config
  mountPath: /jmx-exporter
 ``` 
  
Add the volume:

```
volumes:
- name: jmx-config
  configMap:
    name: cassandra-jmx-config
```
✅ 4. Add a Prometheus ServiceMonitor or Annotation

Expose port 7070 in the container:
```
ports:
- name: jmx
  containerPort: 7070
```  
  Then annotate the Service:
```
metadata:
    annotations:
        prometheus.io/scrape: "true"
        prometheus.io/port: "7070"
```
Or use a ServiceMonitor if you're using Prometheus Operator.

✅ 5. Custom Cassandra Image with Agent (optional)

If you're using gcr.io/google-samples/cassandra:v14, it does not include the JMX exporter agent.

You’ll need to build a custom image like:

Dockerfile
```
FROM gcr.io/google-samples/cassandra:v14
ADD jmx_prometheus_javaagent-0.20.0.jar /jmx-exporter/
```
Push to a registry and update your StatefulSet image.

