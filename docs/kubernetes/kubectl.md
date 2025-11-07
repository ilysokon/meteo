kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide

# go into container command line
kubectl exec --stdin --tty cassandra-0-0  -n meteo -- /bin/bash
