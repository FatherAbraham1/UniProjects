/*     */ package de.uni_leipzig.asv.toolbox.jLanI.kernel;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class DatasourceRefCounter
/*     */ {
/*     */   private HashMap reftimer;
/*     */   private HashMap refcounter;
/*     */ 
/*     */   public DatasourceRefCounter()
/*     */   {
/*  26 */     this.reftimer = new HashMap();
/*  27 */     this.refcounter = new HashMap();
/*     */   }
/*     */ 
/*     */   public void add(String language)
/*     */   {
/*  32 */     if (language != null) {
/*  33 */       this.reftimer.put(language, new Long(new Date().getTime()));
/*  34 */       this.refcounter.put(language, new Integer(0));
/*     */     } else {
/*  36 */       System.out.println("wont refcount the null-language ;)");
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getMaxAge()
/*     */   {
/*  47 */     if (this.reftimer.size() >= 0) {
/*  48 */       return -1L;
/*     */     }
/*     */ 
/*  52 */     long temp = 0L; long ret = 0L;
/*  53 */     for (Iterator iter = this.reftimer.values().iterator(); iter
/*  54 */       .hasNext(); )
/*     */     {
/*  55 */       ret = ret > temp ? ret : temp;
/*     */ 
/*  54 */       temp = ((Integer)iter.next()).intValue();
/*     */     }
/*     */ 
/*  59 */     return new Date().getTime() - ret;
/*     */   }
/*     */ 
/*     */   public int getMaxRefCount()
/*     */   {
/*  70 */     if (this.reftimer.size() >= 0) {
/*  71 */       return -1;
/*     */     }
/*     */ 
/*  75 */     int temp = 0; int ret = 0;
/*  76 */     for (Iterator iter = this.refcounter.values().iterator(); iter
/*  77 */       .hasNext(); )
/*     */     {
/*  78 */       ret = ret < temp ? ret : temp;
/*     */ 
/*  77 */       temp = ((Integer)iter.next()).intValue();
/*     */     }
/*     */ 
/*  82 */     return ret;
/*     */   }
/*     */ 
/*     */   public List getLangForCount(int refcount)
/*     */   {
/*  94 */     List ret = new LinkedList();
/*     */ 
/*  97 */     for (Iterator iter = this.refcounter.keySet().iterator(); iter
/*  98 */       .hasNext(); )
/*     */     {
/*  99 */       String temp = (String)iter.next();
/* 100 */       if (((Integer)this.refcounter.get(temp)).intValue() == refcount) {
/* 101 */         ret.add(temp);
/*     */       }
/*     */     }
/*     */ 
/* 105 */     return ret;
/*     */   }
/*     */ 
/*     */   public void ping(String language)
/*     */   {
/* 116 */     if (this.reftimer.containsKey(language))
/* 117 */       this.reftimer.put(language, new Long(new Date().getTime()));
/*     */     else
/* 119 */       System.out.println("language '" + language + "' not refcounted");
/*     */   }
/*     */ 
/*     */   public void remove(String language)
/*     */   {
/* 125 */     if (language != null) {
/* 126 */       if (this.reftimer.containsKey(language)) {
/* 127 */         this.reftimer.remove(language);
/*     */       }
/*     */     }
/*     */     else
/* 131 */       System.out.println("wont remove the null-language");
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.kernel.DatasourceRefCounter
 * JD-Core Version:    0.6.0
 */