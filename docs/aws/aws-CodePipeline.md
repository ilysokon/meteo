There are a few common ways to deploy services to an Amazon EKS cluster:

Use Kubernetes manifest files (YAML/JSON) and kubectl apply:
    kubectl apply -f deployment.yaml
Use a CI/CD tool like AWS CodePipeline to deploy as part of a continuous delivery pipeline. 
    CodePipeline integrates with services like CodeBuild, CodeDeploy, and EKS.
For Docker containers, deploy directly from an Amazon ECR registry by pulling images and running them on EKS nodes.
For Helm charts, use the Helm CLI to deploy charts stored in an S3 bucket or ECR registry.
For serverless workloads, use the AWS CDK or SAM to package and deploy Lambda functions, API Gateway APIs, etc.
The basic process involves preparing your application configuration and pushing it to EKS. 
    Kubernetes will then schedule and run containers across the EKS-managed nodes.

Let me know if you need help setting up any of these deployment methods or have additional questions! EKS aims to make deploying on Kubernetes as simple as possible.
Here are the basic steps to configure an AWS CodePipeline pipeline to deploy to an Amazon EKS cluster:

Create a new pipeline in the CodePipeline console or via AWS CLI/SDK.
Add a source stage that points to your code repository, such as a GitHub or CodeCommit repo.
Add a build stage using AWS CodeBuild to build Docker images from source. Tag and push images to ECR.
Add a deploy stage that deploys to EKS. Use CodeDeploy or the EKS deploy action to pull images from ECR.
Create IAM roles for CodePipeline, CodeBuild, and CodeDeploy with necessary permissions.
Configure a deployment configuration (appspec.yml) for CodeDeploy to deploy to EKS.
Add environment variables for credentials, regions etc and pass to each stage.
Test the pipeline by committing a change. It should build, test and deploy automatically.
Let me know if any part of setting up the CodePipeline workflow needs more details. Proper IAM configuration is important for cross-service communication to succeed.

By default, CodeBuild looks for a file named buildspec.yml in the source code root directory. If your buildspec file uses a different name or location, enter its path from the source root here (for example, buildspec-two.yml or configuration/buildspec.yml).

?
Enter a service role
Enter a service role with CodeDeploy permissions that grants AWS CodeDeploy access to your target instances.