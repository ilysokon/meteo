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