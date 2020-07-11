package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import org.apache.pdfbox.pdmodel.PDDocument;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CompressPDFPanel extends JPanel {
    private CompressPDFPanel compressPDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();
    private File doc2Compress;
    private PDDocument PDF2Compress;
    private JPanel fileHandlerPanel = new JPanel();
    private FilePreviewPanel filePreviewPanel;
    private Driver compressionDriver = new Driver();
    private ComboSelectionPanel comboSelectionPanel;
    private String[] compressionQualityCombo = {"Low","Medium","High"};
    private ArrayList<Integer> pageCounter;



    public PDDocument getPDF2Compress() {
        return PDF2Compress;
    }

    public ArrayList<Integer> getPageCounter() {
        return pageCounter;
    }


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

    private static ActionListener createProgressMonitorListener(Component parent, ArrayList<Integer> pageCounter){
        UIManager.put("ProgressMonitor.progressText", "File Compression");
        CompressPDFPanel newMater = new CompressPDFPanel((MainFrame)parent);
        return (ae) -> {
            new Thread(() -> {
                UIManager.put("ProgressMonitor.progressText", "File Compression");
                CompressPDFPanel newMate = new CompressPDFPanel((MainFrame)parent);
                //creating a ProgressMonitor instance
                ProgressMonitor progressMonitor = new ProgressMonitor(parent,
                        "Compressing File", "Compression Start", 0,
                        newMate.getPDF2Compress().getNumberOfPages());

                //decide after 200 millis whether to show popup or not
                progressMonitor.setMillisToDecideToPopup(200);
                //after deciding if predicted time is longer than 200 show popup
                progressMonitor.setMillisToPopup(200);
                progressMonitor.setNote("Compressing page: " + pageCounter.get(pageCounter.size() - 1));
                progressMonitor.setProgress(pageCounter.get(pageCounter.size() - 1));
                try {
                    //delay task simulation
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
                progressMonitor.setNote("Compression Finished!");

            }).start();
        };
    }


    }

