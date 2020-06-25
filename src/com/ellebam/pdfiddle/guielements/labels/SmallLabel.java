package com.ellebam.pdfiddle.guielements.labels;

import javax.swing.*;
import java.awt.*;

public class SmallLabel extends JLabel {
    private Font smallLabelFont = new Font("Arial",Font.PLAIN,15);
    private SmallLabel smallLabel;

    public SmallLabel(String labelText){
        smallLabel=this;
        smallLabel.setText(labelText);
        smallLabel.setFont(smallLabelFont);
    }
}
