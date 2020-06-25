package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.SmallLabel;

import javax.swing.*;
import java.awt.*;

public class SmallLabelPanel extends JPanel {
    private SmallLabelPanel smallLabelPanel;

    public SmallLabelPanel(SmallLabel smallLabel){
        smallLabelPanel = this;
        smallLabelPanel.setLayout(new BoxLayout(smallLabelPanel, BoxLayout.Y_AXIS));
        smallLabelPanel.setOpaque(false);
        smallLabelPanel.add(smallLabel);
    }
}
