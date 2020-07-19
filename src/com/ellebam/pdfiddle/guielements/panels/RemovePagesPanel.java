/*      Copyright [2020] [Arian Jamborzadeh]
        SPDX-License-Identifier: Apache-2.0  */


package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.colors.HighlightColor;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.layouts.WrapLayout;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class RemovePagesPanel extends JPanel  {

    private RemovePagesPanel             removePagesPanel;
    private SelectDocPseudoButton        selectDocPseudoButton       = new SelectDocPseudoButton();
    private File                         doc2RemovePages;
    private PDDocument                   pdf2RemovePages;
    private WrapLayout                   wrapLayout                  = new WrapLayout();
    private JPanel                       fileHandlerPanel            = new JPanel(wrapLayout);
    private JScrollPane                  fileHandlerScroller         = new JScrollPane(fileHandlerPanel);
    private Driver                       removalDriver               = new Driver();
    private Color                        removeBorderColor           = new HighlightColor();
    private Border                       removeItemBorder            = BorderFactory.createLineBorder
                                                                        (removeBorderColor,5,true);
    private ArrayList<Boolean>           pages2RemoveList;



    public RemovePagesPanel (MainFrame mainFrame){
        removePagesPanel = this;

        removePagesPanel.setLayout((new BoxLayout(removePagesPanel, BoxLayout.Y_AXIS)));
        removePagesPanel.add(Box.createRigidArea(new Dimension(1000,20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the PDF-Document to remove pages from"));

        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);


        WrapLayout wrapLayout = new WrapLayout();
        wrapLayout.setAlignment(FlowLayout.LEFT);


        fileHandlerPanel.setSize(fileHandlerScroller.getSize());


        fileHandlerScroller.setBorder(null);



        //setting up the controlButtons
        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Remove");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        //standard BackButton functionality
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                removePagesPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });
        //setting up the RemoveButton to fire the removePagesFromPDF() method. It will take the informations processed
        //through the previewPanels and the pages2RemoveList (entry true if page should be removed) and save a copy
        // of the PDF document with the wished pages removed

        controlButtonCarrier.operatorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                removalDriver.removePagesFromPDF(doc2RemovePages, removalDriver.chooseSaveDirectory(mainFrame),
                        pages2RemoveList, mainFrame);
            }
        });



        removePagesPanel.add(headerPanel);
        removePagesPanel.add(selectCarrierPanel);
        removePagesPanel.add(Box.createRigidArea(new Dimension(10,10)));
        removePagesPanel.add(fileHandlerScroller);
        removePagesPanel.add(Box.createVerticalGlue());
        removePagesPanel.add(controlButtonCarrier);



        selectDocPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);



                Thread operationThread = new Thread(()-> {
                    if (fileHandlerPanel.getComponentCount() > 1) {
                        fileHandlerPanel.removeAll();
                    }
                    try {
                        doc2RemovePages = File.createTempFile("temp", null);
                        pdf2RemovePages = removalDriver.handlePDFEncryption(removalDriver.chooseDoc(mainFrame), mainFrame);
                        pdf2RemovePages.save(doc2RemovePages);


                        ProgressMonitor progressMonitor = new ProgressMonitor(mainFrame,"File Loading",
                                "Start loading...",0, pdf2RemovePages.getNumberOfPages()-1);
                        progressMonitor.setMillisToDecideToPopup(50);
                        progressMonitor.setMillisToPopup(50);
                        pages2RemoveList = new ArrayList<>();
                        for (int i = 0; i < pdf2RemovePages.getNumberOfPages(); i++) {

                            FilePreviewPanel filePreviewPanel = new FilePreviewPanel(doc2RemovePages, mainFrame, i);


                            //setting the interaction when user hovers over the filePreviewPanel (setting red border)
                            filePreviewPanel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    super.mouseEntered(e);
                                    filePreviewPanel.setBorder(removeItemBorder);
                                }
                            });
                            //setting the interaction when user doesent hover over the filePreviewPanel anymore (remove border)
                            filePreviewPanel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    super.mouseExited(e);
                                    filePreviewPanel.setBorder(null);
                                }
                            });

                            //setting the interaction when user clicks the Panel. The ArrayList pages2RemoveList is
                            //is updated when user decides to remove the page by clicking on it
                            int finalI = i;
                            filePreviewPanel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    super.mouseClicked(e);
                                    if(!pages2RemoveList.get(finalI)){
                                        filePreviewPanel.setBackground(Color.red);
                                        pages2RemoveList.set(finalI,true);
                                    }else{

                                        filePreviewPanel.setBackground(null);
                                        pages2RemoveList.set(finalI,false);
                                    }

                                }
                            });


                            //adding the processed pages as a preview Panel to the fileHandlerPanel
                            fileHandlerPanel.add(filePreviewPanel);
                            //updating the list for page removal for this page
                            pages2RemoveList.add(false);
                            progressMonitor.setNote("Loading page: "+i);
                            progressMonitor.setProgress(i);
                            pdf2RemovePages.close();
                        }
                        fileHandlerPanel.add(Box.createVerticalGlue());
                        JOptionPane.showMessageDialog(mainFrame,"Loading successful!");


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    fileHandlerPanel.validate();
                    fileHandlerScroller.validate();
                    mainFrame.validate();

                });



                operationThread.start();
            }
        });
    }
}
