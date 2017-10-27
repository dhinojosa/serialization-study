package com.evolutionnext;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.serializer.KryoRegistrator;
import org.junit.Test;

import java.util.Arrays;


//Source: https://github.com/holdenk/learning-spark-examples/blob/master/src/main/java/com/oreilly/learningsparkexamples/java/BasicAvgWithKryo.java
public class $6_SparkWithKryoTest {

    static class AvgCount implements java.io.Serializable {
        public AvgCount() {
            total_ = 0;
            num_ = 0;
        }

        public AvgCount(int total, int num) {
            total_ = total;
            num_ = num;
        }

        public float avg() {
            return total_ / (float) num_;
        }

        public int total_;
        public int num_;
    }

    public static class AvgRegistrator implements KryoRegistrator {
        public void registerClasses(Kryo kryo) {
            kryo.register(AvgCount.class, new FieldSerializer(kryo, AvgCount.class));
        }
    }

    @Test
    public void testProcessData() throws InterruptedException {
        String master = "local[*]";

        SparkConf conf = new SparkConf().setMaster(master).setAppName("basicavgwithkyro");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        conf.set("spark.kryo.registrator", AvgRegistrator.class.getName());
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
        Function2<AvgCount, Integer, AvgCount> addAndCount = (Function2<AvgCount, Integer, AvgCount>) (a, x) -> {
            a.total_ += x;
            a.num_ += 1;
            return a;
        };
        Function2<AvgCount, AvgCount, AvgCount> combine = (Function2<AvgCount, AvgCount, AvgCount>) (a, b) -> {
            a.total_ += b.total_;
            a.num_ += b.num_;
            return a;
        };
        AvgCount initial = new AvgCount(0, 0);
        AvgCount result = rdd.aggregate(initial, addAndCount, combine);
        System.out.println(result.avg());


        Thread.sleep(3 * 60 * 1000);
    }
}

