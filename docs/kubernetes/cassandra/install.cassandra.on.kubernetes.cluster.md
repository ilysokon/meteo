https://kubernetes.io/docs/tutorials/stateful-application/cassandra/

Note:
Cassandra and Kubernetes both use the term node to mean a member of a cluster. In this tutorial, the Pods that belong to the StatefulSet are Cassandra nodes and are members of the Cassandra cluster (called a ring). When those Pods run in your Kubernetes cluster, the Kubernetes control plane schedules those Pods onto Kubernetes Nodes.

When a Cassandra node starts, it uses a seed list to bootstrap discovery of other nodes in the ring. This tutorial deploys a custom Cassandra seed provider that lets the database discover new Cassandra Pods as they appear inside your Kubernetes cluster.

Creating a headless Service for Cassandra
In Kubernetes, a Service describes a set of Pods that perform the same task.

The following Service is used for DNS lookups between Cassandra Pods and clients within your cluster:
    
    ```yaml
apiVersion: v1
kind: Service
metadata:
labels:
app: cassandra
name: cassandra
spec:
clusterIP: None
ports:
- port: 9042
  selector:
  app: cassandra
```

# Create a Service to track all Cassandra StatefulSet members from the cassandra-service.yaml file:

kubectl apply -f cassandra-service.yaml -n meteo

# Validating (optional) Get the Cassandra Service.

kubectl get svc cassandra
    kubectl get svc cassandra -n meteo
    NAME        TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)    AGE
    cassandra   ClusterIP   None         <none>        9042/TCP   14s

