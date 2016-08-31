package SparkImp.SentencesCleaner;

public interface SentenceFilter {
	public boolean sentenceIsValid(String sentence);

	public int getFilterID();

	public String getFilterDescription();

	public int getHits();
}