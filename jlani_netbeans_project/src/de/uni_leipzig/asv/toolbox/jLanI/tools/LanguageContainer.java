/*    */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*    */ 
/*    */ public class LanguageContainer
/*    */ {
/* 13 */   private static LanguageContainer singleton = null;
/*    */ 
/* 15 */   private String[] languages = null;
/*    */ 
/* 17 */   private String[] defaults = { "de", "en", "fr" };
/*    */ 
/*    */   private LanguageContainer()
/*    */   {
/* 21 */     this.languages = this.defaults;
/*    */   }
/*    */ 
/*    */   public static LanguageContainer getInstance()
/*    */   {
/* 26 */     if (singleton == null) {
/* 27 */       singleton = new LanguageContainer();
/*    */     }
/* 29 */     return singleton;
/*    */   }
/*    */ 
/*    */   public void setLanguages(String[] lans)
/*    */   {
/* 34 */     if ((lans != null) && (lans.length > 0))
/* 35 */       this.languages = lans;
/*    */   }
/*    */ 
/*    */   public String[] getLanguages()
/*    */   {
/* 41 */     return this.languages;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.LanguageContainer
 * JD-Core Version:    0.6.0
 */