/*     */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class GlobalPreferences
/*     */ {
/*     */   public static final String DEFAULT_PROPERTY_FILE = "properties.ini";
/*     */   public static final String DEFAULT_PROPERTY_HEADER = "default";
/*  28 */   private String propertyHeader = "default";
/*     */ 
/*  30 */   private String propertyFile = "properties.ini";
/*     */ 
/*  32 */   private Properties properties = null;
/*     */ 
/*  34 */   private static GlobalPreferences singleton = null;
/*     */ 
/*     */   private GlobalPreferences()
/*     */   {
/*  38 */     readFrom(this.propertyFile);
/*  39 */     singleton = this;
/*     */   }
/*     */ 
/*     */   private void readFrom(String fileName)
/*     */   {
/*  44 */     this.properties = new Properties();
/*  45 */     File f = new File(fileName);
/*     */ 
/*  47 */     if ((f.exists()) && (f.isFile()) && (f.canRead())) {
/*     */       try
/*     */       {
/*  50 */         FileInputStream is = new FileInputStream(f);
/*  51 */         this.properties.load(is);
/*  52 */         is.close();
/*  53 */         this.propertyFile = fileName;
/*  54 */         Log.getInstance().log("Properties loaded successfully from File" + fileName + "!\n");
/*     */       } catch (FileNotFoundException e) {
/*  56 */         Log.getInstance().err("Preferences File not found! \n" + e.getMessage() + "\n");
/*     */       } catch (IOException e) {
/*  58 */         Log.getInstance().err("IOException during Preferences File init! \n" + e.getMessage() + "\n");
/*     */       }
/*     */     }
/*     */     else
/*  62 */       Log.getInstance().log("No Preferencesfile found, using empty thang!");
/*     */   }
/*     */ 
/*     */   public static GlobalPreferences getInstance()
/*     */   {
/*  68 */     if (singleton == null) {
/*  69 */       return new GlobalPreferences();
/*     */     }
/*  71 */     return singleton;
/*     */   }
/*     */ 
/*     */   public void read(String fileName)
/*     */   {
/*  77 */     readFrom(fileName);
/*     */   }
/*     */ 
/*     */   public void write(String fileName)
/*     */   {
/*  82 */     this.propertyFile = (fileName == null ? this.propertyFile : fileName);
/*  83 */     write();
/*     */   }
/*     */ 
/*     */   public String getProperty(String name)
/*     */   {
/*  88 */     return this.properties.getProperty(name);
/*     */   }
/*     */ 
/*     */   public synchronized void setProperty(String name, String value)
/*     */   {
/*  93 */     this.properties.setProperty(name, value);
/*     */   }
/*     */ 
/*     */   public void setPropertyFile(String fileName)
/*     */   {
/*  98 */     this.propertyFile = (fileName == null ? this.propertyFile : fileName);
/*     */   }
/*     */ 
/*     */   public void setPropertyHeader(String header)
/*     */   {
/* 103 */     this.propertyHeader = (header == null ? this.propertyHeader : header);
/*     */   }
/*     */ 
/*     */   public void write()
/*     */   {
/*     */     try {
/* 109 */       File f = new File(this.propertyFile);
/* 110 */       FileOutputStream os = new FileOutputStream(f);
/* 111 */       this.properties.store(os, this.propertyHeader);
/* 112 */       os.close();
/* 113 */       Log.getInstance().log("Preferences saved successfully!");
/*     */     } catch (FileNotFoundException e) {
/* 115 */       Log.getInstance().err("Error while saving Preferences File Not Found!\n" + e.getMessage());
/*     */     } catch (IOException e) {
/* 117 */       Log.getInstance().err("Error while saving Preferences IOError!\n" + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 124 */     StringBuffer buffer = new StringBuffer();
/* 125 */     for (Iterator it = this.properties.keySet().iterator(); it.hasNext(); )
/*     */     {
/* 127 */       String currKey = it.next().toString();
/* 128 */       String currVal = System.getProperty(currKey);
/* 129 */       buffer.append(currKey + "\t" + currVal + "\n");
/*     */     }
/* 131 */     return buffer.toString();
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.GlobalPreferences
 * JD-Core Version:    0.6.0
 */