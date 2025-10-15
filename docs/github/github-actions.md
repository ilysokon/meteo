Error: HTTP Status 403 for request POST https://api.github.com/repos/ilysokon/meteo/dependency-graph/snapshots
Error: Response body:
{
"message": "Resource not accessible by integration",
"documentation_url": "https://docs.github.com/rest/dependency-graph/dependency-submission#create-a-snapshot-of-dependencies-for-a-repository",
"status": "403"
}

Fix: 
    https://docs.github.com/en/code-security/supply-chain-security/understanding-your-software-supply-chain/configuring-automatic-dependency-submission-for-your-repository

on host explorer-4 run:
    base64 -w 0 ~/.kube/config
and the output content to put to KUBE_CONFIG_DATA variable of Actions secrets and variables on github: https://github.com/ilysokon/meteo/settings/secrets/actions

After the steps described in docs/kubernetes/access-kubernetes-api-remotely.md were done and
    in ~/.kube/config the server was set to: https://geometeo.ddns.net:6447 and step above was related to KUBE_CONFIG_DATA was repeated
        the deploy to kubernetes action on github.con: https://github.com/ilysokon/meteo/actions/runs/13664627688/job/38402954467 started working
    
