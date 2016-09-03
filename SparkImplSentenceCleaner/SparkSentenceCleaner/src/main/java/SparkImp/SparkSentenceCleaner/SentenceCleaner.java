package SparkImp.SparkSentenceCleaner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import gnu.getopt.Getopt;

public class SentenceCleaner {
	// create a java spark context
	static SparkConf conf = new SparkConf().setAppName("SparkSentenceCleaner").setMaster("local");
	static JavaSparkContext sc = new JavaSparkContext(conf);

	private static HashMap<Integer, SentenceFilter> filterMap = null;

	static String inputFilePath = "";
	static String outputFilePath = "";
	static String langCode = "";
	static String textType = "";
	static String inputType = "";
	static boolean verbose = false;
	static boolean showSummary = false;
	static boolean multiThreaded = false;
	static boolean replaceStrings = false;
	static boolean exchangeOutput = false;
	static int inputColumn = -1;

	/**
	 * prints help information to stdout
	 */
	public static void printHelp() {
		System.out.println(
				"Usage: java -jar SentenceCleaner.jar -i INPUT -o OUTPUT [-l LANG_CODE] [-t TEXTTYPE] [-c COLUMN] [-m] [-r] [-s] [-v] [-e]");
		System.out.println("INPUT\t path to inputfile");
		System.out.println("OUTPUT\t path to outputfile");
		System.out.println("LANG_CODE\t language code in ISO 639-3");
		System.out.println("TEXTTYPE\t text type: web|news|wikipedia");
		System.out.println(
				"COLUMN\t column number: treats input as tabulator separated file, checks only specified column, index starts with 0");
		// System.out.println("m\t multithreaded: use multiple threads for large
		// files (highly experimental!)");
		System.out.println("r\t replace: replace HTML entities with UTF8 characters");
		System.out.println("s\t summary: write summary to stdout");
		System.out.println("v\t verbose: verbose output");
		System.out.println("e\t exchange: write the ill-formed sentences to output (+triggered rule)");
	}

	private static void init(String[] args) {
		Getopt g = new Getopt("SentenceCleaner", args, "hi:l:t:o:c:vsmre");
		int c;
		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 'h':
				printHelp();
				System.exit(0);
			case 'i':
				inputFilePath = g.getOptarg();
				break;
			case 'l':
				langCode = g.getOptarg();
				break;
			case 'o':
				outputFilePath = g.getOptarg();
				break;
			case 'c':
				inputColumn = Integer.parseInt(g.getOptarg());
				break;
			case 't':
				textType = g.getOptarg();
				break;
			case 'v':
				verbose = true;
				break;
			case 's':
				showSummary = true;
				break;
			case 'm':
				// should be removed
				multiThreaded = true;
				break;
			case 'r':
				replaceStrings = true;
				break;
			case 'e':
				exchangeOutput = true;
				break;
			}
		}
	}

	private static HashMap<Integer, SentenceFilter> loadRuleFiles(File generalRuleFile, File inputFile) {
		HashMap<Integer, SentenceFilter> filterMap = new HashMap<Integer, SentenceFilter>();
		if (generalRuleFile.exists() && inputFile.exists()) {
			filterMap.putAll(RuleFileParser.parseRuleFile(generalRuleFile, "General", verbose));
			File textTypeFile = new File("rules", "texttype_" + textType + ".rules");
			if (textTypeFile.exists())
				filterMap.putAll(RuleFileParser.parseRuleFile(textTypeFile, textType, verbose));
			File languageFile = new File("rules", "lang_" + langCode + ".rules");
			if (languageFile.exists())
				filterMap.putAll(RuleFileParser.parseRuleFile(languageFile, langCode, verbose));
			if (filterMap != null) {
				// if (multiThreaded) {
				// if (inputColumn != -1) // input is tab separated file
				// inputType = "tab-separated";
				// else // input is Wortschatz raw text file
				// inputType = "Wortschatz-raw-text";
				// } else {
				if (inputColumn != -1) // input is tab separated file
					inputType = "tab-separated";
				else // input is Wortschatz raw text file
					inputType = "raw-text";
				// }
			}
		} else {
			System.out.println("There has to exist at least the inputfile \"" + inputFile.getName()
					+ "\" and the general rule file \"rules/general.rules\"!");
		}
		return (filterMap);
	}

	public static Iterable<String> checkTabFile(String line, int inputColumn, Boolean replaceStrings,
			Boolean exchangeOutput) {

		boolean isValid = true;
		String[] lineArray;
		String[] output = null;
		int outputIndex = 0;
		lineArray = line.split("\t");
		// String replacements
		if (inputColumn < lineArray.length & replaceStrings) {
			StringReplacements sr = new StringReplacements();
			lineArray[inputColumn] = sr.replaceEntities(lineArray[inputColumn]);
		}
		// sequential filter checks
		isValid = true;
		Iterator<Integer> iter = filterMap.keySet().iterator();
		while (iter.hasNext()) {
			SentenceFilter filter = filterMap.get(iter.next());
			if (inputColumn >= lineArray.length || !filter.sentenceIsValid(lineArray[inputColumn])) {
				isValid = false;
				if (exchangeOutput) { // write ill-formed sentences
					String outputLine = "";
					for (int i = 0; i < lineArray.length; i++)
						outputLine += lineArray[i] + "\t";
					if (outputLine.length() > 0)
						outputLine = outputLine.substring(0, outputLine.length() - 1);
					outputLine += "\tRule: " + filter.getFilterID() + " " + filter.getFilterDescription() + "\n";
					output[outputIndex++] = outputLine;
				}
				break;
			}
		}
		// write output
		if (isValid && !exchangeOutput) {
			String outputLine = "";
			for (int i = 0; i < lineArray.length; i++)
				outputLine += lineArray[i] + "\t";
			if (outputLine.length() > 0)
				outputLine = outputLine.substring(0, outputLine.length() - 1);
			outputLine += "\n";
			output[outputIndex++] = outputLine;
		}
		return (Arrays.asList(output));
	}

	public static Iterable<String> checkRawtextFile(String line, Boolean replaceStrings, Boolean exchangeOutput) {
		boolean isValid = true;
		String[] output = { "" };
		int outputIndex = 0;
		// avoid removal of Wortschatz-Metainformation-Tags (old format)
		if (line.trim().startsWith("<quelle") | line.trim().startsWith("<datum")
				| line.trim().startsWith("<sachgebiet")) {
			output[outputIndex++] = line;
		}
		// avoid removal of Wortschatz-Metainformation-Tags (new format)
		if (line.trim().startsWith("<source")) {
			output[outputIndex++] = line;
		}
		// String replacements
		if (replaceStrings) {
			StringReplacements sr = new StringReplacements();
			line = sr.replaceEntities(line);
		}
		// sequential filter checks
		isValid = true;
		Iterator<Integer> iter = filterMap.keySet().iterator();
		while (iter.hasNext()) {
			SentenceFilter filter = filterMap.get(iter.next());
			if (!filter.sentenceIsValid(line)) {
				isValid = false;
				if (exchangeOutput) // write ill-formed sentences
					output[outputIndex++] = (line + "\tRule: " + filter.getFilterID() + " "
							+ filter.getFilterDescription());
				break;
			}
		}
		// write well-formed sentences
		if (isValid && !exchangeOutput)
			output[outputIndex++] = (line);
		return (Arrays.asList(output));
	}

	public static void writeStatistics(Boolean writeSummaryToStdout) {
		if (writeSummaryToStdout)
			System.out.println("\nrules statistic:");
		CleanerLogging.loadStatFile();
		Iterator<Integer> iter = filterMap.keySet().iterator();
		while (iter.hasNext()) {
			SentenceFilter filter = filterMap.get(iter.next());
			if (writeSummaryToStdout)
				System.out.println("Filter \"" + filter.getFilterDescription() + "\"\t" + filter.getHits());
			CleanerLogging.addNewStats(filter.getFilterDescription(), filter.getHits());
		}
		CleanerLogging.saveStatFile();
	}

	public static void main(String[] args1) {
		// parameters
		if (args1.length == 0) {
			printHelp();
			// System.exit(1);
		}
		String[] args = new String[5];
		args[0] = "-i";
		// args[1] = "/Users/soheila/Documents/workspace/input/inputRaw";
		args[1] = "/Users/soheila/Documents/workspace/input/inputRawBigData";
		args[2] = "-o";
		// args[3] = "/Users/soheila/Documents/workspace/output/Lines";
		args[3] = "/Users/soheila/Documents/workspace/output/LinesBigData";
		args[4] = "-s";
		init(args);

		String inputFile = args[1];
		String outputDir = args[3];
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
		// load general rules
		File generalRulesFil = new File("rules", "general.rules");
		filterMap = loadRuleFiles(generalRulesFil, new File(inputFile));
		JavaRDD<String> temporaryCleanedLines = lines.flatMap(new FlatMapFunction<String, String>() {
			public Iterable<String> call(String line) {
				switch (inputType) {
				case "raw-text":
					return checkRawtextFile(line, replaceStrings, exchangeOutput);
				case "tab-separated-multiThreaded":
					// not implemented jet
					break;
				case "raw-text-multiThreaded":
					// not needed
					// cleaner.checkRawtextFileMultiThreadedOld(inputFileOld,outputFileOld,
					// replaceStrings, exchangeOutput);
					break;
				case "tab-separated":
					return checkTabFile(line, inputColumn, replaceStrings, exchangeOutput);
				default:
					break;
				}
				return null;
			}
		});
		temporaryCleanedLines.cache();
		JavaRDD<String> cleanedLines = temporaryCleanedLines.filter(new Function<String, Boolean>() {
			public Boolean call(String line) {
				return (line.length() > 0);
			}
		});
		cleanedLines.saveAsTextFile(outputDir);
		writeStatistics(showSummary);
	}

}
