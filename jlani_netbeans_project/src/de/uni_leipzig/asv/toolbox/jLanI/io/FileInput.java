/*    */ package de.uni_leipzig.asv.toolbox.jLanI.io;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileReader;
/*    */ 
/*    */ public class FileInput
/*    */   implements Input
/*    */ {
/*    */   private BufferedReader in;
/*    */   private File file;
/*    */   private String fileName;
/* 16 */   private int id = 0;
/* 17 */   private int curProg = 0;
/*    */   private int sentLength;
/*    */ 
/*    */   public FileInput(String fileName)
/*    */     throws FileNotFoundException
/*    */   {
/* 22 */     this.fileName = fileName;
/* 23 */     this.file = new File(fileName);
/* 24 */     this.in = new BufferedReader(new FileReader(this.file));
/*    */   }
/*    */   public String getFileName(){
               return this.fileName;
           }
/*    */   public String readSent() throws Exception
/*    */   {
/* 29 */     String sent = this.in.readLine();

/* 30 */        this.id += 1;
/* 31 */        if (sent == null )
/* 32 */            this.in.close();
/*    */        else {
                    
/* 34 */            this.sentLength = sent.length();
                    
/*    */        }
/* 36 */     return sent;

/*    */   }
            
/*    */   public String getId() {
/* 39 */     return String.valueOf(this.id);
/*    */   }
/* 41 */   public int getMaxProgress() { return (int)this.file.length(); } 
/*    */   public int getIncProgress() {
/* 43 */     return this.sentLength + 2;
/*    */   }
/*    */   public int getCurrentProgress() {
/* 46 */     this.curProg += getIncProgress();
/* 47 */     return this.curProg;
/*    */   }
/*    */


 }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.io.FileInput
 * JD-Core Version:    0.6.0
 */