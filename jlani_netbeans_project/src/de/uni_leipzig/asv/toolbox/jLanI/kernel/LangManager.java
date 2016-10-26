/*    */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*    */ 
/*    */ import de.uni_leipzig.asv.toolbox.jLanI.tools.Preferences;
/*    */ import java.io.File;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class LangManager
/*    */ {
/*  9 */   static HashMap inactiv = new HashMap();
/* 10 */   static LanIKernel kern = null;
/* 11 */   public static boolean loaded = false;
/*    */ 
/*    */   public static int addNewLanguage(String lang, String file)
/*    */     throws DataSourceException
/*    */   {
/* 16 */     String wordlistDir = kern.prefs.getProperty("WordlistDir");
/*    */ 
/* 18 */     kern.datasourcemanager.addDatasource(lang, file, null);
/* 19 */     kern.dumpDatasource(lang, wordlistDir + System.getProperty("file.separator") + lang + ".ser.gz");
/* 20 */     if (inactiv.containsKey(lang)) {
/* 21 */       activ(lang, false);
/*    */     }
/*    */ 
/* 24 */     return getSourceSize(lang);
/*    */   }
/*    */ 
/*    */   private static int getSourceSize(String lang)
/*    */   {
/* 29 */     return kern.datasourcemanager.hasLanguage(lang) ? 
/* 30 */       ((DataSource)kern.datasourcemanager.datasources.get(lang)).size() : 
/* 31 */       ((DataSource)inactiv.get(lang)).size();
/*    */   }
/*    */ 
/*    */   public static int addNewLanguageFromDb(String lang, HashMap wordlist) throws DataSourceException
/*    */   {
/* 36 */     String wordlistDir = kern.prefs.getProperty("WordlistDir");
/*    */ 
/* 38 */     kern.datasourcemanager.datasources.put(lang, new DataSource(lang, wordlist));
/* 39 */     kern.dumpDatasource(lang, wordlistDir + System.getProperty("file.separator") + lang + ".ser.gz");
/* 40 */     if (inactiv.containsKey(lang)) {
/* 41 */       activ(lang, false);
/*    */     }
/*    */ 
/* 44 */     return getSourceSize(lang);
/*    */   }
/*    */ 
/*    */   public static HashMap getAllLanguages()
/*    */   {
/* 49 */     loaded = true;
/* 50 */     HashMap allLanguages = new HashMap();
/*    */     try
/*    */     {
/* 53 */       kern = LanIKernel.getInstance();
/* 54 */       Iterator it = kern.datasourcemanager.datasources.values().iterator();
/* 55 */       while (it.hasNext())
/*    */       {
/* 57 */         DataSource ds = (DataSource)it.next();
/* 58 */         allLanguages.put(ds.getName(), new Integer(ds.size()));
/*    */       }
/*    */     } catch (Exception e) {
/* 60 */       e.printStackTrace();
/* 61 */     }return allLanguages;
/*    */   }
/*    */ 
/*    */   public static void delete(String lang)
/*    */   {
/*    */     try
/*    */     {
/* 69 */       String wordlistDir = kern.prefs.getProperty("WordlistDir");
/* 70 */       File delFile1 = new File(wordlistDir + System.getProperty("file.separator") + lang + ".ser.gz");
/* 71 */       File delFile2 = new File(wordlistDir + System.getProperty("file.separator") + lang + ".txt");
/* 72 */       delFile1.delete();
/* 73 */       delFile2.delete();
/* 74 */       kern.datasourcemanager.datasources.remove(lang);
/* 75 */       inactiv.remove(lang); } catch (Exception e) {
/* 76 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void activ(String lang, boolean act)
/*    */   {
/* 82 */     if (act)
/* 83 */       kern.datasourcemanager.datasources.put(lang, inactiv.remove(lang));
/*    */     else
/* 85 */       inactiv.put(lang, kern.datasourcemanager.datasources.remove(lang));
/*    */   }
/*    */ 
/*    */   public static boolean containsLanguage(String lang)
/*    */   {
/* 91 */     boolean retval = false;
/* 92 */     if (kern.datasourcemanager != null) {
/* 93 */       retval = (kern.datasourcemanager.datasources.containsKey(lang)) || (inactiv.containsKey(lang));
/*    */     }
/*    */ 
/* 96 */     return retval;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.LangManager
 * JD-Core Version:    0.6.0
 */