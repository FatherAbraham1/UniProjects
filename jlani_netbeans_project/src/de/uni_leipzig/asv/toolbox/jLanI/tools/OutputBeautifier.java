/*    */ package de.uni_leipzig.asv.toolbox.jLanI.tools;
/*    */ 
/*    */ import de.uni_leipzig.asv.toolbox.jLanI.kernel.Response;
/*    */ import java.io.PrintStream;
/*    */ import java.text.DecimalFormat;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class OutputBeautifier
/*    */ {
/*    */   public static String beautify(Response response)
/*    */   {
/* 24 */     Map results = response.getResult();
/* 25 */     System.err.println(ToStringTools.hashMapToString(results));
/*    */ 
/* 27 */     Iterator it = results.keySet().iterator();
/*    */ 
/* 29 */     if (!it.hasNext()) {
/* 30 */       return "empty";
/*    */     }
/*    */ 
/* 33 */     String top1 = it.next().toString();
/* 34 */     Double top1_count = (Double)results.get(top1);
/*    */ 
/* 36 */     if (!it.hasNext()) {
/* 37 */       return top1 + " " + "100%";
/*    */     }
/*    */ 
/* 40 */     String top2 = it.next().toString();
/* 41 */     Double top2_count = (Double)results.get(top1);
/*    */ 
/* 43 */     if (top2_count.doubleValue() > top1_count.doubleValue())
/*    */     {
/* 45 */       Double tmp = top1_count;
/* 46 */       top1_count = top2_count;
/* 47 */       top2_count = tmp;
/*    */ 
/* 49 */       String tmp_ = top1;
/* 50 */       top1 = top2;
/* 51 */       top2 = tmp_;
/*    */     }
/*    */ 
/* 54 */     double count = top1_count.doubleValue() + top2_count.doubleValue();
/*    */ 
/* 56 */     while (it.hasNext())
/*    */     {
/* 59 */       String currLan = it.next().toString();
/* 60 */       Double currLanCount = (Double)results.get(currLan);
/*    */ 
/* 63 */       if (top1_count.doubleValue() < currLanCount.doubleValue())
/*    */       {
/* 65 */         top2 = top1;
/* 66 */         top2_count = top1_count;
/* 67 */         top1 = currLan;
/* 68 */         top1_count = currLanCount;
/*    */       }
/* 72 */       else if (top2_count.doubleValue() < currLanCount.doubleValue())
/*    */       {
/* 74 */         top2 = currLan;
/* 75 */         top2_count = currLanCount;
/*    */       }
/*    */ 
/* 79 */       count += currLanCount.doubleValue();
/*    */     }
/*    */ 
/* 82 */     double top1_perc = top1_count.doubleValue() / count * 100.0D;
/* 83 */     double top2_perc = top2_count.doubleValue() / count * 100.0D;
/*    */ 
/* 85 */     DecimalFormat df = new DecimalFormat("##0.00");
/* 86 */     return top1 + " " + df.format(top1_perc) + "\t" + top2 + " " + df.format(top2_perc);
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.tools.OutputBeautifier
 * JD-Core Version:    0.6.0
 */