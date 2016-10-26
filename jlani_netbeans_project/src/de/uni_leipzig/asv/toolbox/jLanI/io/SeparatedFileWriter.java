package de.uni_leipzig.asv.toolbox.jLanI.io;

import de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeparatedFileWriter {

    String outfolder = "out";
    private FileWriter fstream;
    private BufferedWriter out;

    public SeparatedFileWriter(String outputfolder) {
        outfolder = outputfolder;
        boolean status;
        status = new File(outfolder).mkdir();

    }

    public void writeSentToLangfile(String lang, String sent) throws IOException {
        this.fstream = new FileWriter(outfolder + "/" + lang + ".txt", true);
        this.out = new BufferedWriter(this.fstream);
        try {

            this.out.write(sent + System.getProperty("line.separator"));
            this.out.close();
        } catch (IOException ex) {
            Logger.getLogger(SeparatedFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.out.close();
            } catch (IOException ex) {
                Logger.getLogger(SeparatedFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeSentWithMetaInfo(String lang, LangResult res, String sent) throws Exception {
        this.fstream = new FileWriter(outfolder + "/" + lang + ".txt", true);
        this.out = new BufferedWriter(this.fstream);
        this.out.write(res.toString() + "\t" + sent + System.getProperty("line.separator"));
        this.out.close();
    }

    public void close() {
        try {
            this.out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
