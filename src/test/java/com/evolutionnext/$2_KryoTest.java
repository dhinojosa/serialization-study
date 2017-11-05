package com.evolutionnext;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.evolutionnext.kryo.Coach;
import com.evolutionnext.kryo.LocalDateSerializer;
import com.evolutionnext.kryo.Stadium;
import com.evolutionnext.kryo.Team;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class $2_KryoTest {
    private Team team = new Team("Miami Dolphins",
            new Coach("Adam", "Gase",
                    LocalDate.of(1978, 3, 29)),
            new Stadium("Hard Rock Stadium",
                    "Miami Gardens", "FL"));

    private static Kryo kryo = new Kryo();

    @BeforeClass
    public static void beforeAll() {
       kryo.addDefaultSerializer(LocalDate.class, new LocalDateSerializer());
    }

    @Test
    public void testSerializeDeserializeTeam() throws FileNotFoundException, InterruptedException {
        Output output = new Output(new FileOutputStream("target/team.kryo"));
        kryo.writeObject(output, team);
        output.close();

        Thread.sleep(400);

        Input input = new Input(new FileInputStream("target/team.kryo"));
        Team actualTeam = kryo.readObject(input, Team.class);
        input.close();

        assertThat(actualTeam).isEqualTo(team);
        assertThat(actualTeam.getCoach()).isEqualTo(team.getCoach());
        assertThat(actualTeam.getStadium()).isEqualTo(team.getStadium());
        assertThat(actualTeam.getCoach().getBirthDate()).isEqualTo(team.getCoach().getBirthDate());
    }
}
