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

kubectl apply -f deployment.yaml -n meteo
deployment.apps/meteo-deployment created

kubectl describe pod meteo-deployment-74f98bcb79-4ct7z -n meteo
# pull access denied, repository does not exist or may require authorization: server message: insufficient_scope: authorization failed
    to resolve this issue, login to the docker registry:
    docker login -u ilysokon -p <password>

    kubectl delete deployment/meteo-deployment -n meteo
    kubectl apply -f deployment.yaml -n meteo
        the same issue: pull access denied, repository does not exist or may require authorization: server message: insufficient_scope: authorization failed

