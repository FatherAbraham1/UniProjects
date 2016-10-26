/*    */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*    */ 
/*    */ public class DataSourceException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 664991027778548483L;
/*    */   private int errorcode;
/*    */ 
/*    */   DataSourceException(String string)
/*    */   {
/* 21 */     super(string);
/* 22 */     this.errorcode = -1;
/*    */   }
/*    */ 
/*    */   DataSourceException(String string, int errorcode)
/*    */   {
/* 27 */     super(string);
/* 28 */     this.errorcode = errorcode;
/*    */   }
/*    */ 
/*    */   public int getErrorcode()
/*    */   {
/* 37 */     return this.errorcode;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.DataSourceException
 * JD-Core Version:    0.6.0
 */