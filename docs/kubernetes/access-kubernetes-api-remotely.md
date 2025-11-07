https://stackoverflow.com/questions/61871640/access-kubernetes-api-remotely
1. cd /etc/kubernetes/pki/
   sudo cp ./apiserver.crt ./apiserver.crt.back
   sudo cp ./apiserver.key ./apiserver.key.back
   sudo cp ./apiserver-kubelet-client.crt ./apiserver-kubelet-client.crt.back
   sudo cp ./apiserver-kubelet-client.key ./apiserver-kubelet-client.key.back
   sudo cp ./apiserver-etcd-client.crt ./apiserver-etcd-client.crt.back
   sudo cp ./apiserver-etcd-client.key ./apiserver-etcd-client.key.back

2. sudo rm /etc/kubernetes/pki/apiserver.*
   
   sudo rm /etc/kubernetes/pki/apiserver.crt 
   sudo rm /etc/kubernetes/pki/apiserver.key
   sudo rm /etc/kubernetes/pki/apiserver-kubelet-client.crt
   sudo rm /etc/kubernetes/pki/apiserver-kubelet-client.key
   sudo rm /etc/kubernetes/pki/apiserver-etcd-client.crt
   sudo rm /etc/kubernetes/pki/apiserver-etcd-client.key
3. sudo kubeadm init phase certs apiserver --apiserver-cert-extra-sans=geometeo.ddns.net

4. generate admin.key, admin.crt, ca.crt 
admin.key, admin.crt, ca.crt generated like described in https://stackoverflow.com/questions/61871640/access-kubernetes-api-remotely
sudo kubeadm init phase certs apiserver --apiserver-cert-extra-sans=geometeo.ddns.net

**client-key-data:** (.kube/config)
echo -n "LS0...Cg==" | base64 -d > admin.key

**client-certificate-data:** (.kube/config)
echo -n "LS0...Cg==" | base64 -d > admin.crt

**certificate-authority-data:** (.kube/config)
echo -n "LS0...Cg==" | base64 -d > ca.crt

5. change .kube/config to have geometeo.ddns.net in ~/.kube/config
6. sudo chmod 777 ~/.kube/config

7. sudo reboot

curl https://192.168.2.82:6443/api/v1/pods  \
--key admin.key \
--cert admin.crt \
--cacert ca.crt

curl https://geometeo.ddns.net:6447/api/v1/pods  \
--key admin.key \
--cert admin.crt \
--cacert ca.crt