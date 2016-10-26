package de.uni_leipzig.asv.toolbox.jLanI.io;

public abstract interface Input
{
  public abstract String readSent()
    throws Exception;

  public abstract String getId();
  
  public abstract String getFileName();

  public abstract int getMaxProgress();

  public abstract int getIncProgress();

  public abstract int getCurrentProgress();
}

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.Input
 * JD-Core Version:    0.6.0
 */