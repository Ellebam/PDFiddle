package com.ellebam.pdfiddle.guielements.panels;

import com.ellebam.pdfiddle.driver.Driver;
import com.ellebam.pdfiddle.guielements.MainFrame;
import com.ellebam.pdfiddle.guielements.buttons.SelectDocPseudoButton;
import com.ellebam.pdfiddle.guielements.labels.HeaderLabel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class DecryptPDFPanel extends JPanel {

    private DecryptPDFPanel decryptPDFPanel;
    private SelectDocPseudoButton selectDocPseudoButton = new SelectDocPseudoButton();
    private File doc2Decrypt;
    private PDDocument PDF2Decrypt;
    private JPanel fileHandlerPanel = new JPanel();
    private FilePreviewPanel filePreviewPanel;
    private Driver decryptionDriver = new Driver();
    private Boolean overwriteSourceFileBool = Boolean.FALSE;
    private Checkbox overwriteSourceFileCheckbox;
    private JPanel overwriteSourceFileCheckboxPanel;
    private String sourceFileDirectory;

    public DecryptPDFPanel(MainFrame mainFrame) {
        decryptPDFPanel = this;
        decryptPDFPanel.setLayout((new BoxLayout(decryptPDFPanel, BoxLayout.Y_AXIS)));
        decryptPDFPanel.add(Box.createRigidArea(new Dimension(1000, 20)));
        HeaderPanel headerPanel = new HeaderPanel(new HeaderLabel(
                "Select the PDF-Document to decrypt"));

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
                decryptPDFPanel.setVisible(false);
                mainFrame.setAndAddCurrentPanel(new OpeningPanel(mainFrame));
            }
        });


        controlButtonCarrier.operatorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

               /* JTextField authorPasswordField = new JTextField();
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
                if (result==JOptionPane.OK_OPTION){*/

                    decryptionDriver.decryptPDF(doc2Decrypt,overwriteSourceFileBool,mainFrame, sourceFileDirectory);
                    decryptPDFPanel.setVisible(false);
                    mainFrame.setAndAddCurrentPanel(new DecryptPDFPanel(mainFrame));
                }
           // }
        });

        decryptPDFPanel.add(headerPanel);
        decryptPDFPanel.add(selectCarrierPanel);
        decryptPDFPanel.add(overwriteSourceFileCheckboxPanel);
        decryptPDFPanel.add(fileHandlerPanel);
        decryptPDFPanel.add(controlButtonCarrier);
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
                    doc2Decrypt = File.createTempFile("temp",null);
                    PDF2Decrypt =decryptPDFPanel.handlePDFEncryptionPlus(decryptionDriver.chooseDoc(mainFrame),
                            mainFrame);


                    PDF2Decrypt.save(doc2Decrypt);
                    filePreviewPanel = new FilePreviewPanel(doc2Decrypt,mainFrame,0);
                    fileHandlerPanel.add(filePreviewPanel);
                    PDF2Decrypt.close();


                }catch(Exception ex){ex.printStackTrace();
                }
                overwriteSourceFileCheckboxPanel.setVisible(true);
                fileHandlerPanel.revalidate();
            }
        });
    }

    /**
     * The same method as handlePDFEncryption() from Driver class  but only with an Update of a String representing the
     * directory of the source file
     * @param doc2Handle file which needs to be handled
     * @param mainFrame parent frame
     * @return password handled document
     */
    public PDDocument handlePDFEncryptionPlus(File doc2Handle, MainFrame mainFrame) {
        PDDocument handledDoc = new PDDocument();
        try {
            try {
                handledDoc = PDDocument.load(doc2Handle);
                sourceFileDirectory = doc2Handle.getAbsolutePath();

            } catch (InvalidPasswordException invalidPasswordException) {

                handledDoc = PDDocument.load(doc2Handle,
                        JOptionPane.showInputDialog(mainFrame,
                                doc2Handle.getName() + " is password protected. " +
                                        "Please enter the password to open the document"));
                sourceFileDirectory = doc2Handle.getAbsolutePath();


            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error while loading file!");
            }
        } catch (InvalidPasswordException ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(mainFrame, "Password incorrect!");
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error while loading file!");
        }

        handledDoc.setAllSecurityToBeRemoved(true);
        return  handledDoc;


    }
}
