package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.SmallLabel;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for the SmallLabels
 */
public class SmallLabelPanel extends JPanel {
    private SmallLabelPanel smallLabelPanel;

    /**
     * standard Constructor for a JLabel to hold a SmallLabel.
     * @param smallLabel JLabel which this Panel is going to hold
     */
    public SmallLabelPanel(SmallLabel smallLabel){
        smallLabelPanel = this;
        smallLabelPanel.setLayout(new BoxLayout(smallLabelPanel, BoxLayout.Y_AXIS));
        smallLabelPanel.setOpaque(false);
        smallLabelPanel.add(smallLabel);
    }
}
