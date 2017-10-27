package com.evolutionnext;

import com.evolutionnext.proto.AddressBookProtos;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class $3_ProtoTest {
    @Test
    public void testSerializeAndDeserialize() throws IOException, InterruptedException {
        AddressBookProtos.Person.PhoneNumber address =
                AddressBookProtos.Person.PhoneNumber.newBuilder()
                                                    .setNumber("+351 999 999 999")
                                                    .setType(AddressBookProtos.Person.PhoneType.HOME)
                                                    .build();

        AddressBookProtos.Person pre =
                AddressBookProtos.Person.newBuilder()
                                        .setId(12345)
                                        .setName("Wild Belle")
                                        .setEmail("wb@aol.com")
                                        .addPhones(address)
                                        .build();


        FileOutputStream aOutput = new FileOutputStream("target/person.probin");
        pre.writeTo(aOutput);
        aOutput.close();

        Thread.sleep(500);
        AddressBookProtos.Person post =
                AddressBookProtos.Person.parseFrom(
                        new FileInputStream("target/person.probin"));

        assertThat(pre).isEqualTo(post);
    }
}
