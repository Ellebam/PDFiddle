package com.ellebam.pdfiddle.guielements.panels;



import javax.swing.*;
import java.awt.*;

public class OpeningPanelPseudoButton extends JPanel {
    OpeningPanelPseudoButton openingPanelPseudoButton;

    protected int strokeSize = 1;
    protected Color shadowColor = Color.black;
    protected boolean shady = true;
    protected boolean highQuality = true;
    protected Dimension arcs = new Dimension(20, 20);
    protected int shadowGap = 5;
    protected int shadowOffset = 4;
    protected int shadowAlpha = 150;
    protected Color color = new Color(246,195,36);


    public OpeningPanelPseudoButton(SmallLabelPanel smallLabelPanel) {
        super();
        setOpaque(false);
        openingPanelPseudoButton = this;
        openingPanelPseudoButton.add(smallLabelPanel);
        openingPanelPseudoButton.setPreferredSize(new Dimension(65, 65));
        openingPanelPseudoButton.setBackground(Color.LIGHT_GRAY);

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
