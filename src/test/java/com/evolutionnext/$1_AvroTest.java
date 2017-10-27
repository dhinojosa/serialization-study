package com.evolutionnext;

import com.evolutionnext.avro.Album;
import com.evolutionnext.avro.Artist;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class $1_AvroTest {

    private File file = new File("artist.avro");

    private Album thriller = new Album("Thriller", 1982);
    private Album bad = new Album("Bad", 1987);
    private Artist artist = new Artist("Michael Jackson",
            "King of Pop", Arrays.asList(thriller, bad));

    @Test
    public void testSerialize() throws IOException {
        boolean success = file.createNewFile();
        if (! success) throw new RuntimeException("File not created");
        
        DatumWriter<Artist> userDatumWriter = new SpecificDatumWriter<>(Artist.class);
        DataFileWriter<Artist> dataFileWriter = new DataFileWriter<>(userDatumWriter);
        dataFileWriter.create(artist.getSchema(), file);
        dataFileWriter.append(artist);
        dataFileWriter.close();
    }

    //Obviously run after serialize
    @Test
    public void testDeserialize() throws IOException {
        DatumReader<Artist> artistDatumReader = new SpecificDatumReader<>(Artist.class);
        DataFileReader<Artist> dataFileReader = new DataFileReader<>(file, artistDatumReader);
        List<Artist> artistList = new ArrayList<>();
        while (dataFileReader.hasNext()) {
            artistList.add(dataFileReader.next());
        }
        assertThat(artistList).hasSize(1).contains(artist);
    }
}
