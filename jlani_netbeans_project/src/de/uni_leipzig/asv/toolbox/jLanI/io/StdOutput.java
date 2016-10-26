/*   */ package de.uni_leipzig.asv.toolbox.jLanI.io;
/*   */ 
/*   */ import de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult;
/*   */ import java.io.PrintStream;
/*   */ 
/*   */ public class StdOutput
/*   */   implements Output
/*   */ {
/*   */   public void writeSent(LangResult res, String id, String sent)
/*   */     throws Exception
/*   */   {
/* 8 */     System.out.println(res + "\t" + sent);
/*   */   }
/*   */ 
/*   */   public void close()
/*   */   {
/*   */   }
/*   */

    @Override
    public void writeSource(String cur_source) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.StdOutput
 * JD-Core Version:    0.6.0
 */