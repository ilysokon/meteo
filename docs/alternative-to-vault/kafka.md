https://dev.to/giveitatry/deploying-apache-kafka-4x-on-kubernetes-with-kraft-sasl-and-high-availability-3m0k

from ~/git/meteo/k8s
# kubectl apply -f kafka/namespace.yaml
# kubectl apply -f kafka/kafka-svc.yaml
# kubectl apply -f kafka/kafka-jaas.yaml
# kubectl apply -f kafka/kafka-sasl.yaml
# On explorer-4 
## 1. run the script from k8s/kafka/kafka-localpath-values.sh
## 2. Затем скачай официальный chart и установи его:
    git clone --depth 1 --branch v0.0.37 \
        https://github.com/rancher/local-path-provisioner.git
## 3. установи chart
    helm upgrade --install kafka-localpath-provisioner \
    ./local-path-provisioner/deploy/chart/local-path-provisioner \
    --namespace kafka-storage \
    --create-namespace \
    --values kafka-localpath-values.yaml
## 4. Проверить установку
    sudo kubectl --kubeconfig=/etc/kubernetes/admin.conf \
    get pods -n kafka-storage -o wide
    
    sudo kubectl --kubeconfig=/etc/kubernetes/admin.conf \
    get storageclass kafka-localpath -o yaml

    У StorageClass должно быть:
        provisioner: rancher.io/kafka-localpath
        reclaimPolicy: Retain
        volumeBindingMode: WaitForFirstConsumer
# Generating Your Own Cluster ID
    The cluster ID q1Sh-9_ISia_zwGINzRvyQ used in this guide was generated with:
    /opt/kafka/bin/kafka-storage.sh random-uuid

    and set it in the Kafka StatefulSet to replace the preseted value in --cluster-id q1Sh-9_ISia_zwGINzRvyQ \ 
# kubectl apply -f kafka/kafka-statefulset.yaml

## Check kubernetes
    kubectl get namespaces
    NAME              STATUS   AGE
    default           Active   373d
    kafka             Active   16m
    kube-node-lease   Active   373d
    kube-public       Active   373d
    kube-system       Active   373d
    meteo             Active   373d
    vault             Active   306d

    kubectl get statefulset -n kafka
    NAME    READY   AGE
    kafka   3/3     12m

    kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide
    NAMESPACE       NAME                                                              READY   STATUS    RESTARTS          AGE    IP              NODE         NOMINATED NODE   READINESS GATES
    kafka-storage   kafka-localpath-provisioner-local-path-provisioner-84c4f6f66stw   1/1     Running   0                 75m    172.16.242.62   explorer-6   <none>           <none>
    kafka           kafka-0                                                           1/1     Running   0                 118s   172.16.242.3    explorer-6   <none>           <none>
    kafka           kafka-1                                                           1/1     Running   0                 80s    172.16.35.179   explorer-7   <none>           <none>
    kafka           kafka-2                                                           1/1     Running   0                 44s    172.16.242.6    explorer-6   <none>           <none>

# TODO: Step 7 from https://dev.to/giveitatry/deploying-apache-kafka-4x-on-kubernetes-with-kraft-sasl-and-high-availability-3m0k

# Step 7 — Bootstrap SCRAM Credentials
This is the most involved post-deployment step, and also the most commonly misunderstood. Here is why it requires a special process.

The Chicken-and-Egg Problem
The broker's INTERNAL listener (port 9092) requires SASL/SCRAM-SHA-512 authentication. The kafka-configs.sh admin tool needs to connect to a broker to write SCRAM credentials into Kafka's metadata. But to connect to the broker, you need valid SCRAM credentials — which don't exist yet because you haven't written them.

There are two apparent escape hatches that do not actually work:

Using the JAAS file credentials directly against port 9092 — the JAAS file tells the broker process what credentials to use for its own inter-broker connections, but those credentials must also exist in Kafka's metadata store before the SCRAM handshake will succeed. The JAAS file does not pre-populate metadata.
Using --bootstrap-controller against port 9093 — Kafka 4.x does not support the alterUserScramCredentials admin API via the controller quorum endpoint. It must go through a broker.
The solution is to temporarily open an unauthenticated PLAINTEXT listener on a separate port (9094), use it to write the credentials, then close it again.

## 7a — Add the Temporary Listener to the Service
    Edit kafka-svc.yaml to add port 9094:
```yaml
# kafka-svc.yaml (temporary — port 9094 will be removed after bootstrap)
apiVersion: v1
kind: Service
metadata:
  name: kafka-headless
  namespace: kafka
spec:
  clusterIP: None
  selector:
    app: kafka
  ports:
    - name: internal
      port: 9092
    - name: controller
      port: 9093
    - name: plaintext-bootstrap
      port: 9094
```
## 7b — Add the Temporary Listener to the StatefulSet
    Edit kafka-stateful-set.yaml and make these changes to the broker container:

    Add containerPort: 9094 to the ports list.
```yaml
- name: KAFKA_LISTENERS
  value: "INTERNAL://:9092,CONTROLLER://:9093,PLAINTEXT://:9094"
```

    Change KAFKA_LISTENER_SECURITY_PROTOCOL_MAP to:
```yaml
- name: KAFKA_LISTENER_SECURITY_PROTOCOL_MAP
  value: "INTERNAL:SASL_PLAINTEXT,CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT"
```

    Change KAFKA_ADVERTISED_LISTENERS to:
```yaml
- name: KAFKA_ADVERTISED_LISTENERS
  value: "INTERNAL://$(POD_NAME).kafka-headless.kafka.svc.cluster.local:9092,PLAINTEXT://$(POD_NAME).kafka-headless.kafka.svc.cluster.local:9094"
```

## 7c — Apply and Wait for Rollout
kubectl apply -f kafka-svc.yaml
kubectl apply -f kafka-statefulset.yaml
kubectl rollout status statefulset/kafka -n kafka

## 7d — Register the SCRAM Credentials
```yaml
kubectl exec -n kafka kafka-0 -- \
  /opt/kafka/bin/kafka-configs.sh \
  --bootstrap-server kafka-0.kafka-headless.kafka.svc.cluster.local:9094 \
  --alter \
  --add-config 'SCRAM-SHA-512=[password=supersecret]' \
  --entity-type users \
  --entity-name admin
```
    The result:
        Defaulted container "kafka" out of: kafka, init-node-id (init), format-storage (init)
        Completed updating config for user admin.

    If you need additional users (application service accounts), register them now while port 9094 is still open:
```yaml
kubectl exec -n kafka kafka-0 -- \
/opt/kafka/bin/kafka-configs.sh \
--bootstrap-server kafka-0.kafka-headless.kafka.svc.cluster.local:9094 \
--alter \
--add-config 'SCRAM-SHA-512=[password=meteo]' \
--entity-type users \
--entity-name meteo
```
    The result:
        Defaulted container "kafka" out of: kafka, init-node-id (init), format-storage (init)
        Completed updating config for user meteo.

## 7e — Remove the Temporary Listener
    Revert kafka-svc.yaml to remove port 9094:
    Revert the StatefulSet changes — remove the PLAINTEXT entries from KAFKA_LISTENERS, KAFKA_LISTENER_SECURITY_PROTOCOL_MAP, and KAFKA_ADVERTISED_LISTENERS, and remove the containerPort: 9094 line.

    Apply and wait:
        kubectl apply -f kafka-svc.yaml
        kubectl apply -f kafka-statefulset.yaml
        kubectl rollout status statefulset/kafka -n kafka

## Step 8 — Verify the Cluster
    Check the KRaft quorum status:

        kubectl exec -n kafka kafka-0 -- \
            /opt/kafka/bin/kafka-metadata-quorum.sh \
            --bootstrap-controller kafka-0.kafka-headless.kafka.svc.cluster.local:9093 \
            describe --status  

        Output:
        Defaulted container "kafka" out of: kafka, init-node-id (init), format-storage (init)
        ClusterId:              q1Sh-9_ISia_zwGINzRvyQ
        LeaderId:               2
        LeaderEpoch:            158
        HighWatermark:          3934359
        MaxFollowerLag:         0
        MaxFollowerLagTimeMs:   347
        CurrentVoters:          [{"id": 0, "endpoints": ["CONTROLLER://kafka-0.kafka-headless.kafka.svc.cluster.local:9093"]}, {"id": 1, "endpoints": ["CONTROLLER://kafka-1.kafka-headless.kafka.svc.cluster.local:9093"]}, {"id": 2, "endpoints": ["CONTROLLER://kafka-2.kafka-headless.kafka.svc.cluster.local:9093"]}]
        CurrentObservers:       []

kubectl exec -n kafka kafka-0 -- bash -c '
cat > /tmp/client.properties << EOF
security.protocol=SASL_PLAINTEXT
sasl.mechanism=SCRAM-SHA-512
sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required username="admin" password="supersecret";
EOF

    Create a test topic and produce/consume a message:
```shell script
kubectl exec -n kafka kafka-0 -- bash -c '
cat > /tmp/client.properties << EOF
security.protocol=SASL_PLAINTEXT
sasl.mechanism=SCRAM-SHA-512
sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required username="admin" password="supersecret";
EOF

/opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server kafka-0.kafka-headless.kafka.svc.cluster.local:9092 \
  --command-config /tmp/client.properties \
  --create --topic meteo.netatmo.token --partitions 3 --replication-factor 2

echo "hello kafka" | /opt/kafka/bin/kafka-console-producer.sh \
  --bootstrap-server kafka-0.kafka-headless.kafka.svc.cluster.local:9092 \
  --producer.config /tmp/client.properties \
  --topic meteo.netatmo.token

/opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server kafka-0.kafka-headless.kafka.svc.cluster.local:9092 \
--consumer.config /tmp/client.properties \
--topic meteo.netatmo.token --from-beginning --max-messages 1
'
```
    The result:
        Defaulted container "kafka" out of: kafka, init-node-id (init), format-storage (init)
        WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
        Created topic meteo.netatmo.token.
        Warning: --producer.config is deprecated and will be removed in a future version. Use --command-config instead.
        Option --consumer.config is deprecated and will be removed in a future version. Use --command-config instead.
        hello kafka
        Processed a total of 1 messages
