package com.evolutionnext.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TeamSerializer extends Serializer<Team> {
    @Override
    public void write(Kryo kryo, Output output, Team object) {
       output.writeString(object.getName());
    }

    @Override
    public Team read(Kryo kryo, Input input, Class<Team> type) {
        return new Team(input.readString());
    }
}
