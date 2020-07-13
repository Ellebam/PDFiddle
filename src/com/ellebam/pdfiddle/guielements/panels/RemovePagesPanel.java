package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.layouts.WrapLayout;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
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
    private ArrayList<FilePreviewPanel>  previewPanelList;


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




        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Compress");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                removePagesPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });


        removePagesPanel.add(headerPanel);
        removePagesPanel.add(selectCarrierPanel);
        removePagesPanel.add(fileHandlerScroller);
        removePagesPanel.add(controlButtonCarrier);



        selectDocPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                previewPanelList    =   new ArrayList<>();

                if (fileHandlerPanel.getComponentCount() > 1){
                    fileHandlerPanel.remove(1);
                }
                try{
                    doc2RemovePages = File.createTempFile("temp",null);
                    pdf2RemovePages =removalDriver.handlePDFEncryption(removalDriver.chooseDoc(mainFrame),mainFrame);
                    pdf2RemovePages.save(doc2RemovePages);

                    for(int i=0; i<pdf2RemovePages.getNumberOfPages();i++) {

                        FilePreviewPanel filePreviewPanel = new FilePreviewPanel(doc2RemovePages, mainFrame, i);
                        fileHandlerPanel.add(filePreviewPanel);
                        //fileHandlerPanel.add(Box.createRigidArea(new Dimension(10,10)));
                        pdf2RemovePages.close();
                    }
                    fileHandlerPanel.add(Box.createVerticalGlue());


                }catch(Exception ex){ex.printStackTrace();
                }
                fileHandlerPanel.revalidate();
            }
        });
    }
}
