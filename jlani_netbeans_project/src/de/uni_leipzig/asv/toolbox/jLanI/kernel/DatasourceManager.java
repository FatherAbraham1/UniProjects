/*     */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*     */ 
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ public class DatasourceManager
/*     */ {
/*  36 */   private static DatasourceManager instance = null;
/*     */   public HashMap datasources;
/*     */   private double REST;
/*  42 */   private String wordlistseparator = "\t";
    private LineNumberReader tokenf;
    private LineNumberReader wordlist;
/*     */ 
/*     */   private DatasourceManager()
/*     */   {
/*  50 */     this.datasources = new HashMap();
/*     */ 
/*  53 */     this.REST = 0.1D;
/*     */   }
/*     */ 
/*     */   public static DatasourceManager getInstance()
/*     */   {
/*  63 */     if (instance == null) {
/*  64 */       instance = new DatasourceManager();
/*     */     }
/*  66 */     return instance;
/*     */   }
/*     */ 
/*     */   public boolean addDatasource(String name, String listfile, String tokenfile)
/*     */     throws DataSourceException
/*     */   {
/*  82 */     if (listfile == null) {
/*  83 */       throw new DataSourceException(
/*  84 */         "listfile cannot be null!");
/*     */     }
/*     */ 
/*  94 */     HashMap wordlist = new HashMap();
/*     */ 
/*  96 */     int token = 0;
/*     */ 
/* 103 */     wordlist = parseWordlistFile(listfile, token);
/*     */ 
/* 105 */     DataSource newsource = new DataSource(name, wordlist);
/* 106 */     this.datasources.put(name, newsource);
/*     */ 
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean addSerializedDatasource(String filename)
/*     */   {
/* 122 */     ObjectInputStream is = null;
/* 123 */     DataSource newsource = null;
/*     */     try
/*     */     {
/* 126 */       is = new ObjectInputStream(
/* 127 */         new GZIPInputStream(new FileInputStream(filename)));
/*     */     } catch (FileNotFoundException e) {
/* 129 */       System.err.println("cannot find file '" + filename + "'!");
/* 130 */       e.printStackTrace();
/* 131 */       return false;
/*     */     } catch (IOException e) {
/* 133 */       System.err.println("cannot open file '" + filename + "'!");
/* 134 */       e.printStackTrace();
/* 135 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 139 */       newsource = (DataSource)is.readObject();
/*     */     } catch (IOException e1) {
/* 141 */       System.err.println("cannot read from file '" + filename + "'!");
/* 142 */       e1.printStackTrace();
/* 143 */       return false;
/*     */     } catch (ClassNotFoundException e1) {
/* 145 */       System.out.println("cannot find class!");
/* 146 */       e1.printStackTrace();
/* 147 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 151 */       is.close();
/*     */     } catch (IOException e2) {
/* 153 */       System.err.println("cannot close file '" + filename + "'!");
/* 154 */       e2.printStackTrace();
/* 155 */       return false;
/*     */     }
/*     */ 
/* 158 */     if (this.datasources.containsKey(newsource.getName())) {
/* 159 */       System.err.println("WARNING! overwriting existing '" + 
/* 160 */         newsource.getName() + "' with the datasource from '" + 
/* 161 */         filename + "'!");
/*     */     }
/*     */ 
/* 164 */     this.datasources.put(newsource.getName(), newsource);
/*     */ 
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 174 */     this.datasources = null;
/* 175 */     instance = null;
/*     */   }
/*     */ 
/*     */   public boolean serializeDatasource(String name, String filename)
/*     */   {
/* 189 */     ObjectOutputStream os = null;
/*     */     try
/*     */     {
/* 193 */       os = new ObjectOutputStream(
/* 194 */         new GZIPOutputStream(new FileOutputStream(filename)));
/*     */     } catch (FileNotFoundException e) {
/* 196 */       System.err.println("cannot find file '" + filename + "'!");
/* 197 */       e.printStackTrace();
/* 198 */       return false;
/*     */     } catch (IOException e) {
/* 200 */       System.err.println("cannot open file '" + filename + "'!");
/* 201 */       e.printStackTrace();
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 207 */       os.writeObject(this.datasources.get(name));
/*     */     } catch (IOException e1) {
/* 209 */       System.err.println("cannot write the object stream to file '" + 
/* 210 */         filename + "'!");
/* 211 */       e1.printStackTrace();
/* 212 */       return false;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 217 */       os.close();
/*     */     } catch (IOException e2) {
/* 219 */       System.err.println("cannot close file '" + filename + "'!");
/* 220 */       e2.printStackTrace();
/* 221 */       return false;
/*     */     }
/*     */ 
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean hasLanguage(String language)
/*     */   {
/* 236 */     return this.datasources.containsKey(language);
/*     */   }
/*     */ 
/*     */   public HashMap[] getEvaluationData(Set languages, String word, boolean reduced)
/*     */     throws DataSourceException
/*     */   {
/* 250 */     int num_langs = languages.size();
/* 251 */     String temp = null;
/*     */ 
/* 253 */     if (!this.datasources.keySet().containsAll(languages)) {
/* 254 */       System.out.println("given languages '" + languages + 
/* 255 */         "' is not a subset of '" + this.datasources.keySet() + 
/* 256 */         "'");
/* 257 */       throw new DataSourceException("one or some languages in '" + 
/* 258 */         languages + "' are not known by DatasourceManager!");
/*     */     }
/*     */ 
/* 262 */     HashMap[] retvalues = new HashMap[2];
/* 263 */     HashMap ret = new HashMap();
/* 264 */     if (!reduced) {
/* 265 */       retvalues[1] = new HashMap();
/*     */     }
/* 267 */     Double prob = null;
/* 268 */     String lang = null;
/* 269 */     double probSum = 0.0D;
/* 270 */     int failed = 0;
/*     */ 
/* 273 */     for (Iterator iter = languages.iterator(); iter.hasNext(); ) {
/* 274 */       lang = (String)iter.next();
/*     */ 
/* 276 */       prob = ((DataSource)this.datasources.get(lang)).get(word);
/*     */ 
/* 279 */       if (prob == null) {
/* 280 */         failed++;
/* 281 */         ret.put(lang, null);
/*     */       } else {
/* 283 */         probSum += prob.doubleValue();
/* 284 */         ret.put(lang, prob);
/* 285 */         if (!reduced)
/* 286 */           retvalues[1].put(lang, null);
/*     */       }
/*     */     }
/*     */     double rest;
/*     */     
/* 296 */     if (failed == num_langs)
/* 297 */       rest = 1.0D;
/*     */     else {
/* 299 */       rest = this.REST / num_langs * probSum;
/*     */     }
/*     */ 
/* 303 */     for (Iterator iter = ret.keySet().iterator(); iter.hasNext(); ) {
/* 304 */       lang = (String)iter.next();
/* 305 */       if (ret.get(lang) == null) {
/* 306 */         ret.put(lang, new Double(rest));
/* 307 */         probSum += rest;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 314 */     for (Iterator iter = ret.keySet().iterator(); iter.hasNext(); ) {
/* 315 */       lang = (String)iter.next();
/* 316 */       ret.put(lang, 
/* 318 */         new Double(((Double)ret.get(lang))
/* 317 */         .doubleValue() * 
/* 318 */         num_langs / probSum));
/*     */     }
/*     */ 
/* 323 */     retvalues[0] = ret;
/* 324 */     return retvalues;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 333 */     StringBuffer ret = new StringBuffer();
/*     */ 
/* 335 */     ret.append("DatasourceManager contains:\n");
/* 336 */     ret.append("  " + this.datasources.size() + " languages:");
/* 337 */     ret.append(" " + this.datasources.keySet() + "\n");
/*     */ 
/* 339 */     DataSource temp = null;
/* 340 */     int size = 0;
/* 341 */     for (Iterator iter = this.datasources.values().iterator(); iter
/* 342 */       .hasNext(); )
/*     */     {
/* 343 */       temp = (DataSource)iter.next();
/* 344 */       size += temp.size();
/*     */     }
/* 346 */     ret.append("  " + size + " words");
/*     */ 
/* 348 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   private boolean addDatasource(DataSource source)
/*     */   {
/* 362 */     if (source == null) {
/* 363 */       System.err.println("cannot add the null-datasource");
/* 364 */       return false;
/*     */     }
/*     */ 
/* 368 */     if (this.datasources.containsKey(source.getName()))
/*     */     {
/* 370 */       System.err
/* 371 */         .println("WARNING! datasourcemanager already contains language '" + 
/* 372 */         source.getName() + "'! i will NOT overwrite this.");
/* 373 */       return false;
/*     */     }
/*     */ 
/* 376 */     this.datasources.put(source.getName(), source);
/*     */ 
/* 378 */     return true;
/*     */   }
/*     */ 
/*     */   private int parseTokenFile(String tokenfile)
/*     */     throws DataSourceException
/*     */   {
/* 392 */     int token = 0;
/*     */     try
/*     */     {
/* 397 */       tokenf = new LineNumberReader(new FileReader(tokenfile));
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */       LineNumberReader tokenf;
/* 399 */       System.err.println("cannot open tokenfile '" + tokenfile + "'!");
/* 400 */       throw new DataSourceException(e.getLocalizedMessage());
/*     */     }
/*     */     try
/*     */     {
/*     */       LineNumberReader tokenf = null;
/* 406 */       token = Integer.parseInt(tokenf.readLine());
/*     */     } catch (IOException e1) {
/* 408 */       System.err.println("cannot read from tokenfile!");
/* 409 */       throw new DataSourceException(e1.getLocalizedMessage());
/*     */     } catch (NumberFormatException e2) {
/* 411 */       System.err.println("wrong fileformat in " + tokenfile + 
/* 412 */         "! not an integer!");
/* 413 */       throw new DataSourceException(e2.getLocalizedMessage());
/*     */     }
/*     */ 
/* 416 */     System.out.println("token (from " + tokenfile + "): " + token);
/*     */ 
/* 418 */     return token;
/*     */   }
/*     */ 
/*     */   private HashMap parseWordlistFile(String listfile, int tok)
/*     */     throws DataSourceException
/*     */   {
/* 435 */     double token = 0.0D;
/* 436 */     HashMap wordmap = new HashMap();
/*     */     try
/*     */     {
/* 441 */       wordlist = new LineNumberReader(new FileReader(listfile));
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */       LineNumberReader wordlist;
/* 443 */       System.err.println("cannot open wordlistfile '" + listfile + 
/* 444 */         "'!");
/* 445 */       throw new DataSourceException(e.getLocalizedMessage());
/*     */     }
/*     */     
/*     */     try {
/* 450 */       token = Double.parseDouble(wordlist.readLine());
/*     */     } catch (IOException e1) {
/* 452 */       System.err.println("cannot read token!");
/* 453 */       throw new DataSourceException(e1.getLocalizedMessage());
/*     */     } catch (NumberFormatException e2) {
/* 455 */       System.err.println("wrong fileformat! not an integer!");
/* 456 */       throw new DataSourceException(e2.getLocalizedMessage());
/*     */     }
/* 458 */     if (token <= 0.0D) {
/* 459 */       throw new DataSourceException(
/* 460 */         "token (size of reference corpus) cannot be " + token + 
/* 461 */         "!");
/*     */     }
/* 463 */     System.out.println("token (from " + listfile + "): " + token);
/*     */ 
/* 466 */     int fileSize = (int)new File(listfile).length();
/* 467 */     
/* 468 */     int count = 0;
/*     */     try
/*     */     {
/*     */       String line;
/* 477 */       while ((line = wordlist.readLine()) != null)
/*     */       {
/*     */         
/* 480 */         if (line.length() == 0) {
/* 481 */           System.err.println("ignoring empty line...");
/*     */         }
/*     */         else
/*     */         {
/* 485 */           Integer wordcount = getIntFromLine(line);
/* 486 */           String word = getWordFromLine(line);
/*     */ 
/* 488 */           if (wordmap.containsKey(word)) {
/* 489 */             System.out
/* 490 */               .println("duplicate entry in wordlist: '" + 
/* 491 */               word + 
/* 492 */               "'!\n   keeping the first entry (and ignoring this one)!");
/*     */           }
/*     */           else
/*     */           {
/* 499 */             wordmap.put(word, new Double(
/* 500 */               Double.parseDouble(wordcount.toString()) / 
/* 501 */               Double.parseDouble(new Double(token)
/* 502 */               .toString())));
/*     */           }
/*     */ 
/* 505 */           count += line.length() + 1;
/* 506 */           
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 509 */       System.err.println("error while reading from file " + listfile);
/* 510 */       throw new DataSourceException(e.getLocalizedMessage());
/*     */     }
/*     */     String line;
/* 513 */     return wordmap;
/*     */   }
/*     */ 
/*     */   private String getWordFromLine(String line)
/*     */     throws DataSourceException
/*     */   {
/* 526 */     String[] splitline = line.split(this.wordlistseparator);
/* 527 */     if (splitline.length <= 1) {
/* 528 */       System.err
/* 529 */         .println("line '" + 
/* 530 */         line + 
/* 531 */         "' doesn't match required format '<int><sep><word>' (sep='" + 
/* 532 */         this.wordlistseparator + "')!");
/*     */     }
/*     */     else {
/* 535 */       String ret = line.substring(line.indexOf(this.wordlistseparator) + 1);
/* 536 */       return ret;
/*     */     }
/* 538 */     throw new DataSourceException(
/* 539 */       "wrong format in wordlistfile in line '" + line + "'!");
/*     */   }
/*     */ 
/*     */   private Integer getIntFromLine(String line)
/*     */     throws DataSourceException
/*     */   {
/* 553 */     String[] splitline = line.split(this.wordlistseparator);
/* 554 */     Integer ret = null;
/*     */ 
/* 556 */     if (splitline.length <= 1)
/* 557 */       System.err
/* 558 */         .println("line '" + 
/* 559 */         line + 
/* 560 */         "' doesn't match required format '<int><sep><word>' (sep='" + 
/* 561 */         this.wordlistseparator + "')!");
/*     */     else {
/*     */       try
/*     */       {
/* 565 */         ret = new Integer(Integer.parseInt(splitline[0]));
/* 566 */         return ret;
/*     */       } catch (NumberFormatException e) {
/* 568 */         System.err.println("error in line '" + line + "', " + 
/* 569 */           splitline[0] + " is not an integer!");
/*     */       }
/*     */     }
/* 572 */     throw new DataSourceException(
/* 573 */       "wrong format in wordlistfile in line '" + line + "'!");
/*     */   }
/*     */ 
/*     */   public Set getAvailableLanguages()
/*     */   {
/* 583 */     return this.datasources.keySet();
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.DatasourceManager
 * JD-Core Version:    0.6.0
 */