package com.ellebam.pdfiddle.guielements.labels;

import javax.swing.*;
import java.awt.*;

public class HeaderLabel extends JLabel {
    private Font headerLabelFont = new Font("Arial", Font.PLAIN,25);
    private HeaderLabel headerLabel;


    public HeaderLabel(String labelText){
        headerLabel=this;
        headerLabel.setText(labelText);
        headerLabel.setFont(headerLabelFont);
    }

}
