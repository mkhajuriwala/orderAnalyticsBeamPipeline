package com.kloudbuddy.stream.options;

import jdk.jfr.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

public interface RuntimeOptions extends PipelineOptions, StreamingOptions {

    @Description("The Cloud Pub/Sub topic to read from.")
    @Validation.Required
    public String getInputTopicSubscription();
    void setInputTopicSubscription(String value);

    @Description("The BigQuery Table to insert data into")
    @Validation.Required
    public String getBigQueryTable();
    void setBigQueryTable(String value);

}
