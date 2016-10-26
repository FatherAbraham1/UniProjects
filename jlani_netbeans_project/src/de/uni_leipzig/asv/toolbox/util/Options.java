/*     */ package de.uni_leipzig.asv.toolbox.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class Options
/*     */ {
/*     */   public static final String TRUE = "1";
/*     */   public static final String FALSE = "0";
/*     */   public static final String INI_CONNECTION = "Connection";
/*     */   public static final String INI_CONNECTION_DRIVER = "driver";
/*     */   public static final String INI_CONNECTION_DRIVER_DEFAULT = "org.gjt.mm.mysql.Driver";
/*     */   public static final String INI_CONNECTION_URL = "Url";
/*     */   public static final String INI_CONNECTION_URL_DEFAULT = "jdbc:mysql://lipsia.informatik.uni-leipzig.de/wortschatz?user=bordag&password=blalala";
/*     */   public static final String INI_CONNECTION_USER = "user";
/*     */   public static final String INI_CONNECTION_USER_DEFAULT = "bordag";
/*     */   public static final String INI_CONNECTION_PASS = "passwd";
/*     */   public static final String INI_CONNECTION_PASS_DEFAULT = "blalala";
/*     */   public static final String INI_GENERAL = "General";
/*     */   public static final String INI_GENERAL_OUTPUTFILE = "outputfile";
/*     */   public static final String INI_GENERAL_OUTPUTFILE_DEFAULT = "output";
/*     */   public static final String INI_GENERAL_USEOUTPUTFILE = "useoutputfile";
/*     */   public static final String INI_GENERAL_USEOUTPUTFILE_DEFAULT = "0";
/*     */   public static final String INI_GENERAL_STOPWORTFILE = "stopwortliste";
/*     */   public static final String INI_GENERAL_STOPWORTFILE_DEFAULT = "stopwoerter.txt";
/*     */   public static final String INI_GENERAL_USESTOPWORTFILE = "usestopwortliste";
/*     */   public static final String INI_GENERAL_USESTOPWORTFILE_DEFAULT = "1";
/*     */   public static final String INI_GENERAL_DEBUGLEVEL = "debuglevel";
/*     */   public static final String INI_GENERAL_DEBUGLEVEL_DEFAULT = "0";
/*     */   public static final String INI_GENERAL_QUERYANCHOR = "queryanchor";
/*     */   public static final String INI_GENERAL_QUERYANCHOR_DEFAULT = "#ARG#";
/*     */   public static final String INI_GENERAL_SHOWCLUSTERINGCOEFF = "showclusteringcoefficient";
/*     */   public static final String INI_GENERAL_SHOWCLUSTERINGCOEFF_DEFAULT = "0";
/*     */   public static final String INI_GENERAL_NUMBER2WORD = "number2word";
/*     */   public static final String INI_GENERAL_NUMBER2WORD_DEFAULT = "select w.wort_bin from wortliste w where w.wort_nr = #ARG#;";
/*     */   public static final String INI_GENERAL_WORD2NUMBER = "word2number";
/*     */   public static final String INI_GENERAL_WORD2NUMBER_DEFAULT = "select w.wort_nr from wortliste w where w.wort_bin = \"#ARG#\";";
/*     */   public static final String INI_GENERAL_QUERYFREQUENCY = "queryfrequencyofwordnr";
/*     */   public static final String INI_GENERAL_QUERYFREQUENCY_DEFAULT = "select w.anzahl from wortliste w where w.wort_nr = \"#ARG#\";";
/*     */   public static final String INI_TRIGRAM = "Trigram";
/*     */   public static final String INI_TRIGRAM_TRIMAXRECURSION = "trimaxrecursion";
/*     */   public static final String INI_TRIGRAM_TRIMAXRECURSION_DEFAULT = "0";
/*     */   public static final String INI_TRIGRAM_TRIMINRECURSION = "triminrecursion";
/*     */   public static final String INI_TRIGRAM_TRIMINRECURSION_DEFAULT = "0";
/*     */   public static final String INI_TRIGRAM_MINSIGNIFIKANZ = "triminsignifikanz";
/*     */   public static final String INI_TRIGRAM_MINSIGNIFIKANZ_DEFAULT = "7";
/*     */   public static final String INI_TRIGRAM_MINWORDNR = "minwordnr";
/*     */   public static final String INI_TRIGRAM_MINWORDNR_DEFAULT = "1000";
/*     */   public static final String INI_TRIGRAM_MAXKOLLOKATIONEN = "trimaxkollokationen";
/*     */   public static final String INI_TRIGRAM_MAXKOLLOKATIONEN_DEFAULT = "1000";
/*     */   public static final String INI_TRIGRAM_QUERYKOLLOKATIONEN = "triquerykollokationen";
/*     */   public static final String INI_TRIGRAM_QUERYKOLLOKATIONEN_DEFAULT = "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;";
/*     */   public static final String INI_PARASYNTAGMA = "ParaSyntagma";
/*     */   public static final String INI_PARASYNTAGMA_MINSIGNIFIKANZ = "minsignifikanz";
/*     */   public static final String INI_PARASYNTAGMA_MINSIGNIFIKANZ_DEFAULT = "10";
/*     */   public static final String INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ = "minsecondarysignifikanz";
/*     */   public static final String INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ_DEFAULT = "1";
/*     */   public static final String INI_PARASYNTAGMA_QUERYKOLLOKATIONEN = "querykollokationen";
/*     */   public static final String INI_PARASYNTAGMA_QUERYKOLLOKATIONEN_DEFAULT = "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;";
/*     */   public static final String INI_PARASYNTAGMA_QUERYDISAMBIG = "querydisambkollokationen";
/*     */   public static final String INI_PARASYNTAGMA_QUERYDISAMBIG_DEFAULT = "SELECT d.wort_nr2,d.wort_grp FROM disamb_sig d WHERE d.wort_nr2 > #ARG# and d.wort_nr1 = #ARG# order by d.wort_grp desc;";
/*     */   public static final String INI_PARASYNTAGMA_MAXKOLLOKATIONEN = "maxkollokationen";
/*     */   public static final String INI_PARASYNTAGMA_MAXKOLLOKATIONEN_DEFAULT = "1000";
/*     */   public static final String INI_PARASYNTAGMA_MINWORDNR = "minwordnr";
/*     */   public static final String INI_PARASYNTAGMA_MINWORDNR_DEFAULT = "1000";
/*     */   public static final String INI_PARASYNTAGMA_LASTWORD = "lastword";
/*     */   public static final String INI_PARASYNTAGMA_LASTWORD_DEFAULT = "Elefant";
/*     */   public static final String INI_DISAMBIGUATOR = "Disambiguator";
/*     */   public static final String INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP = "kollokationenperstep";
/*     */   public static final String INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP_DEFAULT = "15";
/*     */   public static final String INI_DISAMBIGUATOR_CLUSTERTHRESHOLD = "clusterthreshold";
/*     */   public static final String INI_DISAMBIGUATOR_CLUSTERTHRESHOLD_DEFAULT = "51";
/*     */   public static final String INI_DISAMBIGUATOR_MINSIGNIFIKANZ = "minsignifikanz";
/*     */   public static final String INI_DISAMBIGUATOR_MINSIGNIFIKANZ_DEFAULT = "10";
/*     */   public static final String INI_DISAMBIGUATOR_MAXKOLLOKATIONEN = "maxkollokationen";
/*     */   public static final String INI_DISAMBIGUATOR_MAXKOLLOKATIONEN_DEFAULT = "1000";
/*     */   public static final String INI_DISAMBIGUATOR_MINWORDNR = "minwordnr";
/*     */   public static final String INI_DISAMBIGUATOR_MINWORDNR_DEFAULT = "1000";
/*     */   public static final String INI_DISAMBIGUATOR_MAXRUNS = "maxruns";
/*     */   public static final String INI_DISAMBIGUATOR_MAXRUNS_DEFAULT = "15";
/*     */   public static final String INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN = "disquerykollokationen";
/*     */   public static final String INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN_DEFAULT = "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;";
/*     */   public static final String INI_DISAMBIGUATOR_LASTWORD = "lastword";
/*     */   public static final String INI_DISAMBIGUATOR_LASTWORD_DEFAULT = "space";
/*     */   public static final String INI_SACHGEBIETE = "Sachgebiete";
/*     */   public static final String INI_SACHGEBIETE_MAXDEFININGWORDS = "maxdefiningwords";
/*     */   public static final String INI_SACHGEBIETE_MAXDEFININGWORDS_DEFAULT = "45";
/*     */   public static final String INI_SACHGEBIETE_DISSTEPSPERWORD = "sachdisstepsperword";
/*     */   public static final String INI_SACHGEBIETE_DISSTEPSPERWORD_DEFAULT = "1";
/*     */   public static final String INI_SACHGEBIETE_MINWORDFREQ = "sachminwordfreq";
/*     */   public static final String INI_SACHGEBIETE_MINWORDFREQ_DEFAULT = "60";
/*     */   public static final String INI_SACHGEBIETE_QUERYSACHGEBIETE = "sgquery";
/*     */   public static final String INI_SACHGEBIETE_QUERYSACHGEBIETE_DEFAULT = "select w.wort_nr from wortliste w, sachgebiet s where s.sa_nr = #ARG# and w.wort_nr = s.wort_nr and w.anzahl > #ARG# limit #ARG#";
/*     */   public static final String INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER = "sachnummerquery";
/*     */   public static final String INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER_DEFAULT = "select s.sa_nr from sachgebiet s where s.sachgebiet = \"#ARG#\";";
/* 192 */   private static Options instance = null;
/*     */ 
/* 197 */   private IniFile iniFile = null;
/*     */ 
/*     */   protected Options()
/*     */   {
/* 204 */     this.iniFile = new IniFile("DBKlassifikator.ini");
/*     */   }
/*     */ 
/*     */   public static Options getInstance()
/*     */   {
/* 209 */     if (instance == null)
/*     */     {
/* 211 */       synchronized (Options.class)
/*     */       {
/* 213 */         instance = new Options();
/*     */       }
/*     */     }
/* 216 */     return instance;
/*     */   }
/*     */ 
/*     */   private String getOptionValue(String part, String option)
/*     */   {
/* 225 */     if (this.iniFile.existsKeyPair(part, option))
/*     */     {
/* 227 */       return this.iniFile.getValue(part, option);
/*     */     }
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   private void setOptionValue(String part, String option, String value)
/*     */   {
/* 237 */     if ((value != null) && (value.length() > 0))
/*     */     {
/* 239 */       this.iniFile.setValue(part, option, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean existsKeyPair(String pKey, String sKey)
/*     */   {
/* 245 */     return this.iniFile.existsKeyPair(pKey, sKey);
/*     */   }
/*     */ 
/*     */   private String getAssured(String pkey, String skey, String defaultValue)
/*     */   {
/* 253 */     if (!this.iniFile.existsKeyPair(pkey, skey))
/*     */     {
/* 255 */       this.iniFile.setValue(pkey, skey, defaultValue);
/*     */     }
/* 257 */     return this.iniFile.getValue(pkey, skey);
/*     */   }
/*     */ 
/*     */   public String getConDriver()
/*     */   {
/* 264 */     return getAssured("Connection", "driver", "org.gjt.mm.mysql.Driver");
/*     */   }
/*     */ 
/*     */   public void setConDriver(String newOption) {
/* 268 */     setOptionValue("Connection", "driver", newOption);
/*     */   }
/*     */ 
/*     */   public String getConUrl()
/*     */   {
/* 277 */     return getAssured("Connection", "Url", "jdbc:mysql://lipsia.informatik.uni-leipzig.de/wortschatz?user=bordag&password=blalala");
/*     */   }
/*     */ 
/*     */   public void setConUrl(String newOption) {
/* 281 */     System.out.println("com.bordag.util.Options.Writing: " + newOption);
/* 282 */     setOptionValue("Connection", "Url", newOption);
/*     */   }
/*     */ 
/*     */   public String getConUser()
/*     */   {
/* 291 */     return getAssured("Connection", "user", "bordag");
/*     */   }
/*     */ 
/*     */   public void setConUser(String newOption) {
/* 295 */     setOptionValue("Connection", "user", newOption);
/*     */   }
/*     */ 
/*     */   public String getConPasswd()
/*     */   {
/* 304 */     return getAssured("Connection", "passwd", "blalala");
/*     */   }
/*     */ 
/*     */   public void setConPasswd(String newOption) {
/* 308 */     setOptionValue("Connection", "passwd", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenOutputFile()
/*     */   {
/* 318 */     return getAssured("General", "outputfile", "output");
/*     */   }
/*     */ 
/*     */   public void setGenOutputFile(String newOption) {
/* 322 */     setOptionValue("General", "outputfile", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenUseOutputFile()
/*     */   {
/* 327 */     return getAssured("General", "useoutputfile", "0");
/*     */   }
/*     */ 
/*     */   public void setGenUseOutputFile(String newOption) {
/* 331 */     setOptionValue("General", "useoutputfile", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenStopwortFile()
/*     */   {
/* 339 */     return getAssured("General", "stopwortliste", "stopwoerter.txt");
/*     */   }
/*     */ 
/*     */   public void setGenStopwortFile(String newOption) {
/* 343 */     setOptionValue("General", "stopwortliste", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenUseStopwortFile()
/*     */   {
/* 348 */     return getAssured("General", "usestopwortliste", "1");
/*     */   }
/*     */ 
/*     */   public void setGenUseStopwortFile(String newOption) {
/* 352 */     setOptionValue("General", "usestopwortliste", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenDebugLevel()
/*     */   {
/* 360 */     return getAssured("General", "debuglevel", "0");
/*     */   }
/*     */ 
/*     */   public void setGenDebugLevel(String newOption) {
/* 364 */     setOptionValue("General", "debuglevel", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenQueryAnchor()
/*     */   {
/* 369 */     return getAssured("General", "queryanchor", "#ARG#");
/*     */   }
/*     */ 
/*     */   public void setGenQueryAnchor(String newOption) {
/* 373 */     setOptionValue("General", "queryanchor", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenShowClusteringCoeff()
/*     */   {
/* 378 */     return getAssured("General", "showclusteringcoefficient", "0");
/*     */   }
/*     */ 
/*     */   public void setGenShowClusteringCoeff(String newOption) {
/* 382 */     setOptionValue("General", "showclusteringcoefficient", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenQueryWord2Number()
/*     */   {
/* 387 */     return getAssured("General", "word2number", "select w.wort_nr from wortliste w where w.wort_bin = \"#ARG#\";");
/*     */   }
/*     */ 
/*     */   public void setGenQueryWord2Number(String newOption) {
/* 391 */     setOptionValue("General", "word2number", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenQueryNumber2Word()
/*     */   {
/* 396 */     return getAssured("General", "number2word", "select w.wort_bin from wortliste w where w.wort_nr = #ARG#;");
/*     */   }
/*     */ 
/*     */   public void setGenQueryNumber2Word(String newOption) {
/* 400 */     setOptionValue("General", "number2word", newOption);
/*     */   }
/*     */ 
/*     */   public String getGenQueryFrequency()
/*     */   {
/* 405 */     return getAssured("General", "queryfrequencyofwordnr", "select w.anzahl from wortliste w where w.wort_nr = \"#ARG#\";");
/*     */   }
/*     */ 
/*     */   public void setGenQueryFrequency(String newOption) {
/* 409 */     setOptionValue("General", "queryfrequencyofwordnr", newOption);
/*     */   }
/*     */ 
/*     */   public String getTriMaxRecursion()
/*     */   {
/* 417 */     return getAssured("Trigram", "trimaxrecursion", "0");
/*     */   }
/*     */ 
/*     */   public void setTriMaxRecursion(String newOption) {
/* 421 */     setOptionValue("Trigram", "trimaxrecursion", newOption);
/*     */   }
/*     */ 
/*     */   public String getTriMinRecursion()
/*     */   {
/* 426 */     return getAssured("Trigram", "triminrecursion", "0");
/*     */   }
/*     */ 
/*     */   public void setTriMinRecursion(String newOption) {
/* 430 */     setOptionValue("Trigram", "triminrecursion", newOption);
/*     */   }
/*     */ 
/*     */   public String getTriMinSignifikanz()
/*     */   {
/* 435 */     return getAssured("Trigram", "triminsignifikanz", "7");
/*     */   }
/*     */ 
/*     */   public void setTriMinSignifikanz(String newOption) {
/* 439 */     setOptionValue("Trigram", "triminsignifikanz", newOption);
/*     */   }
/*     */ 
/*     */   public String getTriMinWordNr()
/*     */   {
/* 444 */     return getAssured("Trigram", "minwordnr", "1000");
/*     */   }
/*     */ 
/*     */   public void setTriMinWordNr(String newOption) {
/* 448 */     setOptionValue("Trigram", "minwordnr", newOption);
/*     */   }
/*     */ 
/*     */   public String getTriMaxKollokationen()
/*     */   {
/* 453 */     return getAssured("Trigram", "trimaxkollokationen", "1000");
/*     */   }
/*     */ 
/*     */   public void setTriMaxKollokationen(String newOption) {
/* 457 */     setOptionValue("Trigram", "trimaxkollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getTriQueryKollokationen()
/*     */   {
/* 462 */     return getAssured("Trigram", "triquerykollokationen", "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;");
/*     */   }
/*     */ 
/*     */   public void setTriQueryKollokationen(String newOption) {
/* 466 */     setOptionValue("Trigram", "triquerykollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaMinSignifikanz()
/*     */   {
/* 473 */     return getAssured("ParaSyntagma", "minsignifikanz", "10");
/*     */   }
/*     */ 
/*     */   public void setParaMinSignifikanz(String newOption) {
/* 477 */     setOptionValue("ParaSyntagma", "minsignifikanz", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaMinSecondarySignifikanz()
/*     */   {
/* 482 */     return getAssured("ParaSyntagma", "minsecondarysignifikanz", "1");
/*     */   }
/*     */ 
/*     */   public void setParaMinSecondarySignifikanz(String newOption) {
/* 486 */     setOptionValue("ParaSyntagma", "minsecondarysignifikanz", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaQueryKollokationen()
/*     */   {
/* 492 */     return getAssured("ParaSyntagma", "querykollokationen", "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;");
/*     */   }
/*     */ 
/*     */   public void setParaQueryKollokationen(String newOption) {
/* 496 */     setOptionValue("ParaSyntagma", "querykollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaQueryDisambig()
/*     */   {
/* 501 */     return getAssured("ParaSyntagma", "querydisambkollokationen", "SELECT d.wort_nr2,d.wort_grp FROM disamb_sig d WHERE d.wort_nr2 > #ARG# and d.wort_nr1 = #ARG# order by d.wort_grp desc;");
/*     */   }
/*     */ 
/*     */   public void setParaQueryDisambig(String newOption) {
/* 505 */     setOptionValue("ParaSyntagma", "querydisambkollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaMaxKollokationen()
/*     */   {
/* 510 */     return getAssured("ParaSyntagma", "maxkollokationen", "1000");
/*     */   }
/*     */ 
/*     */   public void setParaMaxKollokationen(String newOption) {
/* 514 */     setOptionValue("ParaSyntagma", "maxkollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaMinWordNr()
/*     */   {
/* 519 */     return getAssured("ParaSyntagma", "minwordnr", "1000");
/*     */   }
/*     */ 
/*     */   public void setParaMinWordNr(String newOption) {
/* 523 */     setOptionValue("ParaSyntagma", "minwordnr", newOption);
/*     */   }
/*     */ 
/*     */   public String getParaLastWord()
/*     */   {
/* 528 */     return getAssured("ParaSyntagma", "lastword", "Elefant");
/*     */   }
/*     */ 
/*     */   public void setParaLastWord(String newOption) {
/* 532 */     setOptionValue("ParaSyntagma", "lastword", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisKollokationenPerStep()
/*     */   {
/* 540 */     return getAssured("Disambiguator", "kollokationenperstep", "15");
/*     */   }
/*     */ 
/*     */   public void setDisKollokationenPerStep(String newOption) {
/* 544 */     setOptionValue("Disambiguator", "kollokationenperstep", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisClusterThreshold()
/*     */   {
/* 549 */     return getAssured("Disambiguator", "clusterthreshold", "51");
/*     */   }
/*     */ 
/*     */   public void setDisClusterThreshold(String newOption) {
/* 553 */     setOptionValue("Disambiguator", "clusterthreshold", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisMinSignifikanz()
/*     */   {
/* 558 */     return getAssured("Disambiguator", "minsignifikanz", "10");
/*     */   }
/*     */ 
/*     */   public void setDisMinSignifikanz(String newOption) {
/* 562 */     setOptionValue("Disambiguator", "minsignifikanz", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisMaxKollokationen()
/*     */   {
/* 567 */     return getAssured("Disambiguator", "maxkollokationen", "1000");
/*     */   }
/*     */ 
/*     */   public void setDisMaxKollokationen(String newOption) {
/* 571 */     setOptionValue("Disambiguator", "maxkollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisMinWordNr()
/*     */   {
/* 576 */     return getAssured("Disambiguator", "minwordnr", "1000");
/*     */   }
/*     */ 
/*     */   public void setDisMinWordNr(String newOption) {
/* 580 */     setOptionValue("Disambiguator", "minwordnr", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisMaxRuns()
/*     */   {
/* 585 */     return getAssured("Disambiguator", "maxruns", "15");
/*     */   }
/*     */ 
/*     */   public void setDisMaxRuns(String newOption) {
/* 589 */     setOptionValue("Disambiguator", "maxruns", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisQueryKollokationen()
/*     */   {
/* 594 */     return getAssured("Disambiguator", "disquerykollokationen", "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;");
/*     */   }
/*     */ 
/*     */   public void setDisQueryKollokationen(String newOption) {
/* 598 */     setOptionValue("Disambiguator", "disquerykollokationen", newOption);
/*     */   }
/*     */ 
/*     */   public String getDisLastWord()
/*     */   {
/* 603 */     return getAssured("Disambiguator", "lastword", "space");
/*     */   }
/*     */ 
/*     */   public void setDisLastWord(String newOption) {
/* 607 */     setOptionValue("Disambiguator", "lastword", newOption);
/*     */   }
/*     */ 
/*     */   public String getSachMaxDefiningWords()
/*     */   {
/* 614 */     return getAssured("Sachgebiete", "maxdefiningwords", "45");
/*     */   }
/*     */ 
/*     */   public void setSachMaxDefiningWords(String newOption) {
/* 618 */     setOptionValue("Sachgebiete", "maxdefiningwords", newOption);
/*     */   }
/*     */ 
/*     */   public String getSachDisStepsPerWord()
/*     */   {
/* 623 */     return getAssured("Sachgebiete", "sachdisstepsperword", "1");
/*     */   }
/*     */ 
/*     */   public void setSachDisStepsPerWord(String newOption) {
/* 627 */     setOptionValue("Sachgebiete", "sachdisstepsperword", newOption);
/*     */   }
/*     */ 
/*     */   public String getSachQuerySachgebiete()
/*     */   {
/* 632 */     return getAssured("Sachgebiete", "sgquery", "select w.wort_nr from wortliste w, sachgebiet s where s.sa_nr = #ARG# and w.wort_nr = s.wort_nr and w.anzahl > #ARG# limit #ARG#");
/*     */   }
/*     */ 
/*     */   public void setSachQuerySachgebiete(String newOption) {
/* 636 */     setOptionValue("Sachgebiete", "sgquery", newOption);
/*     */   }
/*     */ 
/*     */   public String getSachQuerySachNr()
/*     */   {
/* 641 */     return getAssured("Sachgebiete", "sachnummerquery", "select s.sa_nr from sachgebiet s where s.sachgebiet = \"#ARG#\";");
/*     */   }
/*     */ 
/*     */   public void setSachQuerySachNr(String newOption) {
/* 645 */     setOptionValue("Sachgebiete", "sachnummerquery", newOption);
/*     */   }
/*     */ 
/*     */   public String getMinWordFreq()
/*     */   {
/* 650 */     return getAssured("Sachgebiete", "sachminwordfreq", "60");
/*     */   }
/*     */ 
/*     */   public void setMinWordFreq(String newOption) {
/* 654 */     setOptionValue("Sachgebiete", "sachminwordfreq", newOption);
/*     */   }
/*     */ 
/*     */   public void resetAll()
/*     */   {
/* 664 */     getConUrl();
/* 665 */     getConUser();
/* 666 */     getConPasswd();
/*     */ 
/* 668 */     getGenDebugLevel();
/* 669 */     getGenOutputFile();
/* 670 */     getGenUseOutputFile();
/* 671 */     getGenStopwortFile();
/* 672 */     getGenUseStopwortFile();
/* 673 */     getGenShowClusteringCoeff();
/* 674 */     getGenQueryNumber2Word();
/* 675 */     getGenQueryWord2Number();
/* 676 */     getGenQueryFrequency();
/*     */ 
/* 678 */     getTriMaxRecursion();
/* 679 */     getTriMinRecursion();
/* 680 */     getTriMaxKollokationen();
/* 681 */     getTriMinSignifikanz();
/* 682 */     getTriMinWordNr();
/* 683 */     getTriQueryKollokationen();
/*     */ 
/* 685 */     getDisKollokationenPerStep();
/* 686 */     getDisClusterThreshold();
/* 687 */     getDisMaxKollokationen();
/* 688 */     getDisMinSignifikanz();
/* 689 */     getDisMinWordNr();
/* 690 */     getDisMaxRuns();
/* 691 */     getDisQueryKollokationen();
/* 692 */     getDisLastWord();
/*     */ 
/* 694 */     getParaLastWord();
/* 695 */     getParaMaxKollokationen();
/* 696 */     getParaMinSignifikanz();
/* 697 */     getParaMinWordNr();
/* 698 */     getParaQueryDisambig();
/* 699 */     getParaQueryKollokationen();
/*     */ 
/* 701 */     getSachDisStepsPerWord();
/* 702 */     getSachMaxDefiningWords();
/* 703 */     getSachQuerySachgebiete();
/* 704 */     getSachQuerySachNr();
/* 705 */     getMinWordFreq();
/*     */   }
/*     */ 
/*     */   public void printOptions()
/*     */   {
/* 713 */     Output.println();
/* 714 */     Output.println("[" + "Connection" + "]");
/* 715 */     Output.println("Url" + " = " + getConUrl());
/* 716 */     Output.println("user" + " = " + getConUser());
/* 717 */     Output.println("passwd" + " = " + getConPasswd());
/* 718 */     Output.println();
/* 719 */     Output.println("[" + "General" + "]");
/* 720 */     Output.println("debuglevel" + " = " + getGenDebugLevel());
/* 721 */     Output.println("outputfile" + " = " + getGenOutputFile());
/* 722 */     Output.println("stopwortliste" + " = " + getGenStopwortFile());
/* 723 */     Output.println("showclusteringcoefficient" + " = " + getGenShowClusteringCoeff());
/* 724 */     Output.println("word2number" + " = " + getGenQueryWord2Number());
/* 725 */     Output.println("number2word" + " = " + getGenQueryNumber2Word());
/* 726 */     Output.println("queryfrequencyofwordnr" + " = " + getGenQueryFrequency());
/* 727 */     Output.println();
/* 728 */     Output.println("[" + "Trigram" + "]");
/* 729 */     Output.println("trimaxrecursion" + " = " + getTriMaxRecursion());
/* 730 */     Output.println("triminrecursion" + " = " + getTriMinRecursion());
/* 731 */     Output.println("trimaxkollokationen" + " = " + getTriMaxKollokationen());
/* 732 */     Output.println("triminsignifikanz" + " = " + getTriMinSignifikanz());
/* 733 */     Output.println("minwordnr" + " = " + getTriMinWordNr());
/* 734 */     Output.println("triquerykollokationen" + " = " + getTriQueryKollokationen());
/* 735 */     Output.println();
/* 736 */     Output.println("[" + "Disambiguator" + "]");
/* 737 */     Output.println("clusterthreshold" + " = " + getDisClusterThreshold());
/* 738 */     Output.println("maxkollokationen" + " = " + getDisMaxKollokationen());
/* 739 */     Output.println("kollokationenperstep" + " = " + getDisKollokationenPerStep());
/* 740 */     Output.println("minsignifikanz" + " = " + getDisMinSignifikanz());
/* 741 */     Output.println("minwordnr" + " = " + getDisMinWordNr());
/* 742 */     Output.println("maxruns" + " = " + getDisMaxRuns());
/* 743 */     Output.println("disquerykollokationen" + " = " + getDisQueryKollokationen());
/* 744 */     Output.println();
/* 745 */     Output.println("[" + "ParaSyntagma" + "]");
/* 746 */     Output.println("lastword" + " = " + getParaLastWord());
/* 747 */     Output.println("maxkollokationen" + " = " + getParaMaxKollokationen());
/* 748 */     Output.println("minsignifikanz" + " = " + getParaMinSignifikanz());
/* 749 */     Output.println("minwordnr" + " = " + getParaMinWordNr());
/* 750 */     Output.println("querydisambkollokationen" + " = " + getParaQueryDisambig());
/* 751 */     Output.println("querykollokationen" + " = " + getParaQueryKollokationen());
/* 752 */     Output.println();
/* 753 */     Output.println("[" + "Sachgebiete" + "]");
/* 754 */     Output.println("sachdisstepsperword" + " = " + getSachDisStepsPerWord());
/* 755 */     Output.println("maxdefiningwords" + " = " + getSachMaxDefiningWords());
/* 756 */     Output.println("sgquery" + " = " + getSachQuerySachgebiete());
/* 757 */     Output.println("sachnummerquery" + " = " + getSachQuerySachNr());
/* 758 */     Output.println("sachminwordfreq" + " = " + getMinWordFreq());
/* 759 */     Output.println();
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.Options
 * JD-Core Version:    0.6.0
 */