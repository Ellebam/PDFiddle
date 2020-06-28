package com.ellebam.pdfiddle.guielements.panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SelectDocPseudoButton extends JPanel {
    SelectDocPseudoButton selectDocPseudoButton;

    protected Color buttonColor = new Color(107,214,250);
    protected Color buttonBorderColor = new Color(192, 237, 252);
    protected Dimension buttonDimension = new Dimension(240,120);
    protected Font buttonTextFont = new Font("Arial",Font.BOLD,20);
    protected Border buttonBorder = BorderFactory.createLineBorder(buttonBorderColor,10,true);
    protected JLabel buttonLabel = new JLabel("Select File");
    protected Dimension arcs = new Dimension(20,20);

    public SelectDocPseudoButton (){
        selectDocPseudoButton=this;
        selectDocPseudoButton.setLayout(new BoxLayout(selectDocPseudoButton, BoxLayout.Y_AXIS));
        selectDocPseudoButton.setBackground(buttonColor);
        selectDocPseudoButton.setBorder(buttonBorder);
        selectDocPseudoButton.setPreferredSize(buttonDimension);
        buttonLabel.setAlignmentX(CENTER_ALIGNMENT);
        buttonLabel.setFont(buttonTextFont);
        buttonLabel.setForeground(Color.white);
        selectDocPseudoButton.setOpaque(false);
        selectDocPseudoButton.add(Box.createVerticalGlue());
        selectDocPseudoButton.add(buttonLabel);
        selectDocPseudoButton.add(Box.createVerticalGlue());


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
