package com.evolutionnext;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.evolutionnext.kryo.Coach;
import com.evolutionnext.kryo.Stadium;
import com.evolutionnext.kryo.Team;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class $2_KryoTest {
    private Team team = new Team("Miami Dolphins", new Coach("Adam", "Gase",
            LocalDate.of(1978, 3, 29)),
            new Stadium("Hard Rock Stadium", "Miami Gardens", "FL"));

    @Test
    public void testSerialize() throws FileNotFoundException {
        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream("target/team.kryo"));
        kryo.writeObject(output, team);
        output.close();
    }

    @Test
    public void testDeserialize() throws FileNotFoundException {
        Kryo kryo = new Kryo();
        Input input = new Input(new FileInputStream("file.bin"));
        Team actualTeam = kryo.readObject(input, Team.class);
        input.close();

        assertThat(actualTeam).isEqualTo(team);
        assertThat(actualTeam.getCoach()).isEqualTo(team.getCoach());
        assertThat(actualTeam.getStadium()).isEqualTo(team.getStadium());
    }
}
