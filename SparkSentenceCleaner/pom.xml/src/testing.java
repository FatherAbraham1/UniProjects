package de.uni_leipzig.asv.SentenceCleaner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.regex.*;
import de.uni_leipzig.asv.SentenceCleaner.CleanerThread;

public class testing {
	private Integer NUMBER_OF_THREADS = 4;
	private CleanerThread[] threads = new CleanerThread[NUMBER_OF_THREADS];
	
	public void regexpText() {
		Pattern pattern = Pattern.compile("\\p{L}*");
		
		Matcher m = pattern.matcher("fsafsdславяйнагВрачиопровергаютутвержденияокаменнойначинкебомбывДомодедово");
		System.out.println(m.matches());		
	}
	
	public void run(String filePath) {
		File inputFile = new File(filePath);
		long fileLength = inputFile.length();
		
//		if(fileLength < 100000)		// small file -> only 1 thread
//			NUMBER_OF_THREADS = 1;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(inputFile, "r");
			
			Long startPos = 0l;
			Long endPos = 0l;
			
			// finding offsets and start cleaner threads
			for(int i=1; i<= NUMBER_OF_THREADS; i++) {	 // TODO: gleiche endpos für verschiedene threads (=sehr langer Satz)
				Long candidate = fileLength/NUMBER_OF_THREADS*i;
				raf.seek(candidate); raf.readLine(); endPos = raf.getFilePointer();
				threads[i-1] = new CleanerThread(i-1, null, inputFile, startPos, endPos);
				threads[i-1].run();
				
				startPos = raf.getFilePointer();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		testing t = new testing();
		t.run(args[0]);
		//t.regexpText();
	}
}
