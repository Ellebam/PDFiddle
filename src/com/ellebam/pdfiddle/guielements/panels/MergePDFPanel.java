package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MergePDFPanel extends JPanel {
    private MergePDFPanel mergePDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();
    private ArrayList<File> mergeFileList = new ArrayList<>();
    private JPanel fileHandlerPanel = new JPanel();
    private JPanel fileCarrierPanel = new JPanel();
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

                if(mergeFileList.size() >0) {
                    fileCarrierPanel.setVisible(true);
                }else{fileCarrierPanel.setVisible(false);}

                for(int i =0; i< mergeFileList.size();i++){
                    fileHandlerPanel.add(new PDF2MergeDisplay(mergeFileList,i));
                }
            }
        }));
    }

    /**
     * Inner Class for single JPanels which are loaded and shown, when a file has been loaded for merging
     */
    public class PDF2MergeDisplay extends JPanel {


        PDF2MergeDisplay pdf2MergeDisplay;

        public PDF2MergeDisplay(ArrayList<File> mergeFileList, int mergeFileListIndex){
            pdf2MergeDisplay=this;
            JLabel docNameLabel = new JLabel(mergeFileList.get(mergeFileListIndex).getAbsolutePath());
            JPanel nameLabelPanel = new JPanel();
            nameLabelPanel.add(docNameLabel);

            JPanel cancelButtonPanel = new JPanel();
            addIcons(cancelButtonPanel,"/com/ellebam/pdfiddle/Icons/Vetors Market/down-arrow.png");

            pdf2MergeDisplay.add(nameLabelPanel);
            pdf2MergeDisplay.add(Box.createHorizontalGlue());
            pdf2MergeDisplay.add(cancelButtonPanel);






        }

        public PDF2MergeDisplay getPdf2MergeDisplay() {
            return pdf2MergeDisplay;
        }

        public void setPdf2MergeDisplay(PDF2MergeDisplay pdf2MergeDisplay) {
            this.pdf2MergeDisplay = pdf2MergeDisplay;
        }

        public void addIcons (JPanel iconCarrier, String iconDirectory) {
            try {
                BufferedImage IconPNG = ImageIO.read(getClass().getResource(iconDirectory));
                ImageIcon icon = new ImageIcon(IconPNG);
                JLabel iconLabel = new JLabel(scaleImage(icon, 15, 15));
                iconCarrier.add(iconLabel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
    }









}
