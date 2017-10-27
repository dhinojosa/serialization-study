package com.evolutionnext;

import com.evolutionnext.avro.Album;
import com.evolutionnext.avro.Artist;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class $5_KafkaWithAvroTest {


    @Test
    public void testSend() throws ExecutionException, InterruptedException, IOException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kaf0:9092,kaf1:9092,kaf2:9092"); //At least 2
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.ByteArraySerializer");

        String userSchema = "{\"type\":\"record\"," +
                "\"name\":\"myrecord\"," +
                "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);

        avroRecord.put("f1", "value1");

        SpecificDatumWriter<GenericRecord> writer = new SpecificDatumWriter<>(schema);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(avroRecord, encoder);
        encoder.flush();

        KafkaProducer<String, Object> producer =
                new KafkaProducer<>(properties);
        ProducerRecord<String, Object> producerRecord =
                new ProducerRecord<>("scaled-avro", out.toByteArray());
        Future<RecordMetadata> future = producer.send(producerRecord);
        producer.flush();
        RecordMetadata recordMetadata = future.get();  //blocks
        System.out.format("offset: %d\n", recordMetadata.offset());
        System.out.format("partition: %d\n", recordMetadata.partition());
        System.out.format("timestamp: %d\n", recordMetadata.timestamp());
        System.out.format("topic: %s\n", recordMetadata.topic());
        System.out.format("toString: %s\n", recordMetadata.toString());
    }
}

