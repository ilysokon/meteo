# cassandra-cluster.yaml
    kubectl get pod -n meteo
    NAME                                READY   STATUS    RESTARTS      AGE
    cassandra-0                         0/1     Error     5 (95s ago)   3m15s
    cassandra-1                         0/1     Error     5 (99s ago)   3m13s

    kubectl logs -f cassandra-0 -n meteo
    WARN  14:28:17 Seed provider couldn't lookup host cassandra-0.cassandra.default.svc.cluster.local
    The seed provider lists no seeds.
    Exception (org.apache.cassandra.exceptions.ConfigurationException) encountered during startup: The seed provider lists no seeds.
    ERROR 14:28:17 Exception encountered during startup: The seed provider lists no seeds.

    kubectl logs -f cassandra-1 -n meteo
    WARN  14:31:18 Seed provider couldn't lookup host cassandra-0.cassandra.default.svc.cluster.local
    The seed provider lists no seeds.
    Exception (org.apache.cassandra.exceptions.ConfigurationException) encountered during startup: The seed provider lists no seeds.
    ERROR 14:31:18 Exception encountered during startup: The seed provider lists no seeds.


cassandra-persistent-volume-claim.yaml
cassandra-statefulstate.yaml
deployment-cassandra-statefulset.yaml
deployment-cassandra-statefulset-explorer-6.yaml
deployment-cassandra-statefulset-explorer-7.yaml

# deployment-cassandra-statefulset-explorer-6-with-persistentVolumeClaim.yaml
# deployment-cassandra-statefulset-explorer-7-with-persistentVolumeClaim.yaml
    kubectl get pod -n meteo
    meteo                cassandra-0-0                              1/1     Running   0             14m    172.16.242.54   explorer-6   <none>           <none>
    meteo                cassandra-1-0                              1/1     Running   0             13m    172.16.35.167   explorer-7   <none>           <none>

deployment-cassandra-statefulset-explorer-with-persistentVolumeClaim.yaml