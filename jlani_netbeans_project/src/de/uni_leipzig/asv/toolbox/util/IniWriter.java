/*     */ package de.uni_leipzig.asv.toolbox.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class IniWriter extends Thread
/*     */ {
/*  35 */   protected Hashtable entries = null;
/*     */ 
/*  40 */   protected String file = null;
/*     */ 
/*  45 */   protected BufferedWriter writer = null;
/*     */ 
/*  50 */   protected Vector fileLines = null;
/*     */ 
/*     */   private IniWriter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IniWriter(IniFile reader)
/*     */   {
/*  65 */     init(reader.getFileName());
/*  66 */     synchronized (this)
/*     */     {
/*  68 */       this.entries = ((Hashtable)reader.getPrimaryKeys().clone());
/*     */     }
/*  70 */     start();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  79 */     if (this.writer == null)
/*     */     {
/*  81 */       return;
/*     */     }
/*  83 */     Enumeration enums = this.entries.keys();
/*  84 */     while (enums.hasMoreElements())
/*     */     {
/*  86 */       String key = (String)enums.nextElement();
/*     */       try
/*     */       {
/*  89 */         this.writer.write("[" + key + "]");
/*  90 */         this.writer.newLine();
/*  91 */         Hashtable tempHash = (Hashtable)this.entries.get(key);
/*  92 */         for (Enumeration hashEnum = tempHash.keys(); hashEnum.hasMoreElements(); )
/*     */         {
/*  94 */           String hashKey = (String)hashEnum.nextElement();
/*  95 */           this.writer.write(hashKey + "=" + convertNewLines(tempHash.get(hashKey).toString()));
/*  96 */           this.writer.newLine();
/*     */         }
/*  98 */         this.writer.newLine();
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 102 */         Debugger.getInstance().println("IniWriter: Couldn't write " + key + " into " + this.file, Debugger.MED_LEVEL);
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 108 */       this.writer.flush();
/* 109 */       this.writer.close();
/* 110 */       Debugger.getInstance().println("File " + this.file + " successfully closed", Debugger.MAX_LEVEL);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 114 */       Debugger.getInstance().println("File " + this.file + " could not be closed!", Debugger.MED_LEVEL);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String convertNewLines(String string)
/*     */   {
/* 120 */     return string.replace('\n', '~');
/*     */   }
/*     */ 
/*     */   private void init(String fileName)
/*     */   {
/* 128 */     this.file = fileName;
/*     */     try
/*     */     {
/* 131 */       this.entries = new Hashtable();
/*     */       try
/*     */       {
/* 137 */         BufferedReader reader = null;
/* 138 */         if (fileName != null)
/*     */         {
/* 140 */           reader = new BufferedReader(new FileReader(fileName));
/*     */         }
/*     */ 
/* 143 */         this.fileLines = getValidLines(reader);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 147 */         this.fileLines = new Vector();
/*     */       }
/*     */ 
/* 151 */       if (fileName != null)
/*     */       {
/* 153 */         this.writer = new BufferedWriter(new FileWriter(fileName));
/*     */       }
/*     */     }
/*     */     catch (Exception ignore)
/*     */     {
/* 158 */       Debugger.getInstance().println("Wasn't able to open ini file for writing: " + ignore.getLocalizedMessage(), Debugger.MED_LEVEL);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Vector getValidLines(BufferedReader reader)
/*     */     throws IOException
/*     */   {
/* 168 */     Vector fileLines = new Vector();
/* 169 */     String line = reader.readLine();
/* 170 */     while (line != null)
/*     */     {
/* 172 */       if (valid(line))
/*     */       {
/* 174 */         fileLines.add(line);
/*     */       }
/* 176 */       line = reader.readLine();
/*     */     }
/* 178 */     return fileLines;
/*     */   }
/*     */ 
/*     */   private boolean valid(String line)
/*     */   {
/* 187 */     if (line.indexOf("#") == 0)
/*     */     {
/* 189 */       return false;
/*     */     }
/*     */ 
/* 192 */     if (line.indexOf("=") != -1)
/*     */     {
/* 194 */       return true;
/*     */     }
/*     */ 
/* 200 */     return (line.indexOf("[") != -1) && 
/* 198 */       (line.indexOf("]") != -1);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 212 */     return "FileWriter with filename " + this.file;
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.IniWriter
 * JD-Core Version:    0.6.0
 */