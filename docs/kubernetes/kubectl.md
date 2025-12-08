kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide

# go into container command line
kubectl exec --stdin --tty cassandra-0-0  -n meteo -- /bin/bash

sudo systemctl restart containerd
sudo systemctl status containerd

# This deletes the pod immediately without waiting for graceful shutdown.
kubectl delete pod meteo-deployment-6f9847f7fc-4f5gr -n meteo --force --grace-period=0

# local port : pod port
kubectl port-forward cassandra-0-0 9042:9042

kubectl apply -f secret.yaml