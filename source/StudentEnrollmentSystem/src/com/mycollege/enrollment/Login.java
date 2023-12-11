package com.mycollege.enrollment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

public class Login {
  /**
   * Launch the application
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          new Login();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private JFrame frame;
  
  private JTextField tfdEmail;
  private JPasswordField pfdPassword;

  /**
   * Create the application.
   * 
   * @throws SQLException
   */
  public Login() {
    initializeGUI();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initializeGUI() {
    frame = new JFrame();
    frame.setTitle("MyCollege");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setIconImage(Main.logo.getImage());

    JScrollPane scrollPane = new JScrollPane();
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
    JPanel loginPanel = new JPanel();
    loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    loginPanel.setBackground(new Color(240, 255, 255));
    scrollPane.setViewportView(loginPanel);
    loginPanel.setLayout(new MigLayout("", "[grow][400px][grow]", "[30px][][30px][][][][][30px][30px][]"));

    JLabel lblTitle = new JLabel("LOGIN");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
    loginPanel.add(lblTitle, "cell 1 1");

    JLabel lblEmail = new JLabel("E-mail");
    loginPanel.add(lblEmail, "cell 1 3");

    tfdEmail = new JTextField();
    loginPanel.add(tfdEmail, "cell 1 4,growx");
    tfdEmail.setColumns(10);

    JLabel lblPassword = new JLabel("Password");
    loginPanel.add(lblPassword, "cell 1 5");

    pfdPassword = new JPasswordField();
    pfdPassword.setEchoChar('\u2022');
    loginPanel.add(pfdPassword, "flowx,cell 1 6,growx");

    JToggleButton tbnPasswordShow = new JToggleButton("Show");
    tbnPasswordShow.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (tbnPasswordShow.isSelected()) {
          pfdPassword.setEchoChar((char) 0);
        }
        else {
          pfdPassword.setEchoChar('\u2022');
        }
      }
    });
    loginPanel.add(tbnPasswordShow, "cell 1 6,growy");
    
    JButton btnForgot = new JButton("I forgot my password!");
    loginPanel.add(btnForgot, "cell 1 7,alignx right");

    JButton btnHome = new JButton("Home");
    loginPanel.add(btnHome, "flowx,cell 1 9,growx");

    JButton btnSubmit = new JButton("Submit");
    btnSubmit.setForeground(new Color(255, 255, 255));
    btnSubmit.setBackground(new Color(0, 100, 0));
    loginPanel.add(btnSubmit, "cell 1 9,growx");

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
