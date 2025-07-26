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
