# orderAnalyticsBeamPipeline
Beam pipeline to stream order details from pubSub to BigQuery

# Run from command line or IDE
java -jar target/orderAnalyticsBeamPipeline-bundled-1.0.jar --inputTopicSubscription="<<InputTopic>>" --bigQueryTable="<<BigQueryTable>>" --jobName="OrderAnalyticsStream" --runner=DataflowRunner --gcpTempLocation="<<TempLocation>>" --region=<<Region>> --zone=<<Worker Zone>>

# Build flex template
gcloud dataflow flex-template build gs://<<GCP BUCKET>>/dataflow/templates/order-analytics-stream-flex-template.json \
--image-gcr-path "<<Image path>>/order-analytics:latest" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA11 \
--jar "target/orderAnalyticsBeamPipeline-bundled-1.0.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.kloudbuddy.stream.pipeline.BeamPipeline"

# Run dataflow using flex template
gcloud dataflow flex-template run "order-analytics-`date +%Y%m%d-%H%M%S`" --template-file-gcs-location="<<BUCKET LOCATION>>/order-analytics-stream-flex-template.json" --parameters inputTopicSubscription="<<InputTopic>>" --parameters bigQueryTable="<<BigQueryTable>>" --enable-streaming-engine --service-account-email="<<ServiceAccount>>" --staging-location="<<Staging Location>>/staging/" --temp-location="<<Temp Location>>/tempLocation/" --region="<<Region>>" 

OrderDetails sample json
{
"orderId": "order_123",
"customerId": "customer_xyz",
"customerAddressId": "customerAddress_abc"
}
