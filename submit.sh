SPARK_SUBMIT=/home/reins/zzt/spark-3.1.3-bin-hadoop2.7/bin/spark-submit
JAR=/home/reins/zzt/code/spark-into-kafka/target/spark-into-kafka-1.0-SNAPSHOT.jar
CLASS=com.reins.DumpLog2Kafka
# ARG1=/home/reins/zzt/code/spark-into-kafka/src/main/resources/access.log

$SPARK_SUBMIT \
--packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.1.2 \
--class $CLASS \
--master spark://reins-PowerEdge-R740-0:7077 \
--deploy-mode cluster \
--executor-memory 1G \
--num-executors 10 \
$JAR
# $ARG1
