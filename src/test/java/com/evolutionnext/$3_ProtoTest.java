package com.evolutionnext;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.evolutionnext.proto.AddressBookProtos;

import static org.assertj.core.api.Assertions.assertThat;

public class $3_ProtoTest {
    //AddressBookProtos.Person.PhoneType.HOME
    @Test
    public void testSerializeAndDeserialize() throws IOException, InterruptedException {
        AddressBookProtos.Person.PhoneNumber address =
                com.evolutionnext.proto.AddressBookProtos.Person.PhoneNumber.newBuilder()
                                                                            .setNumber("+351 999 999 999")
                                                                            .setType(AddressBookProtos.Person.PhoneType.HOME)
                                                                            .build();

        com.evolutionnext.proto.AddressBookProtos.Person pre =
                com.evolutionnext.proto.AddressBookProtos.Person.newBuilder()
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
