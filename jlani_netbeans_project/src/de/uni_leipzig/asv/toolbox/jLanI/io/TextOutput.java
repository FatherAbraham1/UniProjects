/*    */ package de.uni_leipzig.asv.toolbox.jLanI.io;
/*    */ 
/*    */ import de.uni_leipzig.asv.toolbox.jLanI.commonTable.CommonTable;

/*    */ import de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult;
/*    */ 
/*    */ public class TextOutput
/*    */   implements Output
/*    */ {
/*    */   public void writeSent(LangResult res, String id, String sent)
/*    */     throws Exception
/*    */   {
/* 12 */     
/*    */   }
/*    */ 
/*    */   public void close()
/*    */   {
/*    */   }
/*    */

    @Override
    public void writeSource(String cur_source) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.TextOutput
 * JD-Core Version:    0.6.0
 */