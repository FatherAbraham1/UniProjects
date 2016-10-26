/*    */ package de.uni_leipzig.asv.toolbox.util;
/*    */ 
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Output
/*    */ {
/*    */   public static void println()
/*    */   {
/* 29 */     println("");
/*    */   }
/*    */ 
/*    */   public static void print(String string)
/*    */   {
/* 37 */     String output = Options.getInstance().getGenOutputFile();
/* 38 */     if (!output.equalsIgnoreCase("stdout"))
/*    */     {
/*    */       try
/*    */       {
/* 42 */         PrintStream out = new PrintStream(new FileOutputStream(output, true));
/* 43 */         out.print(string);
/* 44 */         out.close();
/*    */       }
/*    */       catch (Exception ex)
/*    */       {
/* 48 */         System.err.println("Couldn't open outputfile " + output + " for appending.");
/*    */       }
/*    */     }
/* 51 */     System.out.print(string);
/*    */   }
/*    */ 
/*    */   public static void println(String string)
/*    */   {
/* 59 */     print(string + "\n");
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.Output
 * JD-Core Version:    0.6.0
 */