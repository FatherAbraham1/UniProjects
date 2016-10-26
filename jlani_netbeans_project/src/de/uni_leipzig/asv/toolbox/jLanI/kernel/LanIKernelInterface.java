package de.uni_leipzig.asv.toolbox.jLanI.kernel;

import java.util.Set;

public abstract interface LanIKernelInterface
{
  public abstract Response evaluate(Request paramRequest)
    throws RequestException, DataSourceException;

  public abstract boolean upkeep();

  public abstract Set getAvailableDatasources();
}

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.LanIKernelInterface
 * JD-Core Version:    0.6.0
 */