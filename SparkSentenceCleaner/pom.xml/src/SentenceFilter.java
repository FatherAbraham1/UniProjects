package de.uni_leipzig.asv.SentenceCleaner;

public interface SentenceFilter {
	public boolean sentenceIsValid(String sentence);
	
	public int getFilterID();
	
	public String getFilterDescription();
	
	public int getHits();
}
