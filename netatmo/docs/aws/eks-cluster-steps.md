To set up containerd on managed EKS worker nodes, you would follow these basic steps: [1]

        1. Create an EKS cluster with managed node groups using the AWS Management Console, AWS CLI, or an EKS control plane SDK like the AWS CDK. [2]
        2. When creating the managed node group, specify the EKS-optimized AMI for the operating system of your choice (Amazon Linux 2, Ubuntu, etc). These AMIs come pre-installed with containerd. [3]
        3. No additional configuration is needed on the nodes themselves. EKS will automatically configure containerd as the CRI runtime on the managed nodes during launch.
        4. You can verify containerd is running as expected by checking the kubelet log on any node for messages about registering with the CRI runtime, or by using the crictl command line tool to list containers after deploying a pod.


Create EKS Cluster:
1. Configure Cluster
    name: meteo
    Kubernetes version: 1.28
        Kubernetes version 1.28 reaches the end of standard support on November 2024. 
        If you don't update your cluster to a later version before that date, it will automatically enter extended support. After the extended support preview ends, clusters on versions in extended support will be subject to additional fees.
    Cluster service role:
       Info: 
        For an EKS cluster, the cluster service role determines what permissions are granted to Kubernetes control plane components like the API server and kubelet.
        The best practice role to use is the eksServiceRole provided by AWS. This role has a minimum set of permissions required to manage AWS resources needed by EKS such as EC2 instances, security groups, EBS volumes etc.
        When creating an EKS cluster, you can specify this role ARN either through the AWS CLI or console. For example:

        eksctl create cluster \
        --name my-cluster \
        --region us-east-1 \  
        --cluster-service-role-arn arn:aws:iam::123456789012:role/eksServiceRole
        The eksServiceRole allows EKS to automatically manage the lifecycle of nodes and attach necessary IAM policies. This simplifies operations by not requiring manual IAM policy updates each time a new resource type is added.
        
        Alternatively, you can create a custom IAM role with only the required permissions. 
        But the eksServiceRole ensures all permissions needed for basic cluster functionality out of the box.
        
        To create your Amazon EKS cluster role in the IAM console
            Open the IAM console at https://console.aws.amazon.com/iam/.
            Choose Roles, then Create role.
            Under Trusted entity type, select AWS service.
            From the Use cases for other AWS services dropdown list, choose EKS.
            Choose EKS - Cluster for your use case, and then choose Next.
            On the Add permissions tab, choose Next.
            For Role name, enter a unique name for your role, such as eksClusterRole.
            For Description, enter descriptive text such as Amazon EKS - Cluster role.
            Choose Create role.
    Created in Ohio(us-east-2)