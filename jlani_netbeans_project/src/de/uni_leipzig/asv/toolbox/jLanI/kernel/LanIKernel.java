/*     */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*     */ 
/*     */ import de.uni_leipzig.asv.toolbox.jLanI.tools.Log;
/*     */ import de.uni_leipzig.asv.toolbox.jLanI.tools.Preferences;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class LanIKernel
/*     */   implements LanIKernelInterface
/*     */ {
/*     */   public static final String version = "$Revision: 1.3 $";
/*  32 */   public static String propertyFile = "lanikernel";
/*     */ 
/*  37 */   private static LanIKernel kernelinstance = null;
/*     */   public DatasourceManager datasourcemanager;
/*     */   public Log log;
/*     */   public Preferences prefs;
/*     */   private HashSet blacklist;
/*     */   private Set _allLangs;
/*     */   private String specialChars;
/*     */ 
/*     */   private LanIKernel()
/*     */     throws DataSourceException
/*     */   {
/*  73 */     this.prefs = new Preferences(propertyFile);
/*     */ 
/*  75 */     this.log = Log.getInstance();
/*     */ 
/*  78 */     if (this.prefs.getProperty("KernelLogMode") != null) {
/*     */       try {
/*  80 */         this.log.setLogMode(Integer.parseInt(this.prefs
/*  81 */           .getProperty("KernelLogMode")));
/*     */       } catch (Exception e) {
/*  83 */         this.log.err("LanIKernel: wrong setting in '" + 
/*  84 */           propertyFile + "' KernelLogMode");
/*  85 */         this.log
/*  86 */           .log("LanIKernel: setting KernelLogMode to default: 3");
/*     */       }
/*     */     }
/*     */     else {
/*  90 */       this.log.err("LanIKernel: missing setting 'KernelLogMode' in '" + 
/*  91 */         propertyFile + "'");
/*  92 */       this.log.log("LanIKernel: setting KernelLogMode to default: 3");
/*     */     }
/*     */ 
/*  96 */     this.log.debug("LanIKernel: logging facility enabled");
/*  97 */     this.log.log("LanIKernel: instantiate the LanIKernel object");
/*     */ 
/*  99 */     this.datasourcemanager = DatasourceManager.getInstance();
/*     */     try {
/* 101 */       setupLanguages();
/*     */     }
/*     */     catch (DataSourceException localDataSourceException) {
/*     */     }
/* 105 */     if ((this.prefs.getProperty("BlacklistFile") == null) || 
/* 106 */       (this.prefs.getProperty("BlacklistFile").equals(""))) {
/* 107 */       this.log.log("LanIKernel: no blacklist-file given, that's not a real problem");
/* 108 */       this.blacklist = new HashSet();
/*     */     } else {
/* 110 */       setupBlacklist(this.prefs.getProperty("BlacklistFile"));
/*     */     }
/*     */ 
/* 114 */     if ((this.prefs.getProperty("SpecialChars") == null) || 
/* 115 */       (this.prefs.getProperty("SpecialChars").equals(""))) {
/* 116 */       this.log.log("LanIKernel: no special chars to remove found");
/* 117 */       this.specialChars = null;
/*     */     } else {
/* 119 */       setupSpecialChars(this.prefs.getProperty("SpecialChars"));
/*     */     }
/*     */ 
/* 122 */     this._allLangs = getAvailableDatasources();
/*     */   }
/*     */ 
/*     */   public void setupSpecialChars(String chars)
/*     */   {
/* 133 */     if (chars == null)
/* 134 */       this.specialChars = "";
/*     */     else
/* 136 */       this.specialChars = chars;
/*     */   }
/*     */ 
/*     */   public static synchronized LanIKernel getInstance()
/*     */     throws DataSourceException
/*     */   {
/* 147 */     if (kernelinstance == null) {
/* 148 */       kernelinstance = new LanIKernel();
/*     */     }
/* 150 */     return kernelinstance;
/*     */   }
/*     */ 
/*     */   public boolean loadDumpedDatasource(String filename)
/*     */   {
/* 161 */     this.log.debug("LanIKernel: try to load serialized datasource '" + 
/* 162 */       filename + "'");
/* 163 */     return this.datasourcemanager.addSerializedDatasource(filename);
/*     */   }
/*     */ 
/*     */   public boolean dumpDatasource(String lang)
/*     */   {
/* 174 */     this.log.debug("LanIKernel: try to write datasource '" + lang + 
/* 175 */       "' into '" + lang + ".ser.gz'");
/* 176 */     return dumpDatasource(lang, lang + ".ser.gz");
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 186 */     if (kernelinstance == null) {
/* 187 */       this.log.debug("LanIKernel: not instanciated, nothing to do.");
/* 188 */       return;
/*     */     }
/*     */ 
/* 191 */     this.log.log("LanIKernel: destroying lani kernel instance");
/*     */ 
/* 193 */     kernelinstance = null;
/* 194 */     this.prefs = null;
/* 195 */     this.log
/* 196 */       .debug("LanIKernel: removing reference to datasource manager");
/* 197 */     this.datasourcemanager.reset();
/* 198 */     this.datasourcemanager = null;
/* 199 */     this.log.debug("LanIKernel: disconnecting from log instance");
/* 200 */     this.log = null;
/*     */ 
/* 202 */     System.gc();
/*     */   }
/*     */ 
/*     */   public boolean dumpAllDatasources()
/*     */   {
/* 213 */     this.log.debug("LanIKernel: dumping all datasources...");
/*     */ 
/* 215 */     if (getAvailableDatasources().isEmpty()) {
/* 216 */       this.log
/* 217 */         .err("LanIKernel: cannot serialize wordlists; no wordlists loaded.");
/* 218 */       return false;
/*     */     }
/*     */ 
/* 221 */     boolean ret = true;
/* 222 */     Iterator iter = getAvailableDatasources().iterator();
/*     */ 
/* 224 */     while ((iter
/* 223 */       .hasNext() & 
/* 224 */       ret)) {
/* 225 */       String lang = (String)iter.next();
/* 226 */       ret &= dumpDatasource(lang, lang + ".ser.gz");
/* 227 */       this.log.debug("LanIKernel: wrote wordlist '" + lang + "' to '" + 
/* 228 */         lang + ".ser.gz': " + ret);
/*     */     }
/*     */ 
/* 231 */     return ret;
/*     */   }
/*     */ 
/*     */   public boolean dumpDatasource(String lang, String filename)
/*     */   {
/* 245 */     if (this.datasourcemanager.hasLanguage(lang)) {
/* 246 */       return this.datasourcemanager
/* 247 */         .serializeDatasource(lang, filename);
/*     */     }
/* 249 */     this.log.err("LanIKernel: cannot find language '" + lang + 
/* 250 */       "' for serialization");
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   public Response evaluate(Request request)
/*     */     throws RequestException, DataSourceException
/*     */   {
/* 266 */     this.log.debug("LanIKernel: evaluate() called with request '" + 
/* 267 */       request + "'");
/*     */ 
/* 269 */     if (request == null) {
/* 270 */       this.log
/* 271 */         .err("LanIKernel: you cannot evaluate an empty (null) request object");
/* 272 */       throw new RequestException(
/* 273 */         "evaluate() cannot evaluate empty (null) request objects");
/*     */     }
/*     */ 
/* 277 */     if (request.getLanguages().size() <= 0) {
/* 278 */       this.log
/* 279 */         .debug("LanIKernel: languagelist in request is empty, checking sentence against all languages available");
/* 280 */       this.log.debug(
/* 281 */         "LanIKernel: and available langs are: " + 
/* 282 */         this._allLangs);
/* 283 */       request.setLanguages(getAvailableDatasources());
/*     */     }
/*     */ 
/* 286 */     HashMap seenWords = null;
/* 287 */     HashMap temp = new HashMap();
/*     */ 
/* 290 */     String sentence = request.getSentence();
/*     */ 
/* 293 */     Response response = new Response();
/*     */ 
/* 296 */     response.setId(request.getId());
/*     */ 
/* 300 */     if (request.isReduced()) {
/* 301 */       for (Iterator iter = request.getLanguages().iterator(); iter
/* 302 */         .hasNext(); )
/*     */       {
/* 303 */         temp.put((String)iter.next(), Double.valueOf(1.0D));
/*     */       }
/*     */     } else {
/* 306 */       seenWords = new HashMap();
/*     */ 
/* 308 */       for (Iterator iter = request.getLanguages().iterator(); iter
/* 309 */         .hasNext(); )
/*     */       {
/* 310 */         String langtemp = (String)iter.next();
/* 311 */         temp.put(langtemp, Double.valueOf(1.0D));
/* 312 */         seenWords.put(langtemp, new LinkedList());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 317 */     this.log.debug("LanIKernel: checking sentence '" + 
/* 318 */       sentence + "'");
/*     */ 
/* 320 */     if (sentence == null) {
/* 321 */       this.log.err("LanIKernel: the sentence shouldn't be null");
/* 322 */       return response;
/*     */     }
/*     */ 
/* 326 */     sentence = cleanSentence(sentence);
/*     */ 
/* 328 */     String[] splittedsentence = sentence.split(" ");
/*     */ 
/* 330 */     int sentenceLength = 0;
/* 331 */     boolean reduced = request.isReduced();
/*     */ 
/* 334 */     if (request.getWordsToCheck() > 0)
/*     */     {
/* 336 */       int count = request.getWordsToCheck();
/*     */ 
/* 339 */       if (splittedsentence.length > count) {
/* 340 */         String[] newsplittedsentence = new String[count];
/* 341 */         int stepping = splittedsentence.length / count;
/* 342 */         int j = 0;
/* 343 */         for (int i = 0; i < count; i += stepping) {
/* 344 */           newsplittedsentence[(j++)] = splittedsentence[i];
/*     */         }
/* 346 */         splittedsentence = newsplittedsentence;
/*     */       }
/*     */     }
/*     */ 
/* 350 */     for (int i = 0; i < splittedsentence.length; i++)
/*     */     {
/* 354 */       if (this.blacklist.contains(splittedsentence[i]))
/*     */       {
/*     */         continue;
/*     */       }
/* 358 */       sentenceLength++;
/*     */ 
/* 362 */       HashMap[] tempmap = this.datasourcemanager.getEvaluationData(
/* 363 */         request.getLanguages(), splittedsentence[i], reduced);
/* 364 */       HashMap ret = tempmap[0];
/*     */ 
/* 368 */       if (!reduced) {
/* 369 */         for (Iterator iter = tempmap[1].keySet().iterator(); iter
/* 370 */           .hasNext(); )
/*     */         {
/* 371 */           ((LinkedList)seenWords.get((String)iter.next()))
/* 372 */             .add(splittedsentence[i]);
/*     */         }
/*     */       }
/*     */ 
/* 376 */       for (Iterator iter = request.getLanguages().iterator(); iter
/* 377 */         .hasNext(); )
/*     */       {
/* 378 */         String lang = (String)iter.next();
/* 379 */         Double tempdouble = (Double)ret.get(lang);
/*     */ 
/* 383 */         double tmpValue = tempdouble.doubleValue() * ((Double)temp.get(lang)).doubleValue();
/* 384 */         temp.put(lang, new Double(tmpValue));
/*     */       }
/*     */     }
/*     */ 
/* 388 */     response.setSentenceLength(sentenceLength);
/* 389 */     response.setResult(temp);
/* 390 */     response.setSeenWords(seenWords);
/* 391 */     this.log.debug(
/* 392 */       "LanIKernel: returning response object: '" + 
/* 393 */       response + "'");
/*     */ 
/* 396 */     if (!reduced) {
/* 397 */       HashMap coverage = new HashMap();
/* 398 */       HashMap wordcount = new HashMap();
/* 399 */       double sentencelength = splittedsentence.length;
/* 400 */       for (Iterator iter = response.getSeenWords().keySet().iterator(); iter
/* 401 */         .hasNext(); )
/*     */       {
/* 402 */         String lang = (String)iter.next();
/* 403 */         double coverageVal = 
/* 404 */           ((LinkedList)response
/* 404 */           .getSeenWords().get(lang)).size() / 
/* 405 */           sentencelength;
/* 406 */         int countVal = ((LinkedList)response.getSeenWords().get(lang)).size();
/* 407 */         coverage.put(lang, coverageVal == (0.0D / 0.0D) ? 
/* 408 */           new Double(0.0D) : new Double(coverageVal));
/* 409 */         wordcount.put(lang, new Integer(countVal));
/*     */       }
/* 411 */       response.setCoverage(coverage);
/* 412 */       response.setWordCount(wordcount);
/*     */     }
/*     */ 
/* 416 */     return response;
/*     */   }
/*     */ 
/*     */   public String cleanSentence(String sentence)
/*     */   {
/* 432 */     if ((this.specialChars == null) || (this.specialChars.equals(""))) {
/* 433 */       return sentence;
/*     */     }
/*     */ 
/* 436 */     sentence = sentence.replaceAll(this.specialChars, "");
/*     */ 
/* 438 */     return sentence;
/*     */   }
/*     */ 
/*     */   public Set getAvailableDatasources()
/*     */   {
/* 447 */     return this.datasourcemanager.getAvailableLanguages();
/*     */   }
/*     */ 
/*     */   public boolean isBlacklisted(String word)
/*     */   {
/* 452 */     return this.blacklist.contains(word);
/*     */   }
/*     */ 
/*     */   private boolean setupLanguages()
/*     */     throws DataSourceException
/*     */   {
/* 458 */     this.log.debug("LanIKernel: setting up languages");
/*     */ 
/* 462 */     if (this.prefs.getProperty("WordlistDir") == null) {
/* 463 */       this.log
/* 464 */         .err("LanIKernel: wrong or missing parameter 'WordlistDir'");
/* 465 */       throw new DataSourceException(
/* 466 */         "cannot find 'WordlistDir' in preferences-file");
/*     */     }
/*     */ 
/* 470 */     File wordlistdir = new File(this.prefs.getProperty("WordlistDir"));
/* 471 */     if (!wordlistdir.canRead()) {
/* 472 */       this.log.err("LanIKernel: cannot read wordlistdir '" + 
/* 473 */         wordlistdir.getName() + "'");
/* 474 */       throw new DataSourceException("cannot read WordlistDir '" + 
/* 475 */         wordlistdir.getName() + "'");
/*     */     }
/* 477 */     if (!wordlistdir.isDirectory()) {
/* 478 */       this.log.err("LanIKernel: the wordlistdir '" + 
/* 479 */         wordlistdir.getName() + "' is not a directory!");
/* 480 */       throw new DataSourceException("the wordlistdir '" + 
/* 481 */         wordlistdir.getName() + "' is not a directory!");
/*     */     }
/*     */ 
/* 485 */     this.log.debug("LanIKernel: searching for files now...");
/* 486 */     File[] wordlist = wordlistdir.listFiles();
/* 487 */     if (wordlist.length <= 0) {
/* 488 */       this.log.err("LanIKernel: cannot find any file in '" + 
/* 489 */         wordlistdir.getName() + "'");
/* 490 */       throw new DataSourceException("cannot find wordlists in '" + 
/* 491 */         wordlistdir.getName() + "'");
/*     */     }
/*     */ 
/* 495 */     for (int i = 0; i < wordlist.length; i++)
/*     */     {
/* 497 */       if ((wordlist[i].getName().endsWith(".ser.gz") & 
/* 497 */         wordlist[i].getName().length() > ".ser.gz".length())) {
/* 498 */         this.log.debug("LanIKernel: found wordlist file '" + 
/* 499 */           wordlist[i] + "'");
/* 500 */         loadDumpedDatasource(wordlist[i].getAbsolutePath());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 506 */     for (int i = 0; i < wordlist.length; i++)
/*     */     {
/* 543 */       if ((wordlist[i].getName().endsWith(".words") & 
/* 543 */         wordlist[i].getName().length() > ".words".length())) {
/* 544 */         this.log.debug("LanIKernel: found wordlist file '" + 
/* 545 */           wordlist[i] + "'");
/*     */ 
/* 548 */         String tempstring = wordlist[i].getName().substring(0, 
/* 549 */           wordlist[i].getName().length() - ".words".length());
/*     */ 
/* 555 */         this.datasourcemanager.addDatasource(tempstring, 
/* 556 */           wordlistdir + 
/* 557 */           System.getProperty("file.separator") + 
/* 558 */           tempstring + ".words", wordlistdir + 
/* 559 */           System.getProperty("file.separator") + 
/* 560 */           tempstring + ".tok");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 575 */     System.out.println(this.datasourcemanager);
/*     */ 
/* 577 */     return true;
/*     */   }
/*     */ 
/*     */   private void setupBlacklist(String filename) throws DataSourceException
/*     */   {
/* 582 */     this.blacklist = new HashSet();
/* 583 */     this.log.debug("LanIKernel: try to open the blacklist-file '" + 
/* 584 */       filename + "'");
/*     */ 
/* 586 */     LineNumberReader lnr = null;
/*     */     try {
/* 588 */       lnr = new LineNumberReader(
/* 589 */         new BufferedReader(new FileReader(filename)));
/*     */     } catch (FileNotFoundException e) {
/* 591 */       this.log.err("LanIKernel: blacklist-file '" + filename + 
/* 592 */         "' not found!");
/* 593 */       throw new DataSourceException("blacklist-file '" + filename + 
/* 594 */         "' not found");
/*     */     }
/*     */     try
/*     */     {
/*     */       String line = null;
/*     */       do
/*     */       {
/*     */         
/* 600 */         this.blacklist.add(line);
/*     */       }
/* 599 */       while ((line = lnr.readLine()) != null);
/*     */     }
/*     */     catch (IOException e1)
/*     */     {
/* 603 */       this.log.err("LanIKernel: cannot read from blacklist-file: " + 
/* 604 */         e1.getMessage());
/* 605 */       throw new DataSourceException(
/* 606 */         "error while reading from blacklist-file");
/*     */     }
/*     */     try
/*     */     {
/*     */       String line;
/* 610 */       lnr.close();
/*     */     }
/*     */     catch (Throwable localThrowable) {
/*     */     }
/* 614 */     this.log.log("LanIKernel: " + this.blacklist.size() + 
/* 615 */       " entries from blacklist-file '" + filename + "' read");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 620 */     StringBuffer ret = new StringBuffer();
/* 621 */     ret.append("[LanIKernel Object, ").append(
/* 622 */       this.datasourcemanager.toString().replaceAll("\n", ""))
/* 623 */       .append("]");
/* 624 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   public boolean upkeep()
/*     */   {
/* 634 */     System.gc();
/* 635 */     System.err
/* 636 */       .println("upkeep() not implemented yet... does nothing but a slow System.gc() call...");
/* 637 */     return true;
/*     */   }
/*     */ 
/*     */   public void test()
/*     */     throws RequestException, DataSourceException
/*     */   {
/* 649 */     LanIKernel kernel = getInstance();
/* 650 */     kernel.log.setLogMode(1);
/* 651 */     kernel.log.setDebug(true);
/*     */ 
/* 653 */     Request req = new Request();
/* 654 */     Set temp = new HashSet();
/*     */ 
/* 659 */     req.setLanguages(temp);
/* 660 */     req.setReduce(false);
/* 661 */     req
/* 662 */       .setSentence("my pony is over the ocean, my bonny is over the see");
/* 663 */     System.out.println("\"" + req.getSentence() + "\"");
/* 664 */     System.out.println(kernel.evaluate(req));
/* 665 */     kernel.upkeep();
/*     */ 
/* 668 */     req
/* 669 */       .setSentence("ÃÂÃÂ° und last but not least, bin ich ein _kurzer_ deutscher Satz (hubergel)!");
/* 670 */     System.out.println("\"" + req.getSentence() + "\"");
/* 671 */     Response resp = kernel.evaluate(req);
/* 672 */     System.out.println("response: " + resp);
/* 673 */     System.out.println("   coverage: " + resp.getCoverage());
/*     */ 
/* 675 */     req.setWordsToCheck(0);
/*     */ 
/* 677 */     int n = 30000;
/* 678 */     System.out.println("testing " + n + " times evaluate()...");
/* 679 */     kernel.log.setDebug(false);
/*     */ 
/* 684 */     for (int i = 0; i < 10000; i++) {
/* 685 */       kernel.evaluate(req);
/*     */     }
/*     */ 
/* 688 */     long delta = new Date().getTime();
/*     */ 
/* 690 */     for (int i = 0; i < n; i++) {
/* 691 */       kernel.evaluate(req);
/*     */     }
/* 693 */     kernel.log.setDebug(true);
/* 694 */     long d = 0L;
/* 695 */     System.out.println("... done in " + (
/* 696 */       d = new Date().getTime() - delta) + "ms, " + n / (
/* 697 */       (float)d / 1000.0D) + " 1/s");
/*     */ 
/* 699 */     System.out.println("serialize de");
/* 700 */     kernel.dumpDatasource("de");
/* 701 */     System.out.println("done");
/* 702 */     System.out.println("Deserialize de");
/* 703 */     kernel.loadDumpedDatasource("de.ser.gz");
/* 704 */     System.out.println("done");
/* 705 */     kernel.upkeep();
/* 706 */     System.out.println("dump all datasources to files...");
/* 707 */     kernel.dumpAllDatasources();
/* 708 */     System.out.println("done");
/* 709 */     System.out.println("calling reset()");
/* 710 */     kernel.reset();
/*     */ 
/* 713 */     System.out.println("get a new instance");
/* 714 */     kernel = getInstance();
/* 715 */     System.out.println("done");
/* 716 */     System.out.println(kernel);
/* 717 */     System.out.println("version: " + "$Revision: 1.3 $");
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws RequestException, DataSourceException
/*     */   {
/* 723 */     getInstance().test();
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.LanIKernel
 * JD-Core Version:    0.6.0
 */