package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class EncryptPDFPanel extends JPanel {

    private EncryptPDFPanel encryptPDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();
    private File doc2Encrypt;
    private PDDocument PDF2Encrypt;
    private JPanel fileHandlerPanel = new JPanel();
    private FilePreviewPanel filePreviewPanel;
    private Driver encryptionDriver = new Driver();
    private Boolean overwriteSourceFileBool = Boolean.FALSE;
    private Checkbox overwriteSourceFileCheckbox;
    private JPanel overwriteSourceFileCheckboxPanel;

    public EncryptPDFPanel(MainFrame mainFrame) {
        encryptPDFPanel = this;
        encryptPDFPanel.setLayout((new BoxLayout(encryptPDFPanel, BoxLayout.Y_AXIS)));
        encryptPDFPanel.add(Box.createRigidArea(new Dimension(1000, 20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the PDF-Document to encrypt"));

        JPanel selectCarrierPanel = new JPanel();
        selectCarrierPanel.add(selectDocPseudoButton);


        fileHandlerPanel.setLayout(new BoxLayout(fileHandlerPanel, BoxLayout.Y_AXIS));
        fileHandlerPanel.setAlignmentY(TOP_ALIGNMENT);
        fileHandlerPanel.setOpaque(false);
        fileHandlerPanel.add(Box.createRigidArea(new Dimension(30, 10)));

        /*
          Checkbox changes the status of mergeFilesBool which is used to determine wether the loaded JPEG files have to be
          merged after being converted
         */
        overwriteSourceFileCheckbox = new Checkbox("Overwrite Source File");
        overwriteSourceFileCheckbox.addItemListener(e -> {
            overwriteSourceFileBool = !overwriteSourceFileBool; //if status is changed change mergeFilesBool
            System.out.print(overwriteSourceFileBool);
        });
        overwriteSourceFileCheckboxPanel = new JPanel();
        overwriteSourceFileCheckboxPanel.add(overwriteSourceFileCheckbox);
        overwriteSourceFileCheckboxPanel.setAlignmentX(CENTER_ALIGNMENT);

        ControlButtonCarrier controlButtonCarrier = new ControlButtonCarrier("Encrypt");
        controlButtonCarrier.setAlignmentY(BOTTOM_ALIGNMENT);
        controlButtonCarrier.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                encryptPDFPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });

        controlButtonCarrier.operatorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JTextField authorPasswordField = new JTextField();
                authorPasswordField.setPreferredSize(new Dimension(50,20));
                JTextField userPasswordField = new JTextField();
                userPasswordField.setPreferredSize(new Dimension(50,20));

                JPanel setPasswordsPanel = new JPanel();
                setPasswordsPanel.add(new JLabel("Author Password: "));
                setPasswordsPanel.add(authorPasswordField);
                setPasswordsPanel.add(Box.createHorizontalStrut(15));
                setPasswordsPanel.add(new JLabel("User Password: "));
                setPasswordsPanel.add(userPasswordField);
                 int result = JOptionPane.showConfirmDialog(mainFrame,setPasswordsPanel,
                         "Please enter the desired Author and User Passwords", JOptionPane.OK_CANCEL_OPTION);
                 if (result==JOptionPane.OK_OPTION){
                     String authorPassword = authorPasswordField.getText();
                     String userPassword = userPasswordField.getText();

                     encryptionDriver.encryptPDF(doc2Encrypt,overwriteSourceFileBool,
                             authorPassword,userPassword,mainFrame);
                     encryptPDFPanel.setVisible(false);
                     mainFrame.setAndAddCurrentPanel(new EncryptPDFPanel(mainFrame));
                 }
            }
        });



        encryptPDFPanel.add(headerPanel);
        encryptPDFPanel.add(selectCarrierPanel);
        encryptPDFPanel.add(overwriteSourceFileCheckboxPanel);
        encryptPDFPanel.add(fileHandlerPanel);
        encryptPDFPanel.add(controlButtonCarrier);
        overwriteSourceFileCheckboxPanel.setVisible(false);


        /*
         * The selectDocPseudoButton also triggers the Document preview as a own Panel(icon).
         */
        selectDocPseudoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (fileHandlerPanel.getComponentCount() > 1){
                    fileHandlerPanel.remove(1);
                }
                try{
                    doc2Encrypt = File.createTempFile("temp",null);
                    PDF2Encrypt =encryptionDriver.handlePDFEncryption(encryptionDriver.chooseDoc(mainFrame),mainFrame);
                    PDF2Encrypt.save(doc2Encrypt);
                    filePreviewPanel = new FilePreviewPanel(doc2Encrypt,mainFrame,0);
                    fileHandlerPanel.add(filePreviewPanel);
                    PDF2Encrypt.close();


                }catch(Exception ex){ex.printStackTrace();
                }
                overwriteSourceFileCheckboxPanel.setVisible(true);
                fileHandlerPanel.revalidate();
            }
        });
    }
}
