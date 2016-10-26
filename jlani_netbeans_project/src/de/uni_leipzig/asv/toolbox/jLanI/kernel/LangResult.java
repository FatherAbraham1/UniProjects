/*    */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*    */ 
/*    */ public class LangResult
/*    */ {
/*    */   private int MINWORDCOUNT;
/*    */   private double MINCOVERAGE;
/*    */   public String lang1;
/*    */   public String lang2;
/*    */   public int prob1;
/*    */   public int prob2;
/*    */   public int wordcount1;
/*    */   public double coverage1;
/*    */ 
/*    */   public LangResult(String l1, int p1, String l2, int p2, double c, int wc, double mcv, int mco)
/*    */   {
/* 17 */     this.lang1 = l1;
/* 18 */     this.lang2 = l2;
/* 19 */     this.prob1 = p1;
/* 20 */     this.prob2 = p2;
/* 21 */     this.wordcount1 = wc;
/* 22 */     this.coverage1 = c;
/* 23 */     this.MINWORDCOUNT = mco;
/* 24 */     this.MINCOVERAGE = mcv;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 31 */     return isKnown() ? this.lang1 + "\t" + this.prob1 + "\t" + this.lang2 + "\t" + this.prob2 : "unknown";
/*    */   }
/*    */ 
/*    */   public String toTextOutputString()
/*    */   {
/* 36 */     return isKnown() ? this.lang1 + ":" + this.prob1 + " " + this.lang2 + ":" + this.prob2 : "unknown";
/*    */   }
/*    */ 
/*    */   public boolean isKnown()
/*    */   {
/* 41 */     boolean retval = true;
/*    */ 
/* 44 */     if (this.prob1 < 2 * this.prob2) retval = false;
/* 45 */     if (this.wordcount1 < this.MINWORDCOUNT) retval = false;
/* 46 */     if (this.coverage1 < this.MINCOVERAGE) retval = false;
/*    */ 
/* 48 */     return retval;
/*    */   }
/*    */ 
/*    */   public String getLang1()
/*    */   {
/* 53 */     return isKnown() ? this.lang1 : "unknown";
/*    */   }
/*    */ 
/*    */   public String getLang2() {
/* 57 */     return isKnown() ? this.lang2 : "unknown";
/*    */   }
/*    */ 
/*    */   public int getProb1() {
/* 61 */     return isKnown() ? this.prob1 : 0;
/*    */   }
/*    */ 
/*    */   public int getProb2() {
/* 65 */     return isKnown() ? this.prob2 : 0;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult
 * JD-Core Version:    0.6.0
 */