package com.ellebam.pdfiddle.guielements;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JFrame mainFrame;
    private JPanel currentMainPanel;
    public MainFrame (){
        super("PDFiddle 1.0");
        mainFrame=this;
        setSize(850,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public JPanel getCurrentPanel() {
        return currentMainPanel;
    }

    public void setAndAddCurrentPanel(JPanel currentPanel) {
        this.currentMainPanel = currentPanel;
        mainFrame.getContentPane().add(BorderLayout.CENTER, currentMainPanel);
        currentPanel.revalidate();
    }


    }

