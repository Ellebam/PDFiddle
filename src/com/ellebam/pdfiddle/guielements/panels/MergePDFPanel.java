package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class MergePDFPanel extends JPanel {
    private MergePDFPanel mergePDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();
    private ArrayList<File> mergeFileList = new ArrayList<>();
    private JPanel fileHandlerPanel = new JPanel();
    private Driver mergeDriver = new Driver();

    public MergePDFPanel(MainFrame mainFrame){
        mergePDFPanel = this;
        mergePDFPanel.setLayout(new BoxLayout(mergePDFPanel, BoxLayout.Y_AXIS));
        mergePDFPanel.add(Box.createRigidArea(new Dimension(1000,20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel("Select the PDF-Documents to merge"));



        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);



        fileHandlerPanel.setLayout(new BoxLayout(fileHandlerPanel,BoxLayout.Y_AXIS));
        fileHandlerPanel.setPreferredSize(new Dimension(350,200));
        fileHandlerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,true));

        JPanel fileCarrierPanel = new JPanel();
        JScrollPane fileCarrierScroller = new JScrollPane(fileHandlerPanel);
        fileCarrierScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fileCarrierPanel.add(fileCarrierScroller);


        mergePDFPanel.add(headerPanel);
        mergePDFPanel.add(selectCarrierPanel);
        mergePDFPanel.add(fileCarrierPanel);
        fileCarrierPanel.setVisible(false);




        selectDocPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                try {
                    mergeFileList.add(mergeDriver.chooseDoc());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Error while loading File!");
                }System.out.println(mergeFileList);
            }
        }));
    }

    public class PDF2MergeDisplay extends JPanel {
        PDF2MergeDisplay pdf2MergeDisplay;

        public PDF2MergeDisplay(int mergeFileListIndex){

        }
    }





}
