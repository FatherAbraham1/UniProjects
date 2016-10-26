/*    */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ToStringTools
/*    */ {
/*    */   public static String hashMapToString(Map map)
/*    */   {
/* 21 */     StringBuffer buffer = new StringBuffer();
/* 22 */     int i = 0;
/* 23 */     buffer.append("[");
/* 24 */     for (Iterator it = map.keySet().iterator(); it.hasNext(); )
/*    */     {
/* 26 */       String currKey = it.next().toString();
/* 27 */       String currVal = map.get(currKey).toString();
/* 28 */       buffer.append("{" + currKey + "," + currVal + "}" + (i++ < map.keySet().size() - 1 ? "\n" : ""));
/*    */     }
/* 30 */     buffer.append("]");
/* 31 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */   public static String propertiesToString(Properties map)
/*    */   {
/* 36 */     StringBuffer buffer = new StringBuffer();
/* 37 */     int i = 0;
/* 38 */     buffer.append("[");
/* 39 */     for (Iterator it = map.keySet().iterator(); it.hasNext(); )
/*    */     {
/* 41 */       String currKey = it.next().toString();
/* 42 */       String currVal = System.getProperty(currKey);
/* 43 */       buffer.append("{" + currKey + "," + currVal + "}" + (i++ < map.keySet().size() - 1 ? "\n" : ""));
/*    */     }
/* 45 */     buffer.append("]");
/* 46 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */   public static String arrayToString(Object[] array)
/*    */   {
/* 51 */     StringBuffer buffer = new StringBuffer();
/* 52 */     buffer.append("[");
/* 53 */     for (int i = 0; i < array.length; i++)
/*    */     {
/* 55 */       buffer.append(array[i].toString() + (i < array.length - 1 ? "\n" : ""));
/*    */     }
/* 57 */     buffer.append("]");
/* 58 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */   public static String doubleMatrixToString(double[][] matrix)
/*    */   {
/* 63 */     StringBuffer buffer = new StringBuffer();
/* 64 */     buffer.append("[");
/* 65 */     for (int i = 0; i < matrix.length; i++) {
/* 66 */       for (int j = 0; j < matrix[i].length; j++) {
/* 67 */         buffer.append(matrix[i][j] + (j < matrix[i].length - 1 ? "," : ""));
/*    */       }
/* 69 */       buffer.append(i < matrix.length - 1 ? "\n" : "");
/*    */     }
/* 71 */     buffer.append("]");
/* 72 */     return buffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.ToStringTools
 * JD-Core Version:    0.6.0
 */