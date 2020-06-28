package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;

import javax.swing.*;
import java.awt.*;

public class MergePDFPanel extends JPanel {
    private MergePDFPanel mergePDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();

    public MergePDFPanel(MainFrame mainFrame){
        mergePDFPanel = this;

        mergePDFPanel.setLayout(new BoxLayout(mergePDFPanel, BoxLayout.Y_AXIS));
        mergePDFPanel.add(Box.createRigidArea(new Dimension(0,20)));



        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel("Select the PDF-Documents to merge"));

        JPanel carrierPanel = new JPanel();
        carrierPanel.add(selectDocPseudoButton);



        mergePDFPanel.add(headerPanel);
        mergePDFPanel.add(carrierPanel);


    }
}
