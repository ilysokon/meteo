access from the application:

To get access to AWS services in a Java project, you need to configure credentials and permissions for the application. Here are the basic steps:

    1. Create an IAM user or use an IAM role for your application to access AWS. Attach policies that grant the necessary permissions.

    2. Configure credentials for the application to assume the identity of the IAM user or role. You can store these in a credentials file or use an IAM role for EC2.

    3. Add the AWS SDK for Java to your project dependencies. This allows your application to make requests to AWS services.

    4. Initialize an AWS client using the default credential provider chain. This searches for credentials in environment variables, Java properties, credential files or an IAM role for EC2.

    5. Make API requests using the client. The SDK will authenticate requests using the credentials.

    6. If using temporary credentials, check for expiration and refresh them before making requests.

    For more details on configuring access control and credentials, refer to the AWS documentation on IAM and the AWS SDK for Java. Let me know if you have any other questions!

Configure AWS Credentials in 

~/.aws/credentials
[default]
ws_access_key_id = your-access-key-id
aws_secret_access_key = your-secret-access-key  

to create access key:
https://us-east-1.console.aws.amazon.com/iam/home#/security_credentials
ws_access_key_id: AKIA47CRYWTM3WVTKMH3
aws_secret_access_key: MgVNs5mJtrcbW4U3LbRZhH9t+gfJkHJLVT40K1Km

Unable to connect to AWS:
The security token included in the request is invalid. (Service: Sts, Status Code: 403, Request ID: e0fb725a-5157-48b2-912e-a3eeef08af9d)
as soon as the ws_access_key_id and aws_secret_access_key be specified in ~/.aws/config the error is gone

Also the possibility to specify 
Set the region by setting the region variable in the shared AWS config file located at
~/.aws/config
.
[default]
region = us-west-2
