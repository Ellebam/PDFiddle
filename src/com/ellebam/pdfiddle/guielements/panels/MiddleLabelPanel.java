package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.MiddleLabel;

import javax.swing.*;

public class MiddleLabelPanel extends JPanel {
    private MiddleLabelPanel middleLabelPanel;

    public MiddleLabelPanel(MiddleLabel middleLabel){
        middleLabelPanel=this;
        middleLabelPanel.setOpaque(false);
        middleLabelPanel.add(middleLabel);
    }
}
