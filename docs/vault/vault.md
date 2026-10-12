kubectl exec --stdin --tty vault-0  -n meteo -- /bin/sh

## copy from vault-0 pod the file token from /var/run/secrets/kubernetes.io/serviceaccount/
kubectl cp <namespace>/<pod-name>:<path/to/remote/file> <local/destination/path>
kubectl cp vault/vault-0:/var/run/secrets/kubernetes.io/serviceaccount/token k8s/vault --> token is not a file but a simalink

Mac:k8s ilysokon$ kubectl exec -n vault vault-0 -- ls -l /var/run/secrets/kubernetes.io/serviceaccount/
total 0
lrwxrwxrwx    1 root     vault           13 Dec 10  2025 ca.crt -> ..data/ca.crt
lrwxrwxrwx    1 root     vault           16 Dec 10  2025 namespace -> ..data/namespace
lrwxrwxrwx    1 root     vault           12 Dec 10  2025 token -> ..data/token

# copy token from vault-0 pod to local file: ./vault/token
kubectl cp vault/vault-0:var/run/secrets/kubernetes.io/serviceaccount/..data/token ./vault/token

# Vault default port 8200
kubectl port-forward vault-0 -n vault 8200:8200
    curl http://localhost:8200/v1/sys/health
    {"initialized":true,"sealed":false,"standby":false,"performance_standby":false,"replication_performance_mode":"disabled","replication_dr_mode":"disabled","server_time_utc":1781628145,"version":"1.20.4","enterprise":false,"cluster_name":"vault-cluster-c40171c0","cluster_id":"e57f88b5-caa2-4ee9-1fa9-60a62c2e9440","echo_duration_ms":0,"clock_skew_ms":0,"replication_primary_canary_age_ms":0,"removed_from_cluster":false}

from vault-0 pod:
vault read auth/kubernetes/role/netatmo-secret-writer
    Key                                         Value
    ---                                         -----
    alias_name_source                           serviceaccount_uid
    audience                                    https://kubernetes.default.svc.cluster.local
    bound_service_account_names                 [netatmo-refresher]
    bound_service_account_namespace_selector    n/a
    bound_service_account_namespaces            [meteo]
    policies                                    [netatmo-refresher-policy]
    token_bound_cidrs                           []
    token_explicit_max_ttl                      0s
    token_max_ttl                               0s
    token_no_default_policy                     false
    token_num_uses                              0
    token_period                                0s
    token_policies                              [netatmo-refresher-policy]
    token_ttl                                   1h
    token_type                                  default
    ttl                                         1h

vault list auth/kubernetes/role
    Keys
    ----
    netatmo-secret-writer
    / $ vault read auth/kubernetes/role/netatmo-secret-writer
    Key                                         Value
    ---                                         -----
    alias_name_source                           serviceaccount_uid
    audience                                    https://kubernetes.default.svc.cluster.local
    bound_service_account_names                 [netatmo-refresher]
    bound_service_account_namespace_selector    n/a
    bound_service_account_namespaces            [meteo]
    policies                                    [netatmo-refresher-policy]
    token_bound_cidrs                           []
    token_explicit_max_ttl                      0s
    token_max_ttl                               0s
    token_no_default_policy                     false
    token_num_uses                              0
    token_period                                0s
    token_policies                              [netatmo-refresher-policy]
    token_ttl                                   1h
    token_type                                  default
    ttl                                         1h
    / $ cat /vault/config/*.hcl
    ui = true
    
    listener "tcp" {
    tls_disable = 1
    address = "[::]:8200"
    cluster_address = "[::]:8201"
    # Enable unauthenticated metrics access (necessary for Prometheus Operator)
    #telemetry {
    #  unauthenticated_metrics_access = "true"
    #}
    }
    
    storage "raft" {
    path = "/vault/data"
    }
    
    service_registration "kubernetes" {}
    
    disable_mlock = true

Why this happened (common pattern)

    127.0.0.1 is usually correct only when:
    
    using kubectl port-forward
    running app locally on your laptop
    
    But NOT inside Kubernetes pods
    
    ✅ Final diagnosis
    
    You currently have 2 separate issues:
    
    1. Vault connection issue (your SSL error)
    
    ✔ caused by VAULT_ADDR=http://127.0.0.1:8200
    
    2. Kubernetes auth issue (your 403 earlier)
    
    ✔ separate issue (service account mismatch)
    
    🚀 Fix order
    Fix VAULT_ADDR → point to vault.vault.svc:8200
    Restart your app pod
    Retry login
    Then re-check Kubernetes role binding
    
    If you want, I can help you fix the 403 service account mismatch next, but first you need to fix this networking layer or everything else will keep failing.