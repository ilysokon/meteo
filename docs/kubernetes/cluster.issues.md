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
    

