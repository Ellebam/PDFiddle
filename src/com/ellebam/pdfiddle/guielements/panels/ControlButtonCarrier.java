package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.buttons.PanelControlButton;

import javax.swing.*;
import java.awt.*;

public class ControlButtonCarrier extends JPanel {
    ControlButtonCarrier controlButtonCarrier;
    PanelControlButton backButton;
    PanelControlButton operatorButton;

    public ControlButtonCarrier(String operatorButtonText){
        controlButtonCarrier=this;
        backButton = new PanelControlButton("Back");
        operatorButton = new PanelControlButton(operatorButtonText);
        JPanel temppoCarrier = new JPanel();

        temppoCarrier.setOpaque(true);

        temppoCarrier.setLayout(new BoxLayout(temppoCarrier,BoxLayout.X_AXIS));
        temppoCarrier.add(Box.createHorizontalGlue());
        temppoCarrier.add(backButton);
        temppoCarrier.add(Box.createRigidArea(new Dimension(10,10)));
        temppoCarrier.add(operatorButton);
        temppoCarrier.setAlignmentY(1f);



        controlButtonCarrier.add(Box.createVerticalGlue());
        controlButtonCarrier.add(temppoCarrier);
        controlButtonCarrier.setAlignmentX(LEFT_ALIGNMENT);
        controlButtonCarrier.setAlignmentY(-1f);
        controlButtonCarrier.setMaximumSize(new Dimension (200,50));

    }
}
