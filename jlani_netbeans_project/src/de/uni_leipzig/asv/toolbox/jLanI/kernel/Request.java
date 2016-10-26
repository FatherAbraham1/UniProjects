/*     */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Request
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3216123764097547843L;
/*     */   private String sentence;
/*     */   private Set languages;
/*     */   private int modus;
/*     */   private boolean reduce;
/*     */   private int id;
/*     */   private int wordsToCheck;
/*     */ 
/*     */   public Request()
/*     */     throws RequestException
/*     */   {
/*  67 */     this("", new HashSet(), 0, false);
/*     */   }
/*     */ 
/*     */   public Request(String sentence, Set languages, int modus, boolean reduce)
/*     */     throws RequestException
/*     */   {
/*  86 */     if (sentence == null) {
/*  87 */       throw new RequestException("sentence cannot be null!");
/*     */     }
            
             
/*     */ 
/*  90 */     this.sentence = sentence;
/*  91 */     if (languages == null) {
/*  92 */       throw new RequestException(
/*  93 */         "the set of languages cannot be null!");
/*     */     }
/*  95 */     this.languages = languages;
/*  96 */     this.modus = modus;
/*  97 */     this.reduce = reduce;
/*  98 */     this.id = -1;
/*  99 */     this.wordsToCheck = 0;
/*     */   }
/*     */ 
/*     */   public Set getLanguages()
/*     */   {
/* 107 */     return this.languages;
/*     */   }
/*     */ 
/*     */   public void setLanguages(Set languages)
/*     */     throws RequestException
/*     */   {
/* 117 */     if (languages == null) {
/* 118 */       throw new RequestException(
/* 119 */         "the set of languages cannot be null!");
/*     */     }
/* 121 */     this.languages = languages;
/*     */   }
/*     */ 
/*     */   public String getSentence()
/*     */   {
/* 129 */     return this.sentence;
/*     */   }
/*     */ 
/*     */   public void setSentence(String sentence)
/*     */     throws RequestException
/*     */   {
/* 139 */     if (sentence == null) {
/* 140 */       throw new RequestException("sentence cannot be null!");
/*     */     }
/* 142 */     this.sentence = sentence;
/*     */   }
/*     */ 
/*     */   public boolean isReduced()
/*     */   {
/* 150 */     return this.reduce;
/*     */   }
/*     */ 
/*     */   public void setReduce(boolean reduce)
/*     */   {
/* 162 */     this.reduce = reduce;
/*     */   }
/*     */ 
/*     */   public int getWordsToCheck()
/*     */   {
/* 171 */     return this.wordsToCheck;
/*     */   }
/*     */ 
/*     */   public void setWordsToCheck(int wordsToCheck)
/*     */   {
/* 180 */     if (wordsToCheck < 0) {
/* 181 */       System.err
/* 182 */         .println("upper limit must be greater than or equal to 0, reset value to default 0");
/* 183 */       wordsToCheck = 0;
/*     */     }
/*     */ 
/* 186 */     this.wordsToCheck = wordsToCheck;
/*     */   }
/*     */ 
/*     */   public int getModus()
/*     */   {
/* 194 */     return this.modus;
/*     */   }
/*     */ 
/*     */   public void setModus(int modus)
/*     */   {
/* 203 */     this.modus = modus;
/*     */   }
/*     */ 
/*     */   public int getId()
/*     */   {
/* 211 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 220 */     this.id = id;
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.Request
 * JD-Core Version:    0.6.0
 */