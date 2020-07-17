package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor2;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.layouts.WrapLayout;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ConvertJPEG2PDFPanel extends JPanel {

    private ConvertJPEG2PDFPanel            convertJPEG2PDFPanel;
    private SelectDocPseudoButton           selectDocPseudoButton           = new SelectDocPseudoButton();
    private ArrayList<File>                 listOfJPEGs                     = new ArrayList<>();
    private PDDocument                      pdf2Convert2JPEG;
    private WrapLayout                      wrapLayout                      = new WrapLayout();
    private JPanel                          fileHandlerPanel                = new JPanel(wrapLayout);
    private JScrollPane                     fileHandlerScroller             = new JScrollPane(fileHandlerPanel);
    private Driver                          JPEG2PDFDriver                  = new Driver();
    private Boolean                         mergeFilesBool                  = new Boolean(false);


    public ConvertJPEG2PDFPanel(MainFrame mainFrame){
        convertJPEG2PDFPanel = this;

        convertJPEG2PDFPanel.setLayout((new BoxLayout(convertJPEG2PDFPanel, BoxLayout.Y_AXIS)));
        convertJPEG2PDFPanel.add(Box.createRigidArea(new Dimension(1000, 20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the JPEG-Files to convert to PDF"));

        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);


        WrapLayout wrapLayout = new WrapLayout();
        wrapLayout.setAlignment(FlowLayout.LEFT);


        fileHandlerPanel.setSize(fileHandlerScroller.getSize());
        fileHandlerScroller.setBorder(null);

        //setting up the controlButtons
        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Convert");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        //standard BackButton functionality
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                convertJPEG2PDFPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });


        convertJPEG2PDFPanel.add(headerPanel);
        convertJPEG2PDFPanel.add(selectCarrierPanel);
        convertJPEG2PDFPanel.add(Box.createRigidArea(new Dimension(10,10)));
        convertJPEG2PDFPanel.add(fileHandlerScroller);
        convertJPEG2PDFPanel.add(Box.createVerticalGlue());
        convertJPEG2PDFPanel.add(controlButtonCarrier);
        fileHandlerScroller.setVisible(false);

        selectDocPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                try {
                    JPEG2PDFDriver.addDocs2MergeList(listOfJPEGs,JPEG2PDFDriver.chooseDoc(mainFrame));
                    listOfJPEGs.removeIf(Objects::isNull);

                }catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Error while loading File!");
                }
                System.out.println(listOfJPEGs);
                displayMergeFiles(mainFrame);
            }
        }));
    }
    /**
     * Method responsible for showing the selected files for converting (plus optional merging). 
     * For every selected file it will create a custom Panel with the name of the File
     * @param mainFrame mainFrame is needed so other methods or constructors utilizing this method can access mainframe
     */
    public void displayMergeFiles(MainFrame mainFrame) {
        if(listOfJPEGs.size() >0) {
            fileHandlerScroller.setVisible(true);
        }else{
            fileHandlerPanel.removeAll();
            convertJPEG2PDFPanel.repaint();}
        fileHandlerPanel.removeAll();
        for(int i =0; i< listOfJPEGs.size();i++){
            JPanel carrier = new JPanel();
            carrier.add(new ConvertJPEG2PDFPanel.JPEGCConversionDisplay(listOfJPEGs,i, mainFrame));
            carrier.setAlignmentY(TOP_ALIGNMENT);
            fileHandlerPanel.add(carrier);
        }
        convertJPEG2PDFPanel.revalidate();
    }
    public class JPEGCConversionDisplay extends JPanel{
        JPEGCConversionDisplay                  jpegcConversionDisplay;
        private Color                           fileDisplayColor                = new SecondaryColor2();
        private Dimension                       arcs                            = new Dimension(30,30);
        private JPanel                          controlButtonPanel;
        private Border border                  = BorderFactory.createLineBorder(Color.ORANGE,2,true);
    
    public JPEGCConversionDisplay (ArrayList<File> listOfJPEGs, int JPEGListIndex, MainFrame mainFrame ){
        
        jpegcConversionDisplay                  = this;
        controlButtonPanel                      = new JPanel();
        
        
        jpegcConversionDisplay.setLayout(new BoxLayout(jpegcConversionDisplay,BoxLayout.Y_AXIS));


        JPanel cancelButtonPanel = new JPanel();
        addIcons(cancelButtonPanel, "/com/ellebam/pdfiddle/Icons/Vectors Market/cancel_icon.png");

        JPanel upButtonPanel = new JPanel();
        addIcons(upButtonPanel, "/com/ellebam/pdfiddle/Icons/Vectors Market/up-arrow_icon.png");

        JPanel downButtonPanel = new JPanel();
        addIcons(downButtonPanel, "/com/ellebam/pdfiddle/Icons/Vectors Market/down-arrow_icon.png");

        /**
         *Button for moving Object associated with this panel up in the corresponding ArrayList. Other MouseListeners
         * are used to highlight button when mouse is hovering over it
         */
        controlButtonPanel.add(upButtonPanel);
        upButtonPanel.setOpaque(false);
        upButtonPanel.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Collections.swap(listOfJPEGs,JPEGListIndex,JPEGListIndex-1);
                displayMergeFiles(mainFrame);
            }
        }));
        upButtonPanel.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseEntered (MouseEvent evt){
                upButtonPanel.setBorder(border);

            }
        }));
        upButtonPanel.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseExited (MouseEvent evt){
                upButtonPanel.setBorder(null);
            }
        }));

        /**
         *Button for moving Object associated with this panel down in the corresponding ArrayList. Other MouseListeners
         *  are used to highlight button when mouse is hovering over it
         */
        controlButtonPanel.add(downButtonPanel);
        downButtonPanel.setOpaque(false);
        downButtonPanel.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Collections.swap(listOfJPEGs,JPEGListIndex,JPEGListIndex+1);
                displayMergeFiles(mainFrame);
            }
        }));
        downButtonPanel.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseEntered (MouseEvent evt){
                downButtonPanel.setBorder(border);

            }
        }));
        downButtonPanel.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseExited (MouseEvent evt){
                downButtonPanel.setBorder(null);
            }
        }));

        /**
         *Button for removing Object associated with this panel from the corresponding ArrayList. Other MouseListeners
         * are used to highlight button when mouse is hovering over it
         */
        controlButtonPanel.add(cancelButtonPanel);
        cancelButtonPanel.setOpaque(false);
        cancelButtonPanel.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                listOfJPEGs.remove(JPEGListIndex);
                displayMergeFiles(mainFrame);
            }
        }));
        cancelButtonPanel.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseEntered (MouseEvent evt){
                cancelButtonPanel.setBorder(border);

            }
        }));
        cancelButtonPanel.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseExited (MouseEvent evt){
                cancelButtonPanel.setBorder(null);
            }
        }));
        
        FilePreviewPanel filePreviewPanel = new FilePreviewPanel(listOfJPEGs.get(JPEGListIndex),mainFrame,0);
        
        
        jpegcConversionDisplay.add(controlButtonPanel);
        jpegcConversionDisplay.add(filePreviewPanel);
    }
        /**
         * method for adding icon Images to a new Icon Object and adding that object to a existing Icon carrier JPanel
         * @param iconCarrier the icon carrier JPanel
         * @param iconDirectory saving directory of the .png file (in source folder)
         */
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

        /**
         * method for resizing icons while retaining icon scaling
         * @param icon ImageIcon for resizing
         * @param width desired width
         * @param height desired height
         * @return returns a fully rescaled Icon
         */
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

