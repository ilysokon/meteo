
# Issue: kubernetes: Waiting for a volume to be created either by the external provisioner 'k8s.io/minikube-hostpath' or manually by the system administrator. If volume creation is delayed, please verify that the provisioner is running and correctly registered
## get provisioner in Kubernetes

`kubectl get storageclass`
output:

```
NAME                            PROVISIONER                    RECLAIMPOLICY   VOLUMEBINDINGMODE   ALLOWVOLUMEEXPANSION   AGE
cassandra-localdisk (default)   kubernetes.io/no-provisioner   Delete          Immediate           true                   43h
```

### Info about `kubernetes.io/no-provisioner`
HostPath Provisioner (If Using Local Node Storage)
For small setups or local environments, you can still use the HostPath provisioner if you want to simulate dynamic provisioning using local node directories.

Features:
HostPath simply uses a local directory on a node as a volume.

It is useful for testing and local Kubernetes clusters but should not be used in production environments.

Example Configuration:
```
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
name: hostpath-storage
provisioner: kubernetes.io/no-provisioner
volumeBindingMode: WaitForFirstConsumer
```
## To fix: To set up: Local Volume Provisioner (local-provisioner)

### Info
The local-provisioner is a lightweight provisioner that dynamically provisions local disks as persistent volumes (PVs) in Kubernetes.

#### Features:
Uses the local disk on each node as storage.

Requires manually configuring the local path and setting up directories on nodes.

Ideal for non-cloud environments or simple local testing.

### Install local-provisioner:
```
kubectl apply -f  https://raw.githubusercontent.com/external-storage/local-provisioner/master/deploy/local-provisioner.yaml
```

output:
```
namespace/local-path-storage created
serviceaccount/local-path-provisioner-service-account created
role.rbac.authorization.k8s.io/local-path-provisioner-role created
clusterrole.rbac.authorization.k8s.io/local-path-provisioner-role created
rolebinding.rbac.authorization.k8s.io/local-path-provisioner-bind created
clusterrolebinding.rbac.authorization.k8s.io/local-path-provisioner-bind created
deployment.apps/local-path-provisioner created
storageclass.storage.k8s.io/local-path created
configmap/local-path-config created
```
Check StorageClass and PVs:
After the provisioner is installed, it will create PersistentVolumes using local storage paths defined in your StorageClass.

`kubectl get storageclass -n meteo`

output: 
```
NAME         PROVISIONER             RECLAIMPOLICY   VOLUMEBINDINGMODE      ALLOWVOLUMEEXPANSION   AGE
local-path   rancher.io/local-path   Delete          WaitForFirstConsumer   false                  7m12s
```

change provisioner in ./k8s/deployment-cassandra-statefulset-explorer-6-with-persistentVolumeClaim.yaml to `rancher.io/local-path`
```
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: cassandra-localdisk
provisioner: rancher.io/local-path
allowVolumeExpansion: true
```

kubectl apply -f ./k8s/deployment-cassandra-statefulset-explorer-6-with-persistentVolumeClaim.yaml -n meteo

```
kubectl get pods -n meteo
NAME                                READY   STATUS             RESTARTS       AGE
cassandra-0                         0/1     CrashLoopBackOff   5 (105s ago)   5m59s
meteo-deployment-5cf7b9f48b-854mn   1/1     Running            0              88d
meteo-deployment-5cf7b9f48b-k2vbq   1/1     Running            0              88d
```

```
kubectl get pvc -n meteo

NAME                         STATUS    VOLUME         CAPACITY   ACCESS MODES   STORAGECLASS   VOLUMEATTRIBUTESCLASS   AGE
cassandra-data-cassandra-0   Pending                                            fast           <unset>                 121d
cassandra-pvc                Bound     cassandra-pv   1Gi        RWO            localdisk      <unset>                 44h
```

```
kubectl describe pvc cassandra-data-cassandra-0 -n meteo

Name:          cassandra-data-cassandra-0
Namespace:     meteo
StorageClass:  fast
Status:        Pending
Volume:        
Labels:        app=cassandra
Annotations:   volume.beta.kubernetes.io/storage-provisioner: k8s.io/minikube-hostpath
volume.kubernetes.io/storage-provisioner: k8s.io/minikube-hostpath
Finalizers:    [kubernetes.io/pvc-protection]
Capacity:      
Access Modes:  
VolumeMode:    Filesystem
Used By:       <none>
Events:
Type     Reason              Age                     From                         Message
  ----     ------              ----                    ----                         -------
Warning  ProvisioningFailed  3m43s (x4410 over 18h)  persistentvolume-controller  storageclass.storage.k8s.io "fast" not found
```

```
kubectl get storageclass

NAME                  PROVISIONER             RECLAIMPOLICY   VOLUMEBINDINGMODE      ALLOWVOLUMEEXPANSION   AGE
cassandra-localdisk   rancher.io/local-path   Delete          Immediate              true                   16m
local-path            rancher.io/local-path   Delete          WaitForFirstConsumer   false                  25m
```

to change in pvc 
   `storageClassName: local-path`, was `localdisk`

```
kubectl get pods -n meteo
NAME                                READY   STATUS             RESTARTS        AGE
cassandra-0                         0/1     CrashLoopBackOff   250 (50s ago)   21h
cassandra-1-0                       0/1     CrashLoopBackOff   229 (51s ago)   19h
meteo-deployment-5cf7b9f48b-854mn   1/1     Running            0               89d
meteo-deployment-5cf7b9f48b-k2vbq   1/1     Running            0               89d
```

```
kubectl describe pod cassandra-0 -n meteo

...
Warning  BackOff  3m26s (x5772 over 21h)  kubelet  Back-off restarting failed container cassandra in pod cassandra-0_meteo(d4c554fb-6825-4f25-8ce2-acf6168024c6)
```

```
kubectl logs  cassandra-0 -n meteo 

INFO  13:10:59 Unable to load cassandra-topology.properties; compatibility mode disabled
WARN  13:11:00 Seed provider couldn't lookup host cassandra-0.cassandra.default.svc.cluster.local
Exception (org.apache.cassandra.exceptions.ConfigurationException) encountered during startup: The seed provider lists no seeds.
ERROR 13:11:00 Exception encountered during startup: The seed provider lists no seeds.
The seed provider lists no seeds.

means your Cassandra instance has no seed nodes configured, and as a result, it cannot bootstrap or join the cluster.

```

```
kubectl exec -it cassandra-0 -- /bin/bash

pod cassandra-0 does not have a host assigned
```

