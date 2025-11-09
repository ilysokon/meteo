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
      kubeamd init
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
            