/*    */ package de.uni_leipzig.asv.toolbox.jLanI.io;
/*    */ 
/*    */ import de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileWriter;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class FileOutput
/*    */   implements Output
/*    */ {
/*    */   private BufferedWriter out;
/*    */   private String fileName;
/*    */ 
/*    */   public FileOutput(String fileName)
/*    */     throws Exception
/*    */   {
/* 17 */     this.fileName = fileName;
/* 18 */     this.out = new BufferedWriter(new FileWriter(fileName, true));
/* 19 */     System.out.println(fileName);
/* 20 */     this.out.write("language1\tprobability1\tlanguage2\tprobability2\tsentences" + System.getProperty("line.separator"));
/*    */   }
/*    */ 
/*    */   public void writeSent(LangResult res, String id, String sent) throws Exception
/*    */   {
/* 25 */     this.out.write(res.toString() + "\t" + sent + System.getProperty("line.separator"));

/*    */   }
            public void writeSource(String source) throws Exception
/*    */   {
/* 25 */     this.out.write(source + System.getProperty("line.separator"));

/*    */   }

/*    */ 
/*    */   public void close()
/*    */   {
/*    */     try
/*    */     {
/* 32 */       this.out.close(); } catch (Exception e) {
/* 33 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.FileOutput
 * JD-Core Version:    0.6.0
 */