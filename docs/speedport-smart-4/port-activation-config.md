geometeo-4
   host: explorer-4(192.168.2.82 Kubernetes Master Node)
    port: TCP: 6443 to 6443 // kubernetes --> !!! this port is need to be used in ~/.kube/config: geometeo.ddns.net:6443
    port: TCP: 27 to 22     // ssh
        ssh explorer@geometeo.ddns.net -p 27
geometeo-6
    host: explorer-6
    port: TCP: 6448 to 6443 // kubernetes
    port: TCP: 28 to 22     // ssh
        ssh explorer@geometeo.ddns.net -p 28
geometeo-7
    host: explorer-7
    port: TCP: 6449 to 6443 // kubernetes
    port: TCP: 29 to 22     // ssh
        ssh explorer@geometeo.ddns.net -p 29
    