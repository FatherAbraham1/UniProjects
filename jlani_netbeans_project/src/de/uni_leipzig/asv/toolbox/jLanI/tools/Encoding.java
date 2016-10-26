/*    */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*    */ 
/*    */ public class Encoding
/*    */ {
/* 13 */   private String description = null;
/* 14 */   private String name = null;
/*    */ 
/*    */   public Encoding(String name, String description)
/*    */   {
/* 18 */     this.name = name;
/* 19 */     this.description = description;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 25 */     return this.description;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 29 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 35 */     return this.name + "," + this.description;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.Encoding
 * JD-Core Version:    0.6.0
 */