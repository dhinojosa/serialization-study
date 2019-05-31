package com.evolutionnext;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class $7_AvroGenericTest {
    private File file = new File("target/compatible.avro");

    @Test
    public void testSerializeWithAnUpdate() throws InterruptedException, IOException {
        writeRecord();
        Thread.sleep(500);
        readRecord();
    }

    private void writeRecord() throws IOException {
        String schemaString = "{\"type\":\"record\"," +
                "\"name\":\"myrecord\"," +
                "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(schemaString);
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("f1", "value1");

        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(writer);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(avroRecord);
        dataFileWriter.close();
    }

    private void readRecord() throws IOException {
        String schemaString = "{\"type\":\"record\"," +
                "\"name\":\"myrecord\"," +
                "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}," +
                "{\"name\":\"f2\",\"type\":\"string\", \"default\":\"\"}]}";

        Schema.Parser parser = new Schema.Parser();
        DatumReader<GenericRecord> reader = new GenericDatumReader<>(parser.parse(schemaString));
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, reader);
        GenericRecord record = dataFileReader.next();
        assertThat(record.get("f1")).hasToString("value1");
        assertThat(record.get("f2")).hasToString("");
        dataFileReader.close();
    }
}

