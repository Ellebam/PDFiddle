package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;

import javax.swing.*;

public class HeaderPanel extends JPanel {
    private HeaderPanel headerPanel;

    public HeaderPanel (HeaderLabel headerLabel){
        headerPanel = this;
        headerPanel.setOpaque(false);
        headerPanel.add(headerLabel);
    }
}
