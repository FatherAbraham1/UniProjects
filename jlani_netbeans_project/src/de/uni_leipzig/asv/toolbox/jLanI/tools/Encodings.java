/*    */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Encodings
/*    */ {
/*    */   public static final String BASIC_ENCODING_SET = "java_encodings_basic";
/*    */   public static final String EXTENDED_ENCODING_SET = "java_encodings_extended";
/* 24 */   private Map encodings = null;
/*    */ 
/*    */   public Encodings()
/*    */   {
/* 28 */     this.encodings = new HashMap();
/*    */   }
/*    */ 
/*    */   public void read(String fileName) throws Exception
/*    */   {
/* 33 */     read(new FileInputStream(fileName));
/*    */   }
/*    */ 
/*    */   public void read(InputStream stream) throws Exception
/*    */   {
/* 38 */     BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "ASCII"));
/* 39 */     String line = null;
/* 40 */     while ((line = reader.readLine()) != null) {
/* 41 */       String[] parts = line.split("\t");
/* 42 */       this.encodings.put(parts[0], new Encoding(parts[0], parts[1]));
/*    */     }
/* 44 */     reader.close();
/*    */   }
/*    */ 
/*    */   public void loadEncodingSet(String setName) throws Exception
/*    */   {
/* 49 */     read(getClass().getClassLoader().getResourceAsStream("tt/de/mai01dzx/data/" + setName + ".txt"));
/*    */   }
/*    */ 
/*    */   public Map getEncodings()
/*    */   {
/* 54 */     return this.encodings;
/*    */   }
/*    */ 
/*    */   public void addEncoding(Encoding enc)
/*    */   {
/* 59 */     this.encodings.put(enc.getName(), enc);
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.Encodings
 * JD-Core Version:    0.6.0
 */