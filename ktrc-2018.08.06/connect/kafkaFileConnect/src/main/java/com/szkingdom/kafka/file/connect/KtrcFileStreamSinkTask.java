package com.szkingdom.kafka.file.connect;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

/**
 * @author zhangch
 * @date 2018/7/2 11:11
 */

public class KtrcFileStreamSinkTask extends SinkTask{

    private static final Logger log = LoggerFactory.getLogger(KtrcFileStreamSinkTask.class);

    private String filename;
    private PrintStream outputStream;

    public KtrcFileStreamSinkTask() {
    }

    // for testing
    public KtrcFileStreamSinkTask(PrintStream outputStream) {
        filename = null;
        this.outputStream = outputStream;
    }

    @Override
    public String version() {
        return new KtrcFileStreamSinkConnector().version();
    }

    @Override
    public void start(Map<String, String> props) {
        filename = props.get(KtrcFileStreamSinkConnector.FILE_CONFIG);
        if (filename == null) {
            outputStream = System.out;
        } else {
            try {
                outputStream = new PrintStream(new FileOutputStream(filename, true), false,
                        StandardCharsets.UTF_8.name());
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                throw new ConnectException("Couldn't find or create file for KtrcFileStreamSinkTask", e);
            }
        }
    }

    @Override
    public void put(Collection<SinkRecord> sinkRecords) {
        for (SinkRecord record : sinkRecords) {
            log.trace("Writing line to {}: {}", logFilename(), record.value());
            outputStream.println(record.value());
        }
    }

    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
        log.trace("Flushing output stream for {}", logFilename());
        outputStream.flush();
    }

    @Override
    public void stop() {
        if (outputStream != null && outputStream != System.out){
            outputStream.close();
        }
    }

    private String logFilename() {
        return filename == null ? "stdout" : filename;
    }
}
