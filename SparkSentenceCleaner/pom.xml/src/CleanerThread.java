package de.uni_leipzig.asv.SentenceCleaner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;


class CleanerThread extends Thread {
	private HashMap<Integer, SentenceFilter> filterMap = null;
	
	private int threadNumber;
	private File inputFile;
	private long startPos;
	private long endPos;
	
	public CleanerThread(Integer threadNumber, HashMap<Integer, SentenceFilter> filterMap, File inputFile, Long startPos, Long endPos) {
		this.threadNumber = threadNumber;
		this.filterMap = filterMap;
		this.inputFile = inputFile;
		this.startPos = startPos;
		this.endPos = endPos;
	}
  
	public void run() {
		System.out.println("From "+startPos+" to "+endPos);
		boolean isValid = true;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(inputFile, "r");
			BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile+".out"+threadNumber));
			String line = "";
			
			raf.seek(startPos);
			
			while((raf.getFilePointer() < endPos) && (line = raf.readUTF()) != null) {
				// avoid removal of Wortschatz-Metainformation-Tags (old format)
				if(line.trim().startsWith("<quelle") | line.trim().startsWith("<datum") | line.trim().startsWith("<sachgebiet")) {
					writer.write(line+"\n");
					continue;
				}
				
				// avoid removal of Wortschatz-Metainformation-Tags (new format)
				if(line.trim().startsWith("<source")) {
					writer.write(line+"\n");
					continue;
				}
			
				// sequential filter checks
				isValid = true;
				Iterator<Integer> iter = filterMap.keySet().iterator();
				while(iter.hasNext()) {
					SentenceFilter filter = filterMap.get(iter.next());
					if(!filter.sentenceIsValid(line)) {
						isValid = false;
						break;
					}
				}
			
				if(isValid)
					writer.write(line+"\n");
				writer.flush();
			}
			
			raf.close();
			writer.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}