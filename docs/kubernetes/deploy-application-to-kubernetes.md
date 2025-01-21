https://dev.to/kitarp29/deploy-your-first-java-application-on-k8s-14ke



## 3. **Building the Docker Image**:

Run this command in the same directory:
docker build -t meteo .

Your Docker Image is built and saved on your local. Verify by running this command:

```
docker images
```

## 4. **Pushing the Image**

Create a Tag for the Image you created in the last step using this command:
docker tag meteo:latest ilysokon/meteo:latest

Push this Image to the image repository:

docker push ilysokon/meteo:latest

# Deploy applicatopion to kubernnetes cluster

kubectl apply -f deployment.yaml -n meteo
deployment.apps/meteo-deployment configured

kubectl describe deployment.apps/meteo-deployment -n meteo

kubectl get pods -n meteo
NAME                                READY   STATUS    RESTARTS   AGE
meteo-deployment-68c776cdd6-2wn5b   1/1     Running   0          19m
meteo-deployment-68c776cdd6-tnlf9   1/1     Running   0          20m

kubectl logs -f meteo-deployment-68c776cdd6-2wn5b -n meteo

21:27:40.814 [scheduled-executor-thread-3] INFO  com.meteo.geometeo.GeometeoJob - Simple Job every 10 seconds: 21/1/2025 09:27:40
21:27:40.814 [scheduled-executor-thread-3] INFO  c.meteo.netatmo.NetatmoMeteoService - Gathering Netatmo meteo data
21:27:40.814 [scheduled-executor-thread-3] INFO  c.m.c.CassandraPersistenceService - Geometeo: com.meteo.core.model.Geometeo@4335a250 is persisted in Cassandra
