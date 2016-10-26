/*    */ package de.uni_leipzig.asv.toolbox.jLanI.io;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.StringReader;
/*    */ 
/*    */ public class TextInput
/*    */   implements Input
/*    */ {
/*    */   private BufferedReader in;
/*    */   private String text;
/* 13 */   private int id = 0;
/*    */   private int sentLength;
/* 15 */   private int curProg = 0;
/*    */ 
/*    */   public TextInput(String text) throws Exception
/*    */   {
/* 19 */     this.text = text;
/* 20 */     this.in = new BufferedReader(new StringReader(text));
/*    */   }
/*    */ 
/*    */   public String readSent() throws Exception
/*    */   {
/* 25 */     String sent = this.in.readLine();
/* 26 */     this.id += 1;
/* 27 */     if (sent == null)
/* 28 */       this.in.close();
/*    */     else {
/* 30 */       this.sentLength = sent.length();
/*    */     }
/* 32 */     return sent;
/*    */   }
/*    */   public String getId() {
/* 35 */     return String.valueOf(this.id);
/*    */   }
/* 37 */   public int getMaxProgress() { return this.text.length(); } 
/*    */   public int getIncProgress() {
/* 39 */     return this.sentLength + 2;
/*    */   }
/*    */   public int getCurrentProgress() {
/* 42 */     this.curProg += getIncProgress();
/* 43 */     return this.curProg;
/*    */   }
/*    */

    @Override
    public String getFileName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.TextInput
 * JD-Core Version:    0.6.0
 */