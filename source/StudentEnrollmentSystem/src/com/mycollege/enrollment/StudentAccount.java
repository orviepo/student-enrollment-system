package com.mycollege.enrollment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;

public class StudentAccount {

  private JFrame frame;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          StudentAccount window = new StudentAccount();
          window.frame.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public StudentAccount() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 530);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JScrollPane scrollPane = new JScrollPane();
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
    JPanel panel = new JPanel();
    scrollPane.setViewportView(panel);
    panel.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_1 = new JPanel();
    panel.add(panel_1, BorderLayout.NORTH);
    GridBagLayout gbl_panel_1 = new GridBagLayout();
    gbl_panel_1.columnWidths = new int[] {0, 0};
    gbl_panel_1.rowHeights = new int[]{0, 0};
    gbl_panel_1.columnWeights = new double[]{1.0, 1.0};
    gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    panel_1.setLayout(gbl_panel_1);
    
    JLabel lblNewLabel = new JLabel("MyCollege");
    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
    gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
    gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
    gbc_lblNewLabel.gridx = 0;
    gbc_lblNewLabel.gridy = 0;
    panel_1.add(lblNewLabel, gbc_lblNewLabel);
    
    JButton btnNewButton = new JButton("My Profile");
    GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
    gbc_btnNewButton.anchor = GridBagConstraints.EAST;
    gbc_btnNewButton.gridx = 1;
    gbc_btnNewButton.gridy = 0;
    panel_1.add(btnNewButton, gbc_btnNewButton);
    
    JPanel panel_2 = new JPanel();
    panel.add(panel_2, BorderLayout.CENTER);
    GridBagLayout gbl_panel_2 = new GridBagLayout();
    gbl_panel_2.columnWidths = new int[] {150, 150};
    gbl_panel_2.rowHeights = new int[]{300, 0, 0, 0, 0, 0};
    gbl_panel_2.columnWeights = new double[]{0.0, 0.0};
    gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    panel_2.setLayout(gbl_panel_2);
    
    JLabel lblNewLabel_1 = new JLabel("");
    GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
    gbc_lblNewLabel_1.gridwidth = 2;
    gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
    gbc_lblNewLabel_1.gridx = 0;
    gbc_lblNewLabel_1.gridy = 0;
    panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);
    
    JLabel lblNewLabel_2 = new JLabel("Name: ");
    GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
    gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
    gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_2.gridx = 0;
    gbc_lblNewLabel_2.gridy = 1;
    panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
    
    JLabel lblNewLabel_5 = new JLabel("name");
    GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
    gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
    gbc_lblNewLabel_5.gridx = 1;
    gbc_lblNewLabel_5.gridy = 1;
    panel_2.add(lblNewLabel_5, gbc_lblNewLabel_5);
    
    JLabel lblNewLabel_3 = new JLabel("Section: ");
    GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
    gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
    gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_3.gridx = 0;
    gbc_lblNewLabel_3.gridy = 2;
    panel_2.add(lblNewLabel_3, gbc_lblNewLabel_3);
    
    JLabel lblNewLabel_5_1 = new JLabel("name");
    GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
    gbc_lblNewLabel_5_1.insets = new Insets(0, 0, 5, 0);
    gbc_lblNewLabel_5_1.gridx = 1;
    gbc_lblNewLabel_5_1.gridy = 2;
    panel_2.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);
    
    JLabel lblNewLabel_4 = new JLabel("Status: ");
    GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
    gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
    gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_4.gridx = 0;
    gbc_lblNewLabel_4.gridy = 3;
    panel_2.add(lblNewLabel_4, gbc_lblNewLabel_4);
    
    JLabel lblNewLabel_5_2 = new JLabel("name");
    GridBagConstraints gbc_lblNewLabel_5_2 = new GridBagConstraints();
    gbc_lblNewLabel_5_2.insets = new Insets(0, 0, 5, 0);
    gbc_lblNewLabel_5_2.gridx = 1;
    gbc_lblNewLabel_5_2.gridy = 3;
    panel_2.add(lblNewLabel_5_2, gbc_lblNewLabel_5_2);
    
    JButton btnNewButton_1 = new JButton("Subjects");
    GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
    gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
    gbc_btnNewButton_1.gridx = 0;
    gbc_btnNewButton_1.gridy = 4;
    panel_2.add(btnNewButton_1, gbc_btnNewButton_1);
    
    JButton btnNewButton_2 = new JButton("Bills");
    GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
    gbc_btnNewButton_2.gridx = 1;
    gbc_btnNewButton_2.gridy = 4;
    panel_2.add(btnNewButton_2, gbc_btnNewButton_2);
  }

}
