/*    */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Iterator;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Preferences
/*    */ {
/* 21 */   private Properties properties = null;
/*    */ 
/* 23 */   private String name = null;
/*    */ 
/*    */   public Preferences(String name) throws IllegalArgumentException {
/* 26 */     if (name == null) {
/* 27 */       throw new IllegalArgumentException("name can not be null!");
/*    */     }
/*    */ 
/* 30 */     this.name = name;
/*    */ 
/* 32 */     File f = new File(this.name + ".ini");
/* 33 */     this.properties = new Properties();
/*    */ 
/* 35 */     if ((f.exists()) && (f.canRead()))
/* 36 */       readFrom(f.getAbsolutePath());
/*    */   }
/*    */ 
/*    */   private void readFrom(String fileName)
/*    */   {
/*    */     try {
/* 42 */       FileInputStream is = new FileInputStream(fileName);
/* 43 */       this.properties.load(is);
/* 44 */       is.close();
/*    */ 
/* 46 */       Log.getInstance().log(
/* 47 */         "Properties (" + this.name + 
/* 48 */         ")loaded successfully from File" + fileName + 
/* 49 */         "!\n");
/*    */     } catch (FileNotFoundException e) {
/* 51 */       Log.getInstance().err(
/* 52 */         "Preferences File not found! \n" + e.getMessage() + 
/* 53 */         "\n");
/*    */     } catch (IOException e) {
/* 55 */       Log.getInstance().err(
/* 56 */         "IOException during Preferences File init! \n" + 
/* 57 */         e.getMessage() + "\n");
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getProperty(String name)
/*    */   {
/* 64 */     return this.properties.getProperty(name);
/*    */   }
/*    */ 
/*    */   public synchronized void setProperty(String name, String value) {
/* 68 */     this.properties.setProperty(name, value);
/*    */   }
/*    */ 
/*    */   public void write()
/*    */   {
/*    */     try {
/* 74 */       File f = new File(this.name + ".ini");
/* 75 */       FileOutputStream os = new FileOutputStream(f);
/* 76 */       this.properties.store(os, this.name);
/* 77 */       os.close();
/* 78 */       Log.getInstance().log("Preferences (" + this.name + ") saved successfully!");
/*    */     } catch (FileNotFoundException e) {
/* 80 */       Log.getInstance().err(
/* 81 */         "Error while saving Preferences File (" + this.name + ") Not Found!\n" + 
/* 82 */         e.getMessage());
/*    */     } catch (IOException e) {
/* 84 */       Log.getInstance().err(
/* 85 */         "Error while saving Preferences (" + this.name + ") IOError!\n" + 
/* 86 */         e.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 92 */     StringBuffer buffer = new StringBuffer();
/* 93 */     for (Iterator it = this.properties.keySet().iterator(); it.hasNext(); ) {
/* 94 */       String currKey = it.next().toString();
/* 95 */       String currVal = System.getProperty(currKey);
/* 96 */       buffer.append(currKey + "\t" + currVal + "\n");
/*    */     }
/* 98 */     return buffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.Preferences
 * JD-Core Version:    0.6.0
 */