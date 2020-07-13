package com.ellebam.pdfiddle.guielements.buttons;

import com.ellebam.pdfiddle.guielements.colors.HighlightColor;
import com.ellebam.pdfiddle.guielements.colors.PrimaryColor;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor1;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class SelectDocPseudoButton extends JPanel {
    SelectDocPseudoButton selectDocPseudoButton;

    protected Color         buttonColor         =   new PrimaryColor();
    protected Color         highlightColor      =   new HighlightColor();
    protected Dimension     arcs                =   new Dimension(20,20);
    protected Color         buttonBorderColor   =   new SecondaryColor1();
    protected Dimension     buttonDimension     =   new Dimension(240,120);
    protected Font          buttonTextFont      =   new Font("Arial",Font.BOLD,20);
    protected Border        buttonBorder        =   BorderFactory.createLineBorder(buttonBorderColor,10,true);
    protected Border        buttonBorder2       =   BorderFactory.createLineBorder(highlightColor,10,true);
    protected JLabel        buttonLabel         =   new JLabel("Select File");


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

        selectDocPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseEntered (MouseEvent evt){
                selectDocPseudoButton.setBorder(buttonBorder2);


            }
        }));
        selectDocPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseExited (MouseEvent evt){
                selectDocPseudoButton.setBorder(buttonBorder);
            }
        }));

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
