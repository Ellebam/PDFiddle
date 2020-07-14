package com.ellebam.pdfiddle.driver;

import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.panels.*;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Driver {


    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.buildGui();




    }

    public void buildGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MainFrame mainFrame = new MainFrame();
        mainFrame.setAndAddCurrentPanel(new ConvertPDF2JPEGsPanel(mainFrame));

    }

    public void decryptPDF(File Doc2Decrypt, Boolean overwriteSourceFile, MainFrame mainFrame) {
        try {
            Driver temporalDriver = new Driver();
            try {
                PDDocument PDF2Decrypt = PDDocument.load(Doc2Decrypt);
                JOptionPane.showMessageDialog(null, "Document not encrypted!");
            } catch (InvalidPasswordException invalidPasswordException) {
                PDDocument PDF2Decrypt = PDDocument.load(Doc2Decrypt,
                        JOptionPane.showInputDialog(null,
                                Doc2Decrypt.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));
                PDF2Decrypt.setAllSecurityToBeRemoved(true);
                if (overwriteSourceFile) {
                    PDF2Decrypt.save(Doc2Decrypt.getAbsolutePath());
                } else {
                    PDF2Decrypt.save(temporalDriver.chooseSaveDirectory(mainFrame) + "\\DecryptedPDF.pdf");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while decrypting!");
        }
    }

    public void encryptPDF(File Doc2Encrypt, Boolean overwriteSourceFile, String authorPassword, String userPassword,
                           MainFrame mainFrame) {
        try {
            Driver tempoDriver = new Driver();
            PDDocument PDF2Encrypt = PDDocument.load(Doc2Encrypt);
            AccessPermission accessPermission = new AccessPermission();
            StandardProtectionPolicy spp = new StandardProtectionPolicy(authorPassword, userPassword, accessPermission);
            spp.setEncryptionKeyLength(128);
            spp.setPermissions(accessPermission);
            PDF2Encrypt.protect(spp);
            if (overwriteSourceFile) {
                PDF2Encrypt.save(Doc2Encrypt.getAbsolutePath());
            } else {
                PDF2Encrypt.save(tempoDriver.chooseSaveDirectory(mainFrame) + "\\EncryptedPDF.pdf");
            }
            PDF2Encrypt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * This method will turn JPEGs into PDF-Documents. It utilizes a boolean (mergeFiles) to determine wether the JPEGs
     * should be converted into separate PDF-Files or merged into one singular PDF-File
     *
     * @param listOfJPEGs          ArrayList of Files containing the JPEGs to convert
     * @param destinationDirectory saving directory
     * @param mergeFiles           ArrayList containing Files to merge for mergeDocs()-Method
     */
    public void convertJPEG2PDF(ArrayList<File> listOfJPEGs, String destinationDirectory,
                                Boolean mergeFiles, MainFrame mainFrame) {
       /* boolean mergeFiles =true;
        ArrayList<File> listOfJPEGs = new ArrayList<>();
        listOfJPEGs.add(driver.chooseDoc());
        listOfJPEGs.add(driver.chooseDoc());
        listOfJPEGs.add(driver.chooseDoc());
        listOfJPEGs.add(driver.chooseDoc());
        System.out.println(listOfJPEGs.get(0).getAbsolutePath());

        driver.convertJPEG2PDF(listOfJPEGs,driver.chooseSaveDirectory(),mergeFiles);*/

        try {
            if (mergeFiles) {
                ArrayList<File> mergeList = new ArrayList<>();

                for (int i = 0; i < listOfJPEGs.size(); i++) {
                    PDDocument JPEGConversionDoc = new PDDocument();
                    PDPage page = new PDPage();
                    JPEGConversionDoc.addPage(page);
                    PDImageXObject pdImage = PDImageXObject.createFromFile(listOfJPEGs.get(i).getAbsolutePath(),
                            JPEGConversionDoc);
                    page.setMediaBox(new PDRectangle(0, 0, pdImage.getWidth(), pdImage.getHeight()));
                    PDPageContentStream contentStream = new PDPageContentStream(JPEGConversionDoc, JPEGConversionDoc.getPage(0));
                    contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                    contentStream.close();
                    JPEGConversionDoc.save(destinationDirectory + "\\tempPDF" + i + ".pdf");

                    File tempFile = new File(destinationDirectory + "\\tempPDF" + i + ".pdf");
                    mergeList.add(tempFile);
                }

                Driver tempDriver = new Driver();
                for (int u = 0; u < mergeList.size(); u++) {
                    System.out.println(mergeList.get(u).getName());
                }
                tempDriver.mergePDFDocs(destinationDirectory,"", mergeList, mainFrame);


                for (int i = 0; i < listOfJPEGs.size(); i++) {
                    File tempFile = new File(destinationDirectory + "\\tempPDF" + i + ".pdf");
                    if (tempFile.delete()) {
                        System.out.println(destinationDirectory + "\\tempPDF" + i + ".pdf deleted");
                    } else {
                        System.out.println(destinationDirectory + "\\tempPDF" + i + ".pdf not deleted");
                    }
                }

            } else {
                for (int i = 0; i < listOfJPEGs.size(); i++) {
                    PDDocument JPEGConversionDoc = new PDDocument();

                    PDPage page = new PDPage();
                    JPEGConversionDoc.addPage(page);
                    PDImageXObject pdImage = PDImageXObject.createFromFile(listOfJPEGs.get(i).getAbsolutePath(),
                            JPEGConversionDoc);
                    page.setMediaBox(new PDRectangle(0, 0, pdImage.getWidth(), pdImage.getHeight()));

                    PDPageContentStream contentStream = new PDPageContentStream(JPEGConversionDoc, JPEGConversionDoc.getPage(0));
                    contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                    contentStream.close();
                    JPEGConversionDoc.save(destinationDirectory + "\\newPDF" + i + ".pdf");
                    System.out.println("new PDF saved");
                    JPEGConversionDoc.close();

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Works similar to removePagesFromPDF() since it utilizes a ArrayList containing booleans to determine
     * which pages have to be processed. With that information, the method will pick Pages from a given
     * PDF File and convert them into JPEGs while also saving them (with given DPI).
     *
     * @param Doc2Convert2JPEG     PDF to convert pages from
     * @param destinationDirectory saving directory
     * @param Pages2Convert        ArrayList to track which pages should be converted
     * @param DPI                  sets quality of created JPEG
     */
    public void convertPDF2JPEGs(File Doc2Convert2JPEG, String destinationDirectory,
                                 ArrayList<Boolean> Pages2Convert, float DPI, MainFrame mainFrame) {

        /*ArrayList<Boolean> Pages2Convert = new ArrayList<>();
        Pages2Convert.add(true);
        for(int i =0; i<9;i++){
            Pages2Convert.add(false);
        }
        Pages2Convert.add(true);
        Pages2Convert.add(false);

        driver.convertPDF2JPEGs(driver.chooseDoc(),driver.chooseSaveDirectory(),Pages2Convert,400);*/
        //try {
            try {
                PDDocument extractionPDF = PDDocument.load(Doc2Convert2JPEG, MemoryUsageSetting.setupTempFileOnly());
                int numberOfPages = extractionPDF.getNumberOfPages();
                PDFRenderer renderer = new PDFRenderer(extractionPDF);
                for (int i = 0; i < numberOfPages; i++) {
                    if (Pages2Convert.get(i)) {
                        BufferedImage image = renderer.renderImageWithDPI(i, DPI, ImageType.RGB);
                        ImageIO.write(image, "JPEG", new File(
                                destinationDirectory + "\\converted Image" + i + ".jpeg"));
                    }
                }
                extractionPDF.close();
            /*} catch (InvalidPasswordException invalidPasswordException) {
                PDDocument extractionPDF = PDDocument.load(Doc2Convert2JPEG,
                        JOptionPane.showInputDialog(null,
                                Doc2Convert2JPEG.getName() + " is password protected. " +
                                        "Please enter the password to open the document"),
                        MemoryUsageSetting.setupTempFileOnly());
                int numberOfPages = extractionPDF.getNumberOfPages();
                System.out.println(numberOfPages);
                PDFRenderer renderer = new PDFRenderer(extractionPDF);
                for (int i = 0; i < numberOfPages; i++) {
                    if (Pages2Convert.get(i)) {
                        BufferedImage image = renderer.renderImageWithDPI(i, DPI, ImageType.RGB);
                        ImageIO.write(image, "JPEG", new File(
                                destinationDirectory + "\\converted Image" + i + ".jpeg"));
                    }
                }
                extractionPDF.close();
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Method from removing multiple pages of a PDF Document at once. It takes a PDF-File to remove Pages from
     * and an ArrayList with Booleans for determining which pages will have to be removed. The method iterates
     * through the Pages2Remove list until it finds a "true" and then removes it and the page with the same index
     * from the PDF document
     *
     * @param Doc2RemovePagesFrom  Initial PDF-File to remove pages from
     * @param destinationDirectory saving directory
     * @param Pages2Remove         ArrayList containing booleans for pages to remove(true)
     * @param mainFrame            parent frame for displaying content
     */
    public void removePagesFromPDF(File Doc2RemovePagesFrom, String destinationDirectory,
                                   ArrayList<Boolean> Pages2Remove, MainFrame mainFrame) {


        try {
                PDDocument pageRemovalPDF = PDDocument.load(Doc2RemovePagesFrom);
                int numberOfPages = pageRemovalPDF.getNumberOfPages();
                System.out.println(numberOfPages);
                while (Pages2Remove.contains(true)) {
                    for (int i = 0; i < numberOfPages; i++) {
                        if (Pages2Remove.get(i)) {
                            pageRemovalPDF.removePage(i);
                            Pages2Remove.remove(i);

                            break;
                        }
                    }
                }
                pageRemovalPDF.save(destinationDirectory + "\\PagesRemoved.pdf");
                JOptionPane.showMessageDialog(mainFrame,"Page removal successful!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while removing pages!");
        }
    }


    /**
     * This method is used for compressing a given PDF-File. It turns every page of the File into a JPEG-File which
     *      * can me saved with the given quality (compressionDPI) and re-converted into a single
     *      PDF (with the help of mergePDFDocsNoMessage()).
     * @param Doc2Compress file to compress
     * @param destinationDirectory Directory for saving compressed file
     * @param compressionDPI desired compression quality in DPI
     * @param mainframe parent frame
     */
    public void compressPDF(File Doc2Compress, String destinationDirectory, float compressionDPI, MainFrame mainframe) {


        ArrayList<Integer> pageCounter = new ArrayList<>(); //used for keeping track of the page at work
        ArrayList<Integer> noOfPages = new ArrayList<>();  // used for getting the total number of pages dinamically

        //used for tracking if the user wants to stop the compression
        ArrayList<Boolean> exitList = new ArrayList<>();
        boolean exit = new Boolean(false);
        exitList.add(exit);


        /**This is the first of two threads. This one (t1) takes the input file (Doc2Compress) and turns its single pages
         * into buffered images with low quality. It will then merge all pages to one PDDocument again and save it as a
         * pdf file. It will stop the main for loop (compressing all pages) when the boolean in exitList is turned to
         * "true" by the other thread (t2)
         */

        Thread t1 = new Thread(() -> {
        try {

            PDDocument pdDocument = new PDDocument();
            PDDocument oDocument = PDDocument.load(Doc2Compress);
            int numberOfPages = oDocument.getNumberOfPages() ;
            System.out.println(numberOfPages);
            noOfPages.add(numberOfPages);
            PDFRenderer pdfRenderer = new PDFRenderer(oDocument);


                    for (int i = 0; i < numberOfPages; i++) {
                        if(!exitList.get(0)) {
                            PDPage page = new PDPage(PDRectangle.LETTER);


                            BufferedImage bim = pdfRenderer.renderImageWithDPI(i, compressionDPI, ImageType.RGB);
                            PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
                            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
                            float newHeight = PDRectangle.LETTER.getHeight();
                            float newWidth = PDRectangle.LETTER.getWidth();
                            contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
                            contentStream.close();

                            pdDocument.addPage(page);
                            System.out.println("Page " + i + " compressed");
                            pageCounter.add(i);
                            try {
                                Thread.sleep(50);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }else{break; }
                    }

                    //File compressedFile = new File(destinationDirectory + "_compressed" + ".pdf");
                    pdDocument.save(destinationDirectory + "\\compressedFile.pdf");
                    pdDocument.close();
                    if(!exitList.get(0)) {
                        JOptionPane.showMessageDialog(mainframe, "Compression finished!");
                    }else {
                        JOptionPane.showMessageDialog(mainframe, "Compression terminated!");
                    }



        } catch (IOException e) {
            e.printStackTrace();
                JOptionPane.showMessageDialog(mainframe, "Error while compressing file!");
        }
        });

        /** this thread manages the dispaly of the ProgressMonitor. It will get the page number of the document to
         *  compress and build a screen showing the compression progress of the process. If the user selects "cancel"
         *  on the ProgressMonitor the exitList will be edited so that the other thread is stopped.
         *
         */
       Thread t2 = new Thread(() -> {

            UIManager.put("ProgressMonitor.progressText", "File Compression");
           try {
               Thread.sleep(1000);
           }catch(Exception exception){exception.printStackTrace();}
            //creating a ProgressMonitor instance
            ProgressMonitor progressMonitor = new ProgressMonitor(mainframe,
                    "Compressing File", "Compression Start", 0,
                    noOfPages.get(0));
           System.out.println("progress monitor started");
           progressMonitor.setMillisToPopup(10);
            //decide after 50 millis whether to show popup or not
            progressMonitor.setMillisToDecideToPopup(10);
            //after deciding if predicted time is longer than 50 show popup

            for(int i = 0;i<=noOfPages.get(0);i++) {
                System.out.println("for: " +i);
                if(progressMonitor.isCanceled()){
                    exitList.remove(0);
                    exitList.add(new Boolean(true));
                    System.out.println("thread cancelled");
                    break;
                }
                progressMonitor.setNote("Compressing page: " + pageCounter.get(pageCounter.size() - 1));
                progressMonitor.setProgress(i);
                try {
                    //delay task simulation
                    TimeUnit.MILLISECONDS.sleep(350);

                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }
            progressMonitor.setNote("Compression Finished!");

        });

       t1.start();
       System.out.print(noOfPages);
       t2.start();



    }

    /**
     * This method splits a PDF Document in variable smaller PDF Documents. It needs input from the user regarding
     * the saving directory, the number of PDFs to create and the Pages at which the PDFs have to be split.
     *
     * @param destinationDirectory saving directory
     * @param Doc2Split            the Document which has to be split
     * @param splitPointList       ArrayList which takes the Page-Points at which the Document has to be split
     */
    public void splitPDFDocs(String destinationDirectory, File Doc2Split,
                             ArrayList<Integer> splitPointList, MainFrame mainFrame) {



        try {
            try {
                //splitter to split the PDF in singular PDF Files
                PDDocument document = PDDocument.load(Doc2Split);
                Splitter splitter = new Splitter();
                List<PDDocument> Pages = splitter.split(document);
                Iterator<PDDocument> iterator = Pages.listIterator();

                int i;
                if (splitPointList.size() < 3) {
                    i = 1;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory +"\\splitPDF_"+ i++ + ".pdf");
                    }JOptionPane.showMessageDialog(mainFrame,"File successfully split!");
                } else {
                    i = 0;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory +"\\temp_"+ i++ + ".pdf");
                    }
                    i = 0;
                    for (int u = 0; u < splitPointList.size() - 1; u++) {
                        ArrayList<File> mergeList = new ArrayList<>();
                        for (int h = 0; h < (splitPointList.get(u + 1) - splitPointList.get(u)); h++) {
                            System.out.println("U=" + u);
                            File temporalFile = new File(destinationDirectory +"\\temp_"+ i + ".pdf");
                            mergeList.add(temporalFile);
                            i++;
                        }
                        int x = u + 1;
                        mergePDFDocsNoMessage(destinationDirectory,"split Nr" + x+" ", mergeList, mainFrame);
                        System.out.println("Number " + x + " of new documents created");
                    }
                    for (int z = (splitPointList.get(splitPointList.size() - 1)) - 1; z > -1; z--) {
                        //this part deletes the singular files which have to be created by the splitter
                        File definitiveFile = new File(destinationDirectory +"\\temp_"+ z + ".pdf");
                        if (definitiveFile.delete()) {
                            System.out.println(destinationDirectory +"\\temp_"+ z + ".pdf deleted");
                        } else System.out.println(destinationDirectory +"\\temp_"+ z + ".pdf not deleted");
                    }

                }JOptionPane.showMessageDialog(mainFrame,"File successfully split!");
                document.close();
            } catch (InvalidPasswordException invalidPasswordException) {

                PDDocument document = PDDocument.load(Doc2Split,
                        JOptionPane.showInputDialog(null,
                                Doc2Split.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));
                Splitter splitter = new Splitter();
                List<PDDocument> Pages = splitter.split(document);
                Iterator<PDDocument> iterator = Pages.listIterator();

                int i;
                if (splitPointList.size() < 2) {
                    i = 1;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory +"\\splitPDF_"+ i++ + ".pdf");
                    }JOptionPane.showMessageDialog(mainFrame,"File successfully split!");
                } else {
                    i = 0;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory +"\\temp_"+ i++ + ".pdf");
                    }
                    i = 0;
                    for (int u = 0; u < splitPointList.size() - 1; u++) {
                        ArrayList<File> mergeList = new ArrayList<>();
                        for (int h = 0; h < (splitPointList.get(u + 1) - splitPointList.get(u)); h++) {
                            System.out.println("U=" + u);
                            File temporalFile = new File(destinationDirectory +"\\temp_"+ i + ".pdf");
                            mergeList.add(temporalFile);
                            i++;
                        }
                        int x = u + 1;
                        mergePDFDocsNoMessage(destinationDirectory,"split Nr" + x+" ", mergeList, mainFrame);
                        System.out.println("Number " + x + " of new documents created");
                    }
                    for (int z = (splitPointList.get(splitPointList.size() - 1)) - 1; z > -1; z--) {
                        //this part deletes the singular files which have to be created by the splitter
                        File definitiveFile = new File(destinationDirectory +"\\temp_"+ z + ".pdf");
                        if (definitiveFile.delete()) {
                            System.out.println(destinationDirectory +"\\temp_"+ z + ".pdf deleted");
                        } else System.out.println(destinationDirectory +"\\temp_"+ z + ".pdf not deleted");
                    }

                }JOptionPane.showMessageDialog(mainFrame,"File successfully split!");
                document.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while splitting PDF!");
        }
    }


    /**
     * Standard merge method (PDFBox) to merge PDF Documents. It utilizes an ArrayList to track all the documents
     * that have to be merged
     *
     * @param destinationDirectory saving directory
     * @param mergeList            list of PDFs to merge
     */
    public void mergePDFDocs(String destinationDirectory,String x, ArrayList<File> mergeList, MainFrame mainFrame) {
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName(destinationDirectory + "\\"+x+"mergedPDF.pdf");
        try {
            for (int i = 0; i < mergeList.size(); i++) {
                File file = new File(mergeList.get(i).getAbsolutePath());
                PDFmerger.addSource(file);
            }
            PDFmerger.mergeDocuments(null);
            JOptionPane.showMessageDialog(mainFrame, "Files successfully merged!");
            mainFrame.getCurrentPanel().setVisible(false);
            mainFrame.setAndAddCurrentPanel(new MergePDFPanel(mainFrame));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while merging files!");

        }
    }

    /**
     * this method is the same as mergePDFDocs, only without the messageDialog (used for other methods to manipulate
     * PDFs in which the messageDialog isn't wished
     * @param destinationDirectory saving directory
     * @param x String to widen the filename
     * @param mergeList List containing the files to merge
     * @param mainFrame parent container
     */
    public void mergePDFDocsNoMessage(String destinationDirectory,String x, ArrayList<File> mergeList, MainFrame mainFrame) {
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName(destinationDirectory + "\\"+x+"mergedPDF.pdf");
        try {
            for (int i = 0; i < mergeList.size(); i++) {
                File file = new File(mergeList.get(i).getAbsolutePath());
                PDFmerger.addSource(file);
            }
            PDFmerger.mergeDocuments(null);
            mainFrame.getCurrentPanel().setVisible(false);
            mainFrame.setAndAddCurrentPanel(new MergePDFPanel(mainFrame));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while processing files!");

        }
    }

    public ArrayList<File> addDocs2MergeList(ArrayList<File> mergeList, File file2Add) {
        mergeList.add(file2Add);
        return mergeList;
    }

    public File chooseDoc(MainFrame mainFrame) {
        JFileChooser fileChoose = new JFileChooser("C:\\Arinhobag");
        fileChoose.setDialogTitle("Choose a file");
        fileChoose.showOpenDialog(mainFrame);
        return fileChoose.getSelectedFile();
    }

    public String chooseSaveDirectory(MainFrame mainFrame) {
        JFileChooser fileSave = new JFileChooser("C:\\Arinhobag");
        fileSave.setDialogTitle("Choose a saving directory");
        fileSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileSave.showOpenDialog(mainFrame);
        return fileSave.getSelectedFile().getAbsolutePath();
    }

    public String[] createRangeArray(int start, int end) {
        IntStream stream = IntStream.range(start, end);
        int[] intRange = stream.toArray();

        String[] stringRange = new String[intRange.length];
        for (int i = 0; i < intRange.length; i++) {
            stringRange[i] = String.valueOf(intRange[i]);
        }

        return stringRange;
    }

    public PDDocument handlePDFEncryption(File doc2Handle, MainFrame mainFrame) {
        PDDocument handledDoc = new PDDocument();
        try {
            try {
                handledDoc = PDDocument.load(doc2Handle);

            } catch (InvalidPasswordException invalidPasswordException) {

                 handledDoc = PDDocument.load(doc2Handle,
                        JOptionPane.showInputDialog(mainFrame,
                                doc2Handle.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));


            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Password incorrect!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while loading file!");
        }

            handledDoc.setAllSecurityToBeRemoved(true);
            return  handledDoc;


    }

    public void changeCurrentPanel (JPanel newPanel, MainFrame mainFrame){
        mainFrame.getCurrentPanel().setVisible(false);
        mainFrame.setAndAddCurrentPanel(newPanel);
        mainFrame.getCurrentPanel().revalidate();
    }


    public void displayUserMessage (String variableString,MainFrame mainFrame){
        JOptionPane.showMessageDialog(mainFrame,variableString);
    }





}
