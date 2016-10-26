/*    */ package de.uni_leipzig.asv.toolbox.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Debugger
/*    */ {
/* 11 */   private static Debugger instance = null;
/* 12 */   public static int NUL_LEVEL = 0;
/* 13 */   public static int MIN_LEVEL = 1;
/* 14 */   public static int MED_LEVEL = 2;
/* 15 */   public static int MAX_LEVEL = 3;
/* 16 */   private static int level = 0;
/*    */ 
/*    */   public static Debugger getInstance()
/*    */   {
/* 30 */     if (instance == null)
/*    */     {
/* 32 */       synchronized (Debugger.class)
/*    */       {
/* 34 */         instance = new Debugger();
/*    */       }
/*    */     }
/* 37 */     return instance;
/*    */   }
/*    */ 
/*    */   public void println(String message, int level)
/*    */   {
/* 46 */     level = new Integer(Options.getInstance().getGenDebugLevel()).intValue();
/* 47 */     if (level <= level)
/*    */     {
/* 49 */       System.err.println("Debugger: [" + message + "]");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.Debugger
 * JD-Core Version:    0.6.0
 */