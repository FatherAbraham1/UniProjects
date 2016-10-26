/*     */ package de.uni_leipzig.asv.toolbox.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class IniFile
/*     */ {
/*     */   public static final String PKEY_LEFT_BRACKET = "[";
/*     */   public static final String PKEY_RIGHT_BRACKET = "]";
/*     */   public static final String ASSIGNMENT = "=";
/*     */   public static final String COMMENT = "#";
/*  41 */   protected Hashtable entries = null;
/*     */ 
/*  47 */   protected String file = null;
/*     */ 
/*     */   public IniFile()
/*     */   {
/*  55 */     init(null);
/*     */   }
/*     */ 
/*     */   public IniFile(File file)
/*     */   {
/*  63 */     init(file.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   public IniFile(String fileName)
/*     */   {
/*  72 */     init(fileName);
/*     */   }
/*     */ 
/*     */   private void init(String fileName)
/*     */   {
/*  80 */     this.file = fileName;
/*  81 */     IniReader reader = null;
/*     */     try
/*     */     {
/*  84 */       reader = new IniReader(fileName);
/*  85 */       this.entries = reader.getPrimaryKeys();
/*     */     }
/*     */     catch (Exception ignore)
/*     */     {
/*  89 */       this.entries = new Hashtable();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Hashtable getPrimaryKey(String key)
/*     */   {
/*  99 */     return (Hashtable)this.entries.get(key);
/*     */   }
/*     */ 
/*     */   public String getValue(String primaryKey, String secondaryKey)
/*     */   {
/* 107 */     return (String)((Hashtable)this.entries.get(primaryKey)).get(secondaryKey);
/*     */   }
/*     */ 
/*     */   public void setValues(String primaryKey, Hashtable hash)
/*     */   {
/* 116 */     if ((primaryKey == null) || (hash == null) || (primaryKey.length() < 1) || 
/* 117 */       (hash.size() < 1))
/*     */     {
/* 119 */       return;
/*     */     }
/* 121 */     this.entries.put(primaryKey, hash);
/* 122 */     IniWriter writer = new IniWriter(this);
/*     */   }
/*     */ 
/*     */   public void setValue(String primaryKey, String secondaryKey, String value)
/*     */   {
/* 130 */     if ((primaryKey == null) || (secondaryKey == null) || (value == null) || 
/* 131 */       (primaryKey.length() < 1) || (secondaryKey.length() < 1))
/*     */     {
/* 133 */       return;
/*     */     }
/* 135 */     Object secVal = this.entries.get(primaryKey);
/* 136 */     Hashtable tempHash = null;
/* 137 */     if (secVal == null)
/*     */     {
/* 139 */       tempHash = new Hashtable();
/*     */     }
/*     */     else
/*     */     {
/* 143 */       tempHash = (Hashtable)secVal;
/*     */     }
/* 145 */     tempHash.put(secondaryKey, value);
/* 146 */     this.entries.put(primaryKey, tempHash);
/* 147 */     IniWriter writer = new IniWriter(this);
/*     */   }
/*     */ 
/*     */   public boolean existsKeyPair(String primKey, String secKey)
/*     */   {
/* 155 */     Hashtable val = (Hashtable)this.entries.get(primKey);
/* 156 */     if (val != null)
/*     */     {
/* 158 */       if (val.containsKey(secKey))
/*     */       {
/* 160 */         return true;
/*     */       }
/*     */     }
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   public Hashtable getPrimaryKeys()
/*     */   {
/* 171 */     return this.entries;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 180 */     return this.file;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 189 */     return "IniFile with filename " + this.file;
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.IniFile
 * JD-Core Version:    0.6.0
 */