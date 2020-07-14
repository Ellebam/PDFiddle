package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.buttons.PanelControlButton;

import javax.swing.*;
import java.awt.*;

public class ControlButtonCarrier extends JPanel {
    ControlButtonCarrier            controlButtonCarrier;
    PanelControlButton              backButton;
    PanelControlButton              operatorButton;



    String                          backButtonText;

    public ControlButtonCarrier(String operatorButtonText){
        controlButtonCarrier=this;
        setBackButtonText("Back");
        backButton = new PanelControlButton(backButtonText);
        operatorButton = new PanelControlButton(operatorButtonText);
        JPanel tempoCarrier = new JPanel();

        tempoCarrier.setOpaque(true);

        tempoCarrier.setLayout(new BoxLayout(tempoCarrier,BoxLayout.X_AXIS));
        tempoCarrier.add(Box.createHorizontalGlue());
        tempoCarrier.add(backButton);
        tempoCarrier.add(Box.createRigidArea(new Dimension(10,10)));
        tempoCarrier.add(operatorButton);
        tempoCarrier.setAlignmentY(1f);



        controlButtonCarrier.add(Box.createVerticalGlue());
        controlButtonCarrier.add(tempoCarrier);
        controlButtonCarrier.setAlignmentX(LEFT_ALIGNMENT);
        controlButtonCarrier.setAlignmentY(-1f);
        controlButtonCarrier.setMaximumSize(new Dimension (200,50));

    }

    public void setBackButtonText(String backButtonText) {
        this.backButtonText = backButtonText;
    }
}
