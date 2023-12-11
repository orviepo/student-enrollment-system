package com.mycollege.enrollment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Signup {
  
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
          new Signup(1);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private String database;

  private JFrame frame;
  
  private int studentId;
  
  private JTextField tfdEmail;
  private JPasswordField pfdPassword;
  private JPasswordField pfdConfirm;

  /**
   * Create the application.
   * 
   * @throws SQLException
   */
  public Signup(int studentId) {
    this.database = Main.DATABASE;
    this.studentId = studentId;
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
    
    JPanel signupPanel = new JPanel();
    signupPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    signupPanel.setBackground(new Color(240, 255, 255));
    scrollPane.setViewportView(signupPanel);
    signupPanel.setLayout(new MigLayout("", "[grow][400px,grow][grow]", "[30px][][30px][][][][][][][30px][]"));

    JLabel lblTitle = new JLabel("Create your account");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
    signupPanel.add(lblTitle, "cell 1 1");

    JLabel lblEmail = new JLabel("E-mail");
    signupPanel.add(lblEmail, "cell 1 3");

    tfdEmail = new JTextField();
    signupPanel.add(tfdEmail, "cell 1 4,growx");
    tfdEmail.setColumns(10);

    JLabel lblPassword = new JLabel("Password");
    signupPanel.add(lblPassword, "cell 1 5");

    pfdPassword = new JPasswordField();
    pfdPassword.setEchoChar('\u2022');
    signupPanel.add(pfdPassword, "flowx,cell 1 6,growx");

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
    signupPanel.add(tbnPasswordShow, "cell 1 6,growy");
    
    JLabel lblConfirm = new JLabel("Confirm Password");
    signupPanel.add(lblConfirm, "cell 1 7");
    
    pfdConfirm = new JPasswordField();
    pfdConfirm.setEchoChar('\u2022');
    signupPanel.add(pfdConfirm, "flowx,cell 1 8,growx");

    JButton btnHome = new JButton("Home");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        int option = JOptionPane.showOptionDialog(null, "Leaving will discard entered information!\nAre you sure?",
            "MyCollege Signup", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        
        if (option == 0) {
          new StudentHome();
          
          try (
              MyQuery query = new MyQuery(database, "students");
              ) {
            query.delete("id", studentId);
          }
          catch (SQLException f) {
            f.printStackTrace();
          }
          
          frame.dispose();
        }
      }
    });
    signupPanel.add(btnHome, "flowx,cell 1 10,growx");

    JButton btnSubmit = new JButton("Submit");
    btnSubmit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submit();
      }
    });
    btnSubmit.setForeground(new Color(255, 255, 255));
    btnSubmit.setBackground(new Color(0, 100, 0));
    signupPanel.add(btnSubmit, "cell 1 10,growx");
    
    JToggleButton tbnConfirmShow = new JToggleButton("Show");
    tbnConfirmShow.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (tbnConfirmShow.isSelected()) {
          pfdConfirm.setEchoChar((char) 0);
        }
        else {
          pfdConfirm.setEchoChar('\u2022');
        }
      }
    });
    signupPanel.add(tbnConfirmShow, "cell 1 8");
    
    ArrayList<JTextField> textFields = new ArrayList<>();
    textFields.add(tfdEmail);
    textFields.add(pfdPassword);
    textFields.add(pfdConfirm);
    
    for (JTextField tfd : textFields) {
      tfd.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          if (tfd.getText().isEmpty() && tfd.isEditable()) {
            tfd.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 2, true), Main.DEFAULT_BORDER));
          }
          else {
            tfd.setBorder(Main.DEFAULT_BORDER);
          }
        }
      });
    }

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  } // initializeGUI()

  private void submit() {
    String email = tfdEmail.getText();
    char[] password = pfdPassword.getPassword();
    char[] confirm = pfdConfirm.getPassword();
    
    int validity = MyAccount.checkTokens(email, password, confirm, 8);
    
    switch (validity) {
      case MyAccount.VALID: {
        try (
            MyQuery queryUsers = new MyQuery(database, "users");
            MyQuery queryStudents = new MyQuery(database, "students");
            ) {
          MyEncryption encrypted = new MyEncryption(password);
          byte[] hash = encrypted.getHash();
          byte[] salt = encrypted.getSalt();
          
          queryUsers.addData("email", email);
          queryUsers.addData("code", hash);
          queryUsers.addData("salt", salt);
          queryUsers.create();
          
          int userid = queryUsers.getCreateId();
          
          queryStudents.addData("userid", userid);
          queryStudents.update("id", studentId);
          
          JOptionPane.showMessageDialog(null, "Signup successful!", "MyCollege Signup", JOptionPane.INFORMATION_MESSAGE);
          
          new Subjects(studentId);
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
        break;
      }
      case MyAccount.INVALID_VALUE: {
        JOptionPane.showMessageDialog(null, "Invalid username/email", "MyAccount", JOptionPane.ERROR_MESSAGE);
        break;
      }
      case MyAccount.INVALID_LENGTH: {
        JOptionPane.showMessageDialog(null, "Password too short", "MyAccount", JOptionPane.ERROR_MESSAGE);
        break;
      }
      case MyAccount.MISMATCH: {
        JOptionPane.showMessageDialog(null, "Password does not match", "MyAccount", JOptionPane.ERROR_MESSAGE);
        break;
      }
    } // switch
  } // submit()
} // Signup
