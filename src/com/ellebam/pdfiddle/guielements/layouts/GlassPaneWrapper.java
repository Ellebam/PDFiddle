package com.ellebam.pdfiddle.guielements.layouts;

import javax.swing.*;
import java.awt.event.MouseAdapter;

public class GlassPaneWrapper  extends JLayeredPane {
    private JPanel glassPanel = new JPanel();

    public GlassPaneWrapper (JComponent wrappedPanel){
        glassPanel.setOpaque(false);
        glassPanel.setVisible(false);
        glassPanel.addMouseListener(new MouseAdapter() {});
        glassPanel.setFocusable(true);

        wrappedPanel.setSize(wrappedPanel.getPreferredSize());
        add(wrappedPanel,JLayeredPane.DEFAULT_LAYER);
        add(glassPanel, JLayeredPane.PALETTE_LAYER);

        glassPanel.setPreferredSize(wrappedPanel.getPreferredSize());
        glassPanel.setSize(wrappedPanel.getPreferredSize());
        setPreferredSize(wrappedPanel.getPreferredSize());
    }

    public void activateGlassPane(boolean activate){
        glassPanel.setVisible(activate);
        if(activate){
            glassPanel.requestFocusInWindow();
            glassPanel.setFocusTraversalKeysEnabled(false);
        }
    }
}
