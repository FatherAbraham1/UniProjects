/*     */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Log
/*     */ {
/*     */   public static final int DEFAULT_LOGMODE = 3;
/*     */   public static final boolean DEFAULT_DEBUG_MODE = true;
/*     */   public static final boolean DEFAULT_TIMESTAMP_MODE = true;
/*     */   public static final String DEFAULT_LOGPREFIX = "";
/*  37 */   private String logPrefix = "";
/*     */ 
/*  39 */   private static Log singleton = null;
/*     */ 
/*  41 */   private int logMode = 3;
/*     */ 
/*  43 */   private boolean debugMode = true;
/*     */ 
/*  45 */   private boolean timeStampMode = true;
/*     */ 
/*  47 */   private long initTime = 0L;
/*     */ 
/*  49 */   private int count = 0;
/*     */ 
/*  51 */   private StringBuffer logBuffer = null;
/*     */ 
/*  53 */   private int bufferLimit = -1;
/*     */ 
/*     */   private Log()
/*     */   {
/*  57 */     this.logBuffer = new StringBuffer();
/*  58 */     singleton = this;
/*  59 */     Date d = new Date();
/*  60 */     this.initTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public static Log getInstance()
/*     */   {
/*  65 */     if (singleton == null) {
/*  66 */       return new Log();
/*     */     }
/*  68 */     return singleton;
/*     */   }
/*     */ 
/*     */   public void saveLog()
/*     */   {
/*     */     try
/*     */     {
/*  80 */       File logFile = new File(this.logPrefix + this.initTime + (this.count++ > 0 ? "_" + this.count : "") + ".log");
/*  81 */       BufferedWriter logWriter = null;
/*  82 */       if (logFile.exists())
/*  83 */         logWriter = new BufferedWriter(new FileWriter(logFile, true));
/*     */       else {
/*  85 */         logWriter = new BufferedWriter(new FileWriter(logFile));
/*     */       }
/*  87 */       logWriter.write(this.logBuffer.toString());
/*  88 */       logWriter.close();
/*  89 */       this.logBuffer = new StringBuffer();
/*     */     }
/*     */     catch (IOException e) {
/*  92 */       System.err.println("Error writing logfile!\n\t" + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized void write(String string, boolean error)
/*     */   {
/*  98 */     switch (this.logMode)
/*     */     {
/*     */     case 1:
/* 101 */       if (error)
/* 102 */         System.err.print(string);
/*     */       else {
/* 104 */         System.out.print(string);
/*     */       }
/* 106 */       break;
/*     */     case 2:
/* 109 */       appendToLogBuffer(string);
/* 110 */       break;
/*     */     case 3:
/* 113 */       if (error)
/* 114 */         System.err.print(string);
/*     */       else {
/* 116 */         System.out.print(string);
/*     */       }
/* 118 */       appendToLogBuffer(string);
/* 119 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void appendToLogBuffer(String string)
/*     */   {
/* 129 */     if ((this.bufferLimit != -1) && (this.logBuffer.toString().length() + string.length() > this.bufferLimit)) {
/* 130 */       saveLog();
/*     */     }
/*     */ 
/* 133 */     this.logBuffer.append(string);
/*     */   }
/*     */ 
/*     */   public synchronized void log(String string)
/*     */   {
/* 139 */     String logString = (this.timeStampMode ? "(" + getTime() + ")" : "") + " LOG : " + string + "\n";
/* 140 */     write(logString, false);
/*     */   }
/*     */ 
/*     */   public synchronized void err(String string)
/*     */   {
/* 145 */     String logString = (this.timeStampMode ? "(" + getTime() + ")" : "") + " ERR : " + string + "\n";
/* 146 */     write(logString, true);
/*     */   }
/*     */ 
/*     */   public synchronized void debug(String string)
/*     */   {
/* 151 */     if (this.debugMode)
/*     */     {
/* 153 */       String logString = (this.timeStampMode ? "(" + getTime() + ")" : "") + " DEBUG : " + string + "\n";
/* 154 */       write(logString, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDebug(boolean b)
/*     */   {
/* 160 */     this.debugMode = b;
/*     */   }
/*     */ 
/*     */   public void setTimeStampMode(boolean b)
/*     */   {
/* 165 */     this.debugMode = b;
/*     */   }
/*     */ 
/*     */   public void setLogMode(int i)
/*     */   {
/* 170 */     this.logMode = i;
/*     */   }
/*     */ 
/*     */   public void setLogPrefix(String prefix)
/*     */   {
/* 175 */     this.logPrefix = (prefix == null ? "" : prefix);
/*     */   }
/*     */ 
/*     */   public void setBufferLimit(int limit)
/*     */   {
/* 180 */     this.bufferLimit = (limit > 0 ? limit : this.bufferLimit);
/*     */   }
/*     */ 
/*     */   private String getTime()
/*     */   {
/* 187 */     SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
/* 188 */     Date now = new Date();
/* 189 */     return formatter.format(now);
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.Log
 * JD-Core Version:    0.6.0
 */