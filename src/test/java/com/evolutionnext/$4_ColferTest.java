package com.evolutionnext;

import com.evolutionnext.colfer.demo.Course;
import com.evolutionnext.colfer.demo.Hole;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class $4_ColferTest {
    private static File file = null;

    @BeforeClass
    public static void beforeClass() {
        file = new File("target/golfbin");
    }


    @Test
    public void testSerializeDeserialize() throws IOException, InterruptedException {
        Hole hole1 = new Hole();
        hole1.lat = 123.0;
        hole1.lon = 401.0;
        hole1.par = 2;
        hole1.sand = false;
        hole1.water = false;

        Hole hole2 = new Hole();
        hole2.lat = 124.0;
        hole2.lon = 400.0;
        hole2.par = 2;
        hole2.sand = false;
        hole2.water = true;

        Course beforeCourse = new Course();
        beforeCourse.name = "Old Course at St. Andrews";
        beforeCourse.ID = 4091L;
        beforeCourse.tags = new String[]{"Scotland", "St Andrews", "Home of Golf"};
        beforeCourse.holes = new Hole[]{hole1, hole2};

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        beforeCourse.marshal(fileOutputStream, null); //null provides max buffer

        Thread.sleep(500);

        Course.Unmarshaller unmarshaller =
                new Course.Unmarshaller(new FileInputStream(file), null);

        Course afterCourse = unmarshaller.next();
        assertThat(beforeCourse).isEqualTo(afterCourse);
    }
}
