package com.kloudbuddy.stream.pipeline;

import com.google.api.services.bigquery.model.TableRow;
import com.google.gson.Gson;
import com.kloudbuddy.stream.model.OrderDetails;
import com.kloudbuddy.stream.options.RuntimeOptions;
import com.kloudbuddy.stream.utils.BigQuerySchemaUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

public class BeamPipeline {
    public static void main(String[] args) {
        RuntimeOptions options =
                PipelineOptionsFactory.fromArgs(args).withValidation().as(RuntimeOptions.class);
        Pipeline pipeline = Pipeline.create(options);
        pipeline
                .apply("Read PubSub Messages", PubsubIO.readStrings()
                        .fromSubscription(options.getInputTopicSubscription()))
                .apply(ParDo.of(new DoFn<String, OrderDetails>() {
                    @ProcessElement
                    public void processElement(ProcessContext processContext) {
                        processContext.output(new Gson().fromJson(processContext.element(), OrderDetails.class));
                    }
                }))
                .apply(BigQueryIO.<OrderDetails>write()
                        .to(options.getBigQueryTable())
                        .withSchema(BigQuerySchemaUtils.createSchema())
                        .withFormatFunction((OrderDetails orderDetail) ->
                                new TableRow().set("orderId", orderDetail.getOrderId())
                                        .set("customerId", orderDetail.getCustomerId())
                                        .set("customerAddressId", orderDetail.getCustomerAddressId()))
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));
        pipeline.run();
    }
}
