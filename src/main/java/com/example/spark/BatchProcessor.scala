package com.example.spark

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._

object BatchProcessor {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Batch Data Processor")
      .master("local[*]")
      .getOrCreate()

    val df = spark.read
      .format("jdbc")
      .option("url", "jdbc:postgresql://localhost:5432/mydatabase")
      .option("dbtable", "public.processed_data")
      .option("user", "myuser")
      .option("password", "srikant")
      .load()

    val analyticsDf = df.groupBy("data_length")
      .count()
      .withColumnRenamed("count", "frequency")
      .orderBy(desc("frequency"))

    analyticsDf.write
      .format("jdbc")
      .option("url", "jdbc:postgresql://localhost:5432/mydatabase")
      .option("dbtable", "public.analytics_data")
      .option("user", "myuser")
      .option("password", "srikant")
      .mode("overwrite")
      .save()

    spark.stop()
  }
}
