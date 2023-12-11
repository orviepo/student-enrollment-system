package com.mycollege.enrollment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import javax.swing.UIManager;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class StudentHome {
  // 

  /**
   * Launch the application
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          new StudentHome();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private JFrame frame;

  /**
   * Create the application.
   * 
   * @throws SQLException
   */
  public StudentHome() {
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
    scrollPane.setBorder(null);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    JPanel homePanel = new JPanel();
    scrollPane.setViewportView(homePanel);
    homePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    GridBagLayout gbl_homePanel = new GridBagLayout();
    gbl_homePanel.columnWidths = new int[] {300};
    gbl_homePanel.rowHeights = new int[] {300, 0, 0, 40, 0};
    gbl_homePanel.columnWeights = new double[]{0.0};
    gbl_homePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
    homePanel.setLayout(gbl_homePanel);

    JLabel lblLogo = new JLabel("");
    lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblLogo.setIcon(Main.logo);
    lblLogo.setPreferredSize(new Dimension(300, 300));
    GridBagConstraints gbc_lblLogo = new GridBagConstraints();
    gbc_lblLogo.gridx = 0;
    gbc_lblLogo.gridy = 0;
    homePanel.add(lblLogo, gbc_lblLogo);

    JLabel lblTitle = new JLabel("MyCollege");
    lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    GridBagConstraints gbc_lblTitle = new GridBagConstraints();
    gbc_lblTitle.anchor = GridBagConstraints.NORTH;
    gbc_lblTitle.gridx = 0;
    gbc_lblTitle.gridy = 1;
    homePanel.add(lblTitle, gbc_lblTitle);
    lblTitle.setFont(new Font("Cairo Black", Font.PLAIN, 36));
    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel lblSubtitle = new JLabel("Your future awaits!");
    GridBagConstraints gbc_lblSubtitle = new GridBagConstraints();
    gbc_lblSubtitle.insets = new Insets(10, 0, 10, 0);
    gbc_lblSubtitle.gridy = 2;
    homePanel.add(lblSubtitle, gbc_lblSubtitle);
    lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblSubtitle.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
    lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

    JButton btnEnroll = new JButton("Enroll now!");
    GridBagConstraints gbc_btnEnroll = new GridBagConstraints();
    gbc_btnEnroll.fill = GridBagConstraints.BOTH;
    gbc_btnEnroll.gridy = 3;
    homePanel.add(btnEnroll, gbc_btnEnroll);
    btnEnroll.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnEnroll.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnEnroll.setForeground(new Color(255, 255, 255));
    btnEnroll.setBackground(new Color(0, 100, 0));

    JButton btnContinue = new JButton("I am a continuing student");
    GridBagConstraints gbc_btnContinue = new GridBagConstraints();
    gbc_btnContinue.insets = new Insets(5, 0, 0, 0);
    gbc_btnContinue.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnContinue.gridy = 4;
    homePanel.add(btnContinue, gbc_btnContinue);
    btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    btnContinue.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new Login();
        frame.dispose();
      }
    });

    btnEnroll.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new Registration();
        frame.dispose();
      }
    });
    
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  
}
