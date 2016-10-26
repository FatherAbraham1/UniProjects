package de.uni_leipzig.asv.toolbox.jLanI.main;

import de.uni_leipzig.asv.toolbox.jLanI.io.FileInput;
import de.uni_leipzig.asv.toolbox.jLanI.io.FileOutput;
import de.uni_leipzig.asv.toolbox.jLanI.io.Input;
import de.uni_leipzig.asv.toolbox.jLanI.io.Output;
import de.uni_leipzig.asv.toolbox.jLanI.io.SeparatedFileWriter;
import de.uni_leipzig.asv.toolbox.jLanI.io.StdInput;
import de.uni_leipzig.asv.toolbox.jLanI.io.StdOutput;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.DataSourceException;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.LanIKernel;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.LanIKernelInterface;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.LangResult;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.Request;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.RequestException;
import de.uni_leipzig.asv.toolbox.jLanI.kernel.Response;
import de.uni_leipzig.asv.toolbox.jLanI.tools.Log;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLIMain {

    private static final Log logger = Log.getInstance();

    //only needed if you want status reporting
    //private static int linecount = 0;
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("usage: CLIMain <inFile> <outFolder> <singleFile?> <withMetaInfo?>\n");
            System.out.println("<inFile>: SentenceFile\n <outFolder>: where jLanI writes the output\n <singleFile?>: write all data into one file (true/false)\n <withMetaInfo?>: write jLanI statistics into language files (true/false)");
            return;
        }

        Set languages = new HashSet();
        if (args.length > 4) {
            for (int i = 4; i < args.length; i++) {
                languages.add(args[i]);
            }
        }

        //only needed if you want status reporting
        //logger.log("counting lines...");
        //try {
        //    linecount = countLines(args[0]);
        //} catch (IOException ex) {
        //    Logger.getLogger(CLIMain.class.getName()).log(Level.SEVERE, null, ex);
        //}
        //logger.log("Lines in file: " + String.valueOf(linecount));
        if (args.length < 4) {
            start(getInput(args[0]), args[1], args[2], "false", languages);
        }
        if (args.length == 4) {
            start(getInput(args[0]), args[1], args[2], args[3], languages);
        }

    }

    private static Input getInput(String inFile) {
        Input input = null;
        try {
            if (inFile.equals("-")) {
                input = new StdInput();
            } else {
                input = new FileInput(inFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    private static Output getOutput(String outFile) {
        Output output = null;
        try {
            if (outFile.equals("-")) {
                output = new StdOutput();
            } else {
                output = new FileOutput(outFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return count;
        } finally {
            is.close();
        }
    }

    private static void writeSentsAndSourcesToFiles(Boolean singleFile, Boolean metaInfo, LanIKernelInterface k, Output mainout, List<String> sents, List<String> sourcetemp, String cur_lang, String cur_source, Set langs, SeparatedFileWriter writer) throws RequestException, DataSourceException, IOException, Exception {
        sourcetemp.clear();
        String current_sent;
        if (singleFile) {
            mainout.writeSource(cur_source);
        }
        for (int i = 0; i < sents.size(); i++) {
            current_sent = sents.get(i);
            //Request begin
            Request req = new Request();
            req.setLanguages(langs);
            req.setSentence(current_sent);
            Response res = k.evaluate(req);
            LangResult toWrite = res.getLangResult(0.15D, 2);
            //Request end
            if (singleFile) {
                mainout.writeSent(toWrite, "", current_sent);
            } else {
                cur_lang = toWrite.toString().split("\t")[0];
                if (!sourcetemp.contains(cur_lang)) {
                    writer.writeSentToLangfile(cur_lang, cur_source);
                    sourcetemp.add(cur_lang);
                }
                if (metaInfo) {
                    System.out.println(toWrite.toString());
                    writer.writeSentWithMetaInfo(cur_lang, toWrite, current_sent);
                } else {
                    writer.writeSentToLangfile(cur_lang, current_sent);
                }

            }
        }
    }

    //this method only prints the progress of the processing
    static void updateProgress(double progressPercentage) {
        logger.log("\r" + String.valueOf(Math.round(progressPercentage * 100)) + "% done");
    }

    private static void start(Input in, String outputfolder, String singleFile, String withMetaInformation, Set languages) {
        try {
            logger.log("StandaloneClientCLI: loading kernel!");
            LanIKernelInterface kern = LanIKernel.getInstance();
            logger.log("StandaloneClientCLI: loading kernel done!");
            Output out = null;
            boolean metaInfoSwitch = Boolean.parseBoolean(withMetaInformation);
            boolean singleFileSwitch = Boolean.parseBoolean(singleFile);
            if (singleFileSwitch) {
                out = getOutput(outputfolder + "/jLanI_mainOutput.txt");
            }
            String sent;
            SeparatedFileWriter sw = new SeparatedFileWriter(outputfolder);
            String current_source = null;
            String current_lang = null;
            List<String> sentlist = new ArrayList<String>();
            List<String> sourcewrittento = new ArrayList<String>();
            //int index = 1;
            //double progress;
            logger.log("Processing file: " + in.getFileName());
            logger.log("Output folder: " + outputfolder);
            while ((sent = in.readSent()) != null) {
                //only if you need status reports when processing large files
                //progress = (float) index / linecount;
                //updateProgress(progress);
                //index++;

                if (!(sent.contains("<source>") || sent.contains("<quelle>"))) {
                    sentlist.add(sent); //collect sentences of current source
                } else {
                    //we found a source tag
                    if (current_source != null && !current_source.equals(sent)) { //write to files if the source changed and was not null before
                        writeSentsAndSourcesToFiles(singleFileSwitch, metaInfoSwitch, kern, out, sentlist, sourcewrittento, current_lang, current_source, languages, sw);
                        sentlist.clear();
                    }
                    current_source = sent.trim();
                }
            }
            writeSentsAndSourcesToFiles(singleFileSwitch, metaInfoSwitch, kern, out, sentlist, sourcewrittento, current_lang, current_source, languages, sw);
            sentlist.clear();
            if (singleFileSwitch) {
                if (out != null) {
                    out.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        logger.log("Finished");
    }

}
