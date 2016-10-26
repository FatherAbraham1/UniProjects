package SparkJLanI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.spark_project.protobuf.ByteString.Output;

public class CLIMain {
	private static final Logger logger = java.util.logging.Logger.getInstance();
	// create a java spark context
	static SparkConf conf = new SparkConf().setAppName("SparkSentenceCleaner");
	static JavaSparkContext sparkContext = new JavaSparkContext(conf);

	@SuppressWarnings("unchecked")
	public static void main(String[] args1) {
		if (args1.length < 3) {
			System.out.println("usage: CLIMain <inFile> <outFolder> <singleFile?> <withMetaInfo?>\n");
			System.out.println(
					"<inFile>: SentenceFile\n <outFolder>: where jLanI writes the output\n <singleFile?>: write all data into one file (true/false)\n <withMetaInfo?>: write jLanI statistics into language files (true/false)");
			// return;
		}
		String[] args = new String[5];
		args[0] = "/Users/soheila/Documents/workspace/output/output.txt";// output
																			// of
																			// cleaner
		args[1] = "/Users/soheila/Documents/workspace/JLanIData/OutputNew";
		args[2] = "false";
		args[3] = "false";

		@SuppressWarnings("rawtypes")
		Set languages = new HashSet();
		if (args.length > 4) {
			for (int i = 4; i < args.length; i++) {
				languages.add(args[i]);
			}
		}

		// only needed if you want status reporting
		// logger.log("counting lines...");
		// try {
		// linecount = countLines(args[0]);
		// } catch (IOException ex) {
		// Logger.getLogger(CLIMain.class.getName()).log(Level.SEVERE, null,
		// ex);
		// }
		// logger.log("Lines in file: " + String.valueOf(linecount));
		if (args.length < 4) {
			start(args[0], args[1], args[2], "false", languages);
		}
		if (args.length == 4) {
			start(args[0], args[1], args[2], args[3], languages);
		}

	}

	private static void writeSentsAndSourcesToFiles(Boolean singleFile, Boolean metaInfo, LanIKernelInterface k,
			Output mainout, List<String> sents, List<String> sourcetemp, String cur_lang, String cur_source, Set langs,
			SeparatedFileWriter writer) throws RequestException, DataSourceException, IOException, Exception {
		sourcetemp.clear();
		String current_sent;
		if (singleFile) {
			mainout.writeSource(cur_source);
		}
		for (int i = 0; i < sents.size(); i++) {
			current_sent = sents.get(i);
			// Request begin
			Request req = new Request();
			req.setLanguages(langs);
			req.setSentence(current_sent);
			Response res = k.evaluate(req);
			LangResult toWrite = res.getLangResult(0.15D, 2);
			// Request end
			if (singleFile) {
				mainout.writeSent(toWrite, "", current_sent);
			} else {
				cur_lang = toWrite.toString().split("\t")[0];
				if (!sourcetemp.contains(cur_lang)) {
					writer.writeSentToLangfile(cur_lang, cur_source);
					sourcetemp.add(cur_lang);
				}
				if (metaInfo) {
					System.out.println(toWrite.toString());
					writer.writeSentWithMetaInfo(cur_lang, toWrite, current_sent);
				} else {
					writer.writeSentToLangfile(cur_lang, current_sent);
				}

			}
		}
	}

	// this method only prints the progress of the processing
	static void updateProgress(double progressPercentage) {
		logger.log("\r" + String.valueOf(Math.round(progressPercentage * 100)) + "% done");
	}

	private static void start(String inputFile, String outputfolder, String singleFile, String withMetaInformation,
			Set languages) {
		try {
			// logger.log("StandaloneClientCLI: loading kernel!");
			LanIKernelInterface kern = LanIKernel.getInstance();
			// logger.log("StandaloneClientCLI: loading kernel done!");
			Output out = null;
			String outputFile;
			boolean metaInfoSwitch = Boolean.parseBoolean(withMetaInformation);
			boolean singleFileSwitch = Boolean.parseBoolean(singleFile);
			if (singleFileSwitch) {
				outputFile = outputfolder + "/jLanI_mainOutput.txt";
			}

			conf.set("spark.hadoop.validateOutputSpecs", "false");// rewrite
																	// output
			JavaRDD<String> textFile = sparkContext.textFile(inputFile);
			// split up into words
			JavaRDD<String> lines = textFile.flatMap(new FlatMapFunction<String, String>() {
				private static final long serialVersionUID = 1L;

				public Iterable<String> call(String s) {
					return Arrays.asList(s.split("\\r?\\n"));
				}
			});

			String sent;
			// SeparatedFileWriter sw = new SeparatedFileWriter(outputfolder);
			String current_source = null;
			String current_lang = null;
			List<String> sentlist = new ArrayList<String>();
			List<String> sourcewrittento = new ArrayList<String>();
			// int index = 1;
			// double progress;
			// logger.log("Processing file: " + inputFile.getFileName());
			// logger.log("Output folder: " + outputfolder);
			while ((sent = inputFile.readSent()) != null) {
				// only if you need status reports when processing large files
				// progress = (float) index / linecount;
				// updateProgress(progress);
				// index++;

				if (!(sent.contains("<source>") || sent.contains("<quelle>"))) {
					sentlist.add(sent); // collect sentences of current source
				} else {
					// we found a source tag
					if (current_source != null && !current_source.equals(sent)) {
						// write to files if the source changed and was not null
						// before
						writeSentsAndSourcesToFiles(singleFileSwitch, metaInfoSwitch, kern, out, sentlist,
								sourcewrittento, current_lang, current_source, languages, sw);
						sentlist.clear();
					}
					current_source = sent.trim();
				}
			}
			writeSentsAndSourcesToFiles(singleFileSwitch, metaInfoSwitch, kern, out, sentlist, sourcewrittento,
					current_lang, current_source, languages, sw);
			sentlist.clear();
			if (singleFileSwitch) {
				if (out != null) {
					out.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		logger.log("Finished");
	}

}
