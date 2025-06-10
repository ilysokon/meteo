# cassandra-cluster.yaml
```
kubectl get pod -n meteo
NAME                                READY   STATUS    RESTARTS      AGE
cassandra-0                         1/1     Running     0           3m15s
cassandra-1                         1/1     Running     0           3m13s
```

cassandra-persistent-volume-claim.yaml
cassandra-statefulstate.yaml
deployment-cassandra-statefulset.yaml
deployment-cassandra-statefulset-explorer-6.yaml
deployment-cassandra-statefulset-explorer-7.yaml

# deployment-cassandra-statefulset-explorer-6-with-persistentVolumeClaim.yaml
# deployment-cassandra-statefulset-explorer-7-with-persistentVolumeClaim.yaml
```
kubectl get pod -n meteo
meteo                cassandra-0-0                              1/1     Running   0             14m    172.16.242.54   explorer-6   <none>           <none>
meteo                cassandra-1-0                              1/1     Running   0             13m    172.16.35.167   explorer-7   <none>           <none>
```
deployment-cassandra-statefulset-explorer-with-persistentVolumeClaim.yaml

start_cassandra_on_kubrnetes_cluster_with_jmx_prometheus_javaagent.md
  -- not yet done, only instruction how to do from ChatGPT
