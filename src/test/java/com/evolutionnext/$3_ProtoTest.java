package com.evolutionnext;

import com.evolutionnext.proto.AutomobileProtos;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class $3_ProtoTest {

    @Test
    public void testSerializeAndDeserialize() throws IOException, InterruptedException {
        AutomobileProtos.Manufacturer manufacturer = AutomobileProtos
                .Manufacturer
                .newBuilder()
                .setName("Chrysler")
                .setCeo("Sergio Marchionne").build();

        AutomobileProtos.Car chrysler300 = AutomobileProtos.Car
                .newBuilder().setMake(manufacturer).setModel("300")
                .setYear(2017).build();
        AutomobileProtos.Car chryslerPacifica = AutomobileProtos.Car
                .newBuilder().setMake(manufacturer).setModel("Pacifica")
                .setYear(2017).build();
        AutomobileProtos.Car chrysler200 = AutomobileProtos.Car
                .newBuilder().setMake(manufacturer).setModel("200")
                .setYear(2017).build();

        AutomobileProtos.Manufacturer manufacturerOutput =
                manufacturer.toBuilder()
                            .addAllCars(Arrays.asList(chrysler200, chrysler300,
                                    chryslerPacifica)).build();

        FileOutputStream fileOutputStream =
                new FileOutputStream("target/chrysler.bin");
        manufacturerOutput.writeTo(fileOutputStream);
        fileOutputStream.close();


        AutomobileProtos.Manufacturer manufacturerInput = AutomobileProtos
                .Manufacturer
                .parseFrom(new FileInputStream("target/chrysler.bin"));

        assertThat(manufacturerOutput).isEqualTo(manufacturerInput);
        assertThat(manufacturerOutput.getCarsCount()).isEqualTo(3);
    }

    @Test
    public void testDeserializeCollection() throws IOException, InterruptedException {
        AutomobileProtos.Manufacturer chryslerSeed = AutomobileProtos
                .Manufacturer
                .newBuilder()
                .setName("Chrysler")
                .setCeo("Sergio Marchionne").build();

        AutomobileProtos.Car chrysler300 = AutomobileProtos.Car.newBuilder().setMake(chryslerSeed).setModel("300").setYear(2017).build();
        AutomobileProtos.Car chryslerPacifica = AutomobileProtos.Car.newBuilder().setMake(chryslerSeed).setModel("Pacifica").setYear(2017).build();
        AutomobileProtos.Car chrysler200 = AutomobileProtos.Car.newBuilder().setMake(chryslerSeed).setModel("200").setYear(2017).build();

        AutomobileProtos.Manufacturer chrysler = chryslerSeed.toBuilder().addAllCars(Arrays.asList(chrysler200, chrysler300, chryslerPacifica)).build();

        AutomobileProtos.Manufacturer fordSeed = AutomobileProtos
                .Manufacturer
                .newBuilder()
                .setName("Ford")
                .setCeo("James Hackett").build();

        AutomobileProtos.Car taurus = AutomobileProtos.Car.newBuilder().setMake(fordSeed).setModel("Taurus").setYear(2017).build();
        AutomobileProtos.Car fusion  = AutomobileProtos.Car.newBuilder().setMake(fordSeed).setModel("Fusion").setYear(2017).build();
        AutomobileProtos.Car f150 = AutomobileProtos.Car.newBuilder().setMake(fordSeed).setModel("F-150").setYear(2017).build();
        AutomobileProtos.Car f250 = AutomobileProtos.Car.newBuilder().setMake(fordSeed).setModel("F-250").setYear(2017).build();

        AutomobileProtos.Manufacturer ford = fordSeed.toBuilder().addAllCars(Arrays.asList(taurus, fusion, f150, f250)).build();

        AutomobileProtos.Manufacturers beforeManufacturers = AutomobileProtos.Manufacturers.newBuilder().addResult(chrysler).addResult(ford).build();
        FileOutputStream fileOutputStream = new FileOutputStream("target/manufacturers.bin");
        beforeManufacturers.writeTo(fileOutputStream);
        fileOutputStream.close();

        AutomobileProtos.Manufacturers afterManufacturers = AutomobileProtos
                .Manufacturers
                .parseFrom(new FileInputStream("target/manufacturers.bin"));

        assertThat(afterManufacturers.getResultCount()).isEqualTo(2);
        assertThat(afterManufacturers.getResultList().get(1).getCarsList().get(0).getModel()).isEqualTo("Taurus");
    }
}
