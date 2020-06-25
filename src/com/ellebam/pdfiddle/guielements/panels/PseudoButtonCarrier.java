package com.ellebam.pdfiddle.guielements.panels;

import javax.swing.*;
import java.awt.*;

public class PseudoButtonCarrier extends JPanel {
    private PseudoButtonCarrier pseudoButtonCarrier;
    protected Color color =Color.BLUE;
    protected Dimension arcs = new Dimension(20,20);
    protected Dimension dimension = new Dimension (200,100);

    public PseudoButtonCarrier(MiddleLabelPanel middleLabelPanel,
                               OpeningPanelPseudoButton openingPanelPseudoButton){
        pseudoButtonCarrier  = this;
        pseudoButtonCarrier.setLayout(new BoxLayout(pseudoButtonCarrier, BoxLayout.Y_AXIS));
        pseudoButtonCarrier.add(middleLabelPanel);
        pseudoButtonCarrier.add(openingPanelPseudoButton);
        pseudoButtonCarrier.setPreferredSize(dimension);
    }

    public PseudoButtonCarrier(MiddleLabelPanel middleLabelPanel,
                               OpeningPanelPseudoButton openingPanelPseudoButton1,
                               OpeningPanelPseudoButton openingPanelPseudoButton2){
        pseudoButtonCarrier  = this;
        pseudoButtonCarrier.setLayout(new BoxLayout(pseudoButtonCarrier, BoxLayout.Y_AXIS));
        pseudoButtonCarrier.add(middleLabelPanel);

        JPanel fakePanel = new JPanel();
        fakePanel.setLayout(new BoxLayout(fakePanel,BoxLayout.X_AXIS));
        fakePanel.add(openingPanelPseudoButton1);
        fakePanel.add(Box.createRigidArea(new Dimension(20,0)));
        fakePanel.add(openingPanelPseudoButton2);
        fakePanel.setOpaque(false);

        pseudoButtonCarrier.add(fakePanel);
        pseudoButtonCarrier.setPreferredSize(dimension);

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D)g;
        graphics.setColor(color);
        graphics.fillRoundRect(0,0,width,height,arcs.width,arcs.height);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
