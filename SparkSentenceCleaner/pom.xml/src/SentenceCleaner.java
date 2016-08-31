package de.uni_leipzig.asv.SentenceCleaner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;

import de.uni_leipzig.asv.SentenceCleaner.Replacements.StringReplacements;
import gnu.getopt.Getopt;

/**
 * central class for cleaning a sentence file via rule files
 * 
 * @author Thomas Eckart
 *
 */
public class SentenceCleaner {
	private HashMap<Integer, SentenceFilter> filterMap = null;
	private Integer NUMBER_OF_THREADS = 4;
	private CleanerThread[] threads = new CleanerThread[NUMBER_OF_THREADS];

	/**
	 * constructor
	 * 
	 * @param filterMap
	 *            HashMap with SentenceFilters
	 */
	public SentenceCleaner(HashMap<Integer, SentenceFilter> filterMap) {
		this.filterMap = filterMap;
	}

	/**
	 * takes inputFile in Wortschatz raw text file (<quelle> or <source> format)
	 * and checks every line against SentenceFilters in filterVector
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param replaceStrings
	 * @param exchangeOutput
	 */
	public void checkRawtextFile(File inputFile, File outputFile, Boolean replaceStrings, Boolean exchangeOutput) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

			boolean isValid = true;
			String line = "";
			while ((line = reader.readLine()) != null) {
				// avoid removal of Wortschatz-Metainformation-Tags (old format)
				if (line.trim().startsWith("<quelle") | line.trim().startsWith("<datum")
						| line.trim().startsWith("<sachgebiet")) {
					writer.write(line + "\n");
					continue;
				}

				// avoid removal of Wortschatz-Metainformation-Tags (new format)
				if (line.trim().startsWith("<source")) {
					writer.write(line + "\n");
					continue;
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
							writer.write(line + "\tRule: " + filter.getFilterID() + " " + filter.getFilterDescription()
									+ "\n");
						break;
					}
				}

				// write well-formed sentences
				if (isValid && !exchangeOutput)
					writer.write(line + "\n");
			}

			reader.close();
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * takes inputFile in Wortschatz raw text file (<quelle> or <source> format)
	 * and checks every line against SentenceFilters in filterVector
	 * (multithreaded version)
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param replaceStrings
	 * @param exchangeOutput
	 *            TODO
	 */
	public void checkRawtextFileMultiThreaded(File inputFile, File outputFile, Boolean replaceStrings,
			Boolean exchangeOutput) {
		try {
			RandomAccessFile raf = new RandomAccessFile(inputFile, "r");
			long fileLength = raf.length();

			Long startPos = 0l;
			Long endPos = 0l;

			if (fileLength < 100000) // small file -> only 1 thread
				NUMBER_OF_THREADS = 1;
			System.out.println("Using " + NUMBER_OF_THREADS + " thread(s)...");

			// finding offsets and start cleaner threads
			for (int i = 1; i <= NUMBER_OF_THREADS; i++) { // TODO: gleiche
															// endpos für
															// verschiedene
															// threads (=sehr
															// langer Satz)
				Long candidate = fileLength / NUMBER_OF_THREADS * i;
				raf.seek(candidate);
				raf.readLine();
				endPos = raf.getFilePointer();
				threads[i - 1] = new CleanerThread(i - 1, filterMap, inputFile, startPos, endPos);
				threads[i - 1].run();

				startPos = raf.getFilePointer();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * takes inputFile in generic tabulator separated file and checks specified
	 * line against SentenceFilters in filterVector
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param inputColumn
	 * @param replaceStrings
	 * @param exchangeOutput
	 */
	public void checkTabFile(File inputFile, File outputFile, int inputColumn, Boolean replaceStrings,
			Boolean exchangeOutput) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

			boolean isValid = true;
			String line = "";
			String[] lineArray;
			while ((line = reader.readLine()) != null) {
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
							outputLine += "\tRule: " + filter.getFilterID() + " " + filter.getFilterDescription()
									+ "\n";
							writer.write(outputLine);
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
					writer.write(outputLine);
				}
			}

			reader.close();
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * write statistics to log file
	 * 
	 * @param writeSummaryToStdout
	 */
	public void writeStatistics(Boolean writeSummaryToStdout) {
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

	public static void main(String[] args1) {
		// parameters
		if (args1.length == 0) {
			printHelp();
			// System.exit(1);
		}
		String[] args = new String[4];
		args[0] = "-i";
		args[1] = "/Users/soheila/workspace/input/leipzig_example_de.txt";
		args[2] = "-o";
		args[3] = "/Users/soheila/workspace/output/output.txt";
		// java -jar SentenceCleaner.jar -i
		// /Users/soheila/workspace/input/leipzig_example_de.txt -o
		// /Users/soheila/workspacee/output.txt

		Getopt g = new Getopt("SentenceCleaner", args, "hi:l:t:o:c:vsmre");

		String inputFilePath = "", outputFilePath = "", langCode = "", textType = "";
		boolean verbose = false;
		boolean showSummary = false;
		boolean multiThreaded = false;
		boolean replaceStrings = false;
		boolean exchangeOutput = false;
		int inputColumn = -1;
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

		// files
		File inputFile = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		File generalRuleFile = new File("rules", "general.rules");

		// load rule files and run cleaner
		if (generalRuleFile.exists() && inputFile.exists()) {
			HashMap<Integer, SentenceFilter> filterMap = new HashMap<Integer, SentenceFilter>();
			filterMap.putAll(RuleFileParser.parseRuleFile(generalRuleFile, "General", verbose));

			File textTypeFile = new File("rules", "texttype_" + textType + ".rules");
			if (textTypeFile.exists())
				filterMap.putAll(RuleFileParser.parseRuleFile(textTypeFile, textType, verbose));

			File languageFile = new File("rules", "lang_" + langCode + ".rules");
			if (languageFile.exists())
				filterMap.putAll(RuleFileParser.parseRuleFile(languageFile, langCode, verbose));

			if (filterMap != null) {
				SentenceCleaner cleaner = new SentenceCleaner(filterMap);

				if (multiThreaded) {
					if (inputColumn != -1) // input is tab separated file
						System.out.println("No implemented yet...");
					// cleaner.checkTabFileMultiThreaded(inputFile, outputFile);
					else // input is Wortschatz raw text file
						cleaner.checkRawtextFileMultiThreaded(inputFile, outputFile, replaceStrings, exchangeOutput);
				} else {
					if (inputColumn != -1) // input is tab separated file
						cleaner.checkTabFile(inputFile, outputFile, inputColumn, replaceStrings, exchangeOutput);
					else // input is Wortschatz raw text file
						cleaner.checkRawtextFile(inputFile, outputFile, replaceStrings, exchangeOutput);
				}

				cleaner.writeStatistics(showSummary);
			}
		} else {
			System.out.println("There has to exist at least the inputfile \"" + inputFile.getName()
					+ "\" and the general rule file \"rules/general.rules\"!");
		}
	}
}
