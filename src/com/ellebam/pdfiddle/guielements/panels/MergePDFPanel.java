package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor1;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.sun.scenario.effect.Merge;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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
        //fileHandlerPanel.setPreferredSize(new Dimension(350,200));



        JScrollPane fileCarrierScroller = new JScrollPane(fileHandlerPanel);
        fileCarrierScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fileCarrierScroller.setPreferredSize(new Dimension(350,200));
        fileCarrierScroller.setOpaque(false);
        fileCarrierScroller.setBorder(BorderFactory.createEmptyBorder());


        mergePDFPanel.add(headerPanel);
        mergePDFPanel.add(selectCarrierPanel);
        mergePDFPanel.add(fileCarrierScroller);
        mergePDFPanel.add(new ControlButtonCarrier(("Merge")));

        fileCarrierPanel.setVisible(false);





        selectDocPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                try {
                    mergeFileList.add(mergeDriver.chooseDoc());
                    mergeFileList.removeIf(Objects::isNull);

                }catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Error while loading File!");
                }
                System.out.println(mergeFileList);
                displayMergeFiles(mainFrame);
            }
        }));
    }

    public void displayMergeFiles(MainFrame mainFrame) {
        if(mergeFileList.size() >0) {
            fileCarrierPanel.setVisible(true);
        }else{/*fileCarrierPanel.setVisible(false);*/
            fileHandlerPanel.removeAll();
            mergePDFPanel.repaint();}
        fileHandlerPanel.removeAll();
        for(int i =0; i< mergeFileList.size();i++){
            JPanel carrier = new JPanel();
            carrier.add(new PDF2MergeDisplay(mergeFileList,i, mainFrame));
            //carrier.setOpaque(false);
            carrier.setAlignmentY(TOP_ALIGNMENT);
            fileHandlerPanel.add(carrier);
        }
        mergePDFPanel.revalidate();
    }



    /**
     * Inner Class for single JPanels which are loaded and shown, when a file has been loaded for merging
     */
    public class PDF2MergeDisplay extends JPanel {


        PDF2MergeDisplay pdf2MergeDisplay;
        protected Color fileDisplayColor = new SecondaryColor1();
        protected Dimension arcs = new Dimension(30, 30);


        public PDF2MergeDisplay(ArrayList<File> mergeFileList, int mergeFileListIndex, MainFrame mainframe) {

            pdf2MergeDisplay = this;
            JLabel docNameLabel = new JLabel(mergeFileList.get(mergeFileListIndex).getAbsoluteFile().getName());
            JPanel nameLabelPanel = new JPanel();
            docNameLabel.setAlignmentX(LEFT_ALIGNMENT);
            nameLabelPanel.setPreferredSize(new Dimension(200, 25));
            nameLabelPanel.add(docNameLabel);
            docNameLabel.setAlignmentY(BOTTOM_ALIGNMENT);
            nameLabelPanel.setOpaque(false);


            JPanel cancelButtonPanel = new JPanel();
            addIcons(cancelButtonPanel, "/com/ellebam/pdfiddle/Icons/Vectors Market/cancel_icon.png");

            JPanel upButtonPanel = new JPanel();
            addIcons(upButtonPanel, "/com/ellebam/pdfiddle/Icons/Vectors Market/up-arrow_icon.png");

            JPanel downButtonPanel = new JPanel();
            addIcons(downButtonPanel, "/com/ellebam/pdfiddle/Icons/Vectors Market/down-arrow_icon.png");

            pdf2MergeDisplay.add(nameLabelPanel);
            pdf2MergeDisplay.add(Box.createHorizontalGlue());

            pdf2MergeDisplay.add(upButtonPanel);
            upButtonPanel.setOpaque(false);
            upButtonPanel.addMouseListener((new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Collections.swap(mergeFileList,mergeFileListIndex,mergeFileListIndex-1);
                    displayMergeFiles(mainframe);
                }
            }));

            pdf2MergeDisplay.add(downButtonPanel);
            downButtonPanel.setOpaque(false);
            downButtonPanel.addMouseListener((new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Collections.swap(mergeFileList,mergeFileListIndex,mergeFileListIndex+1);
                    displayMergeFiles(mainframe);
                }
            }));

            pdf2MergeDisplay.add(cancelButtonPanel);
            cancelButtonPanel.setOpaque(false);
            cancelButtonPanel.addMouseListener((new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mergeFileList.remove(mergeFileListIndex);
                    displayMergeFiles(mainframe);
                }
            }));


            pdf2MergeDisplay.setPreferredSize(new Dimension(350, 40));
            pdf2MergeDisplay.setOpaque(false);

        }

        public PDF2MergeDisplay getPdf2MergeDisplay() {
            return pdf2MergeDisplay;
        }

        public void setPdf2MergeDisplay(PDF2MergeDisplay pdf2MergeDisplay) {
            this.pdf2MergeDisplay = pdf2MergeDisplay;
        }

        public void addIcons(JPanel iconCarrier, String iconDirectory) {
            try {
                BufferedImage IconPNG = ImageIO.read(getClass().getResource(iconDirectory));
                ImageIcon icon = new ImageIcon(IconPNG);
                JLabel iconLabel = new JLabel(scaleImage(icon, 30, 25));
                iconCarrier.add(iconLabel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public ImageIcon scaleImage(ImageIcon icon, int width, int height) {
            int newWidth = icon.getIconWidth();
            int newHeight = icon.getIconHeight();

            if (icon.getIconWidth() > width) {
                newWidth = width;
                newHeight = (newWidth * icon.getIconHeight()) / icon.getIconWidth();
            }

            if (newHeight > height) {
                newHeight = height;
                newWidth = (icon.getIconWidth() * newHeight / icon.getIconHeight());
            }

            return new ImageIcon(icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setColor(fileDisplayColor);
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }


    }









}
