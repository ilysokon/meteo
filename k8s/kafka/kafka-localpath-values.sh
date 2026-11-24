 #!/usr/bin/env bash
  cat > kafka-localpath-values.yaml <<'EOF'
  storageClass:
    create: true
    name: kafka-localpath
    provisionerName: rancher.io/kafka-localpath
    defaultClass: false
    defaultVolumeType: hostPath
    reclaimPolicy: Retain
    volumeBindingMode: WaitForFirstConsumer

  nodePathMap:
    - node: explorer-4
      paths: []
    - node: explorer-6
      paths:
        - /var/lib/kafka-data
    - node: explorer-7
      paths:
        - /var/lib/kafka-data
    EOF