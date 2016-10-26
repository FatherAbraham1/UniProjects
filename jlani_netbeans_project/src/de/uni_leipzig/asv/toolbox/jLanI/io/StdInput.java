/*    */ package de.uni_leipzig.asv.toolbox.jLanI.io;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ public class StdInput
/*    */   implements Input
/*    */ {
/*  8 */   private BufferedReader reader = null;
/*    */ 
/*    */   public StdInput()
/*    */   {
/* 12 */     this.reader = new BufferedReader(new InputStreamReader(System.in));
/*    */   }
/*    */ 
/*    */   public String readSent() throws Exception
/*    */   {
/* 17 */     return this.reader.readLine();
/*    */   }
/*    */   public String getId() {
/* 20 */     return ""; } 
/* 21 */   public int getMaxProgress() { return -1; } 
/* 22 */   public int getIncProgress() { return -1; } 
/* 23 */   public int getCurrentProgress() { return -1;
/*    */   }
/*    */

    @Override
    public String getFileName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.StdInput
 * JD-Core Version:    0.6.0
 */