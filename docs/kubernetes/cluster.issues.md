# 1. The error below is appeared after the power of the PCs where the kubernetes cluster is installed is switched off

kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide
E0726 19:17:42.714026   10719 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
E0726 19:17:42.716098   10719 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
E0726 19:17:42.718354   10719 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
E0726 19:17:42.720049   10719 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
E0726 19:17:42.721900   10719 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
The connection to the server geometeo.ddns.net:6447 was refused - did you specify the right host or port?

    kubectl get nodes -v=10
    I0726 19:39:02.758352   11064 loader.go:395] Config loaded from file:  /home/explorer/.kube/config
    I0726 19:39:02.759670   11064 round_trippers.go:466] curl -v -XGET  -H "Accept: application/json;g=apidiscovery.k8s.io;v=v2beta1;as=APIGroupDiscoveryList,application/json" -H "User-Agent: kubectl/v1.29.12 (linux/amd64) kubernetes/9253c9b" 'https://geometeo.ddns.net:6447/api?timeout=32s'
    I0726 19:39:02.777495   11064 round_trippers.go:495] HTTP Trace: DNS Lookup for geometeo.ddns.net resolved to [{87.144.185.136 }]
    I0726 19:39:02.778584   11064 round_trippers.go:508] HTTP Trace: Dial to tcp:87.144.185.136:6447 failed: dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.778620   11064 round_trippers.go:553] GET https://geometeo.ddns.net:6447/api?timeout=32s  in 18 milliseconds
    I0726 19:39:02.778644   11064 round_trippers.go:570] HTTP Statistics: DNSLookup 16 ms Dial 1 ms TLSHandshake 0 ms Duration 18 ms
    I0726 19:39:02.778657   11064 round_trippers.go:577] Response Headers:
    E0726 19:39:02.778715   11064 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.778806   11064 cached_discovery.go:120] skipped caching discovery info due to Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.778901   11064 round_trippers.go:466] curl -v -XGET  -H "Accept: application/json;g=apidiscovery.k8s.io;v=v2beta1;as=APIGroupDiscoveryList,application/json" -H "User-Agent: kubectl/v1.29.12 (linux/amd64) kubernetes/9253c9b" 'https://geometeo.ddns.net:6447/api?timeout=32s'
    I0726 19:39:02.779845   11064 round_trippers.go:495] HTTP Trace: DNS Lookup for geometeo.ddns.net resolved to [{87.144.185.136 }]
    I0726 19:39:02.780483   11064 round_trippers.go:508] HTTP Trace: Dial to tcp:87.144.185.136:6447 failed: dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.780515   11064 round_trippers.go:553] GET https://geometeo.ddns.net:6447/api?timeout=32s  in 1 milliseconds
    I0726 19:39:02.780531   11064 round_trippers.go:570] HTTP Statistics: DNSLookup 0 ms Dial 0 ms TLSHandshake 0 ms Duration 1 ms
    I0726 19:39:02.780545   11064 round_trippers.go:577] Response Headers:
    E0726 19:39:02.780596   11064 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.780610   11064 cached_discovery.go:120] skipped caching discovery info due to Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.781419   11064 shortcut.go:103] Error loading discovery information: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.781507   11064 round_trippers.go:466] curl -v -XGET  -H "Accept: application/json;g=apidiscovery.k8s.io;v=v2beta1;as=APIGroupDiscoveryList,application/json" -H "User-Agent: kubectl/v1.29.12 (linux/amd64) kubernetes/9253c9b" 'https://geometeo.ddns.net:6447/api?timeout=32s'
    I0726 19:39:02.782655   11064 round_trippers.go:495] HTTP Trace: DNS Lookup for geometeo.ddns.net resolved to [{87.144.185.136 }]
    I0726 19:39:02.783191   11064 round_trippers.go:508] HTTP Trace: Dial to tcp:87.144.185.136:6447 failed: dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.783221   11064 round_trippers.go:553] GET https://geometeo.ddns.net:6447/api?timeout=32s  in 1 milliseconds
    I0726 19:39:02.783234   11064 round_trippers.go:570] HTTP Statistics: DNSLookup 1 ms Dial 0 ms TLSHandshake 0 ms Duration 1 ms
    I0726 19:39:02.783245   11064 round_trippers.go:577] Response Headers:
    E0726 19:39:02.783295   11064 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.783307   11064 cached_discovery.go:120] skipped caching discovery info due to Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.783415   11064 round_trippers.go:466] curl -v -XGET  -H "Accept: application/json;g=apidiscovery.k8s.io;v=v2beta1;as=APIGroupDiscoveryList,application/json" -H "User-Agent: kubectl/v1.29.12 (linux/amd64) kubernetes/9253c9b" 'https://geometeo.ddns.net:6447/api?timeout=32s'
    I0726 19:39:02.784485   11064 round_trippers.go:495] HTTP Trace: DNS Lookup for geometeo.ddns.net resolved to [{87.144.185.136 }]
    I0726 19:39:02.785218   11064 round_trippers.go:508] HTTP Trace: Dial to tcp:87.144.185.136:6447 failed: dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.785247   11064 round_trippers.go:553] GET https://geometeo.ddns.net:6447/api?timeout=32s  in 1 milliseconds
    I0726 19:39:02.785258   11064 round_trippers.go:570] HTTP Statistics: DNSLookup 0 ms Dial 0 ms TLSHandshake 0 ms Duration 1 ms
    I0726 19:39:02.785266   11064 round_trippers.go:577] Response Headers:
    E0726 19:39:02.785306   11064 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.785320   11064 cached_discovery.go:120] skipped caching discovery info due to Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.785399   11064 round_trippers.go:466] curl -v -XGET  -H "Accept: application/json;g=apidiscovery.k8s.io;v=v2beta1;as=APIGroupDiscoveryList,application/json" -H "User-Agent: kubectl/v1.29.12 (linux/amd64) kubernetes/9253c9b" 'https://geometeo.ddns.net:6447/api?timeout=32s'
    I0726 19:39:02.786517   11064 round_trippers.go:495] HTTP Trace: DNS Lookup for geometeo.ddns.net resolved to [{87.144.185.136 }]
    I0726 19:39:02.787189   11064 round_trippers.go:508] HTTP Trace: Dial to tcp:87.144.185.136:6447 failed: dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.787215   11064 round_trippers.go:553] GET https://geometeo.ddns.net:6447/api?timeout=32s  in 1 milliseconds
    I0726 19:39:02.787226   11064 round_trippers.go:570] HTTP Statistics: DNSLookup 1 ms Dial 0 ms TLSHandshake 0 ms Duration 1 ms
    I0726 19:39:02.787234   11064 round_trippers.go:577] Response Headers:
    E0726 19:39:02.787274   11064 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.787288   11064 cached_discovery.go:120] skipped caching discovery info due to Get "https://geometeo.ddns.net:6447/api?timeout=32s": dial tcp 87.144.185.136:6447: connect: connection refused
    I0726 19:39:02.787915   11064 helpers.go:264] Connection error: Get https://geometeo.ddns.net:6447/api?timeout=32s: dial tcp 87.144.185.136:6447: connect: connection refused
    The connection to the server geometeo.ddns.net:6447 was refused - did you specify the right host or port?

    **Most probably to resolve the issue the commands from _access-kubernetes-api-remotely.md_ are need to be repeated**
        
After the command 
    sudo kubeadm reset 
  were applied all files in cd /etc/kubernetes/pki/ were removed and the error 
  "The connection to the server geometeo.ddns.net:6447 was refused - did you specify the right host or port?" is appeared
    
    to resolve:
      kubeadm init
      and then 
        kubeadm join all nodes again. the join command is given by the "kubeamd init" command

kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide
Unable to connect to the server: tls: failed to verify certificate: x509: certificate signed by unknown authority (possibly because of "crypto/rsa: verification error" while trying to verify candidate authority certificate "kubernetes")          
    This error often occurs when you are using an old or wrong config from a previous kubernetes installation or setup.

            The below commands will remove the old or wrong config and copy the new config to the .kube directory as well as set the correct permissions. 
            You can first make a backup of the old or wrong config if you think you might still need using the command mv $HOME/.kube $HOME/.kube.bak:

            rm -rf $HOME/.kube || true    
            mkdir -p $HOME/.kube   
            sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config   
            sudo chown $(id -u):$(id -g) $HOME/.kube/config

kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide
error: error loading config file "/home/explorer/.kube/config": open /home/explorer/.kube/config: permission denied
Solution:
sudo chown -R $(id -u):$(id -g) $HOME/.kube/

# 2.Warning  FailedScheduling  14m (x18 over 99m)  default-scheduler  0/3 nodes are available: 1 node(s) had untolerated taint {node-role.kubernetes.io/control-plane: }, 2 node(s) had untolerated taint {node.kubernetes.io/unreachable: }. preemption: 0/3 nodes are available: 3 Preemption is not helpful for scheduling.
kubectl describe pod meteo-deployment-854d8db8b7-d9xvc -n meteo

## 2.1. Remove the taints on the master so that you can schedule pods on it.(https://stackoverflow.com/questions/59484509/node-had-taints-that-the-pod-didnt-tolerate-error-when-deploying-to-kubernetes)
kubectl taint nodes --all node-role.kubernetes.io/master-
taint "node-role.kubernetes.io/master" not found

    2.1.1 Nodes NotReady
    kubectl get nodes --show-labels
    NAME         STATUS     ROLES           AGE    VERSION    LABELS
    explorer-4   Ready      control-plane   239d   v1.29.12   beta.kubernetes.io/arch=amd64,beta.kubernetes.io/os=linux,kubernetes.io/arch=amd64,kubernetes.io/hostname=explorer-4,kubernetes.io/os=linux,node-role.kubernetes.io/control-plane=,node.kubernetes.io/exclude-from-external-load-balancers=
    explorer-6   NotReady   <none>          239d   v1.29.12   beta.kubernetes.io/arch=amd64,beta.kubernetes.io/os=linux,kubernetes.io/arch=amd64,kubernetes.io/hostname=explorer-6,kubernetes.io/os=linux
    explorer-7   NotReady   <none>          239d   v1.29.12   beta.kubernetes.io/arch=amd64,beta.kubernetes.io/os=linux,kubernetes.io/arch=amd64,kubernetes.io/hostname=explorer-7,kubernetes.io/os=linux
        
        kubectl describe node explorer-6 
        MemoryPressure       Unknown   Fri, 25 Jul 2025 20:54:29 +0200   Sun, 17 Aug 2025 19:46:27 +0200   NodeStatusUnknown   Kubelet stopped posting node status.
         
        Solution 1:
            ssh explorer@explorer-6
            sudo systemctl restart kubelet

            ssh explorer@explorer-7
            sudo systemctl restart kubelet    
             
            Result: no effect, nothing is changed
        
        Solution 2: 
            Check swap on or off ---> free -m
            if swap is on , turn off --->sudo swapoff -a
            sudo reboot
            now its works.....!
            
                explorer@explorer-6: free -m
                    total        used        free      shared  buff/cache   available
                    Mem:           15833         500       15232           1         369       15332
                    Swap:           4095           0        4095
                explorer@explorer-6:~$ sudo swapoff -a
                explorer@explorer-6:~$ free -m
                    total        used        free      shared  buff/cache   available
                    Mem:           15833         610       14979           1         513       15222
                    Swap:              0           0           0
                The same for explorer-7
                
            after that all nodes are having status Ready

# 3. Cassandra nodes can't start(deployment-cassandra-statefulset-explorer-6-with-persistentVolumeClaim.yaml and deployment-cassandra-statefulset-explorer-7-with-persistentVolumeClaim.yaml)
    Solution: 
        kubectl apply -f coredns.yaml.sed.clean (k8s/coredns.yaml.sed.clean)
            Result cassandra nodes successfully started

        check that cassandra service(k8s/cassandra-service.yaml) is not missing
            This allows Cassandra pods to discover each other at predictable DNS names like: cassandra-0-0.cassandra.meteo.svc.cluster.local
        
        check that seed is defined like below in deployment-cassandra-statefulset-explorer-6-with-persistentVolumeClaim.yaml and deployment-cassandra-statefulset-explorer-7-with-persistentVolumeClaim.yaml
            - name: CASSANDRA_SEEDS
              value: cassandra-0-0.cassandra.meteo.svc.cluster.local


kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide
E1015 16:55:11.829798    2498 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6443/api?timeout=32s": dial tcp 87.144.189.118:6443: connect: connection refused
E1015 16:55:11.832111    2498 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6443/api?timeout=32s": dial tcp 87.144.189.118:6443: connect: connection refused
E1015 16:55:11.834623    2498 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6443/api?timeout=32s": dial tcp 87.144.189.118:6443: connect: connection refused
E1015 16:55:11.836856    2498 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6443/api?timeout=32s": dial tcp 87.144.189.118:6443: connect: connection refused
E1015 16:55:11.839656    2498 memcache.go:265] couldn't get current server API group list: Get "https://geometeo.ddns.net:6443/api?timeout=32s": dial tcp 87.144.189.118:6443: connect: connection refused
The connection to the server geometeo.ddns.net:6443 was refused - did you specify the right host or port?
    Solution:
        1. Router: http://192.168.2.1/html/login/login.html?lang=en
            Internet
                Port Activation
                    geometeo-4  
                            Public    Local
                        TCP 6443 - --> 6443 -   (before was: TCP 6447 - --> 6443 -)
        2. Possible just change in explorer-4 (.kube/config) geometeo.ddns.net:6443 to geometeo.ddns.net:6447 

# 4. Nodes: NotRedy
kubectl get nodes
NAME         STATUS     ROLES           AGE   VERSION
explorer-4   Ready      control-plane   35d   v1.29.12
explorer-6   NotReady   <none>          35d   v1.29.12
explorer-7   NotReady   <none>          35d   v1.29.12

    journalctl -u kubelet -n 30 --no-pager
    Nov 09 16:06:56 explorer-6 systemd[1]: kubelet.service: Main process exited, code=exited, status=1/FAILURE
    Nov 09 16:06:56 explorer-6 systemd[1]: kubelet.service: Failed with result 'exit-code'.
    Nov 09 16:07:06 explorer-6 systemd[1]: kubelet.service: Scheduled restart job, restart counter is at 568.
    Nov 09 16:07:06 explorer-6 systemd[1]: Started kubelet.service - kubelet: The Kubernetes Node Agent.
    Nov 09 16:07:06 explorer-6 kubelet[7546]: Flag --container-runtime-endpoint has been deprecated, This parameter should be set via the config file specified by the Kubelet's --config flag. See https://kubernetes.io/docs/tasks/administer-cluster/kubelet-config-file/ for more information.
    Nov 09 16:07:06 explorer-6 kubelet[7546]: Flag --pod-infra-container-image has been deprecated, will be removed in a future release. Image garbage collector will get sandbox image information from CRI.
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.765971    7546 server.go:209] "--pod-infra-container-image will not be pruned by the image garbage collector in kubelet and should also be set in the remote runtime"
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.769395    7546 server.go:492] "Kubelet version" kubeletVersion="v1.29.12"
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.769414    7546 server.go:494] "Golang settings" GOGC="" GOMAXPROCS="" GOTRACEBACK=""
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.769632    7546 server.go:924] "Client rotation is on, will bootstrap in background"
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.770906    7546 certificate_store.go:130] Loading cert/key pair from "/var/lib/kubelet/pki/kubelet-client-current.pem".
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.772387    7546 dynamic_cafile_content.go:157] "Starting controller" name="client-ca-bundle::/etc/kubernetes/pki/ca.crt"
    Nov 09 16:07:06 explorer-6 kubelet[7546]: I1109 16:07:06.779871    7546 server.go:750] "--cgroups-per-qos enabled, but --cgroup-root was not specified.  defaulting to /"
    Nov 09 16:07:06 explorer-6 kubelet[7546]: E1109 16:07:06.780002    7546 run.go:74] "command failed" err="failed to run Kubelet: running with swap on is not supported, please disable swap! or set --fail-swap-on flag to false. /proc/swaps contained: [Filename\t\t\t\tType\t\tSize\t\tUsed\t\tPriority /swap.img                               file\t\t4194300\t\t0\t\t-2]"
    Nov 09 16:07:06 explorer-6 systemd[1]: kubelet.service: Main process exited, code=exited, status=1/FAILURE
    Nov 09 16:07:06 explorer-6 systemd[1]: kubelet.service: Failed with result 'exit-code'.
    Nov 09 16:07:16 explorer-6 systemd[1]: kubelet.service: Scheduled restart job, restart counter is at 569.
    Nov 09 16:07:16 explorer-6 systemd[1]: Started kubelet.service - kubelet: The Kubernetes Node Agent.
    Nov 09 16:07:17 explorer-6 kubelet[7557]: Flag --container-runtime-endpoint has been deprecated, This parameter should be set via the config file specified by the Kubelet's --config flag. See https://kubernetes.io/docs/tasks/administer-cluster/kubelet-config-file/ for more information.
    Nov 09 16:07:17 explorer-6 kubelet[7557]: Flag --pod-infra-container-image has been deprecated, will be removed in a future release. Image garbage collector will get sandbox image information from CRI.
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.010080    7557 server.go:209] "--pod-infra-container-image will not be pruned by the image garbage collector in kubelet and should also be set in the remote runtime"
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.013367    7557 server.go:492] "Kubelet version" kubeletVersion="v1.29.12"
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.013387    7557 server.go:494] "Golang settings" GOGC="" GOMAXPROCS="" GOTRACEBACK=""
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.013589    7557 server.go:924] "Client rotation is on, will bootstrap in background"
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.014874    7557 certificate_store.go:130] Loading cert/key pair from "/var/lib/kubelet/pki/kubelet-client-current.pem".
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.016701    7557 dynamic_cafile_content.go:157] "Starting controller" name="client-ca-bundle::/etc/kubernetes/pki/ca.crt"
    Nov 09 16:07:17 explorer-6 kubelet[7557]: I1109 16:07:17.023686    7557 server.go:750] "--cgroups-per-qos enabled, but --cgroup-root was not specified.  defaulting to /"
    Nov 09 16:07:17 explorer-6 kubelet[7557]: E1109 16:07:17.023811    7557 run.go:74] "command failed" err="failed to run Kubelet: running with swap on is not supported, please disable swap! or set --fail-swap-on flag to false. /proc/swaps contained: [Filename\t\t\t\tType\t\tSize\t\tUsed\t\tPriority /swap.img                               file\t\t4194300\t\t0\t\t-2]"
    Nov 09 16:07:17 explorer-6 systemd[1]: kubelet.service: Main process exited, code=exited, status=1/FAILURE
    Nov 09 16:07:17 explorer-6 systemd[1]: kubelet.service: Failed with result 'exit-code'.
        Reason:
        Nov 09 16:07:17 explorer-6 kubelet[7557]: E1109 16:07:17.023811    7557 run.go:74] "command failed" err="failed to run Kubelet: running with swap on is not supported, please disable swap! or set --fail-swap-on flag to false. /proc/swaps contained: [Filename\t\t\t\tType\t\tSize\t\tUsed\t\tPriority /swap.img 
        
        Solution:
            On all worker noides:
                sudo swapoff -a
                sudo systemctl restart kubelet

                To Make the change permanent (disable swap at boot):
                Edit /etc/fstab and comment out or remove the swap entry:
                to comment out the line like below
                # /swap.img     none    swap    sw      0       0
                save the file and restart kubelet
                sudo systemctl restart kubelet

            kubectl get nodes
            NAME         STATUS     ROLES           AGE   VERSION
            explorer-4   Ready      control-plane   35d   v1.29.12
            explorer-6   Ready      <none>          35d   v1.29.12
            explorer-7   Ready      <none>          35d   v1.29.12
# 5 Pod is terminating for ever
kube-system   calico-kube-controllers-658d97c59c-x54nf   0/1     Terminating   39               137m   172.16.37.201   explorer-4   <none>           <none>
    ## Solution:
    kubectl delete pod -n kube-system -l k8s-app=calico-node
            
# 6 couldn't get current server API group list: the server has asked for the client to provide credentials 

kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide
E1012 21:22:18.465012 2664224 memcache.go:265] couldn't get current server API group list: the server has asked for the client to provide credentials
E1012 21:22:18.473260 2664224 memcache.go:265] couldn't get current server API group list: the server has asked for the client to provide credentials
E1012 21:22:18.481197 2664224 memcache.go:265] couldn't get current server API group list: the server has asked for the client to provide credentials
E1012 21:22:18.490108 2664224 memcache.go:265] couldn't get current server API group list: the server has asked for the client to provide credentials
E1012 21:22:18.498278 2664224 memcache.go:265] couldn't get current server API group list: the server has asked for the client to provide credentials
    https://chatgpt.com/c/6acd625c-50d4-83eb-9ebd-7353a03a1e95
    сначала определим, падает ли сам API server из-за собственной конфигурации или из-за etcd.
    sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
    logs ETCD_CONTAINER_ID 2>&1 | tail -80

    sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
    logs 89823b43cd611
    I1012 22:41:02.622068       1 options.go:222] external host was not specified, using 192.168.2.82
    I1012 22:41:02.622746       1 server.go:148] Version: v1.29.15
    I1012 22:41:02.622777       1 server.go:150] "Golang settings" GOGC="" GOMAXPROCS="" GOTRACEBACK=""
    I1012 22:41:02.873801       1 shared_informer.go:311] Waiting for caches to sync for node_authorizer
    W1012 22:41:02.877127       1 logging.go:59] [core] [Channel #1 SubChannel #4] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:02Z is after 2026-10-04T17:08:23Z"
    I1012 22:41:02.878668       1 plugins.go:157] Loaded 12 mutating admission controller(s) successfully in the following order: NamespaceLifecycle,LimitRanger,ServiceAccount,NodeRestriction,TaintNodesByCondition,Priority,DefaultTolerationSeconds,DefaultStorageClass,StorageObjectInUseProtection,RuntimeClass,DefaultIngressClass,MutatingAdmissionWebhook.
    I1012 22:41:02.878682       1 plugins.go:160] Loaded 13 validating admission controller(s) successfully in the following order: LimitRanger,ServiceAccount,PodSecurity,Priority,PersistentVolumeClaimResize,RuntimeClass,CertificateApproval,CertificateSigning,ClusterTrustBundleAttest,CertificateSubjectRestriction,ValidatingAdmissionPolicy,ValidatingAdmissionWebhook,ResourceQuota.
    I1012 22:41:02.878968       1 instance.go:297] Using reconciler: lease
    W1012 22:41:02.879353       1 logging.go:59] [core] [Channel #2 SubChannel #3] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:02Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:02.882095       1 logging.go:59] [core] [Channel #5 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:02Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:03.885248       1 logging.go:59] [core] [Channel #1 SubChannel #4] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:03Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:03.887293       1 logging.go:59] [core] [Channel #2 SubChannel #3] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:03Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:03.887503       1 logging.go:59] [core] [Channel #5 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:03Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:05.227328       1 logging.go:59] [core] [Channel #5 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:05Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:05.411106       1 logging.go:59] [core] [Channel #1 SubChannel #4] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:05Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:05.599609       1 logging.go:59] [core] [Channel #2 SubChannel #3] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:05Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:07.871837       1 logging.go:59] [core] [Channel #2 SubChannel #3] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:07Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:08.022375       1 logging.go:59] [core] [Channel #1 SubChannel #4] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:08Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:08.099713       1 logging.go:59] [core] [Channel #5 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:08Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:11.898640       1 logging.go:59] [core] [Channel #5 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:11Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:12.310094       1 logging.go:59] [core] [Channel #2 SubChannel #3] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:12Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:12.878669       1 logging.go:59] [core] [Channel #1 SubChannel #4] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:12Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:17.478940       1 logging.go:59] [core] [Channel #5 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:17Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:19.164867       1 logging.go:59] [core] [Channel #2 SubChannel #3] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:19Z is after 2026-10-04T17:08:23Z"
    W1012 22:41:19.294278       1 logging.go:59] [core] [Channel #1 SubChannel #4] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "transport: authentication handshake failed: tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:19Z is after 2026-10-04T17:08:23Z"
    F1012 22:41:22.879839       1 instance.go:290] Error creating leases: error creating storage factory: context deadline exceeded
        Теперь причина установлена точно: истёк TLS-сертификат, который используется для подключения kube-apiserver к etcd.
        Ключевая строка:
            x509: certificate has expired or is not yet valid:
            current time 2026-10-12T22:41:02Z
            is after 2026-10-04T17:08:23Z
        Сертификат истёк 4 октября 2026 года. Из-за этого API server не может подключиться к etcd на 127.0.0.1:2379, а затем завершается с ошибкой:
        F1012 22:41:22 ... Error creating leases:
        error creating storage factory: context deadline exceeded

        С высокой вероятностью это сертификат клиента etcd для API server (apiserver-etcd-client.crt), но перед обновлением нужно подтвердить, какой именно сертификат истёк.
        sudo kubeadm certs check-expiration
            [check-expiration] Reading configuration from the cluster...
            [check-expiration] FYI: You can look at this config file with 'kubectl -n kube-system get cm kubeadm-config -o yaml'
            [check-expiration] Error reading configuration from the Cluster. Falling back to default configuration
            
            CERTIFICATE                EXPIRES                  RESIDUAL TIME   CERTIFICATE AUTHORITY   EXTERNALLY MANAGED
            admin.conf                 Oct 04, 2026 17:08 UTC   <invalid>       ca                      no      
            apiserver                  Oct 15, 2026 12:27 UTC   2d              ca                      no      
            apiserver-etcd-client      Oct 04, 2026 17:08 UTC   <invalid>       etcd-ca                 no      
            apiserver-kubelet-client   Oct 04, 2026 17:08 UTC   <invalid>       ca                      no      
            controller-manager.conf    Oct 04, 2026 17:08 UTC   <invalid>       ca                      no      
            etcd-healthcheck-client    Oct 04, 2026 17:08 UTC   <invalid>       etcd-ca                 no      
            etcd-peer                  Oct 04, 2026 17:08 UTC   <invalid>       etcd-ca                 no      
            etcd-server                Oct 04, 2026 17:08 UTC   <invalid>       etcd-ca                 no      
            front-proxy-client         Oct 04, 2026 17:08 UTC   <invalid>       front-proxy-ca          no      
            scheduler.conf             Oct 04, 2026 17:08 UTC   <invalid>       ca                      no      
            super-admin.conf           Oct 04, 2026 17:08 UTC   <invalid>       ca                      no
            
            CERTIFICATE AUTHORITY   EXPIRES                  RESIDUAL TIME   EXTERNALLY MANAGED
            ca                      Oct 02, 2035 17:08 UTC   8y              no      
            etcd-ca                 Oct 02, 2035 17:08 UTC   8y              no      
            front-proxy-ca          Oct 02, 2035 17:08 UTC   8y              no     
        
        Отлично, теперь картина ясна. На explorer-4 истекла не одна, а целая группа сертификатов Kubernetes, срок действия которых закончился 4 октября 2026 года. CA-сертификаты при этом действительны до 2035 года — это хорошая новость.
        Наиболее важные истёкшие сертификаты:
        - apiserver-etcd-client — API server не может подключиться к etcd. Это непосредственно подтверждено журналом.
          - etcd-server, etcd-peer, etcd-healthcheck-client — могут нарушать работу самого etcd.
          - apiserver-kubelet-client и сертификаты в .conf — могут вызвать дополнительные проблемы после восстановления API server.
            Сертификат apiserver пока действителен до 15 октября 2026 года. Не нужно пересоздавать CA и не нужно сбрасывать кластер. Правильный путь — обновить истёкшие сертификаты, сохранив существующие CA и данные etcd.
        
            Шаг 1. Сделай резервную копию
                На explorer-4:
                    sudo cp -a /etc/kubernetes \
                    "/root/kubernetes-backup-$(date +%Y%m%d-%H%M%S)"
    
                Это сохранит сертификаты, kubeconfig-файлы и static pod-манифесты.
            Шаг 2. Обнови сертификаты через kubeadm
                sudo kubeadm certs renew all
                Команда обновляет сертификаты, которыми управляет kubeadm, используя существующие CA и ключи. Она не должна удалять данные etcd.
                После завершения проверь результат:
                sudo kubeadm certs check-expiration
                    Reading configuration from the cluster...
                    [check-expiration] FYI: You can look at this config file with 'kubectl -n kube-system get cm kubeadm-config -o yaml'
                    [check-expiration] Error reading configuration from the Cluster. Falling back to default configuration
                    
                    CERTIFICATE                EXPIRES                  RESIDUAL TIME   CERTIFICATE AUTHORITY   EXTERNALLY MANAGED
                    admin.conf                 Oct 12, 2027 22:48 UTC   364d            ca                      no      
                    apiserver                  Oct 12, 2027 22:48 UTC   364d            ca                      no      
                    apiserver-etcd-client      Oct 12, 2027 22:48 UTC   364d            etcd-ca                 no      
                    apiserver-kubelet-client   Oct 12, 2027 22:48 UTC   364d            ca                      no      
                    controller-manager.conf    Oct 12, 2027 22:48 UTC   364d            ca                      no      
                    etcd-healthcheck-client    Oct 12, 2027 22:48 UTC   364d            etcd-ca                 no      
                    etcd-peer                  Oct 12, 2027 22:48 UTC   364d            etcd-ca                 no      
                    etcd-server                Oct 12, 2027 22:48 UTC   364d            etcd-ca                 no      
                    front-proxy-client         Oct 12, 2027 22:48 UTC   364d            front-proxy-ca          no      
                    scheduler.conf             Oct 12, 2027 22:48 UTC   364d            ca                      no      
                    super-admin.conf           Oct 12, 2027 22:48 UTC   364d            ca                      no
                    
                    CERTIFICATE AUTHORITY   EXPIRES                  RESIDUAL TIME   EXTERNALLY MANAGED
                    ca                      Oct 02, 2035 17:08 UTC   8y              no      
                    etcd-ca                 Oct 02, 2035 17:08 UTC   8y              no      
                    front-proxy-ca          Oct 02, 2035 17:08 UTC   8y              no      

            Отлично — сертификаты успешно обновлены. Теперь все перечисленные сертификаты, включая apiserver-etcd-client, etcd-server и etcd-peer, действительны до 12 октября 2027 года. CA-сертификаты остаются действительными до 2035 года.
            Сообщение Error reading configuration from the Cluster ожидаемо при недоступном API server: kubeadm не смог прочитать конфигурацию из кластера и использовал настройки по умолчанию. Само по себе это не означает, что обновление сертификатов не удалось.
            Следующий шаг — заставить kube-apiserver перечитать обновлённые сертификаты и проверить, что API server запускается.
            1. Перезапусти etcd, чтобы он загрузил новые сертификаты
               На explorer-4 сначала проверь, что манифест существует:
               sudo ls -l /etc/kubernetes/manifests/etcd.yaml


            Поскольку сертификаты etcd тоже обновлены, работающий процесс etcd может продолжать использовать старые сертификаты в памяти.
            Перед перезапуском: если у тебя несколько control-plane-узлов, не трогай etcd, пока не проверишь состояние остальных участников и кворум. Если это единственный control-plane-узел, короткая недоступность API при перезапуске ожидаема.
            2. Перезапусти API server
               Если обновление сертификатов завершилось успешно, после перезапуска etcd можно перезапустить только static pod API server. Способ через временное перемещение манифеста:
               sudo mkdir -p /root/k8s-manifests-hold
               sudo mv /etc/kubernetes/manifests/kube-apiserver.yaml \
               /root/k8s-manifests-hold/


            Подожди примерно 20 секунд, затем верни манифест:
            sudo mv /root/k8s-manifests-hold/kube-apiserver.yaml \
            /etc/kubernetes/manifests/
            
            
            Kubelet должен обнаружить возвращённый манифест и создать контейнер заново.
            3. Проверь результат
               sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
               ps -a | grep kube-apiserver


            Затем проверь API локально:
            kubectl --kubeconfig=/etc/kubernetes/admin.conf \
            get --raw='/readyz?verbose'
                [+]ping ok
                [+]log ok
                [+]etcd ok
                [+]etcd-readiness ok
                [+]informer-sync ok
                [+]poststarthook/start-kube-apiserver-admission-initializer ok
                [+]poststarthook/generic-apiserver-start-informers ok
                [+]poststarthook/priority-and-fairness-config-consumer ok
                [+]poststarthook/priority-and-fairness-filter ok
                [+]poststarthook/storage-object-count-tracker-hook ok
                [+]poststarthook/start-apiextensions-informers ok
                [+]poststarthook/start-apiextensions-controllers ok
                [+]poststarthook/crd-informer-synced ok
                [+]poststarthook/start-service-ip-repair-controllers ok
                [+]poststarthook/rbac/bootstrap-roles ok
                [+]poststarthook/scheduling/bootstrap-system-priority-classes ok
                [+]poststarthook/priority-and-fairness-config-producer ok
                [+]poststarthook/start-system-namespaces-controller ok
                [+]poststarthook/bootstrap-controller ok
                [+]poststarthook/start-cluster-authentication-info-controller ok
                [+]poststarthook/start-kube-apiserver-identity-lease-controller ok
                [+]poststarthook/start-kube-apiserver-identity-lease-garbage-collector ok
                [+]poststarthook/start-legacy-token-tracking-controller ok
                [+]poststarthook/aggregator-reload-proxy-client-cert ok
                [+]poststarthook/start-kube-aggregator-informers ok
                [+]poststarthook/apiservice-registration-controller ok
                [+]poststarthook/apiservice-status-available-controller ok
                [+]poststarthook/kube-apiserver-autoregistration ok
                [+]autoregister-completion ok
                [+]poststarthook/apiservice-openapi-controller ok
                [+]poststarthook/apiservice-openapiv3-controller ok
                [+]poststarthook/apiservice-discovery-controller ok
                [+]shutdown ok
                readyz check passed

                Успешный результат — HTTP-ответ с ok и проверками готовности, завершившимися успешно.
                Но сначала ответь на один важный вопрос: explorer-4 — единственный control-plane-узел в твоём кластере или есть другие? Это определяет безопасный порядок перезапуска etcd.
                
                    да

                Понял: explorer-4 — единственный control-plane-узел.
                Тогда действуем осторожно. Сертификаты уже обновлены, но etcd и API server должны перечитать их.
                Сначала проверь, что etcd работает и его данные доступны. Выполни:
                sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
                logs e297bf8dc62e6 2>&1 | tail -40


                Затем проверь, какие контейнеры работают сейчас:
                sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
                ps | grep -E 'etcd|kube-apiserver'


                Пришли вывод обеих команд. После этого определим, нужно ли перезапускать etcd или достаточно перезапустить только kube-apiserver. Не удаляй данные etcd и не выполняй kubeadm reset.
                    sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \\
                    logs e297bf8dc62e6 2>&1 | tail -40
                    {"level":"warn","ts":"2026-10-12T22:41:11.898591Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:48466","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:41:12.310095Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:48476","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:41:12.878559Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:48490","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:41:17.478942Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:39852","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:41:19.164823Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:39856","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:41:19.294145Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:39858","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:41:24.309501Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"192.168.2.82:36066","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:41:24Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:41:24 WARNING: [core] [Channel #3 SubChannel #5] grpc: addrConn.createTransport failed to connect to {Addr: "192.168.2.82:2379", ServerName: "192.168.2.82:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:42:25.673372Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:54918","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:42:25Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:42:25 WARNING: [core] [Channel #4 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:43:44.317693Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"192.168.2.82:51138","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:43:44Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:43:44 WARNING: [core] [Channel #3 SubChannel #5] grpc: addrConn.createTransport failed to connect to {Addr: "192.168.2.82:2379", ServerName: "192.168.2.82:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:44:28.829885Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:57878","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:44:28Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:44:28 WARNING: [core] [Channel #4 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:45:52.195198Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"192.168.2.82:50118","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:45:52Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:45:52 WARNING: [core] [Channel #3 SubChannel #5] grpc: addrConn.createTransport failed to connect to {Addr: "192.168.2.82:2379", ServerName: "192.168.2.82:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:46:25.052578Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37000","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:25.053271Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:36986","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:25.057267Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37012","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:26.060239Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37036","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:26.062244Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37024","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:26.062436Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37038","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:27.363565Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37042","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:27.534557Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37054","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:27.814881Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37064","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:29.912708Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37080","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:30.318756Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37082","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:30.430863Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:37098","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:34.103182Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:45970","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:34.303119Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:45984","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:34.425519Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:45994","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:39.789861Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:45996","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:40.609156Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:45998","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:40.974171Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:46002","server-name":"","error":"remote error: tls: bad certificate"}
                    {"level":"warn","ts":"2026-10-12T22:46:48.720396Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:43762","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:46:48Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:46:48 WARNING: [core] [Channel #4 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:47:51.799061Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"192.168.2.82:48980","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:47:51Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:47:51 WARNING: [core] [Channel #3 SubChannel #5] grpc: addrConn.createTransport failed to connect to {Addr: "192.168.2.82:2379", ServerName: "192.168.2.82:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    {"level":"warn","ts":"2026-10-12T22:48:31.560506Z","caller":"embed/config_logging.go:170","msg":"rejected connection on client endpoint","remote-addr":"127.0.0.1:38430","server-name":"","error":"tls: failed to verify certificate: x509: certificate has expired or is not yet valid: current time 2026-10-12T22:48:31Z is after 2026-10-04T17:08:23Z"}
                    2026/10/12 22:48:31 WARNING: [core] [Channel #4 SubChannel #6] grpc: addrConn.createTransport failed to connect to {Addr: "127.0.0.1:2379", ServerName: "127.0.0.1:2379", }. Err: connection error: desc = "error reading server preface: remote error: tls: expired certificate"
                    
                    sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \\
                      ps | grep -E 'etcd|kube-apiserver'
                    06784d1aa27d6       f44c6888a2d24       About a minute ago   Running             **kube-apiserver**            0                   b7adaab40aa48       **kube-apiserver**-explorer-4
                    e297bf8dc62e6       a9e7e6b294baf       34 minutes ago       Running             **etcd**                      68                  818a522ea7c76       **etcd**-explorer-4

                    Отлично! Kubernetes API server полностью восстановился. 🎉
                    Главные проверки прошли успешно:
                    - etcd ok и etcd-readiness ok — API server может работать с etcd.
                      - informer-sync ok — синхронизация информеров завершена.
                      - Все проверки poststarthook завершились успешно.
                      - readyz check passed — API server готов принимать запросы.

                    Важно: сертификаты обновлены до октября 2027 года. Чтобы не столкнуться с этой проблемой снова, запланируй проверку сертификатов за несколько недель до истечения срока:
                
                Последний шаг: проверь доступ с Mac
                Теперь на Mac выполни исходную команду:
                После sudo kubeadm certs renew all на explorer-4 сертификаты обновились, но локальный ~/.kube/config на Mac может всё ещё содержать старый клиентский сертификат. Давай проверим это.

                scp explorer@192.168.2.82:/etc/kubernetes/admin.conf ~/git/meteo/docs/kubernetes/admin.conf
                und update all sertificates on mac in ~/.kube/config from admin.conf

                Issue is related to certificate expiration: new pod can't be started because of Контейнер kube-controller-manager запущен 12 октября в 22:21 UTC, а обновлённый сертификат в controller-manager.conf действителен с 12 октября в 22:48 UTC.
                    Это важная зацепка: контейнер запустился примерно на 26 минут раньше обновления сертификата. 
                    Поэтому перезапуск действительно может решить проблему.
                        Logs: sudo crictl \\
                            --runtime-endpoint unix:///run/containerd/containerd.sock \\
                              logs --timestamps 06784d1aa27d6 2>&1 |
                              grep '2026-10-13T08:37'
                            **2026-10-13T08:37**:00.019081979Z E1013 08:37:00.018860       1 authentication.go:73] "Unable to authenticate the request" err="[x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:00Z is after 2026-10-04T17:08:25Z, verifying certificate SN=4258685507280856546, SKID=, AKID=D2:47:CC:A1:CE:B5:E9:D5:50:06:00:A2:5E:A2:E2:E6:E9:C3:C5:B7 failed: x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:00Z is after 2026-10-04T17:08:25Z]"
                            **2026-10-13T08:37**:00.650562097Z E1013 08:37:00.650330       1 authentication.go:73] "Unable to authenticate the request" err="[x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:00Z is after 2026-10-04T17:08:24Z, verifying certificate SN=3721422170803740900, SKID=, AKID=D2:47:CC:A1:CE:B5:E9:D5:50:06:00:A2:5E:A2:E2:E6:E9:C3:C5:B7 failed: x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:00Z is after 2026-10-04T17:08:24Z]"
                            **2026-10-13T08:37**:03.424173232Z E1013 08:37:03.423948       1 authentication.go:73] "Unable to authenticate the request" err="[x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:03Z is after 2026-10-04T17:08:24Z, verifying certificate SN=3721422170803740900, SKID=, AKID=D2:47:CC:A1:CE:B5:E9:D5:50:06:00:A2:5E:A2:E2:E6:E9:C3:C5:B7 failed: x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:03Z is after 2026-10-04T17:08:24Z]"
                            **2026-10-13T08:37**:03.466311106Z E1013 08:37:03.466106       1 authentication.go:73] "Unable to authenticate the request" err="[x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:03Z is after 2026-10-04T17:08:25Z, verifying certificate SN=4258685507280856546, SKID=, AKID=D2:47:CC:A1:CE:B5:E9:D5:50:06:00:A2:5E:A2:E2:E6:E9:C3:C5:B7 failed: x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:03Z is after 2026-10-04T17:08:25Z]"
                            **2026-10-13T08:37**:04.134949534Z E1013 08:37:04.134749       1 authentication.go:73] "Unable to authenticate the request" err="[x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:04Z is after 2026-10-04T17:08:25Z, verifying certificate SN=4258685507280856546, SKID=, AKID=D2:47:CC:A1:CE:B5:E9:D5:50:06:00:A2:5E:A2:E2:E6:E9:C3:C5:B7 failed: x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:04Z is after 2026-10-04T17:08:25Z]"
                            **2026-10-13T08:37**:05.749885158Z E1013 08:37:05.749652       1 authentication.go:73] "Unable to authenticate the request" err="[x509: certificate has expired or is not yet valid: current time **2026-10-13T08:37**:05Z is after 2026-10-04T17:08:25Z, verifying certificate SN=4258685507280856546, SKID=, AK
                    Solution: Restart kube-controller-manager:  
                        sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
                        stop c90713ea1cf20       
                        Подожди 30–60 секунд и найди новый контейнер:
                            sudo crictl --runtime-endpoint unix:///run/containerd/containerd.sock \
                            ps -a | grep kube-controller-manager

                For explorer-4 on which also kubectl commands are used(kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces -o wide):

                    mkdir -p ~/.kube
                    sudo cp /etc/kubernetes/admin.conf ~/.kube/config
                    sudo chown "$(id -u):$(id -g)" ~/.kube/config
                    chmod 600 ~/.kube/config

                Check Scheduler
                sudo crictl ps -a --name kube-scheduler
                    WARN[0000] runtime connect using default endpoints: [unix:///var/run/dockershim.sock unix:///run/containerd/containerd.sock unix:///run/crio/crio.sock unix:///var/run/cri-dockerd.sock]. As the default settings are now deprecated, you should set the endpoint instead.
                    ERRO[0000] validate service connection: validate CRI v1 runtime API for endpoint "unix:///var/run/dockershim.sock": rpc error: code = Unavailable desc = connection error: desc = "transport: Error while dialing: dial unix /var/run/dockershim.sock: connect: no such file or directory"
                    WARN[0000] image connect using default endpoints: [unix:///var/run/dockershim.sock unix:///run/containerd/containerd.sock unix:///run/crio/crio.sock unix:///var/run/cri-dockerd.sock]. As the default settings are now deprecated, you should set the endpoint instead.
                    ERRO[0000] validate service connection: validate CRI v1 image API for endpoint "unix:///var/run/dockershim.sock": rpc error: code = Unavailable desc = connection error: desc = "transport: Error while dialing: dial unix /var/run/dockershim.sock: connect: no such file or directory"
                    CONTAINER           IMAGE               CREATED             STATE               NAME                ATTEMPT             POD ID              POD
                    d19beecd80ddb       9ea0bd82ed4f6       13 hours ago        Running             kube-scheduler      16                  d6944f6f00519       kube-scheduler-explorer-4
                    2f98b53e39be2       9ea0bd82ed4f6       13 hours ago        Exited              kube-scheduler      15                  c84b122903f7f       kube-scheduler-explorer-4

                sudo kubectl --kubeconfig=/etc/kubernetes/admin.conf \
                        logs kube-scheduler-explorer-4 -n kube-system --since=30m
                sudo kubectl --kubeconfig=/etc/kubernetes/admin.conf \
                    logs kube-scheduler-explorer-4 -n kube-system --since=30m
                    W1013 10:33:33.306659       1 reflector.go:539] k8s.io/client-go@v0.0.0/tools/cache/reflector.go:229: failed to list *v1.StatefulSet: Unauthorized
                    E1013 10:33:33.306736       1 reflector.go:147] k8s.io/client-go@v0.0.0/tools/cache/reflector.go:229: Failed to watch *v1.StatefulSet: failed to list *v1.StatefulSet: Unauthorized
                    W1013 10:33:34.251681       1 reflector.go:539] k8s.io/client-go@v0.0.0/tools/cache/reflector.go:229: failed to list *v1.Pod: Unauthorized
                    E1013 10:33:34.251757       1 reflector.go:147] k8s.io/client-go@v0.0.0/tools/cache/reflector.go:229: Failed to watch *v1.Pod: failed to list *v1.Pod: Unauthorized
                    W1013 10:33:34.789037       1 reflector.go:539] k8s.io/client-go@v0.0.0/tools/cache/reflector.go:229: failed to list *v1.Service: Unauthorized
                    E1013 10:33:34.789112       1 reflector.go:147] k8s.io/client-go@v0.0.0/tools/cache/reflector.go:229: Failed to watch *v1.Service: failed to list *v1.Service: Unauthorized

                sudo crictl \
                --runtime-endpoint unix:///run/containerd/containerd.sock \
                stop d19beecd80ddb
                
                Kubelet должен автоматически запустить его заново, используя актуальный конфигурационный файл.
                2. Убедиться, что появился новый контейнер
                   Подожди несколько секунд и выполни:
                   sudo crictl \
                   --runtime-endpoint unix:///run/containerd/containerd.sock \
                   ps --name kube-scheduler

                3. Проверить, исчез ли Unauthorized
                   sudo kubectl --kubeconfig=/etc/kubernetes/admin.conf \
                   logs kube-scheduler-explorer-4 -n kube-system --since=2m
                
                