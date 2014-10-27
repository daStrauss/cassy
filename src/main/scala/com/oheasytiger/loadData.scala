package com.oheasytiger
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector._



object loadData {
	def main(args: Array[String]) {

		val lnz = scala.io.Source.fromFile(System.getenv("HOME") + "/newAwsk").getLines
			.map(y=>y.split("="))
			.map(y=>(y(0),y(1)))
			.toMap

		val key = lnz.getOrElse("id","foo")
		val sec = lnz.getOrElse("secret","foo")
		println("key: %s, sec: %s".format(key,sec))

		val conf = new SparkConf(true)
			.setAppName("trials")
			.set("spark.executor.uri","http://d3kbcqa49mib13.cloudfront.net/spark-1.1.0-bin-cdh4.tgz")
   			.set("spark.cassandra.connection.host", "ec2-54-234-96-185.compute-1.amazonaws.com")
		println("foo")

		val sc = new SparkContext(conf)
		val dta = sc.cassandraTable("dstrauss","dmx")
		println(dta.first)

		val cln = sc.textFile("s3n://" + key + ":" + sec + "@usedTaCould/pagecounts-20141001-000000.gz")
		println("counts count %s".format(cln.count))

		case class rwx(domain: String, page : String, accum_time: String, count: Int)
		val tzr = cln.map(y=>y.split(" ")).map(y=>rwx(y(0),y(1), "2014-10-01 00:00:00", y(3).toInt))
		println(tzr.first)

		tzr.saveToCassandra("dstrauss", "dmp", SomeColumns("domain", "page","accum_time", "count"))

		val nc = sc.cassandraTable("dstrauss","dmp")
		println("counts %s".format(nc.count))
	}
}
