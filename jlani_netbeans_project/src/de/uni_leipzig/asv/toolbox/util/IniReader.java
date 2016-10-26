/*     */ package de.uni_leipzig.asv.toolbox.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class IniReader
/*     */ {
/*  34 */   protected Hashtable entries = null;
/*     */ 
/*  36 */   protected String file = null;
/*     */ 
/*     */   private IniReader()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IniReader(File file)
/*     */     throws FileNotFoundException
/*     */   {
/*  51 */     init(file.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   public IniReader(String fileName)
/*     */     throws FileNotFoundException
/*     */   {
/*  60 */     init(fileName);
/*     */   }
/*     */ 
/*     */   private void init(String fileName)
/*     */     throws FileNotFoundException
/*     */   {
/*  68 */     this.file = fileName;
/*     */     try
/*     */     {
/*  71 */       this.entries = new Hashtable();
/*     */ 
/*  73 */       BufferedReader reader = null;
/*  74 */       if (fileName != null)
/*     */       {
/*  76 */         reader = new BufferedReader(new FileReader(fileName));
/*     */       }
/*     */ 
/*  79 */       Vector fileLines = getValidLines(reader);
/*  80 */       this.entries = fillEntries(this.entries, fileLines);
/*     */     }
/*     */     catch (FileNotFoundException fnf)
/*     */     {
/*  84 */       this.entries = new Hashtable();
/*  85 */       throw fnf;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  89 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Vector getValidLines(BufferedReader reader)
/*     */     throws IOException
/*     */   {
/* 100 */     Vector fileLines = new Vector();
/* 101 */     if (reader == null)
/*     */     {
/* 103 */       return fileLines;
/*     */     }
/* 105 */     String line = reader.readLine();
/* 106 */     while (line != null)
/*     */     {
/* 108 */       if (valid(line))
/*     */       {
/* 110 */         fileLines.add(line);
/*     */       }
/* 112 */       line = reader.readLine();
/*     */     }
/* 114 */     return fileLines;
/*     */   }
/*     */ 
/*     */   private Hashtable fillEntries(Hashtable table, Vector lines)
/*     */   {
/* 122 */     Hashtable temp = new Hashtable();
/* 123 */     String line = null;
/* 124 */     lines = reverse(lines);
/*     */ 
/* 126 */     for (Enumeration e = lines.elements(); e.hasMoreElements(); )
/*     */     {
/* 128 */       line = (String)e.nextElement();
/*     */ 
/* 130 */       if (isPrimaryKey(line))
/*     */       {
/* 132 */         if (temp != null)
/*     */         {
/* 134 */           table.put(line.substring(1, line.length() - 1), temp);
/*     */         }
/* 136 */         temp = new Hashtable();
/*     */       } else {
/* 138 */         if (!isSecondaryKey(line))
/*     */           continue;
/* 140 */         if (temp == null)
/*     */         {
/* 142 */           Debugger.getInstance().println("malformed ini file.", Debugger.MED_LEVEL);
/*     */         }
/* 144 */         temp.put(getKey(line), convertNewLines(getValue(line)));
/*     */       }
/*     */     }
/* 147 */     return table;
/*     */   }
/*     */ 
/*     */   private String convertNewLines(String string)
/*     */   {
/* 152 */     if (string.lastIndexOf("~") > -1)
/*     */     {
/* 154 */       StringTokenizer tokenizer = new StringTokenizer(string, "~");
/* 155 */       String retVal = "";
/* 156 */       int i = 0;
/* 157 */       while (tokenizer.hasMoreTokens())
/*     */       {
/* 159 */         String curToken = tokenizer.nextToken();
/* 160 */         if (i > 0) retVal = retVal + "\n" + curToken; else
/* 161 */           retVal = retVal + curToken;
/* 162 */         i++;
/*     */       }
/* 164 */       return retVal;
/*     */     }
/* 166 */     return string;
/*     */   }
/*     */ 
/*     */   private Vector reverse(Vector lines)
/*     */   {
/* 174 */     Vector newLines = new Vector(lines.capacity());
/* 175 */     for (Enumeration e = lines.elements(); e.hasMoreElements(); )
/*     */     {
/* 177 */       newLines.add(0, e.nextElement());
/*     */     }
/* 179 */     return newLines;
/*     */   }
/*     */ 
/*     */   private String getKey(String line)
/*     */   {
/* 188 */     String retVal = line.substring(0, line.indexOf("="));
/* 189 */     retVal = retVal.trim();
/* 190 */     return retVal;
/*     */   }
/*     */ 
/*     */   private String getValue(String line)
/*     */   {
/* 200 */     String retVal = line.substring(line.indexOf("=") + "=".length(), line.length());
/* 201 */     retVal = retVal.trim();
/* 202 */     return retVal;
/*     */   }
/*     */ 
/*     */   private boolean valid(String line)
/*     */   {
/* 211 */     if (line.indexOf("#") == 0)
/*     */     {
/* 213 */       return false;
/*     */     }
/*     */ 
/* 216 */     if (line.indexOf("=") != -1)
/*     */     {
/* 218 */       return true;
/*     */     }
/*     */ 
/* 224 */     return (line.indexOf("[") != -1) && 
/* 222 */       (line.indexOf("]") != -1);
/*     */   }
/*     */ 
/*     */   private boolean isSecondaryKey(String line)
/*     */   {
/* 235 */     if ((line.indexOf("[") != -1) && 
/* 236 */       (line.indexOf("]") != -1))
/*     */     {
/* 238 */       return false;
/*     */     }
/*     */ 
/* 242 */     return line.indexOf("=") != -1;
/*     */   }
/*     */ 
/*     */   private boolean isPrimaryKey(String line)
/*     */   {
/* 255 */     return (line.indexOf("[") != -1) && 
/* 253 */       (line.indexOf("]") != -1);
/*     */   }
/*     */ 
/*     */   public Hashtable getPrimaryKeys()
/*     */   {
/* 265 */     return this.entries;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 274 */     return "IniReader with filename " + this.file;
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.IniReader
 * JD-Core Version:    0.6.0
 */