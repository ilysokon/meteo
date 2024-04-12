Here are the basic steps to create an EC2 t2.micro instance: [1]

Sign in to the AWS Management Console and open the EC2 console at https://console.aws.amazon.com/ec2/.
Choose "Launch Instance".
Select Amazon Linux 2 AMI (64-bit x86) from the list of quick start images.
On the "Choose an Instance Type" page, select t2.micro from the instance type list.
Click "Next: Configure Instance Details" and keep the default settings.
On the "Add Storage" page, keep the default EBS volume size of 8 GiB.
On the "Add Tags" page, you can optionally add tags like Name and Project.
Click "Next: Configure Security Group" and create a new security group with port 22 open for SSH access.
Click "Review and Launch" and verify your settings are correct.
Select "Launch" to create the instance.
Select an existing key pair or create a new one, then click "Launch Instances".
You can now see the new instance in the EC2 console while it launches and starts. Allow a few minutes for the instance to be fully ready.

Two EC2 Instances:
ssh -i "geometeo2.pem" ec2-user@ec2-13-60-40-87.eu-north-1.compute.amazonaws.com
ssh -i "geometeo2.pem" ec2-user@ec2-16-171-15-23.eu-north-1.compute.amazonaws.com