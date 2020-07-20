/*      Copyright [2020] [Arian Jamborzadeh]
        SPDX-License-Identifier: Apache-2.0
        Icons made by <a href="https://www.flaticon.com/authors/freepik"  */

package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.OpeningPanelPseudoButton;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.labels.MiddleLabel;
import com.ellebam.pdfiddle.guielements.labels.SmallLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * First Panel to appear when the program (and thus the mainFrame) is started. It shows OpeningPanelPseudobuttons (JPanels)
 * which will lead to the desired functionality when clicked.
 */
public class OpeningPanel extends JPanel {
    private OpeningPanel        openingPanel;
    private Driver              driver              =       new Driver();



    /**
     * Constructor for creation of OpeningPanel. It takes a MainFrame object as argument for changing fo the Panel when
     * a OpeningPanelPseudobutton is clicked
     *
     * @param mainFrame Parent of OpeningPanel
     */
    public OpeningPanel(MainFrame mainFrame){
        openingPanel = this;

        openingPanel.setLayout(new BoxLayout(openingPanel, BoxLayout.Y_AXIS));
        openingPanel.add(Box.createRigidArea(new Dimension(0,20)));
        openingPanel.setBackground(Color.WHITE);



        HeaderPanel headerPanel =new HeaderPanel(new HeaderLabel("Welcome to PDFiddle 1.0"));
        headerPanel.setPreferredSize(new Dimension(2000,30));

        //PseudoButtonCarrier for merge & split PDF Pseudobuttons
        SmallLabelPanel splitSmallLabelPanel = new SmallLabelPanel(new SmallLabel("PDF-Splitter"));
        OpeningPanelPseudoButton splitPseudoButton = new OpeningPanelPseudoButton(splitSmallLabelPanel);
        SmallLabelPanel mergeSmallLabelPanel = new SmallLabelPanel(new SmallLabel("PDF-Merger"));
        OpeningPanelPseudoButton mergePseudoButton = new OpeningPanelPseudoButton(mergeSmallLabelPanel);
        MiddleLabel mergeAndSplitLabel = new MiddleLabel("Merge or Split");
        MiddleLabelPanel mergeAndSplitLabelPanel = new MiddleLabelPanel(mergeAndSplitLabel);
        PseudoButtonCarrier mergeAndSplitCarrier = new PseudoButtonCarrier(mergeAndSplitLabelPanel,
                mergePseudoButton,splitPseudoButton);

        //adding icons
        addIcon2Pseudobutton(mergePseudoButton,"/com/ellebam/pdfiddle/Icons/Freepik/mergePDF_Icon.png");
        addIcon2Pseudobutton(splitPseudoButton,"/com/ellebam/pdfiddle/Icons/Freepik/splitPDF_Icon.png");

        //adding Mouselisteners (opening corresponding new merge & split PDF Pseudobuttons)
        mergePseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e){
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new MergePDFPanel(mainFrame));
            }
        }));

        splitPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e){
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new SplitPDFPanel(mainFrame));
            }
        }));



        //PseudoButtonCarrier for compressPDF Pseudobutton
        SmallLabelPanel compressSmallLabelPanel = new SmallLabelPanel(new SmallLabel("PDF-Compressor"));
        OpeningPanelPseudoButton compressPseudoButton = new OpeningPanelPseudoButton(compressSmallLabelPanel);
        MiddleLabel compressLabel = new MiddleLabel("Compress");
        MiddleLabelPanel compressLabelPanel = new MiddleLabelPanel(compressLabel);
        PseudoButtonCarrier compressCarrier = new PseudoButtonCarrier(compressLabelPanel,
                compressPseudoButton);
        addIcon2Pseudobutton(compressPseudoButton,"/com/ellebam/pdfiddle/Icons/Freepik/compressPDF_Icon.png");

        //adding Mouselistener for compressPseudoButton
        compressPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e){
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new CompressPDFPanel(mainFrame));
            }
        }));

        //PseudoButtonCarrier for removePagesPDF Pseudobutton
        SmallLabelPanel pageRemoverSmallLabelPanel = new SmallLabelPanel(new SmallLabel("Page Remover"));
        OpeningPanelPseudoButton pageRemoverPseudoButton = new OpeningPanelPseudoButton(pageRemoverSmallLabelPanel);
        MiddleLabel pageRemoverLabel = new MiddleLabel("Remove Pages");
        MiddleLabelPanel pageRemoverLabelPanel = new MiddleLabelPanel(pageRemoverLabel);
        PseudoButtonCarrier pageRemoverCarrier = new PseudoButtonCarrier(pageRemoverLabelPanel,
                pageRemoverPseudoButton);
        addIcon2Pseudobutton(pageRemoverPseudoButton,"/com/ellebam/pdfiddle/Icons/Own Icons/pageRemoval_icon.png");

        //adding Mouselistener for removePagesPDF Pseudobutton
        pageRemoverPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e){
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new RemovePagesPanel(mainFrame));
            }
        }));

        //PseudoButtonCarrier for JPEG2PDF and PDF2JPEG Pseudobuttons
        SmallLabelPanel JPEG2PDFSmallLabelPanel = new SmallLabelPanel(new SmallLabel("JPEG to PDF"));
        OpeningPanelPseudoButton JPEG2PDFPseudoButton = new OpeningPanelPseudoButton(JPEG2PDFSmallLabelPanel);
        SmallLabelPanel PDF2JPEGSmallLabelPanel = new SmallLabelPanel(new SmallLabel("PDF to JPEG"));
        OpeningPanelPseudoButton PDF2JPEGPseudoButton = new OpeningPanelPseudoButton(PDF2JPEGSmallLabelPanel);
        MiddleLabel JPEGCenterLabel = new MiddleLabel("JPEG Center");
        MiddleLabelPanel JPEGCenterLabelPanel = new MiddleLabelPanel(JPEGCenterLabel);
        PseudoButtonCarrier JPEGCenterCarrier = new PseudoButtonCarrier(JPEGCenterLabelPanel,
                JPEG2PDFPseudoButton,PDF2JPEGPseudoButton);
        addIcon2Pseudobutton(JPEG2PDFPseudoButton,"/com/ellebam/pdfiddle/Icons/Freepik/JPEG2PDF_Icon.png");
        addIcon2Pseudobutton(PDF2JPEGPseudoButton,"/com/ellebam/pdfiddle/Icons/Freepik/PDF2JPEG_Icon.png");

        //adding MouseListener for PDF2JPEG Pseudobutton
        PDF2JPEGPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new ConvertPDF2JPEGsPanel(mainFrame));
            }
        });

        //adding MouseListener for JPEG2PDF Pseudobutton
        JPEG2PDFPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new ConvertJPEG2PDFPanel(mainFrame));
            }
        });

        //PseudoButtonCarrier for encryptPDF and decryptPDF Pseudobuttons
        SmallLabelPanel encryptPDFSmallLabelPanel = new SmallLabelPanel(new SmallLabel("Encrypt PDF"));
        OpeningPanelPseudoButton encryptPDFPseudoButton = new OpeningPanelPseudoButton(encryptPDFSmallLabelPanel);
        SmallLabelPanel decryptPDFSmallLabelPanel = new SmallLabelPanel(new SmallLabel("Decrypt JPEG"));
        OpeningPanelPseudoButton decryptPDFPseudoButton = new OpeningPanelPseudoButton(decryptPDFSmallLabelPanel);
        MiddleLabel encryptionLabel = new MiddleLabel("Encryption");
        MiddleLabelPanel encryptionLabelPanel = new MiddleLabelPanel(encryptionLabel);
        PseudoButtonCarrier encryptionCarrier = new PseudoButtonCarrier(encryptionLabelPanel,
                encryptPDFPseudoButton,decryptPDFPseudoButton);
        addIcon2Pseudobutton(encryptPDFPseudoButton,"/com/ellebam/pdfiddle/Icons/Freepik/encryptPDF_Icon.png");
        addIcon2Pseudobutton(decryptPDFPseudoButton,"/com/ellebam/pdfiddle/Icons/Own Icons/decryptPDF_icon.png");

        //adding MouseListener for encryptPDF Pseudobutton
        encryptPDFPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new EncryptPDFPanel(mainFrame));
            }
        });

        //adding MouseListener for decryptPDF Pseudobutton
        decryptPDFPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                openingPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new DecryptPDFPanel(mainFrame));
            }
        });



        JPanel carrierPanel = new JPanel();
        carrierPanel.setOpaque(true);
        carrierPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
        carrierPanel.add(headerPanel);
        carrierPanel.add(mergeAndSplitCarrier);
        carrierPanel.add(compressCarrier);
        carrierPanel.add(pageRemoverCarrier);
        carrierPanel.add(JPEGCenterCarrier);
        carrierPanel.add(encryptionCarrier);
        carrierPanel.setBackground(Color.white);
        carrierPanel.add(Box.createVerticalGlue());


        openingPanel.add(carrierPanel);

        openingPanel.setVisible(true);

    }

    /**
     * Constructor for scaling the OpeningPanelPseudoButtons Icons to desired dimension without losing the aspect ratio
     * @param icon icon object to resize
     * @param width desired width of icon
     * @param height desired height of icon
     * @return resized icon
     */
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

    /**
     * Method for combining a PNG File from source (icon) with the corresponding OpeningPanelPseudoButton
     * @param pseudobutton corresponding OpeningPanelPseudoButton
     * @param iconDirectory directory of PNG File
     */
    public void addIcon2Pseudobutton (OpeningPanelPseudoButton pseudobutton, String iconDirectory) {
        try {
            BufferedImage IconPNG = ImageIO.read(getClass().getResource(iconDirectory));
            ImageIcon icon = new ImageIcon(IconPNG);
            JLabel iconLabel = new JLabel(scaleImage(icon, 45, 45));
            pseudobutton.add(iconLabel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
