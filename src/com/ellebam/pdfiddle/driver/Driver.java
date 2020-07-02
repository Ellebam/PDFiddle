package com.ellebam.pdfiddle.driver;

import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.panels.MergePDFPanel;
import com.ellebam.pdfiddle.guielements.panels.OpeningPanel;
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

public class Driver {



    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.buildGui();






    }

    public void buildGui(){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setAndAddCurrentPanel(new MergePDFPanel(mainFrame));

    }

    public void decryptPDF(File Doc2Decrypt, Boolean overwriteSourceFile, MainFrame mainFrame){
        try{
            Driver temporalDriver = new Driver();
            try{
                PDDocument PDF2Decrypt = PDDocument.load(Doc2Decrypt);
                JOptionPane.showMessageDialog(null, "Document not encrypted!");
            }catch(InvalidPasswordException invalidPasswordException){
                PDDocument PDF2Decrypt = PDDocument.load(Doc2Decrypt,
                        JOptionPane.showInputDialog(null,
                                Doc2Decrypt.getName()+" is password protected. " +
                                        "Please enter the password to open the document"));
                PDF2Decrypt.setAllSecurityToBeRemoved(true);
                if(overwriteSourceFile){
                    PDF2Decrypt.save(Doc2Decrypt.getAbsolutePath());
                }else{
                    PDF2Decrypt.save(temporalDriver.chooseSaveDirectory(mainFrame)+"\\DecryptedPDF.pdf");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error while decrypting!");
        }
    }

    public void encryptPDF(File Doc2Encrypt, Boolean overwriteSourceFile, String authorPassword, String userPassword,
                           MainFrame mainFrame){
        try {
            Driver tempoDriver = new Driver();
            PDDocument PDF2Encrypt = PDDocument.load(Doc2Encrypt);
            AccessPermission accessPermission = new AccessPermission();
            StandardProtectionPolicy spp = new StandardProtectionPolicy(authorPassword,userPassword,accessPermission);
            spp.setEncryptionKeyLength(128);
            spp.setPermissions(accessPermission);
            PDF2Encrypt.protect(spp);
            if(overwriteSourceFile){
                PDF2Encrypt.save(Doc2Encrypt.getAbsolutePath());
            }else{
                PDF2Encrypt.save(tempoDriver.chooseSaveDirectory(mainFrame)+"\\EncryptedPDF.pdf");
            }
            PDF2Encrypt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    /**
     *This method will turn JPEGs into PDF-Documents. It utilizes a boolean (mergeFiles) to determine wether the JPEGs
     * should be converted into separate PDF-Files or merged into one singular PDF-File
     * @param listOfJPEGs ArrayList of Files containing the JPEGs to convert
     * @param destinationDirectory saving directory
     * @param mergeFiles ArrayList containing Files to merge for mergeDocs()-Method
     */
    public void convertJPEG2PDF(ArrayList<File> listOfJPEGs, String destinationDirectory,
                                Boolean mergeFiles, MainFrame mainFrame){
       /* boolean mergeFiles =true;
        ArrayList<File> listOfJPEGs = new ArrayList<>();
        listOfJPEGs.add(driver.chooseDoc());
        listOfJPEGs.add(driver.chooseDoc());
        listOfJPEGs.add(driver.chooseDoc());
        listOfJPEGs.add(driver.chooseDoc());
        System.out.println(listOfJPEGs.get(0).getAbsolutePath());

        driver.convertJPEG2PDF(listOfJPEGs,driver.chooseSaveDirectory(),mergeFiles);*/

        try{
            if(mergeFiles){
                ArrayList<File> mergeList = new ArrayList<>();

                for(int i = 0; i<listOfJPEGs.size();i++) {
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

                    File tempFile = new File (destinationDirectory + "\\tempPDF" + i + ".pdf");
                    mergeList.add(tempFile);
                }

                Driver tempDriver = new Driver();
                for (int u =0; u<mergeList.size();u++){
                    System.out.println(mergeList.get(u).getName());
                }
                tempDriver.mergePDFDocs(destinationDirectory, mergeList,mainFrame);


                for(int i = 0; i<listOfJPEGs.size();i++){
                    File tempFile = new File(destinationDirectory + "\\tempPDF"+ i + ".pdf");
                    if (tempFile.delete()) {
                        System.out.println(destinationDirectory + "\\tempPDF"+ i + ".pdf deleted");
                    } else {System.out.println(destinationDirectory+ "\\tempPDF" + i + ".pdf not deleted"); }
                }

            }else{
                for(int i = 0; i<listOfJPEGs.size();i++){
                    PDDocument JPEGConversionDoc = new PDDocument();

                    PDPage page = new PDPage();
                    JPEGConversionDoc.addPage(page);
                    PDImageXObject pdImage = PDImageXObject.createFromFile(listOfJPEGs.get(i).getAbsolutePath(),
                            JPEGConversionDoc);
                    page.setMediaBox(new PDRectangle(0,0,pdImage.getWidth(),pdImage.getHeight()));

                    PDPageContentStream contentStream = new PDPageContentStream(JPEGConversionDoc,JPEGConversionDoc.getPage(0));
                    contentStream.drawImage(pdImage,0,0,page.getMediaBox().getWidth(),page.getMediaBox().getHeight());
                    contentStream.close();
                    JPEGConversionDoc.save(destinationDirectory+"\\newPDF"+i+".pdf");
                    System.out.println("new PDF saved");
                    JPEGConversionDoc.close();

                }

            }
        }catch(Exception ex){ ex.printStackTrace();}
    }


    /**
     * Works similar to removePagesFromPDF() since it utilizes a ArrayList containing booleans to determine
     * which pages have to be processed. With that information, the method will pick Pages from a given
     * PDF File and convert them into JPEGs while also saving them (with given DPI).
     * @param Doc2Convert2JPEG PDF to convert pages from
     * @param destinationDirectory saving directory
     * @param Pages2Convert ArrayList to track which pages should be converted
     * @param DPI sets quality of created JPEG
     */
    public void convertPDF2JPEGs (File Doc2Convert2JPEG, String destinationDirectory,
                                  ArrayList<Boolean> Pages2Convert, float DPI){

        /*ArrayList<Boolean> Pages2Convert = new ArrayList<>();
        Pages2Convert.add(true);
        for(int i =0; i<9;i++){
            Pages2Convert.add(false);
        }
        Pages2Convert.add(true);
        Pages2Convert.add(false);

        driver.convertPDF2JPEGs(driver.chooseDoc(),driver.chooseSaveDirectory(),Pages2Convert,400);*/
        try{
            try{
            PDDocument extractionPDF = PDDocument.load(Doc2Convert2JPEG, MemoryUsageSetting.setupTempFileOnly());
            int numberOfPages = extractionPDF.getNumberOfPages();
            PDFRenderer renderer = new PDFRenderer(extractionPDF);
            for(int i=0; i < numberOfPages;i++){
                if(Pages2Convert.get(i)){
                    BufferedImage  image = renderer.renderImageWithDPI(i,DPI,ImageType.RGB);
                    ImageIO.write(image,"JPEG",new File(
                            destinationDirectory+"\\converted Image"+i+".jpeg"));
                }
            }
            extractionPDF.close();
            }catch(InvalidPasswordException invalidPasswordException){
                PDDocument extractionPDF = PDDocument.load(Doc2Convert2JPEG,
                        JOptionPane.showInputDialog(null,
                                Doc2Convert2JPEG.getName()+" is password protected. " +
                                        "Please enter the password to open the document"),
                                MemoryUsageSetting.setupTempFileOnly());
                int numberOfPages = extractionPDF.getNumberOfPages();
                System.out.println(numberOfPages);
                PDFRenderer renderer = new PDFRenderer(extractionPDF);
                for(int i=0; i < numberOfPages;i++){
                    if(Pages2Convert.get(i)){
                        BufferedImage  image = renderer.renderImageWithDPI(i,DPI,ImageType.RGB);
                        ImageIO.write(image,"JPEG",new File(
                                destinationDirectory+"\\converted Image"+i+".jpeg"));
                    }
                }
                extractionPDF.close();}
        }catch(Exception ex){ex.printStackTrace();}

    }

    /**
     * Method from removing multiple pages of a PDF Document at once. It takes a PDF-File to remove Pages from
     * and an ArrayList with Booleans for determining which pages will have to be removed. The method iterates
     * through the Pages2Remove list until it finds a "true" and then removes it and the page with the same index
     * from the PDF document
     * @param Doc2RemovePagesFrom Initial PDF-File to remove pages from
     * @param destinationDirectory saving directory
     * @param Pages2Remove ArrayList containing booleans for pages to remove(true)
     */
    public void removePagesFromPDF(File Doc2RemovePagesFrom, String destinationDirectory,
                                   ArrayList<Boolean> Pages2Remove){

        /*Unit Testing:
        ArrayList<Boolean> Pages2Remove = new ArrayList<>();
        Pages2Remove.add(true);
        for(int i =0; i<9;i++){
            Pages2Remove.add(false);
        }
        Pages2Remove.add(true);
        Pages2Remove.add(false);


        System.out.println(Pages2Remove);

        driver.removePagesFromPDF(driver.chooseDoc(),driver.chooseSaveDirectory(),Pages2Remove);*/
        try{
            try{
            PDDocument pageRemovalPDF = PDDocument.load(Doc2RemovePagesFrom);
            int numberOfPages = pageRemovalPDF.getNumberOfPages();
            System.out.println(numberOfPages);
            while(Pages2Remove.contains(true)) {
                for (int i = 0; i < numberOfPages; i++) {
                    if (Pages2Remove.get(i)) {
                        pageRemovalPDF.removePage(i);
                        Pages2Remove.remove(i);

                        break;
                    }
                }
            }
            pageRemovalPDF.save(destinationDirectory+"\\PagesRemoved.pdf");
            }catch(InvalidPasswordException invalidPasswordException) {
                PDDocument pageRemovalPDF = PDDocument.load(Doc2RemovePagesFrom,
                        JOptionPane.showInputDialog(null,
                                Doc2RemovePagesFrom.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));
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
            }
        }catch (Exception ex){ex.printStackTrace();}
    }


    /**
     * This method is used for compressing a given PDF-File. It turns every page of the File into a JPEG-File which
     * can me saved with the given quality (compressionDPI).
     * @param Doc2Compress file to compress
     * @param destinationDirectory saving directory
     * @param compressionDPI float to determine compression quality
     */
    public void compressPDF(File Doc2Compress, String destinationDirectory, float compressionDPI ){


        try {
            try{
        PDDocument pdDocument = new PDDocument();
        PDDocument oDocument = PDDocument.load(Doc2Compress);
        PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
        int numberOfPages = oDocument.getNumberOfPages();
        PDPage page;

        for (int i = 0; i < numberOfPages; i++) {
            page = new PDPage(PDRectangle.LETTER);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(i,compressionDPI, ImageType.RGB);
            PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
            float newHeight = PDRectangle.LETTER.getHeight();
            float newWidth = PDRectangle.LETTER.getWidth();
            contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
            contentStream.close();

            pdDocument.addPage(page);
        }
        //File compressedFile = new File(destinationDirectory + "_cpmpressed" + ".pdf");
        pdDocument.save(destinationDirectory+"\\compressedFile.pdf");
        pdDocument.close();
            }catch(InvalidPasswordException invalidPasswordException) {
                PDDocument pdDocument = new PDDocument();
                PDDocument oDocument = PDDocument.load(Doc2Compress,
                        JOptionPane.showInputDialog(null,
                                Doc2Compress.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));
                PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
                int numberOfPages = oDocument.getNumberOfPages();
                PDPage page;

                for (int i = 0; i < numberOfPages; i++) {
                    page = new PDPage(PDRectangle.LETTER);
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(i, compressionDPI, ImageType.RGB);
                    PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
                    PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
                    float newHeight = PDRectangle.LETTER.getHeight();
                    float newWidth = PDRectangle.LETTER.getWidth();
                    contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
                    contentStream.close();

                    pdDocument.addPage(page);
                }
            }


    } catch (IOException e) {
        e.printStackTrace();
    }
}

    /**
     * This method splits a PDF Document in variable smaller PDF Documents. It needs input from the user regarding
     * the saving directory, the number of PDFs to create and the Pages at which the PDFs have to be split.
     *
     * @param destinationDirectory saving directory
     * @param Doc2Split the Document which has to be split
     * @param splitPointList ArrayList which takes the Page-Points at which the Document has to be split
     */
    public void splitPDFDocs (String destinationDirectory, File Doc2Split,
                              ArrayList<Integer> splitPointList, MainFrame mainFrame) {

        /*ArrayList<Integer> splitPointList = new ArrayList<>();
        splitPointList.add(0);
        splitPointList.add(1);
        splitPointList.add(6);
        String directory =("C:\\Arinhobag\\Code\\Java\\IntelliJ\\PDFiddle\\newAge");
        driver.splitPDFDocs(directory,driver.chooseDoc(),splitPointList);*/

        try {
            try {
                //splitter to split the PDF in singular PDF Files
                PDDocument document = PDDocument.load(Doc2Split);
                Splitter splitter = new Splitter();
                List<PDDocument> Pages = splitter.split(document);
                Iterator<PDDocument> iterator = Pages.listIterator();

                if (splitPointList.size() < 1) {
                    int i = 1;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory + i++ + ".pdf");
                    }
                    document.close();
                } else {
                    int i = 0;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory + i++ + ".pdf");
                    }
                    i = 0;
                    for (int u = 0; u < splitPointList.size() - 1; u++) {
                        ArrayList<File> mergeList = new ArrayList<>();
                        for (int h = 0; h < (splitPointList.get(u + 1) - splitPointList.get(u)); h++) {
                            System.out.println("U=" + u);
                            File temporalFile = new File(destinationDirectory + i + ".pdf");
                            mergeList.add(temporalFile);
                            i++;
                        }
                        int x = u + 1;
                        mergePDFDocs(destinationDirectory + "Nr" + x + ".pdf", mergeList, mainFrame);
                        System.out.println("Number " + x + " of new documents created");
                    }
                    for (int z = (splitPointList.get(splitPointList.size() - 1)) - 1; z > -1; z--) {
                        //this part deletes the singular files which have to be created by the splitter
                        File definitiveFile = new File(destinationDirectory + z + ".pdf");
                        if (definitiveFile.delete()) {
                            System.out.println(destinationDirectory + z + ".pdf deleted");
                        } else System.out.println(destinationDirectory + z + ".pdf not deleted");
                    }
                    document.close();

                }
            } catch (InvalidPasswordException invalidPasswordException) {

                PDDocument document = PDDocument.load(Doc2Split,
                        JOptionPane.showInputDialog(null,
                                Doc2Split.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));
                Splitter splitter = new Splitter();
                List<PDDocument> Pages = splitter.split(document);
                Iterator<PDDocument> iterator = Pages.listIterator();

                if (splitPointList.size() < 1) {
                    int i = 1;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory + i++ + ".pdf");
                    }
                    document.close();
                } else {
                    int i = 0;
                    while (iterator.hasNext()) {
                        PDDocument temporalDocument = iterator.next();
                        temporalDocument.save(destinationDirectory + i++ + ".pdf");
                    }
                    i = 0;
                    for (int u = 0; u < splitPointList.size() - 1; u++) {
                        ArrayList<File> mergeList = new ArrayList<>();
                        for (int h = 0; h < (splitPointList.get(u + 1) - splitPointList.get(u)); h++) {
                            System.out.println("U=" + u);
                            File temporalFile = new File(destinationDirectory + i + ".pdf");
                            mergeList.add(temporalFile);
                            i++;
                        }
                        int x = u + 1;
                        mergePDFDocs(destinationDirectory + "Nr" + x + ".pdf", mergeList, mainFrame);
                        System.out.println("Number " + x + " of new documents created");
                    }
                    for (int z = (splitPointList.get(splitPointList.size() - 1)) - 1; z > -1; z--) {
                        //this part deletes the singular files which have to be created by the splitter
                        File definitiveFile = new File(destinationDirectory + z + ".pdf");
                        if (definitiveFile.delete()) {
                            System.out.println(destinationDirectory + z + ".pdf deleted");
                        } else System.out.println(destinationDirectory + z + ".pdf not deleted");
                    }
                    document.close();


                }
            }
        }catch (Exception ex) {ex.printStackTrace();}
        }



    /**
     * Standard merge method (PDFBox) to merge PDF Documents. It utilizes an ArrayList to track all the documents
     * that have to be merged
     * @param destinationDirectory saving directory
     * @param mergeList list of PDFs to merge
     */
    public void mergePDFDocs (String destinationDirectory, ArrayList<File> mergeList, MainFrame mainFrame){
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName(destinationDirectory+"\\mergedPDFs.pdf");
        try {
            for (int i = 0; i < mergeList.size(); i++) {
                File file = new File(mergeList.get(i).getAbsolutePath());
                PDFmerger.addSource(file);
            }
            PDFmerger.mergeDocuments(null);
        }catch (Exception ex){ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while merging files!");}
}


public ArrayList<File> addDocs2MergeList (ArrayList<File> mergeList, File file2Add){
        mergeList.add(file2Add);
        return mergeList;
    }
public File chooseDoc(){
        JFileChooser fileChoose = new JFileChooser("C:\\Arinhobag");
        fileChoose.showOpenDialog(null);
        return fileChoose.getSelectedFile();
}

public String chooseSaveDirectory (MainFrame mainFrame){
    JFileChooser fileSave = new JFileChooser("C:\\Arinhobag");
    fileSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileSave.showOpenDialog(mainFrame);
    return fileSave.getSelectedFile().getAbsolutePath();
}
}
