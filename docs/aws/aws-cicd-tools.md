Some common CI/CD tools supported by AWS include:

CodePipeline: AWS's own fully-managed continuous delivery service that automates build, test and deploy pipelines. It integrates with services like CodeCommit, CodeBuild, CodeDeploy, Lambda etc.
Jenkins: The popular open source automation server. Can be deployed on EC2, ECS, EKS or managed as a service using Elastic Beanstalk. Integrates with many AWS services.
GitHub Actions: GitHub's own CI/CD system. Can build, test and deploy directly from GitHub to AWS services.
GitLab CI/CD: GitLab's integrated CI/CD pipelines. Can be self-hosted on EC2 or managed with GitLab Runner.
CircleCI: Fully-managed continuous integration service. Integrates with CodeCommit, CodeBuild, CodeDeploy and other AWS tools.
Travis CI: Open source CI service. Can deploy to AWS through plugins or scripts.
AWS CodeBuild: Fully-managed build service within AWS to compile source code, run tests and produce deployment artifacts like packages.
So in summary, the major open source CI/CD tools like Jenkins, GitLab and Travis all integrate well with AWS. And AWS provides its own tools like CodePipeline, CodeBuild for automation within AWS services.