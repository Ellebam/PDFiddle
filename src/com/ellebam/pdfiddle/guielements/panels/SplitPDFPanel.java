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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class SplitPDFPanel extends JPanel {
    private SplitPDFPanel splitPDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();
    private File doc2Split;
    private PDDocument PDF2Split;
    private ArrayList<Integer> splitPointList;
    private JPanel fileHandlerPanel = new JPanel();
    private Driver splitDriver = new Driver();
    private ComboSelectionPanel comboSelectionPanel;
    private String[] splitNumCombo = {"0","1","2","3","4","5"};
    private FilePreviewPanel filePreviewPanel;



    public SplitPDFPanel (MainFrame mainFrame){
        splitPDFPanel = this;
        splitPDFPanel.setLayout((new BoxLayout(splitPDFPanel, BoxLayout.Y_AXIS)));
        splitPDFPanel.add(Box.createRigidArea(new Dimension(1000,20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the PDF-Document to split"));

        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);

        fileHandlerPanel.setLayout(new BoxLayout(fileHandlerPanel,BoxLayout.Y_AXIS));
        comboSelectionPanel = new ComboSelectionPanel(splitNumCombo,"<html>Select number of splits<br/>(select 0 for splitting all pages)</html>");
        fileHandlerPanel.setAlignmentY(TOP_ALIGNMENT);
        fileHandlerPanel.setOpaque(false);
        fileHandlerPanel.add(comboSelectionPanel);
        comboSelectionPanel.setVisible(false);
        fileHandlerPanel.add(Box.createRigidArea(new Dimension(30,10)));





        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Split");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                splitPDFPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));

            }
        });

        controlButtonCarrier.operatorButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                ArrayList<String[]> stringRangeList = new ArrayList<>();

                if(comboSelectionPanel.getComboBox().getSelectedIndex()==0){
                    splitDriver.splitPDFDocs(splitDriver.chooseSaveDirectory(mainFrame),
                            splitDriver.chooseDoc(),splitPointList,mainFrame);
                }else{
                for(int i=0; i<comboSelectionPanel.getComboBox().getSelectedIndex();i++){
                    //continue here!!
                }


                }
            }


        });

        splitPDFPanel.add(headerPanel);
        splitPDFPanel.add(selectCarrierPanel);
        splitPDFPanel.add(fileHandlerPanel);
        splitPDFPanel.add(controlButtonCarrier);



        selectDocPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (fileHandlerPanel.getComponentCount() > 1){
                    fileHandlerPanel.remove(1);
                }
                try{
                    doc2Split = splitDriver.chooseDoc();
                    filePreviewPanel = new FilePreviewPanel(doc2Split,mainFrame,0);
                    fileHandlerPanel.add(filePreviewPanel);
                    comboSelectionPanel.setVisible(true);


                }catch(Exception ex){ex.printStackTrace();
            }
                fileHandlerPanel.revalidate();
        }
    });
}

}
