package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.OpeningPanelPseudoButton;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FilePreviewPanel extends JPanel {

    private BufferedImage image;
    private FilePreviewPanel filePreviewPanel;
    private Driver previewDriver = new Driver();



    public FilePreviewPanel(File file, MainFrame mainFrame, int previewPage) {
        filePreviewPanel=this;
        try {
            try {
                File previewFile = File.createTempFile("temp", null);
                PDDocument extractionPDF = PDDocument.load(file, MemoryUsageSetting.setupTempFileOnly());
                PDFRenderer renderer = new PDFRenderer(extractionPDF);
                BufferedImage image = renderer.renderImageWithDPI(previewPage, 200, ImageType.RGB);
                ImageIO.write(image, "JPEG", previewFile);


                addIcon2FilePreviewPanel(this, previewFile, mainFrame);

                extractionPDF.close();
            } catch (InvalidPasswordException invalidPasswordException) {
                File previewFile = File.createTempFile("temp", null);

                PDDocument extractionPDF = PDDocument.load(file,
                        JOptionPane.showInputDialog(null,
                                file.getName() + " is password protected. " +
                                        "Please enter the password to open the document"),
                        MemoryUsageSetting.setupTempFileOnly());
                PDFRenderer renderer = new PDFRenderer(extractionPDF);
                BufferedImage image = renderer.renderImageWithDPI(previewPage, 400, ImageType.RGB);
                ImageIO.write(image, "File", previewFile);


                addIcon2FilePreviewPanel(this, previewFile, mainFrame);
                filePreviewPanel.setBackground(Color.RED);
                extractionPDF.close();
            }
        }catch(Exception ex){ex.printStackTrace();}

    }

    public ImageIcon scaleImage (ImageIcon icon, int width, int height){
        int newWidth = icon.getIconWidth();
        int newHeight = icon.getIconHeight();

        if(icon.getIconWidth()>width){
            newWidth = width;
            newHeight = (newWidth*icon.getIconHeight())/icon.getIconWidth();
        }

        if(newHeight>height){
            newHeight=height;
            newWidth=(icon.getIconWidth()*newHeight/icon.getIconHeight());
        }

        return new ImageIcon(icon.getImage().getScaledInstance(newWidth,newHeight,Image.SCALE_DEFAULT));
    }

    public void addIcon2FilePreviewPanel (FilePreviewPanel filePreviewPanel, File file, MainFrame mainFrame) {
        try {
            BufferedImage IconImage = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(IconImage);
            JLabel iconLabel = new JLabel(scaleImage(icon, 180, 180));
            filePreviewPanel.add(iconLabel);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,
                    "Error while loading File preview!");
        }
    }


}
