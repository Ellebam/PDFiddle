package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.guielements.colors.HighlightColor;
import com.ellebam.pdfiddle.guielements.colors.SecondaryColor1;
import com.ellebam.pdfiddle.guielements.labels.SmallLabel;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;

public class ComboSelectionPanel extends JPanel {
    private ComboSelectionPanel                 comboSelectionPanel;
    private Color                               panelColor                      = new SecondaryColor1();
    private Dimension                           arcs                            = new Dimension(30,30);
    private SmallLabel                          panelLabel;
    private JPanel                              carrierPanel;
    private JComboBox                           comboBox;



    public ComboSelectionPanel(String[] comboList, String labelText){
        comboSelectionPanel=this;
        panelLabel = new SmallLabel(labelText);
        SmallLabelPanel smallLabelPanel = new SmallLabelPanel(panelLabel);
        carrierPanel = new JPanel();
        carrierPanel.setLayout(new BoxLayout(carrierPanel,BoxLayout.X_AXIS));
        carrierPanel.setOpaque(false);
        carrierPanel.setAlignmentY(CENTER_ALIGNMENT);
        smallLabelPanel.setAlignmentY(CENTER_ALIGNMENT);
        comboBox = new JComboBox(comboList);
        comboBox.setSelectedIndex(0);


        carrierPanel.add(smallLabelPanel);
        carrierPanel.add(Box.createRigidArea(new Dimension(30,10)));
        carrierPanel.add(comboBox);

        comboSelectionPanel.add(carrierPanel);
        comboSelectionPanel.setMaximumSize(new Dimension(300,80));


    }

    public JComboBox getComboBox() {
        return comboBox;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(panelColor);
        graphics.fillRoundRect(0,0,width,height,arcs.width,arcs.height);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
