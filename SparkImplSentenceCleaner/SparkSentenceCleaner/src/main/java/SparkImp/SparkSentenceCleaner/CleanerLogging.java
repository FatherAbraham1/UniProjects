package SparkImp.SparkSentenceCleaner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

public class CleanerLogging {
	private static HashMap<String, Integer> stats = null;

	/**
	 * adds new rule stats to existing values
	 * 
	 * @param key
	 * @param value
	 */
	public static void addNewStats(String key, int value) {
		if (stats.containsKey(key)) {
			stats.put(key, value + stats.get(key));
		} else {
			stats.put(key, value);
		}
	}

	/**
	 * loads existing stats from log/rules.stat
	 */
	public static void loadStatFile() {
		stats = new HashMap<String, Integer>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("log", "rules.stat")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				stats.put(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
			}
		} catch (Exception e) {
		}
	}

	/**
	 * saves updated stats to log/rules.stat
	 */
	public static void saveStatFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("log", "rules.stat")));

			Iterator<String> iter = stats.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				writer.write(key + "\t" + stats.get(key) + "\n");
			}
			writer.close();
		} catch (Exception e) {
		}
	}
}
