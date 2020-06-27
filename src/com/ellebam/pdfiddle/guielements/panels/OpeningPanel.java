package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.labels.MiddleLabel;
import com.ellebam.pdfiddle.guielements.labels.SmallLabel;

import javax.swing.*;
import java.awt.*;

public class OpeningPanel extends JPanel {
    private JPanel openingPanel;



    public OpeningPanel(){
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

        //PseudoButtonCarrier for compressPDF Pseudobutton
        SmallLabelPanel compressSmallLabelPanel = new SmallLabelPanel(new SmallLabel("PDF-Compressor"));
        OpeningPanelPseudoButton compressPseudoButton = new OpeningPanelPseudoButton(compressSmallLabelPanel);
        MiddleLabel compressLabel = new MiddleLabel("Compress");
        MiddleLabelPanel compressLabelPanel = new MiddleLabelPanel(compressLabel);
        PseudoButtonCarrier compressCarrier = new PseudoButtonCarrier(compressLabelPanel,
                compressPseudoButton);

        //PseudoButtonCarrier for removePagesPDF Pseudobutton
        SmallLabelPanel pageRemoverSmallLabelPanel = new SmallLabelPanel(new SmallLabel("Page Remover"));
        OpeningPanelPseudoButton pageRemoverPseudoButton = new OpeningPanelPseudoButton(pageRemoverSmallLabelPanel);
        MiddleLabel pageRemoverLabel = new MiddleLabel("Remove Pages");
        MiddleLabelPanel pageRemoverLabelPanel = new MiddleLabelPanel(pageRemoverLabel);
        PseudoButtonCarrier pageRemoverCarrier = new PseudoButtonCarrier(pageRemoverLabelPanel,
                pageRemoverPseudoButton);

        //PseudoButtonCarrier for JPEG2PDF and PDF2JPEG Pseudobuttons
        SmallLabelPanel JPEG2PDFSmallLabelPanel = new SmallLabelPanel(new SmallLabel("JPEG to PDF"));
        OpeningPanelPseudoButton JPEG2PDFPseudoButton = new OpeningPanelPseudoButton(JPEG2PDFSmallLabelPanel);
        SmallLabelPanel PDF2JPEGSmallLabelPanel = new SmallLabelPanel(new SmallLabel("PDF to JPEG"));
        OpeningPanelPseudoButton PDF2JPEGPseudoButton = new OpeningPanelPseudoButton(PDF2JPEGSmallLabelPanel);
        MiddleLabel JPEGCenterLabel = new MiddleLabel("JPEG Center");
        MiddleLabelPanel JPEGCenterLabelPanel = new MiddleLabelPanel(JPEGCenterLabel);
        PseudoButtonCarrier JPEGCenterCarrier = new PseudoButtonCarrier(JPEGCenterLabelPanel,
                JPEG2PDFPseudoButton,PDF2JPEGPseudoButton);

        //PseudoButtonCarrier for encryptPDF and decryptPDF Pseudobuttons
        SmallLabelPanel encryptPDFSmallLabelPanel = new SmallLabelPanel(new SmallLabel("Encrypt PDF"));
        OpeningPanelPseudoButton encryptPDFPseudoButton = new OpeningPanelPseudoButton(encryptPDFSmallLabelPanel);
        SmallLabelPanel decryptPDFSmallLabelPanel = new SmallLabelPanel(new SmallLabel("Decrypt JPEG"));
        OpeningPanelPseudoButton decryptPDFPseudoButton = new OpeningPanelPseudoButton(decryptPDFSmallLabelPanel);
        MiddleLabel encryptionLabel = new MiddleLabel("Encryption");
        MiddleLabelPanel encryptionLabelPanel = new MiddleLabelPanel(encryptionLabel);
        PseudoButtonCarrier encryptionCarrier = new PseudoButtonCarrier(encryptionLabelPanel,
                encryptPDFPseudoButton,decryptPDFPseudoButton);


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
}
