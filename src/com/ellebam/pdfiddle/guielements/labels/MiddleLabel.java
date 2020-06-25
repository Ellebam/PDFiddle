package com.ellebam.pdfiddle.guielements.labels;

import javax.swing.*;
import java.awt.*;

public class MiddleLabel extends JLabel  {
    private Font middleLabelFont = new Font("Arial",Font.PLAIN,18);
    private  MiddleLabel middleLabel;

    public MiddleLabel (String labelText){
        middleLabel=this;
        middleLabel.setText(labelText);
        middleLabel.setFont(middleLabelFont);
    }
}
