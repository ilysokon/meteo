scp username@host.com:/dir/of/file.txt \local\dir\file.txt

ssh explorer@explorer-4

# copy file from pod to local
kubectl cp <namespace>/<pod-name>:<path/to/remote/file> <local/destination/path>
kubectl cp vault/vault-0:/etc/hosts /etc/hosts