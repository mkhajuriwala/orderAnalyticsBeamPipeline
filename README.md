# orderAnalyticsBeamPipeline
Beam pipeline to stream order details from pubSub to BigQuery

java -jar target/orderAnalyticsBeamPipeline-bundled-1.0.jar --inputTopicSubscription="projects/mkloud/subscriptions/orderAnalyticsSubscription" --bigQueryTable="pubSubBigQueryDataSet.OrderDetailsTable" --jobName="OrderAnalyticsStream" --runner=DataflowRunner --gcpTempLocation="gs://mhk-dataflow-bucket/OrderAnalyticsTemp" --region=northamerica-northeast1 --zone=northamerica-northeast1-b

gcloud dataflow flex-template build gs://mhk-dataflow-bucket-1/dataflow/templates/order-analytics-stream-flex-template.json \
--image-gcr-path "northamerica-northeast1-docker.pkg.dev/mkloud/mkloud-artifacts/dataflow/order-analytics:latest" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA11 \
--jar "target/orderAnalyticsBeamPipeline-bundled-1.0.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.kloudbuddy.stream.pipeline.BeamPipeline"


gcloud dataflow flex-template run "order-analytics-`date +%Y%m%d-%H%M%S`" --template-file-gcs-location="gs://mhk-dataflow-bucket-1/dataflow/templates/order-analytics-stream-flex-template.json" --parameters inputTopicSubscription="projects/mkloud/subscriptions/orderAnalyticsSubscription" --parameters bigQueryTable="pubSubBigQueryDataSetUsCentral.OrderDetailsTable" --enable-streaming-engine --service-account-email="dataflow-sa@mkloud.iam.gserviceaccount.com" --staging-location="gs://mhk-dataflow-bucket-1/staging/" --temp-location="gs://mhk-dataflow-bucket-1/tempLocation/" --region="us-central1" 

OrderDetails sample json
{
"orderId": "order_123",
"customerId": "customer_xyz",
"customerAddressId": "customerAddress_abc"
}