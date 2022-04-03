package com.reins;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.types.DataTypes;

public class DumpLog2Kafka {
    public static void main(String[] args) {
        // Load the text file into Spark.
        // if (args.length != 4) {
        // System.out.println("arguments size error." + args.length);
        // return;
        // }
        SparkSession spark = SparkSession.builder()
                .config("spark.master", "local")
                .getOrCreate();
        spark.sparkContext().setLogLevel("ERROR");
        StructType schema = new StructType()
                .add("taxiId", DataTypes.LongType)
                .add("tripId", DataTypes.StringType)
                .add("timestamp", DataTypes.LongType)
                .add("longtitude", DataTypes.DoubleType)
                .add("latitude", DataTypes.DoubleType)
                .add("speed", DataTypes.DoubleType);
        Dataset<Row> rawData = spark
                .readStream()
                .option("header", true)
                .format("csv")
                .schema(schema)
                .csv("/home/reins/zzt/code/spark-into-kafka/data");
        // rawData.createOrReplaceTempView("logs");
        // Dataset<Row> result = spark.sql("select count(*) from logs");
        try {
            // StreamingQuery query =
            // result.writeStream().outputMode(OutputMode.Update()).format("console").start();
            StreamingQuery query = rawData
                    .selectExpr("CAST(timestamp AS STRING) AS key", "to_json(struct(*)) AS value")
                    .writeStream()
                    .format("kafka")
                    .outputMode("append")
                    .option("kafka.bootstrap.servers", "localhost:9092")
                    .option("topic", "taxi_log")
                    .option("checkpointLocation", "/home/reins/zzt/code/spark-into-kafka/ckpt")
                    .start();
            query.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
        spark.stop();
    }
}