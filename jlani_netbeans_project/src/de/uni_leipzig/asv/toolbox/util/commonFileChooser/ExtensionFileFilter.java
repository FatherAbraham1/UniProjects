/*    */ package de.uni_leipzig.asv.toolbox.util.commonFileChooser;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class ExtensionFileFilter extends FileFilter
/*    */ {
/* 17 */   private String description = "";
/* 18 */   private ArrayList extensions = new ArrayList();
/*    */ 
/*    */   public void addExtension(String ext)
/*    */   {
/* 27 */     if (!ext.startsWith(".")) {
/* 28 */       ext = "." + ext;
/*    */     }
/* 30 */     this.extensions.add(ext.toLowerCase());
/*    */   }
/*    */ 
/*    */   public void clearExtensions()
/*    */   {
/* 38 */     this.extensions = null;
/* 39 */     this.extensions = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void setDescription(String desc)
/*    */   {
/* 49 */     this.description = desc;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 60 */     return this.description;
/*    */   }
/*    */ 
/*    */   public boolean accept(File f)
/*    */   {
/* 72 */     if (f.isDirectory()) {
/* 73 */       return true;
/*    */     }
/*    */ 
/* 76 */     String name = f.getName().toLowerCase();
/*    */ 
/* 78 */     for (int i = 0; i < this.extensions.size(); i++)
/*    */     {
/* 80 */       if (name.endsWith((String)this.extensions.get(i))) {
/* 81 */         return true;
/*    */       }
/*    */     }
/*    */ 
/* 85 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.commonFileChooser.ExtensionFileFilter
 * JD-Core Version:    0.6.0
 */