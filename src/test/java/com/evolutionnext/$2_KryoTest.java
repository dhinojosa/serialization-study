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
    private Team team = new Team("Miami Dolphins", new Coach("Adam", "Gase"),
            new Stadium("Hard Rock Stadium", "Miami Gardens", "FL"));

    private static Kryo kryo = new Kryo();

    @BeforeClass
    public static void beforeAll() {
       kryo.addDefaultSerializer(LocalDate.class, new LocalDateSerializer());
    }

    @Test
    public void testSerializeDeserializeDate() throws FileNotFoundException, InterruptedException {
        Output output = new Output(new FileOutputStream("target/dates.kryo"));
        LocalDate pre = LocalDate.of(2018, 5, 10);
        kryo.writeObject(output, pre);
        output.close();

        Thread.sleep(400);

        Input input = new Input(new FileInputStream("target/dates.kryo"));
        LocalDate post = kryo.readObject(input, LocalDate.class);
        input.close();

        assertThat(pre).isEqualTo(post);
    }

    @Test
    public void testSerializeDeserialize() throws FileNotFoundException, InterruptedException {
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
    }
}
