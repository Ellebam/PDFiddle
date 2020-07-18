package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import org.apache.pdfbox.pdmodel.PDDocument;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


/**
 * This class represents the CompressPDFPanel where a file is compressed and saved again.
 */
public class CompressPDFPanel extends JPanel {

    private CompressPDFPanel        compressPDFPanel;
    private SelectDocPseudoButton   selectDocPseudoButton   = new SelectDocPseudoButton();
    private File                    doc2Compress;
    private PDDocument              PDF2Compress;
    private JPanel                  fileHandlerPanel        = new JPanel();
    private FilePreviewPanel        filePreviewPanel;
    private Driver                  compressionDriver       = new Driver();
    private ComboSelectionPanel     comboSelectionPanel;
    private String[]                compressionQualityCombo = {"Low","Medium","High"};



    public CompressPDFPanel (MainFrame mainFrame){
        compressPDFPanel = this;
        compressPDFPanel.setLayout((new BoxLayout(compressPDFPanel, BoxLayout.Y_AXIS)));
        compressPDFPanel.add(Box.createRigidArea(new Dimension(1000,20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the PDF-Document to compress"));

        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);


        fileHandlerPanel.setLayout(new BoxLayout(fileHandlerPanel,BoxLayout.Y_AXIS));
        comboSelectionPanel = new ComboSelectionPanel(compressionQualityCombo,"Select compression rate");
        comboSelectionPanel.getComboBox().setSelectedIndex(1);
        fileHandlerPanel.setAlignmentY(TOP_ALIGNMENT);
        fileHandlerPanel.setOpaque(false);
        fileHandlerPanel.add(comboSelectionPanel);
        comboSelectionPanel.setVisible(false);
        fileHandlerPanel.add(Box.createRigidArea(new Dimension(30,10)));

        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Compress");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                compressPDFPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });


        /*
         * selecting the operatorbutton will trigger the compressPDF procedure with the selected comrpession quality
         */
        controlButtonCarrier.operatorButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String saveDirectory = compressionDriver.chooseSaveDirectory(mainFrame);



                if (comboSelectionPanel.getComboBox().getSelectedIndex() == 0){
                    compressionDriver.compressPDF(doc2Compress,saveDirectory,150, mainFrame);
                }else if (comboSelectionPanel.getComboBox().getSelectedIndex() == 1){
                    compressionDriver.compressPDF(doc2Compress,saveDirectory,130, mainFrame);
                }else if (comboSelectionPanel.getComboBox().getSelectedIndex() == 2){
                    compressionDriver.compressPDF(doc2Compress,saveDirectory,100, mainFrame);
                }
            }
        });




        compressPDFPanel.add(headerPanel);
        compressPDFPanel.add(selectCarrierPanel);
        compressPDFPanel.add(fileHandlerPanel);
        compressPDFPanel.add(controlButtonCarrier);

/*
 * The selectDocPseudoButton also triggers the Document preview as a own Panel(icon).
 */
        selectDocPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (fileHandlerPanel.getComponentCount() > 1){
                    fileHandlerPanel.remove(1);
                }
                try{
                    doc2Compress = File.createTempFile("temp",null);
                    PDF2Compress =compressionDriver.handlePDFEncryption(compressionDriver.chooseDoc(mainFrame),mainFrame);
                    PDF2Compress.save(doc2Compress);
                    filePreviewPanel = new FilePreviewPanel(doc2Compress,mainFrame,0);
                    fileHandlerPanel.add(filePreviewPanel);
                    comboSelectionPanel.setVisible(true);
                    PDF2Compress.close();


                }catch(Exception ex){ex.printStackTrace();
                }
                fileHandlerPanel.revalidate();
            }
        });



    }



    }

