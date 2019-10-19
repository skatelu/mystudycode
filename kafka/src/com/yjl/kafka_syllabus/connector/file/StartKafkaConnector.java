package com.yjl.kafka_syllabus.connector.file;

import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;

public class StartKafkaConnector {

    public static void main(String[] args) throws Exception {
        FileStreamSourceTask streamSourceTask = new FileStreamSourceTask();
        List<SourceRecord> poll = streamSourceTask.poll();

        FileStreamSinkTask sinkTask = new FileStreamSinkTask();

    }

}
