package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.buttons.PanelControlButton;

import javax.swing.*;
import java.awt.*;

public class ControlButtonCarrierPlus extends JPanel {
    ControlButtonCarrierPlus        controlButtonCarrier;
    PanelControlButton              secondOperatorButton;
    PanelControlButton              firstOperatorButton;

    public JPanel getTempoCarrier() {
        return tempoCarrier;
    }

    JPanel                          tempoCarrier;




    public ControlButtonCarrierPlus(String firstOperatorButtonText, String secondOperatorButtonText){
        controlButtonCarrier=this;

        secondOperatorButton = new PanelControlButton(secondOperatorButtonText);
        firstOperatorButton = new PanelControlButton(firstOperatorButtonText);
        tempoCarrier = new JPanel();

        tempoCarrier.setOpaque(true);

        tempoCarrier.setLayout(new BoxLayout(tempoCarrier,BoxLayout.X_AXIS));
        tempoCarrier.add(Box.createHorizontalGlue());
        tempoCarrier.add(secondOperatorButton);
        tempoCarrier.add(Box.createRigidArea(new Dimension(10,10)));
        tempoCarrier.add(firstOperatorButton);
        tempoCarrier.setAlignmentY(1f);



        controlButtonCarrier.add(Box.createVerticalGlue());
        controlButtonCarrier.add(tempoCarrier);
        controlButtonCarrier.setAlignmentX(LEFT_ALIGNMENT);
        controlButtonCarrier.setAlignmentY(-1f);
        controlButtonCarrier.setMaximumSize(new Dimension (200,50));

    }

    public PanelControlButton getSecondOperatorButton() {
        return secondOperatorButton;
    }

    public PanelControlButton getFirstOperatorButton() {
        return firstOperatorButton;
    }

}
