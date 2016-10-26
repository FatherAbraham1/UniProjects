/*     */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Response
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3550862422974476882L;
/*     */   private HashMap<String, Double> result;
/*     */   private HashMap seenWords;
/*  32 */   private int errorcode = 0;
/*     */ 
/*  34 */   private int id = -1;
/*     */ 
/*  36 */   private int sentenceLength = -1;
/*     */   private HashMap coverage;
/*     */   private HashMap wordCount;
/*     */ 
/*     */   public HashMap getResult()
/*     */   {
/*  49 */     return this.result;
/*     */   }
/*     */ 
/*     */   public void setResult(HashMap<String, Double> result)
/*     */   {
/*  61 */     this.result = result;
/*     */   }
/*     */ 
/*     */   public LangResult getLangResult(double mincov, int mincount)
/*     */   {
/*  66 */     double sum = 0.0D;
/*  67 */     Set langSet = this.result.keySet();
/*  68 */     String[] langAr = (String[])langSet.toArray(new String[0]);
/*  69 */     int langCount = langAr.length;
/*  70 */     Double[] valueAr = new Double[langCount];
/*     */ 
/*  72 */     for (int i = 0; i < langCount; i++)
/*     */     {
/*  74 */       valueAr[i] = ((Double)this.result.get(langAr[i]));
/*  75 */       sum += valueAr[i].doubleValue();
/*     */     }
/*     */ 
/*  78 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  80 */       for (int j = i + 1; j < langAr.length; j++)
/*     */       {
/*  82 */         if (valueAr[i].doubleValue() >= valueAr[j].doubleValue())
/*     */           continue;
/*  84 */         String temp = langAr[i];
/*  85 */         langAr[i] = langAr[j];
/*  86 */         langAr[j] = temp;
/*     */ 
/*  88 */         Double tmp = valueAr[i];
/*  89 */         valueAr[i] = valueAr[j];
/*  90 */         valueAr[j] = tmp;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  95 */     return new LangResult(langAr[0], (int)(valueAr[0].doubleValue() / sum * 100.0D), 
/*  96 */       langAr[1], (int)(valueAr[1].doubleValue() / sum * 100.0D), 
/*  97 */       getLanguageCoverage(langAr[0]), getLanguageWordCount(langAr[0]), mincov, mincount);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 102 */     StringBuffer ret = new StringBuffer();
/* 103 */     ret.append("[" + this.id + "] Response-Object with values: " + this.result);
/* 104 */     if (this.seenWords != null) {
/* 105 */       ret.append(" seen words: " + this.seenWords);
/*     */     }
/* 107 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   public int getErrorcode()
/*     */   {
/* 115 */     return this.errorcode;
/*     */   }
/*     */ 
/*     */   public void setErrorcode(int errorcode)
/*     */   {
/* 124 */     this.errorcode = errorcode;
/*     */   }
/*     */ 
/*     */   public HashMap getSeenWords()
/*     */   {
/* 132 */     return this.seenWords;
/*     */   }
/*     */ 
/*     */   public void setSeenWords(HashMap seenWords)
/*     */   {
/* 141 */     this.seenWords = seenWords;
/*     */   }
/*     */ 
/*     */   public int getId()
/*     */   {
/* 147 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 153 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public HashMap getCoverage()
/*     */   {
/* 161 */     return this.coverage;
/*     */   }
/*     */ 
/*     */   public HashMap getWordCount() {
/* 165 */     return this.wordCount;
/*     */   }
/*     */ 
/*     */   public void setCoverage(HashMap coverage)
/*     */   {
/* 174 */     this.coverage = coverage;
/*     */   }
/*     */ 
/*     */   public void setWordCount(HashMap wordCount)
/*     */   {
/* 182 */     this.wordCount = wordCount;
/*     */   }
/*     */ 
/*     */   public double getLanguageCoverage(String lang) {
/* 186 */     if (this.coverage == null) {
/* 187 */       System.err.println("Response-Object #" + this.id + ": Attention! this is an reduced dataset! There are no coverage informations available!");
/* 188 */       return -1.0D;
/*     */     }
/* 190 */     if (this.coverage.containsKey(lang)) {
/* 191 */       return ((Double)this.coverage.get(lang)).doubleValue();
/*     */     }
/* 193 */     System.err.println("Response-Object #" + this.id + ": Attention! language " + lang + " not found!");
/* 194 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   public int getLanguageWordCount(String lang)
/*     */   {
/* 200 */     if (this.coverage == null) {
/* 201 */       System.err.println("Response-Object #" + this.id + ": Attention! this is an reduced dataset! There are no coverage informations available!");
/* 202 */       return 0;
/*     */     }
/* 204 */     if (this.wordCount.containsKey(lang)) {
/* 205 */       return ((Integer)this.wordCount.get(lang)).intValue();
/*     */     }
/* 207 */     System.err.println("Response-Object #" + this.id + ": Attention! language " + lang + " not found!");
/* 208 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSentenceLength()
/*     */   {
/* 216 */     return this.sentenceLength;
/*     */   }
/*     */ 
/*     */   public void setSentenceLength(int sentenceLength)
/*     */   {
/* 223 */     this.sentenceLength = sentenceLength;
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.Response
 * JD-Core Version:    0.6.0
 */