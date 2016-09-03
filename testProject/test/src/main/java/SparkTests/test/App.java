package SparkTests.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

public class App {
	static SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
	static JavaSparkContext sc = new JavaSparkContext(conf);

	public static void main(String[] args) {
		// String inputFile =
		// "/Users/soheila/Documents/workspace/input/inputRaw";
		String inputFile = "/Users/soheila/Documents/workspace/input/inputRawBigData";
		String outputDir = "/Users/soheila/Documents/workspace/output/Test";
		// String outputDir =
		// "/Users/soheila/Documents/workspace/output/LinesBigData";
		if (new File(outputDir).exists()) {
			try {
				FileUtils.deleteDirectory(new File(outputDir));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		conf.set("spark.hadoop.validateOutputSpecs", "false");// rewrite output
																// file
		// load input data
		JavaRDD<String> textFile = sc.textFile(inputFile);
		// split up into words
		JavaRDD<String> lines = textFile.flatMap(new FlatMapFunction<String, String>() {
			public Iterable<String> call(String s) {
				return Arrays.asList(s.split("\\r?\\n"));
			}
		});
		lines.cache();
		lines.saveAsTextFile(outputDir);
		System.out.println("Hello World!" + lines.count());

	}
}
