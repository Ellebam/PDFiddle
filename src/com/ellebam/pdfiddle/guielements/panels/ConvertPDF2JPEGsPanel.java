package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.colors.HighlightColor;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.layouts.WrapLayout;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.UIManager.get;

public class ConvertPDF2JPEGsPanel extends JPanel {

    private ConvertPDF2JPEGsPanel           convertPDF2JPEGsPanel;
    private SelectDocPseudoButton           selectDocPseudoButton           = new SelectDocPseudoButton();
    private File                            doc2Convert2JPEG;
    private PDDocument                      pdf2Convert2JPEG;
    private WrapLayout                      wrapLayout                      = new WrapLayout();
    private JPanel                          fileHandlerPanel                = new JPanel(wrapLayout);
    private JScrollPane                     fileHandlerScroller             = new JScrollPane(fileHandlerPanel);
    private Driver                          pdf2JPEGDriver                  = new Driver();
    private Color                           pdf2JPEGBorderColor             = new HighlightColor();
    private Border                          convertItemBorder               = BorderFactory.createLineBorder
                                                                            (pdf2JPEGBorderColor, 5, true);
    private ArrayList<Boolean>              pages2ConvertList;
    private ComboSelectionPanel             comboSelectionPanel;
    private String[]                        conversionQualityCombo          = {"Low","Medium","High"};



    public ConvertPDF2JPEGsPanel(MainFrame mainFrame) {
        convertPDF2JPEGsPanel = this;

        convertPDF2JPEGsPanel.setLayout((new BoxLayout(convertPDF2JPEGsPanel, BoxLayout.Y_AXIS)));
        convertPDF2JPEGsPanel.add(Box.createRigidArea(new Dimension(1000, 20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the PDF-Document to convert pages from"));

        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);


        WrapLayout wrapLayout = new WrapLayout();
        wrapLayout.setAlignment(FlowLayout.LEFT);


        fileHandlerPanel.setSize(fileHandlerScroller.getSize());
        fileHandlerScroller.setBorder(null);


        comboSelectionPanel = new ComboSelectionPanel(conversionQualityCombo,"Select JPEG image quality");
        comboSelectionPanel.getComboBox().setSelectedIndex(1);
        comboSelectionPanel.setVisible(false);

        JPanel conversionButtonCarrier = new JPanel();
        ControlButtonCarrierPlus conversionButtons = new ControlButtonCarrierPlus("Select All", "Deselect");

        conversionButtonCarrier.add(conversionButtons);
        conversionButtonCarrier.setPreferredSize(new Dimension(fileHandlerScroller.getWidth(),50));
        conversionButtonCarrier.setVisible(false);


        //setting up the controlButtons
        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Convert");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        //standard BackButton functionality
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                convertPDF2JPEGsPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });
        //setting up the ConvertButton to fire the convertPDF2JPEGs() method. It will take the informations processed
        //through the previewPanels and the pages2RConvertList (entry true if page should be converted) and save the pages
        //as single JPEG-Files in the desired directory

        controlButtonCarrier.operatorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String saveDirectory = pdf2JPEGDriver.chooseSaveDirectory((mainFrame));

                if(comboSelectionPanel.getComboBox().getSelectedIndex()==0){
                    pdf2JPEGDriver.convertPDF2JPEGs(doc2Convert2JPEG,
                            saveDirectory,pages2ConvertList,150,mainFrame);
                }else if(comboSelectionPanel.getComboBox().getSelectedIndex()==1){
                    pdf2JPEGDriver.convertPDF2JPEGs(doc2Convert2JPEG,saveDirectory,pages2ConvertList,200,mainFrame);
                }else if(comboSelectionPanel.getComboBox().getSelectedIndex()==2) {
                    pdf2JPEGDriver.convertPDF2JPEGs(doc2Convert2JPEG, saveDirectory, pages2ConvertList, 300, mainFrame);
                }

            }
        });

        convertPDF2JPEGsPanel.add(headerPanel);
        convertPDF2JPEGsPanel.add(selectCarrierPanel);
        convertPDF2JPEGsPanel.add(Box.createRigidArea(new Dimension(10,10)));
        convertPDF2JPEGsPanel.add(comboSelectionPanel);
        convertPDF2JPEGsPanel.add(conversionButtonCarrier);
        convertPDF2JPEGsPanel.add(fileHandlerScroller);
        convertPDF2JPEGsPanel.add(Box.createVerticalGlue());
        convertPDF2JPEGsPanel.add(controlButtonCarrier);



        selectDocPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);



                Thread operationThread = new Thread(()-> {
                    if (fileHandlerPanel.getComponentCount() > 1) {
                        fileHandlerPanel.removeAll();
                    }

                    try {
                        doc2Convert2JPEG = File.createTempFile("temp", null);
                        pdf2Convert2JPEG = pdf2JPEGDriver.handlePDFEncryption(pdf2JPEGDriver.chooseDoc(mainFrame), mainFrame);
                        pdf2Convert2JPEG.save(doc2Convert2JPEG);


                        ProgressMonitor progressMonitor = new ProgressMonitor(mainFrame,"File Loading",
                                "Start loading...",0, pdf2Convert2JPEG.getNumberOfPages()-1);
                        progressMonitor.setMillisToDecideToPopup(50);
                        progressMonitor.setMillisToPopup(50);
                        pages2ConvertList= new ArrayList<>();
                        for (int i = 0; i < pdf2Convert2JPEG.getNumberOfPages(); i++) {

                            FilePreviewPanel filePreviewPanel = new FilePreviewPanel(doc2Convert2JPEG, mainFrame, i);


                            //setting the interaction when user hovers over the filePreviewPanel (setting red border)
                            filePreviewPanel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    super.mouseEntered(e);
                                    filePreviewPanel.setBorder(convertItemBorder);
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
                                    if(!pages2ConvertList.get(finalI)){
                                        filePreviewPanel.setBackground(Color.red);
                                        pages2ConvertList.set(finalI,true);
                                    }else{

                                        filePreviewPanel.setBackground(null);
                                        pages2ConvertList.set(finalI,false);
                                    }

                                }
                            });


                            //adding the processed pages as a preview Panel to the fileHandlerPanel
                            fileHandlerPanel.add(filePreviewPanel);
                            //updating the list for page removal for this page
                            pages2ConvertList.add(false);
                            progressMonitor.setNote("Loading page: "+i);
                            progressMonitor.setProgress(i);
                            pdf2Convert2JPEG.close();
                        }
                        fileHandlerPanel.add(Box.createVerticalGlue());
                        JOptionPane.showMessageDialog(mainFrame,"Loading successful!");


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    comboSelectionPanel.setVisible(true);
                    conversionButtonCarrier.setVisible(true);
                    fileHandlerPanel.validate();
                    fileHandlerScroller.validate();
                    mainFrame.validate();

                });



                operationThread.start();
            }
        });

        conversionButtons.getFirstOperatorButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (int i = 0; i<pdf2Convert2JPEG.getNumberOfPages();i++){
                    fileHandlerPanel.getComponent(i).setBackground(Color.red);
                    pages2ConvertList.set(i,true);
                    fileHandlerPanel.validate();
                    fileHandlerScroller.validate();
                    mainFrame.validate();

                }
            }
        });

        conversionButtons.getSecondOperatorButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for(int i=0; i<pdf2Convert2JPEG.getNumberOfPages();i++){
                    fileHandlerPanel.getComponent(i).setBackground(null);
                    pages2ConvertList.set(i,false);
                    fileHandlerPanel.validate();
                    fileHandlerScroller.validate();
                    mainFrame.validate();
                }
            }
        });

    }
}
