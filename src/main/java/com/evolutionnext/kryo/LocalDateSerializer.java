package com.evolutionnext.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.time.LocalDate;

public class LocalDateSerializer extends Serializer<LocalDate> {
    @Override
    public void write(Kryo kryo, Output output, LocalDate object) {
       output.writeInt(object.getYear());
       output.writeInt(object.getMonthValue());
       output.writeInt(object.getDayOfMonth());
    }

    @Override
    public LocalDate read(Kryo kryo, Input input, Class<LocalDate> type) {
        return LocalDate.of(input.readInt(), input.readInt(),
                            input.readInt());
    }
}
