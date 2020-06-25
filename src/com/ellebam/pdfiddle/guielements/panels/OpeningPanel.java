package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.labels.MiddleLabel;
import com.ellebam.pdfiddle.guielements.labels.SmallLabel;

import javax.swing.*;
import java.awt.*;

public class OpeningPanel extends JPanel {
    private JPanel openingPanel;



    public OpeningPanel(){
        openingPanel = this;

        openingPanel.setLayout(new BoxLayout(openingPanel, BoxLayout.Y_AXIS));
        openingPanel.add(Box.createRigidArea(new Dimension(10,50)));
        openingPanel.add(new HeaderPanel(new HeaderLabel("Welcome to PDFiddle 1.0")));


        SmallLabelPanel splitSmallLabelPanel = new SmallLabelPanel(new SmallLabel("SplitPDF"));
        OpeningPanelPseudoButton splitPseudoButton = new OpeningPanelPseudoButton(splitSmallLabelPanel);
        SmallLabelPanel mergeSmallLabelPanel = new SmallLabelPanel(new SmallLabel("MergePDF"));
        OpeningPanelPseudoButton mergePseudoButton = new OpeningPanelPseudoButton(mergeSmallLabelPanel);
        MiddleLabel mergeAndSplitLabel = new MiddleLabel("Merge or Split");
        MiddleLabelPanel mergeAndSplitLabelPanel = new MiddleLabelPanel(mergeAndSplitLabel);
        PseudoButtonCarrier mergeAndSplitCarrier = new PseudoButtonCarrier(mergeAndSplitLabelPanel,
                mergePseudoButton,splitPseudoButton);


        SmallLabelPanel compressSmallLabelPanel = new SmallLabelPanel(new SmallLabel("CompressPDF"));
        OpeningPanelPseudoButton compressPseudoButton = new OpeningPanelPseudoButton(compressSmallLabelPanel);
        MiddleLabel compressLabel = new MiddleLabel("Compress");
        MiddleLabelPanel compressLabelPanel = new MiddleLabelPanel(compressLabel);
        PseudoButtonCarrier compressCarrier = new PseudoButtonCarrier(compressLabelPanel,
                compressPseudoButton);



        JPanel carrierPanel = new JPanel();
        carrierPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
        carrierPanel.add(mergeAndSplitCarrier);
        carrierPanel.add(compressCarrier);

        openingPanel.add(carrierPanel);
        openingPanel.setVisible(true);

    }
}
