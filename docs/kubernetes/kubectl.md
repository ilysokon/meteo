kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide

# go into container command line
kubectl exec --stdin --tty cassandra-0-0  -n meteo -- /bin/bash
kubectl exec --stdin --tty vault-0  -n meteo -- /bin/sh

sudo systemctl restart containerd
sudo systemctl status containerd

# This deletes the pod immediately without waiting for graceful shutdown.
kubectl delete pod meteo-deployment-6f9847f7fc-4f5gr -n meteo --force --grace-period=0

# local port : pod port
kubectl port-forward cassandra-0-0 -n meteo 9042:9042
# Instead of forwarding a single pod forward the service:
kubectl port-forward svc/cassandra -n meteo 9042:9042

kubectl apply -f secret.yaml -n meteo

kubectl rollout restart deployments/meteo -n meteo
kubectl rollout restart statefulset/cassandra-0 -n meteo

kubectl rollout status deployment/meteo -n meteo
kubectl rollout history deployment/meteo -n meteo


# List Container images filtering by Pod namespace
kubectl get pods --namespace meteo -o jsonpath="{.items[*].spec.containers[*].image}"
docker.io/library/cassandra:4.1.10 docker.io/library/cassandra:4.1.10 ilysokon/meteo:c55c5e23fb98551e34101f90f0847a0bdebc943c ilysokon/meteo:c55c5e23fb98551e34101f90f0847a0bdebc943c ilysokon/netatmo:dff368f0ea28092769cca4410a7522ad19c820ba ilysokon/netatmo:dff368f0ea28092769cca4410a7522ad19c820ba ilysokon/netatmo-token-refresher:2c94a7bf0baee6267f1dc5a3229f114879ef3f21