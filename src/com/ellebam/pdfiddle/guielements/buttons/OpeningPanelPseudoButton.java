package com.ellebam.pdfiddle.guielements.buttons;



import com.ellebam.pdfiddle.guielements.colors.HighlightColor;
import com.ellebam.pdfiddle.guielements.colors.PrimaryColor;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor1;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor2;
import com.ellebam.pdfiddle.guielements.panels.MergePDFPanel;
import com.ellebam.pdfiddle.guielements.panels.SmallLabelPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This Class represents the Pseudobuttons on the OpeningPanel. Clicking these inside the OpeningPanel will lead to the
 * described functionality
 */
public class OpeningPanelPseudoButton extends JPanel {
    OpeningPanelPseudoButton openingPanelPseudoButton;

    protected int strokeSize = 1;
    private Color shadowColor = Color.black;
    protected boolean shady = true;
    protected boolean highQuality = true;
    protected Dimension arcs = new Dimension(20, 20);
    protected int shadowGap = 5;
    protected int shadowOffset = 4;
    protected int shadowAlpha = 150;
    protected Color color = new PrimaryColor();
    protected  Color highlightColor = new HighlightColor();



    public OpeningPanelPseudoButton(SmallLabelPanel smallLabelPanel) {
        super();
        setOpaque(false);
        openingPanelPseudoButton = this;
        openingPanelPseudoButton.add(smallLabelPanel);
        openingPanelPseudoButton.setPreferredSize(new Dimension(65, 65));
        openingPanelPseudoButton.setBackground(color);

        openingPanelPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseEntered (MouseEvent evt){
                openingPanelPseudoButton.setBackground(highlightColor);

            }
        }));
        openingPanelPseudoButton.addMouseListener((new MouseAdapter(){
            @Override
            public void mouseExited (MouseEvent evt){
                openingPanelPseudoButton.setBackground(color);
            }
        }));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(),
                shadowColor.getGreen(), shadowColor.getBlue(),
                shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(shadowOffset, shadowOffset, width - strokeSize - shadowOffset,
                    height - strokeSize - shadowOffset, arcs.width, arcs.height);
        } else {
            shadowGap = 1;
        }
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.drawRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
        graphics.setStroke(new BasicStroke());


    }
}
