kubectl apply -f deployment.yaml

kubectl config current-context
kubectl config get-contexts
kubectl config use-context meteo

directly on explorer-4:
kubectl create namespace meteo

kubectl get namespace
NAME              STATUS   AGE
default           Active   30d
kube-node-lease   Active   30d
kube-public       Active   30d
kube-system       Active   30d
meteo             Active   37s

# get the admin.conf file from the remote server

scp explorer@explorer-4:/etc/kubernetes/admin.conf ./docs/kubernetes/admin.conf

add content of admin.conf to ~/./kube/config
