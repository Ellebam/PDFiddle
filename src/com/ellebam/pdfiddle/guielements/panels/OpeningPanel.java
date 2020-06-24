package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import com.ellebam.pdfiddle.guielements.labels.SmallLabel;

import javax.swing.*;
import java.awt.*;

public class OpeningPanel extends JPanel {
    private JPanel openingPanel;

    public OpeningPanel(){
        openingPanel = this;
        openingPanel.setBackground(Color.BLACK);
        openingPanel.setLayout(new BoxLayout(openingPanel, BoxLayout.Y_AXIS));
        openingPanel.add(Box.createRigidArea(new Dimension(10,50)));
        openingPanel.add(new HeaderPanel(new HeaderLabel("Welcome to PDFiddle 1.0")));
        openingPanel.setVisible(true);

    }
}
