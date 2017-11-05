package com.evolutionnext;

import com.evolutionnext.colfer.datebook.Appointment;
import com.evolutionnext.colfer.datebook.Asset;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class $4_ColferTest {
    private static File file = null;

    @BeforeClass
    public static void beforeClass() {
        file = new File("target/apptbin");
    }

    @Test
    public void testSerializeDeserialize() throws IOException, InterruptedException {
        Asset asset = new Asset();
        asset.setName("Room A");
        asset.setAssetType("Room");

        Appointment appointment = new Appointment();
        appointment.setAsset(asset);
        appointment.setCancelled(false);
        Instant reservationInstant = ZonedDateTime.of(2017,
                4, 12,
                9, 0, 0, 0,
                ZoneId.of("America/Chicago")).toInstant();
        appointment.setDatetime(reservationInstant);


        FileOutputStream fileOutputStream = new FileOutputStream(file);
        appointment.marshal(fileOutputStream, null); //null provides max buffer

        Thread.sleep(500);

        Appointment.Unmarshaller unmarshaller =
                new Appointment.Unmarshaller(new FileInputStream(file), null);

        Appointment actual = unmarshaller.next();
        assertThat(actual).isEqualTo(appointment);
        assertThat(actual.getAsset().getName()).isEqualTo("Room A");
    }
}
