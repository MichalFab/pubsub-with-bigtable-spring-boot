# GCP PubSub publisher/subscriber with data inserting to BigTable

### Prerequisites

* Google Cloud SDK installed - [Documentation](https://cloud.google.com/sdk/docs/quickstart)
* Signing in to the project in GCP via Google Cloud SDK
* Set the GOOGLE_APPLICATION_CREDENTIALS environment variable with the credentials.json file location (C:\Users\"yourusername"\AppData\Roaming\gcloud\legacy_credentials\"youremail")

### Usage
1. Set proper configuration data:

Publisher module:

`spring.cloud.gcp.project-id=<your-gcp-project-id>`

 `spring.cloud.gcp.credentials.location=classpath:credentials.json`
 
Subscriber module:

`server.port=8081`

`spring.cloud.gcp.project-id=<your-gcp-project-id>`

 `spring.cloud.gcp.credentials.location=classpath:credentials.json`
 
 `gcp.bigtable.projectId=<your-gcp-project-id>`
 
 `gcp.bigtable.instanceId=<instance id of your GCP BigTable instance>` 
 
 2. Run publisher and Subscriber application
 
 3. Send POST request to publisher application:
 `curl --data "message=Hello world gcp!" localhost:8080/postMessage`
 The publisher application will send a message to the subscriber via PubSub, and then it will be inserted in the BigTable.
 