package de.uni_leipzig.asv.toolbox.jLanI.io;

import de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult;

public abstract interface Output
{
  public abstract void writeSent(LangResult paramLangResult, String paramString1, String paramString2)
    throws Exception;

  public abstract void close();

    public abstract void writeSource(String cur_source) throws Exception;
}

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.Output
 * JD-Core Version:    0.6.0
 */