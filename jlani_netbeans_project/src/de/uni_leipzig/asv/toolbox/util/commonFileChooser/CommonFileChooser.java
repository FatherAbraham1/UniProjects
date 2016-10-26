/*     */ package de.uni_leipzig.asv.toolbox.util.commonFileChooser;
/*     */ 
/*     */ import java.io.File;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class CommonFileChooser
/*     */ {
/*     */   private JFileChooser chooser;
/*     */   private final ExtensionFileFilter eff;
/*     */   private String currentDirectory;
/*     */ 
/*     */   public CommonFileChooser(String[] extensions, String description)
/*     */   {
/*  29 */     this.chooser = new JFileChooser();
/*  30 */     this.eff = new ExtensionFileFilter();
/*     */ 
/*  32 */     this.currentDirectory = ".";
/*     */ 
/*  34 */     if (extensions != null)
/*     */     {
/*  36 */       int l = extensions.length;
/*  37 */       for (int i = 0; i < l; i++)
/*     */       {
/*  39 */         addExtension(extensions[i]);
/*     */       }
/*     */     }
/*     */ 
/*  43 */     if (description != null)
/*     */     {
/*  45 */       setDescription(description);
/*     */     }
/*     */ 
/*  48 */     if (extensions != null)
/*  49 */       this.chooser.setFileFilter(this.eff);
/*     */   }
/*     */ 
/*     */   public void addExtension(String extension)
/*     */   {
/*  60 */     this.eff.addExtension(extension);
/*     */ 
/*  62 */     this.chooser.setFileFilter(this.eff);
/*     */   }
/*     */ 
/*     */   public void clearExtensions()
/*     */   {
/*  70 */     this.eff.clearExtensions();
/*     */ 
/*  72 */     this.chooser.setFileFilter(this.chooser.getAcceptAllFileFilter());
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/*  83 */     this.eff.setDescription(description);
/*     */ 
/*  85 */     this.chooser.setFileFilter(this.eff);
/*     */   }
/*     */ 
/*     */   public String showDialogAndReturnFilename(JPanel parent, String label)
/*     */   {
/*  98 */     String filename = new String();
/*     */ 
/* 100 */     this.chooser.setCurrentDirectory(new File(this.currentDirectory));
/*     */ 
/* 102 */     int result = this.chooser.showDialog(parent, label);
/*     */ 
/* 104 */     if (result == 0)
/*     */     {
/* 107 */       filename = this.chooser.getSelectedFile().getPath();
/*     */ 
/* 109 */       this.currentDirectory = this.chooser.getSelectedFile().getAbsolutePath();
/* 110 */       if (isMSWindowsPlatform())
/*     */       {
/* 112 */         this.currentDirectory = this.currentDirectory.substring(0, this.currentDirectory.lastIndexOf("\\"));
/*     */       }
/*     */       else
/*     */       {
/* 116 */         this.currentDirectory = this.currentDirectory.substring(0, this.currentDirectory.lastIndexOf("/"));
/*     */       }
/*     */     }
/*     */     else {
/* 120 */       return null;
/*     */     }
/*     */ 
/* 123 */     return filename;
/*     */   }
/*     */ 
/*     */   private boolean isMSWindowsPlatform()
/*     */   {
/* 133 */     String os = System.getProperty("os.name");
/*     */ 
/* 135 */     return (os != null) && (os.startsWith("Windows"));
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.util.commonFileChooser.CommonFileChooser
 * JD-Core Version:    0.6.0
 */