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
2.
Hello Igor,

I hope this email finds you well!

Since assisting you is my highest priority, I've tried to reach you on the phone number that we have associated to your AWS account in order to provide you with personal assistance and clarify your doubts, but unfortunately, 
I couldn't establish a connection with you as the call was routed to a voice mail box. However, 
I will be assisting over this email.

From the case notes, I understand that your use case is to create an EKS cluster with a EC2 t2.micro instance which is covered under the Free Tier promotion as per suggestion by Amazon Q, however you are unable to see the option to choose EC2 t2.micro instance as a worker nodes. 
I'm really sorry for any inconvenience this might have caused you and I'll do my best to help you with this.

I have carefully reviewed your inquiry and it is a bit outside our scope. Here in basic support, we handle Billing and Account inquiries and our area of expertise does not involve direct technical support, as we are not AWS-certified engineers like our colleagues from Premium Support.

The level of knowledge we have on the platform is towards the billing dashboard, however even though I am not on the technical side I want to make sure you still get the help you need, so I've thoroughly reviewed the AWS Documentations, I have found a relevant to the query which I believe you will find useful and I will include them below:

● https://docs.aws.amazon.com/eks/latest/userguide/create-managed-node-group.html
● https://repost.aws/knowledge-center/eks-worker-nodes-cluster

Having said that I understand how important it is for you to get the right assistance at the earliest for your issue and I recommend checking out AWS re:Post. This question may have already been answered, and if not, you can post it to the community of fellow users and our own developers who monitor questions.

● https://www.repost.aws/

Alternatively, if the above steps still don’t resolve your query, you may want to consider Premium Support, which gives you a direct line to our engineers, who can help you troubleshoot most technical issues.

● http://aws.amazon.com/premiumsupport

I believe you'll find this information helpful. Please feel free to contact us again if you have any further questions and I will be more than happy to help you.

For immediate assistance, you can also initiate a chat:

1. Please enable pop-ups from your browser settings to allow the chat window to open successfully.

2. Click “Reply” from this case.

3. Select “Chat” from the contact method options.

4. Click “Submit” and we will assist you straight away.

Wishing you a wonderful day!

We value your feedback. Please share your experience by rating this and other correspondences in the AWS Support Center. You can rate a correspondence by selecting the stars in the top right corner of the correspondence.


To create a t2.micro EC2 instance in a VPC, follow these steps:

    1. Create a VPC with an IPv4 CIDR block and enable DNS support.
    2. Create subnets within the VPC for the availability zones you want to launch instances into. Assign the subnets IPv4 CIDR blocks that fall within the VPC's CIDR range.
    3. Create a security group and define ingress rules to allow traffic to the instance as needed.
    4. Launch the t2.micro instance into one of the subnets. Select the VPC and subnet created earlier during instance launch.
    5. Assign the instance to the security group created in step 3.
    6. Configure any other launch options as needed, such as an IAM role.
    7. Launch the instance. It will now be accessible on the VPC network based on the subnet and security group configuration.
    The key aspects are creating a VPC, subnets, and security group prior to launching the EC2 instance. Select these VPC network resources during instance launch to place the t2.micro in your custom VPC network. Refer to the VPC and EC2 sections in the AWS documentation for more details on these steps.