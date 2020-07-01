package com.ellebam.pdfiddle.guielements.buttons;

import com.ellebam.pdfiddle.guielements.colors.SecondaryColor2;

import javax.swing.*;
import java.awt.*;

public class PanelControlButton extends JPanel {
    PanelControlButton panelControlButton;

    protected Color buttonColor = new SecondaryColor2();
    protected  Dimension arcs = new Dimension(20,20);
    protected Dimension buttonDimension = new Dimension (80,40);
    protected Font buttonTextFont = new Font("Arial",Font.PLAIN, 18);
    protected JLabel buttonLabel;

    public PanelControlButton(String textDisplay){
        panelControlButton=this;
        buttonLabel=new JLabel(textDisplay);
        panelControlButton.setLayout(new BoxLayout(panelControlButton,BoxLayout.Y_AXIS));
        panelControlButton.setBackground(buttonColor);
        panelControlButton.setPreferredSize(buttonDimension);
        buttonLabel.setAlignmentX(CENTER_ALIGNMENT);
        panelControlButton.setFont(buttonTextFont);
        panelControlButton.setForeground(Color.WHITE);
        panelControlButton.setOpaque(false);
        panelControlButton.add(Box.createVerticalGlue());
        panelControlButton.add(buttonLabel);
        panelControlButton.add(Box.createVerticalGlue());


    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D)g;
        graphics.setColor(buttonColor);
        graphics.fillRoundRect(0,0,width,height,arcs.width,arcs.height);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}
