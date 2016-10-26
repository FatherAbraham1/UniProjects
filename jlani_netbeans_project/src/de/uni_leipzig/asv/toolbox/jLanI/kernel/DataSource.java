/*    */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DataSource
/*    */   implements Serializable
/*    */ {
/*    */   private HashMap wordlist;
/*    */   private String name;
/*    */   private static final long serialVersionUID = 4459345515315875129L;
/*    */ 
/*    */   public DataSource(String name, HashMap wordlist)
/*    */   {
/* 36 */     this.name = name;
/* 37 */     this.wordlist = wordlist;
/*    */   }
/*    */ 
/*    */   public Double get(String word)
/*    */   {
/*    */     try
/*    */     {
/* 49 */       return (Double)this.wordlist.get(word); } catch (Exception e) {
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 57 */     return this.wordlist.size();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 62 */     return this.name;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.DataSource
 * JD-Core Version:    0.6.0
 */