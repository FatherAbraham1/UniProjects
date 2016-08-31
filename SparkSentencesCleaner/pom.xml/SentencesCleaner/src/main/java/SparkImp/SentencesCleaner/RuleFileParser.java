package SparkImp.SentencesCleaner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class RuleFileParser {
	/**
	 * loads all rules from ruleFile and returns Vector with all created
	 * SentenceFilters
	 * 
	 * @param ruleFile
	 * @param type
	 * @param verbose
	 * @return HashMap with all created SentenceFilters
	 */
	public static HashMap<Integer, SentenceFilter> parseRuleFile(File ruleFile, String type, boolean verbose) {
		if (verbose)
			System.out.println("Try to parse rules from file \"" + ruleFile.getName() + "\"");

		HashMap<Integer, SentenceFilter> filterMap = new HashMap<Integer, SentenceFilter>();
		try {
			BufferedReader ruleFileReader = new BufferedReader(new FileReader(ruleFile));
			SimpleSentenceFilter filter = new SimpleSentenceFilter(verbose);
			String line = "";

			while ((line = ruleFileReader.readLine()) != null) {
				if (line.startsWith("#") || line.startsWith("//") || line.length() == 0) {
					continue;
				} else if (line.toUpperCase().startsWith("RULE")) {
					if (filter.filterIsValid()) {
						if (verbose)
							System.out.println("   ...added rule: " + filter.getFilterDescription());
						filterMap.put(filter.getFilterID(), filter);
						filter = new SimpleSentenceFilter(verbose);
					}
					filter.setFilterID(Integer.parseInt(line.substring(5)));
					continue;
				} else if (line.toUpperCase().startsWith("DESC:")) {
					filter.setFilterDescription(type + " - " + line.substring(5));
					continue;
				} else if (line.toUpperCase().startsWith("REGEXP:")) {
					filter.setPattern(line.substring(7));
					continue;
				} else if (line.toUpperCase().startsWith("MAXLENGTH:")) {
					filter.setMaxLength(Integer.parseInt(line.substring(10)));
					continue;
				} else if (line.toUpperCase().startsWith("MINLENGTH:")) {
					filter.setMinLength(Integer.parseInt(line.substring(10)));
					continue;
				} else if (line.toUpperCase().startsWith("REPLACE_CHARS")) {
					filter.setReplaceCharacterString(line.substring(14));
					continue;
				} else if (line.toUpperCase().startsWith("REPLACE_RATIO")) {
					filter.setReplaceRatio(Float.parseFloat(line.substring(14)));
					continue;
				} else if (line.toUpperCase().startsWith("REPLACE_COUNT")) {
					filter.setReplaceCount(Integer.parseInt(line.substring(14)));
					continue;
				} else if (line.toUpperCase().startsWith("HEX_REGEXP:")) {
					filter.setHexPattern(line.substring(11));
					continue;
				}
			}

			// last rule
			if (filter.filterIsValid()) {
				if (verbose)
					System.out.println("   ...added rule: " + filter.getFilterDescription());
				filterMap.put(filter.getFilterID(), filter);
			}

			if (verbose)
				System.out.println();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

		return filterMap;
	}

	/**
	 * loads all rules from ruleFile and returns Vector with all created
	 * SentenceFilters
	 * 
	 * @param ruleFile
	 * @param type
	 * @return HashMap with all created SentenceFilters
	 */
	public static HashMap<Integer, SentenceFilter> parseRuleFile(File ruleFile, String type) {
		return parseRuleFile(ruleFile, type, false);
	}
}
