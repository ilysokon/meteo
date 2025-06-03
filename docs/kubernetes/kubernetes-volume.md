https://kubernetes.io/docs/concepts/storage/volumes/

kubernetes: create volume mapped to the host

To create a Kubernetes volume mapped to the host, you use a hostPath volume. 
This type of volume mounts a directory or file from the host node's filesystem into a pod. 

Here's how you do it:
1. Define the hostPath volume in your Pod manifest:
   Code

apiVersion: v1
kind: Pod
metadata:
 name: myapp
spec:
 containers:
 - name: my-app
  image: nginx
  ports:
    - containerPort: 8080
  volumeMounts:
    - name: my-volume
      mountPath: /app
 volumes:
- name: my-volume
  hostPath:
    path: /mnt/vpath  # Replace with the path on the host node

Explanation:
  apiVersion: Specifies the Kubernetes API version.
  kind: Indicates that this is a Pod object.
  metadata: Contains metadata about the Pod, like its name.
  spec: Describes the desired state of the Pod, including containers and volumes.
  containers: A list of containers within the Pod.
  name: The name of the container.
  image: The Docker image to use for the container.
  ports: Defines ports exposed by the container.
  volumeMounts: A list of volume mounts for the container.
  name: The name of the volume (matching the volume name defined below).
  mountPath: The path inside the container where the volume will be mounted.
  volumes: A list of volumes used by the Pod.
  name: The name of the volume (matching the volume mount name).
  hostPath: The type of volume, specifying that it's a hostPath volume.
  path: Crucially, this field specifies the full path to the directory or file on the host node that will be mounted.

2. Apply the Pod manifest:
   Code
     kubectl apply -f hostpath-volume.yaml
3. Verify the volume is mounted in the Pod:
   Code
     kubectl exec -it myapp -- /bin/bash

Then, navigate to the mountPath you specified (e.g., /app) to see the contents of the host directory.
Key Considerations:
Host Path Accessibility:
Ensure the path you specify on the host is accessible by the Kubernetes node where the Pod will be scheduled and has the correct permissions.
Port Conflicts:
Be mindful of potential port conflicts if the host directory or file is also used by other processes on the host.
Security:
Using hostPath volumes can have security implications if not handled carefully. Ensure you understand the risks and consider appropriate security measures.
Alternatives:
If you need a more flexible and dynamic solution, consider using Persistent Volumes and Claims, which offer more control over storage provisioning.
Shared Storage:
If you need to share storage across multiple pods, hostPath volumes are not the ideal solution. Consider using network-attached storage or other shared storage options. 