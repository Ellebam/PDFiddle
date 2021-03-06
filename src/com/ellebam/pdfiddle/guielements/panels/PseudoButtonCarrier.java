package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.buttons.OpeningPanelPseudoButton;
import com.ellebam.pdfiddle.guielements.colors.PrimaryColor;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor2;

import javax.swing.*;
import java.awt.*;

/**
 * This Class represents the JPanels which hold the OpeningPanelPseudobuttons for the various functionalities inside
 * the OpeningPanel
 */
public class PseudoButtonCarrier extends JPanel {
    private PseudoButtonCarrier pseudoButtonCarrier;
    protected Color color = new SecondaryColor2();
    protected Dimension arcs = new Dimension(20,20);
    protected Dimension dimension = new Dimension (250,150);

    /**
     * Constructor for the creation of PseudoButtonCarrier. It will take a MiddleLabelPanel and the corresponding
     * OpeningPanelPseudobutton
     *
     * @param middleLabelPanel TextLabel for the PseudoButtonCarrier
     * @param openingPanelPseudoButton corresponding OpeningPanelPseudoButton
     */
    public PseudoButtonCarrier(MiddleLabelPanel middleLabelPanel,
                               OpeningPanelPseudoButton openingPanelPseudoButton){
        pseudoButtonCarrier  = this;
        pseudoButtonCarrier.setLayout(new BoxLayout(pseudoButtonCarrier, BoxLayout.Y_AXIS));
        pseudoButtonCarrier.add(Box.createRigidArea(new Dimension(0,10)));
        pseudoButtonCarrier.add(middleLabelPanel);
        JPanel fakePanel = new JPanel();
        fakePanel.setLayout(new BoxLayout(fakePanel,BoxLayout.X_AXIS));
        fakePanel.add(Box.createHorizontalGlue());
        fakePanel.add(openingPanelPseudoButton);
        fakePanel.add(Box.createHorizontalGlue());
        fakePanel.setOpaque(false);
        pseudoButtonCarrier.add(fakePanel);
        pseudoButtonCarrier.add(Box.createRigidArea(new Dimension(0,5)));
        pseudoButtonCarrier.setOpaque(false);
        pseudoButtonCarrier.setPreferredSize(dimension);
    }

    /**
     * same Constructor as above but for a carrier which displays 2 OpeningPanelPseudoButtons
     * @param middleLabelPanel TextLabel for the PseudoButtonCarrier
     * @param openingPanelPseudoButton1 first corresponding OpeningPanelPseudoButton
     * @param openingPanelPseudoButton2 second corresponding OpeningPanelPseudoButton
     */
    public PseudoButtonCarrier(MiddleLabelPanel middleLabelPanel,
                               OpeningPanelPseudoButton openingPanelPseudoButton1,
                               OpeningPanelPseudoButton openingPanelPseudoButton2){
        pseudoButtonCarrier  = this;
        pseudoButtonCarrier.setLayout(new BoxLayout(pseudoButtonCarrier, BoxLayout.Y_AXIS));
        pseudoButtonCarrier.add(Box.createRigidArea(new Dimension(0,10)));
        pseudoButtonCarrier.add(middleLabelPanel);

        JPanel fakePanel = new JPanel();
        fakePanel.setLayout(new BoxLayout(fakePanel,BoxLayout.X_AXIS));
        fakePanel.add(Box.createRigidArea(new Dimension(5,0)));
        fakePanel.add(openingPanelPseudoButton1);
        fakePanel.add(Box.createRigidArea(new Dimension(15,0)));
        fakePanel.add(openingPanelPseudoButton2);
        fakePanel.add(Box.createRigidArea(new Dimension(5,0)));
        fakePanel.setOpaque(false);


        pseudoButtonCarrier.add(fakePanel);
        pseudoButtonCarrier.add(Box.createRigidArea(new Dimension(0,5)));
        pseudoButtonCarrier.setOpaque(false);
        pseudoButtonCarrier.setPreferredSize(dimension);

    }

    /**
     * overridden paintComponent() method for rounded corners
     * @param g Graphic object to paint
     */
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
