meteo-deployment-56949ddcf7-mmcjk   1/1     Running             0          4d5h
meteo-deployment-749c9c7ccf-p54n4   0/1     ContainerCreating   0          2m43s
meteo-deployment-749c9c7ccf-pzj2j   1/1     Running             0          3m7s
ilysokon@Mac ~ % kubectl rollout status  deployment meteo-deployment -n meteo
Waiting for deployment "meteo-deployment" rollout to finish: 1 old replicas are pending termination...