package com.mycollege.enrollment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

public class Registration {

  /**
   * Create the application.
   * 
   * @throws SQLException
   */
  public Registration() {
    this.database = Main.DATABASE;
    
    initGUI();
  }
  
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
          new Registration();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  private String database;

  private JFrame frame;
  
  private JTextField tfdLastName;
  private JTextField tfdFirstName;
  private JTextField tfdMiddleName;
  private JTextField tfdSuffix;
  
  private JTextField tfdBirthplace;
  private JTextField tfdAddress;
  private JTextField tfdCivilStatus;
  private JTextField tfdNationality;

  private JTextField tfdReligion;
  private JTextField tfdContact;
  private JTextField tfdEmail;
  private JTextField tfdLandline;
  private JTextField tfdOthers;
  
  private JTextField tfdFatherLastName;
  private JTextField tfdFatherFirstName;
  private JTextField tfdFatherMiddleName;
  private JTextField tfdFatherMobile;
  private JTextField tfdFatherAddress;
  
  private JTextField tfdMotherLastName;
  private JTextField tfdMotherFirstName;
  private JTextField tfdMotherMiddleName;
  private JTextField tfdMotherMobile;
  private JTextField tfdMotherAddress;
  
  private JTextField tfdGuardianName;
  private JTextField tfdGuardianContact;
  private JTextField tfdGuardianAddress;
  private JTextField tfdGuardianEmail;

  private JTextField tfdLastSchool;

  private ButtonGroup bng;
  private String gender;
  private JRadioButton rbnMale;
  private JRadioButton rbnFemale;
  
  private JComboBox<String> cbxSchoolYear;
  private JComboBox<String> cbxSemester;
  
  private String birthdate;
  private JComboBox<String> cbxBirthMonth;
  private JComboBox<Integer> cbxBirthDay;
  private JComboBox<Integer> cbxBirthYear;
  
  private JComboBox<String> cbxCourse;
  private JComboBox<Integer> cbxYearLevel;
  
  private JCheckBox chxMiddleName;
  private JCheckBox chxFather;
  private JCheckBox chxMother;
  
  private JTextArea txaAccountDetails;
  private JTextArea txaContactInfo;
  private JTextArea txaParentsInfo;
  private JTextArea txaGuardianInfo;
  private JTextArea txaCourseInfo;
  
  private ArrayList<JTextField> requiredTextFields;
  private ArrayList<JTextField> optionalTextFields;
  private ArrayList<JTextField> textFields;

  /**
   * Initialize the contents of the frame.
   * 
   * @wbp.parser.entryPoint
   */
  private void initGUI() {
    frame = new JFrame();
    frame.setTitle("MyCollege");
    frame.setSize(1280, 720);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setIconImage(Main.logo.getImage());

    JScrollPane scrollPane = new JScrollPane();
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    JTabbedPane registerTabs = new JTabbedPane(SwingConstants.LEFT);
    scrollPane.setViewportView(registerTabs);

    JPanel step1Panel = new JPanel();
    step1Panel.setBackground(new Color(240, 255, 255));
    registerTabs.addTab("Step 1", null, step1Panel, "Account Details");
    step1Panel.setLayout(new MigLayout("", "[grow][200px][200px][200px][200px][grow]",
        "[30px][][30px][][][][15px][][][15px][][][][][][]"));

    JLabel lblTitle1 = new JLabel("New/Transferee Enrollment");
    lblTitle1.setFont(new Font("SansSerif", Font.BOLD, 24));
    step1Panel.add(lblTitle1, "cell 1 1 4 1");

    JLabel lblStep1 = new JLabel("Account Details");
    lblStep1.setFont(new Font("SansSerif", Font.BOLD, 18));
    step1Panel.add(lblStep1, "flowx,cell 1 3 4 1");

    JSeparator separator1_1 = new JSeparator();
    step1Panel.add(separator1_1, "cell 1 3 4 1,growx");

    JLabel lblSchoolYear = new JLabel("School Year");
    step1Panel.add(lblSchoolYear, "cell 1 4");

    JLabel lblSemester = new JLabel("Semester");
    step1Panel.add(lblSemester, "cell 2 4");

    cbxSchoolYear = new JComboBox<>();
    cbxSchoolYear.setModel(new DefaultComboBoxModel<>(new String[] { "S.Y. 2023-2024", "S.Y. 2024-2025" }));
    step1Panel.add(cbxSchoolYear, "cell 1 5,growx");

    cbxSemester = new JComboBox<>();
    cbxSemester.setModel(new DefaultComboBoxModel<>(new String[] { "1ST", "2ND" }));
    step1Panel.add(cbxSemester, "cell 2 5,growx");

    JSeparator separator1_2 = new JSeparator();
    step1Panel.add(separator1_2, "cell 1 6 4 1,growx");

    JLabel lblLastName = new JLabel("Last Name");
    step1Panel.add(lblLastName, "cell 1 7");

    JLabel lblFirstName = new JLabel("First Name");
    step1Panel.add(lblFirstName, "cell 2 7");

    JLabel lblMiddleName = new JLabel("Middle Name");
    step1Panel.add(lblMiddleName, "flowx,cell 3 7,growx");

    JLabel lblSuffix = new JLabel("Suffix");
    step1Panel.add(lblSuffix, "cell 4 7");

    tfdLastName = new JTextField();
    step1Panel.add(tfdLastName, "cell 1 8,growx");
    tfdLastName.setColumns(10);

    tfdFirstName = new JTextField();
    step1Panel.add(tfdFirstName, "cell 2 8,growx");
    tfdFirstName.setColumns(10);

    tfdMiddleName = new JTextField();
    step1Panel.add(tfdMiddleName, "cell 3 8,growx");
    tfdMiddleName.setColumns(10);

    tfdSuffix = new JTextField();
    step1Panel.add(tfdSuffix, "cell 4 8,growx");
    tfdSuffix.setColumns(10);

    JSeparator separator1_3 = new JSeparator();
    step1Panel.add(separator1_3, "cell 1 9 4 1,growx");

    JLabel lblBirthplace = new JLabel("Place of Birth");
    step1Panel.add(lblBirthplace, "cell 1 10");

    JLabel lblAddress = new JLabel("Permanent Address");
    step1Panel.add(lblAddress, "cell 3 10");

    tfdBirthplace = new JTextField();
    step1Panel.add(tfdBirthplace, "cell 1 11 2 1,growx");
    tfdBirthplace.setColumns(10);

    Integer[] days = new Integer[31];
    for (int i = 1; i <= days.length; i++) {
      days[i - 1] = i;
    }

    Integer[] years = new Integer[2020 - 1970 + 1];
    for (int i = 1970; i <= 2020; i++) {
      years[i - 1970] = i;
    }

    tfdAddress = new JTextField();
    step1Panel.add(tfdAddress, "cell 3 11 2 1,growx");
    tfdAddress.setColumns(10);

    JLabel lblGender = new JLabel("Gender");
    step1Panel.add(lblGender, "cell 1 12");

    JLabel lblCivilStatus = new JLabel("Civil Status");
    step1Panel.add(lblCivilStatus, "cell 2 12");

    JLabel lblNationality = new JLabel("Nationality");
    step1Panel.add(lblNationality, "cell 3 12");

    JLabel lblReligion = new JLabel("Religion");
    step1Panel.add(lblReligion, "cell 4 12");

    rbnMale = new JRadioButton("MALE", true);
    step1Panel.add(rbnMale, "flowx,cell 1 13,growx");

    tfdCivilStatus = new JTextField();
    step1Panel.add(tfdCivilStatus, "cell 2 13,growx");
    tfdCivilStatus.setColumns(10);

    tfdNationality = new JTextField();
    step1Panel.add(tfdNationality, "cell 3 13,growx");
    tfdNationality.setColumns(10);

    tfdReligion = new JTextField();
    step1Panel.add(tfdReligion, "cell 4 13,growx");
    tfdReligion.setColumns(10);

    JLabel lblBirthdate = new JLabel("Birthdate");
    step1Panel.add(lblBirthdate, "cell 1 14");

    JPanel step2Panel = new JPanel();
    step2Panel.setBackground(new Color(240, 255, 255));
    registerTabs.addTab("Step 2", null, step2Panel, "Contact Information");
    step2Panel.setLayout(new MigLayout("", "[grow][200px][200px][200px][200px][grow]",
        "[30px][][30px][][][][][][][][][]"));

    JLabel lblTitle2 = new JLabel("New/Transferee Enrollment");
    lblTitle2.setFont(new Font("SansSerif", Font.BOLD, 24));
    step2Panel.add(lblTitle2, "cell 1 1 4 1");

    JLabel lblStep2 = new JLabel("Contact Information");
    lblStep2.setFont(new Font("SansSerif", Font.BOLD, 18));
    step2Panel.add(lblStep2, "flowx,cell 1 3 4 1");

    JSeparator separator2_1 = new JSeparator();
    step2Panel.add(separator2_1, "cell 1 3 4 1,growx");

    JLabel lblContact = new JLabel("Contact Number");
    step2Panel.add(lblContact, "cell 1 4");

    tfdContact = new JTextField();
    step2Panel.add(tfdContact, "cell 1 5 2 1,growx");
    tfdContact.setColumns(10);

    JLabel lblEmail = new JLabel("E-mail");
    step2Panel.add(lblEmail, "cell 1 6");

    tfdEmail = new JTextField();
    step2Panel.add(tfdEmail, "cell 1 7 2 1,growx");
    tfdEmail.setColumns(10);

    JLabel lblLandline = new JLabel("Landline");
    step2Panel.add(lblLandline, "cell 1 8");

    tfdLandline = new JTextField();
    step2Panel.add(tfdLandline, "cell 1 9 2 1,growx");
    tfdLandline.setColumns(10);

    JLabel lblOthers = new JLabel("Others");
    step2Panel.add(lblOthers, "cell 1 10");

    JButton btnStep2Back = new JButton("Back");
    btnStep2Back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(0);
      }
    });
    step2Panel.add(btnStep2Back, "pos 0.1al 0.9al,cell 0 11");

    tfdOthers = new JTextField();
    step2Panel.add(tfdOthers, "cell 1 11 2 1,growx");
    tfdOthers.setColumns(10);

    JButton btnStep2Next = new JButton("Next");
    btnStep2Next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(2);
      }
    });
    step2Panel.add(btnStep2Next, "pos 0.9al 0.9al");

    JPanel step3Panel = new JPanel();
    step3Panel.setBackground(new Color(240, 255, 255));
    registerTabs.addTab("Step 3", null, step3Panel, "Parents' Information");
    step3Panel.setLayout(new MigLayout("", "[grow][200px][200px][200px][200px][grow]",
        "[30px][][30px][][][][][][][][][]"));

    JLabel lblTitle3 = new JLabel("New/Transferee Enrollment");
    lblTitle3.setFont(new Font("SansSerif", Font.BOLD, 24));
    step3Panel.add(lblTitle3, "cell 1 1 4 1");

    JLabel lblStep3 = new JLabel("Parents' Information");
    lblStep3.setFont(new Font("SansSerif", Font.BOLD, 18));
    step3Panel.add(lblStep3, "flowx,cell 1 3 4 1");

    JSeparator separator3_1 = new JSeparator();
    step3Panel.add(separator3_1, "cell 1 3 4 1,growx");

    JLabel lblFather = new JLabel("Father's Information");
    lblFather.setFont(new Font("SansSerif", Font.BOLD, 12));
    step3Panel.add(lblFather, "cell 1 4");

    JLabel lblFatherLastName = new JLabel("Last Name");
    step3Panel.add(lblFatherLastName, "cell 2 4");

    JLabel lblFatherFirstName = new JLabel("First Name");
    step3Panel.add(lblFatherFirstName, "cell 3 4");

    JLabel lblFatherMiddleName = new JLabel("Middle Name");
    step3Panel.add(lblFatherMiddleName, "cell 4 4");

    chxFather = new JCheckBox("Not applicable");
    step3Panel.add(chxFather, "cell 1 5");

    tfdFatherLastName = new JTextField();
    step3Panel.add(tfdFatherLastName, "cell 2 5,growx");
    tfdFatherLastName.setColumns(10);

    tfdFatherFirstName = new JTextField();
    step3Panel.add(tfdFatherFirstName, "cell 3 5,growx");
    tfdFatherFirstName.setColumns(10);

    tfdFatherMiddleName = new JTextField();
    step3Panel.add(tfdFatherMiddleName, "cell 4 5,growx");
    tfdFatherMiddleName.setColumns(10);

    JLabel lblFatherMobile = new JLabel("Mobile Number");
    step3Panel.add(lblFatherMobile, "cell 2 6");

    JLabel lblFatherAddress = new JLabel("Address");
    step3Panel.add(lblFatherAddress, "cell 3 6");

    tfdFatherMobile = new JTextField();
    step3Panel.add(tfdFatherMobile, "cell 2 7,growx");
    tfdFatherMobile.setColumns(10);

    tfdFatherAddress = new JTextField();
    step3Panel.add(tfdFatherAddress, "cell 3 7 2 1,growx");
    tfdFatherAddress.setColumns(10);
    
    chxFather.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        
        ArrayList<JTextField> tfdFather = new ArrayList<>();
        
        tfdFather.add(tfdFatherLastName);
        tfdFather.add(tfdFatherFirstName);
        tfdFather.add(tfdFatherMiddleName);
        tfdFather.add(tfdFatherMobile);
        tfdFather.add(tfdFatherAddress);
        
        if (chxFather.isSelected()) {
          for (JTextField tfd : tfdFather) {
            tfd.setEditable(false);
            tfd.setBorder(Main.DEFAULT_BORDER);
          }
        }
        else {
          for (JTextField tfd : tfdFather) {
            tfd.setEditable(true);
          }
        }
      }
    });

    JLabel lblMother = new JLabel("Mother's Information");
    lblMother.setFont(new Font("SansSerif", Font.BOLD, 12));
    step3Panel.add(lblMother, "cell 1 8");

    JLabel lblMotherLastName = new JLabel("Last Name");
    step3Panel.add(lblMotherLastName, "cell 2 8");

    JLabel lblMotherFirstName = new JLabel("First Name");
    step3Panel.add(lblMotherFirstName, "cell 3 8");

    JLabel lblMotherMiddleName = new JLabel("Middle Name");
    step3Panel.add(lblMotherMiddleName, "cell 4 8");

    chxMother = new JCheckBox("Not applicable");
    step3Panel.add(chxMother, "cell 1 9");

    tfdMotherLastName = new JTextField();
    step3Panel.add(tfdMotherLastName, "cell 2 9,growx");
    tfdMotherLastName.setColumns(10);

    tfdMotherFirstName = new JTextField();
    step3Panel.add(tfdMotherFirstName, "cell 3 9,growx");
    tfdMotherFirstName.setColumns(10);

    tfdMotherMiddleName = new JTextField();
    step3Panel.add(tfdMotherMiddleName, "cell 4 9,growx");
    tfdMotherMiddleName.setColumns(10);

    JLabel lblMotherMobile = new JLabel("Mobile Number");
    step3Panel.add(lblMotherMobile, "cell 2 10");

    JLabel lblMotherAddress = new JLabel("Address");
    step3Panel.add(lblMotherAddress, "cell 3 10");

    chxMother.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        ArrayList<JTextField> tfdMother = new ArrayList<>();
        
        tfdMother.add(tfdMotherLastName);
        tfdMother.add(tfdMotherFirstName);
        tfdMother.add(tfdMotherMiddleName);
        tfdMother.add(tfdMotherMobile);
        tfdMother.add(tfdMotherAddress);
        
        if (chxMother.isSelected()) {
          for (JTextField tfd : tfdMother) {
            tfd.setEditable(false);
            tfd.setBorder(Main.DEFAULT_BORDER);
          }
        }
        else {
          for (JTextField tfd : tfdMother) {
            tfd.setEditable(true);
          }
        }
      }
    });

    JButton btnStep3Back = new JButton("Back");
    btnStep3Back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(1);
      }
    });
    step3Panel.add(btnStep3Back, "pos 0.1al 0.9al,cell 0 11");

    tfdMotherMobile = new JTextField();
    step3Panel.add(tfdMotherMobile, "cell 2 11,growx");
    tfdMotherMobile.setColumns(10);

    tfdMotherAddress = new JTextField();
    step3Panel.add(tfdMotherAddress, "cell 3 11 2 1,growx");
    tfdMotherAddress.setColumns(10);

    JButton btnStep3Next = new JButton("Next");
    btnStep3Next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(3);
      }
    });
    step3Panel.add(btnStep3Next, "pos 0.9al 0.9al");

    bng = new ButtonGroup();

    bng.add(rbnMale);

    JButton btnHome = new JButton("Home");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int confirmOption = JOptionPane.showConfirmDialog(null,
            "All information will be discarded.\n" + "Are you sure you want to leave?", "MyCollege",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmOption == 0) {
          new StudentHome();
          frame.dispose();
        }
      }
    });
    step1Panel.add(btnHome, "pos 0.1al 0.9al");
    
    String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December" };

    cbxBirthMonth = new JComboBox<>();
    cbxBirthMonth.setModel(new DefaultComboBoxModel<>(months));
    step1Panel.add(cbxBirthMonth, "flowx,cell 1 15 2 1,growx");

    rbnFemale = new JRadioButton("FEMALE");
    step1Panel.add(rbnFemale, "cell 1 13,growx");
    bng.add(rbnFemale);

    cbxBirthDay = new JComboBox<>();
    cbxBirthDay.setModel(new DefaultComboBoxModel<>(days));
    cbxBirthDay.setEditable(true);
    step1Panel.add(cbxBirthDay, "cell 1 15 2 1,growx");

    cbxBirthYear = new JComboBox<>();
    cbxBirthYear.setModel(new DefaultComboBoxModel<>(years));
    cbxBirthYear.setEditable(true);
    step1Panel.add(cbxBirthYear, "cell 1 15 2 1,growx");

    chxMiddleName = new JCheckBox("No middle name");
    chxMiddleName.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (chxMiddleName.isSelected()) {
          tfdMiddleName.setEditable(false);
          tfdMiddleName.setBorder(Main.DEFAULT_BORDER);
        }
        else {
          tfdMiddleName.setEditable(true);
        }
      }
    });
    step1Panel.add(chxMiddleName, "cell 3 7,growx");

    JButton btnStep1Next = new JButton("Next");
    btnStep1Next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(1);
      }
    });
    step1Panel.add(btnStep1Next, "pos 0.9al 0.9al,cell 0 0");

    JPanel step4Panel = new JPanel();
    step4Panel.setBackground(new Color(240, 255, 255));
    registerTabs.addTab("Step 4", null, step4Panel, "Guardian's Information");
    step4Panel.setLayout(new MigLayout("", "[grow][200px][200px][200px][200px][grow]",
        "[30px][][30px][][][][][][][][][]"));

    JLabel lblTitle4 = new JLabel("New/Transferee Enrollment");
    lblTitle4.setFont(new Font("SansSerif", Font.BOLD, 24));
    step4Panel.add(lblTitle4, "cell 1 1 4 1");

    JLabel lblStep4 = new JLabel("Guardian's Information");
    lblStep4.setFont(new Font("SansSerif", Font.BOLD, 18));
    step4Panel.add(lblStep4, "flowx,cell 1 3 4 1");

    JSeparator separator4_1 = new JSeparator();
    step4Panel.add(separator4_1, "cell 1 3 4 1,growx");

    JLabel lblGuardianName = new JLabel("Name");
    step4Panel.add(lblGuardianName, "cell 1 4");

    tfdGuardianName = new JTextField();
    step4Panel.add(tfdGuardianName, "cell 1 5 2 1,growx");
    tfdGuardianName.setColumns(10);

    JLabel lblGuardianContact = new JLabel("Contact Number");
    step4Panel.add(lblGuardianContact, "cell 1 6");

    tfdGuardianContact = new JTextField();
    step4Panel.add(tfdGuardianContact, "cell 1 7 2 1,growx");
    tfdGuardianContact.setColumns(10);

    JLabel lblGuardianAddress = new JLabel("Address");
    step4Panel.add(lblGuardianAddress, "cell 1 8");

    tfdGuardianAddress = new JTextField();
    step4Panel.add(tfdGuardianAddress, "cell 1 9 2 1,growx");
    tfdGuardianAddress.setColumns(10);

    JLabel lblGuardianEmail = new JLabel("E-mail");
    step4Panel.add(lblGuardianEmail, "cell 1 10");

    JButton btnStep4Back = new JButton("Back");
    btnStep4Back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(2);
      }
    });
    step4Panel.add(btnStep4Back, "pos 0.1al 0.9al,cell 0 11");

    tfdGuardianEmail = new JTextField();
    step4Panel.add(tfdGuardianEmail, "cell 1 11 2 1,growx");
    tfdGuardianEmail.setColumns(10);

    JButton btnStep4Next = new JButton("Next");
    btnStep4Next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(4);
      }
    });
    step4Panel.add(btnStep4Next, "pos 0.9al 0.9al");

    JPanel step5Panel = new JPanel();
    step5Panel.setBackground(new Color(240, 255, 255));
    registerTabs.addTab("Step 5", null, step5Panel, "Course Level Information");
    step5Panel.setLayout(
        new MigLayout("", "[grow][200px][200px][200px][200px][grow]", "[30px][][30px][][][][][]"));

    JLabel lblTitle5 = new JLabel("New/Transferee Enrollment");
    lblTitle5.setFont(new Font("SansSerif", Font.BOLD, 24));
    step5Panel.add(lblTitle5, "cell 1 1 4 1");

    JLabel lblStep5 = new JLabel("Course Level Information");
    lblStep5.setFont(new Font("SansSerif", Font.BOLD, 18));
    step5Panel.add(lblStep5, "flowx,cell 1 3 4 1");

    JSeparator separator5_1 = new JSeparator();
    step5Panel.add(separator5_1, "cell 1 3 4 1,growx");

    JLabel lblCourse = new JLabel("Course");
    step5Panel.add(lblCourse, "cell 1 4");

    cbxCourse = new JComboBox<>();
    String[] courses = getCourses();
    cbxCourse.setModel(new DefaultComboBoxModel<>(courses));
    step5Panel.add(cbxCourse, "cell 1 5 3 1,growx");

    JLabel lblYearLevel = new JLabel("Year Level");
    step5Panel.add(lblYearLevel, "cell 1 6");

    JLabel lblLastSchool = new JLabel("School Last Attended");
    step5Panel.add(lblLastSchool, "cell 2 6");

    JButton btnStep5Back = new JButton("Back");
    btnStep5Back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(3);
      }
    });
    step5Panel.add(btnStep5Back, "pos 0.1al 0.9al,cell 0 0");

    cbxYearLevel = new JComboBox<>();
    cbxYearLevel.setModel(new DefaultComboBoxModel<>(new Integer[] { 1, 2, 3, 4 }));
    step5Panel.add(cbxYearLevel, "cell 1 7,growx");

    tfdLastSchool = new JTextField();
    step5Panel.add(tfdLastSchool, "cell 2 7 2 1,growx");
    tfdLastSchool.setColumns(10);

    JButton btnStep5Next = new JButton("Next");
    btnStep5Next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(5);
        review();
      }
    });
    step5Panel.add(btnStep5Next, "pos 0.9al 0.9al,cell 0 0");

    JPanel reviewPanel = new JPanel();
    reviewPanel.setBackground(new Color(240, 255, 255));
    registerTabs.addTab("Review", null, reviewPanel, "Review Your Information");
    reviewPanel.setLayout(new MigLayout("", "[grow][210px][210px][210px][210px][210px][grow]",
        "[30px][][30px][][][grow][100px]"));

    JLabel lblTitle6 = new JLabel("New/Transferee Enrollment");
    lblTitle6.setFont(new Font("SansSerif", Font.BOLD, 24));
    reviewPanel.add(lblTitle6, "cell 1 1 5 1");

    JLabel lblStep6 = new JLabel("Review Your Information");
    lblStep6.setFont(new Font("SansSerif", Font.BOLD, 18));
    reviewPanel.add(lblStep6, "flowx,cell 1 3 5 1,aligny center");

    JButton btnRefresh = new JButton("Refresh");
    reviewPanel.add(btnRefresh, "cell 1 3 5 1");

    JSeparator separator6_1 = new JSeparator();
    reviewPanel.add(separator6_1, "cell 1 3 5 1,growx");

    JLabel lblAccountDetails = new JLabel("Account Details");
    lblAccountDetails.setFont(new Font("SansSerif", Font.BOLD, 12));
    reviewPanel.add(lblAccountDetails, "flowx,cell 1 4");

    JLabel lblContactInfo = new JLabel("Contact Information");
    lblContactInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
    reviewPanel.add(lblContactInfo, "flowx,cell 2 4");

    JLabel lblParentsInfo = new JLabel("Parents' Information");
    lblParentsInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
    reviewPanel.add(lblParentsInfo, "flowx,cell 3 4");

    JLabel lblGuardianInfo = new JLabel("Guardian's Information");
    lblGuardianInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
    reviewPanel.add(lblGuardianInfo, "flowx,cell 4 4");

    JLabel lblCourseInfo = new JLabel("Course Level Information");
    lblCourseInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
    reviewPanel.add(lblCourseInfo, "flowx,cell 5 4");

    txaAccountDetails = new JTextArea();
    txaAccountDetails.setEditable(false);
    txaAccountDetails.setLineWrap(true);
    txaAccountDetails.setWrapStyleWord(true);
    reviewPanel.add(txaAccountDetails, "cell 1 5,grow");

    txaContactInfo = new JTextArea();
    txaContactInfo.setEditable(false);
    txaContactInfo.setLineWrap(true);
    txaContactInfo.setWrapStyleWord(true);
    reviewPanel.add(txaContactInfo, "cell 2 5,grow");

    txaParentsInfo = new JTextArea();
    txaParentsInfo.setEditable(false);
    txaParentsInfo.setLineWrap(true);
    txaParentsInfo.setWrapStyleWord(true);
    reviewPanel.add(txaParentsInfo, "cell 3 5,grow");

    txaGuardianInfo = new JTextArea();
    txaGuardianInfo.setEditable(false);
    txaGuardianInfo.setLineWrap(true);
    txaGuardianInfo.setWrapStyleWord(true);
    reviewPanel.add(txaGuardianInfo, "cell 4 5,grow");

    txaCourseInfo = new JTextArea();
    txaCourseInfo.setEditable(false);
    txaCourseInfo.setLineWrap(true);
    txaCourseInfo.setWrapStyleWord(true);
    reviewPanel.add(txaCourseInfo, "cell 5 5,grow");

    JButton btnAccountDetailsChange = new JButton("Change");
    reviewPanel.add(btnAccountDetailsChange, "cell 1 4");
    btnAccountDetailsChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(0);
      }
    });

    JButton btnContactInfoChange = new JButton("Change");
    reviewPanel.add(btnContactInfoChange, "cell 2 4");
    btnContactInfoChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(1);
      }
    });

    JButton btnParentsInfoChange = new JButton("Change");
    reviewPanel.add(btnParentsInfoChange, "cell 3 4");
    btnParentsInfoChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(2);
      }
    });

    JButton btnGuardianInfoChange = new JButton("Change");
    reviewPanel.add(btnGuardianInfoChange, "cell 4 4");
    btnGuardianInfoChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(3);
      }
    });

    JButton btnCourseInfoChange = new JButton("Change");
    reviewPanel.add(btnCourseInfoChange, "cell 5 4");
    btnCourseInfoChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(4);
      }
    });

    JButton btnReviewBack = new JButton("Back");
    btnReviewBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        registerTabs.setSelectedIndex(4);
      }
    });
    reviewPanel.add(btnReviewBack, "pos 0.1al 0.9al,cell 0 6");

    JButton btnSubmit = new JButton("Submit");
    btnSubmit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submit();
      }
    });
    reviewPanel.add(btnSubmit, "pos 0.9al 0.9al");

    btnRefresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        review();
      }
    });

    requiredTextFields = new ArrayList<>();
    requiredTextFields.add(tfdLastName);
    requiredTextFields.add(tfdFirstName);
    requiredTextFields.add(tfdMiddleName);

    requiredTextFields.add(tfdBirthplace);
    requiredTextFields.add(tfdAddress);
    requiredTextFields.add(tfdCivilStatus);
    requiredTextFields.add(tfdNationality);
    requiredTextFields.add(tfdReligion);
    requiredTextFields.add(tfdContact);
    requiredTextFields.add(tfdEmail);

    requiredTextFields.add(tfdFatherLastName);
    requiredTextFields.add(tfdFatherFirstName);
    requiredTextFields.add(tfdFatherMiddleName);
    requiredTextFields.add(tfdFatherMobile);
    requiredTextFields.add(tfdFatherAddress);

    requiredTextFields.add(tfdMotherLastName);
    requiredTextFields.add(tfdMotherFirstName);
    requiredTextFields.add(tfdMotherMiddleName);
    requiredTextFields.add(tfdMotherMobile);
    requiredTextFields.add(tfdMotherAddress);

    for (JTextField tfd : requiredTextFields) {

      tfd.setToolTipText("Required!");

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

    optionalTextFields = new ArrayList<>();
    optionalTextFields.add(tfdSuffix);
    optionalTextFields.add(tfdLandline);
    optionalTextFields.add(tfdOthers);

    optionalTextFields.add(tfdGuardianName);
    optionalTextFields.add(tfdGuardianContact);
    optionalTextFields.add(tfdGuardianAddress);
    optionalTextFields.add(tfdGuardianEmail);

    optionalTextFields.add(tfdLastSchool);

    textFields = new ArrayList<>();
    textFields.addAll(requiredTextFields);
    textFields.addAll(optionalTextFields);

    for (JTextField tfd : textFields) {
      tfd.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
          char c = e.getKeyChar();
          
          if (Character.isLowerCase(c) && tfd.isEditable()) {
            c = Character.toUpperCase(c);
            e.setKeyChar(c);
          }
        }
      });
    }

    ArrayList<JLabel> requiredLabels = new ArrayList<>();
    requiredLabels.add(lblLastName);
    requiredLabels.add(lblFirstName);
    requiredLabels.add(lblMiddleName);

    requiredLabels.add(lblBirthplace);
    requiredLabels.add(lblAddress);
    requiredLabels.add(lblCivilStatus);
    requiredLabels.add(lblNationality);
    requiredLabels.add(lblReligion);
    requiredLabels.add(lblContact);
    requiredLabels.add(lblEmail);

    requiredLabels.add(lblFatherLastName);
    requiredLabels.add(lblFatherFirstName);
    requiredLabels.add(lblFatherMiddleName);
    requiredLabels.add(lblFatherMobile);
    requiredLabels.add(lblFatherAddress);

    requiredLabels.add(lblMotherLastName);
    requiredLabels.add(lblMotherFirstName);
    requiredLabels.add(lblMotherMiddleName);
    requiredLabels.add(lblMotherMobile);
    requiredLabels.add(lblMotherAddress);

    for (JLabel lbl : requiredLabels) {
      lbl.setForeground(Color.RED.darker());
      lbl.setText(lbl.getText() + "*");
    }
    
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
  } // initGUI()

  private ArrayList<Integer> courseIDs = new ArrayList<>();

  private String[] getCourses() {
    try (
        MyQuery queryCourseMaj = new MyQuery(database, "courses_majors");
        MyQuery queryCourses = new MyQuery(database, "courses");
        MyQuery queryMajors = new MyQuery(database, "majors");
        ) {
      
      ArrayList<String> courses = new ArrayList<>();
      
      JdbcRowSet rsetCourseMaj = queryCourseMaj.retrieveAll();
      JdbcRowSet rsetCourses = queryCourses.retrieveAll();
      JdbcRowSet rsetMajors = queryMajors.retrieveAll();
      
      while (rsetCourseMaj.next()) {
        int courseMajID = rsetCourseMaj.getInt("courseMajID");
        courseIDs.add(courseMajID);

        String course = "";
        String major = "";
        
        int studCourseID = rsetCourseMaj.getInt("courseID");
        
        while(rsetCourses.next()) {
          int courseID = rsetCourses.getInt("courseID");
          if (courseID == studCourseID) {
            course = rsetCourses.getString("title");
            rsetCourses.beforeFirst();
            break;
          }
        }
        
        int studMajorID = rsetCourseMaj.getInt("majorID");
        
        while(rsetMajors.next()) {
          int majorID = rsetMajors.getInt("majorID");
          if (majorID == studMajorID) {
            major = rsetMajors.getString("title");
            rsetMajors.beforeFirst();
            break;
          }
        }
        
        if (!major.isBlank()) {
          major = ", " + major;
        }
        
        courses.add(course + major);
      }

      return courses.toArray(new String[courses.size()]);
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void submit() {
    review();
    
    boolean isReady = true;
    
    for (JTextField tfd : requiredTextFields) {
      if (tfd.getText().isBlank()) {
        isReady = false;
        break;
      }
    }
    
    if (isReady) {
      try (
          MyQuery query = new MyQuery(database, "students");
          ) {

        query.addData("schoolYear", cbxSchoolYear.getSelectedItem());
        query.addData("semester", cbxSemester.getSelectedItem());

        query.addData("lastName", tfdLastName.getText());
        query.addData("firstName", tfdFirstName.getText());
        query.addData("middleName", tfdMiddleName.getText());
        query.addData("suffix", tfdSuffix.getText());

        query.addData("birthplace", tfdBirthplace.getText());
        query.addData("address", tfdAddress.getText());
        query.addData("gender", gender);
        query.addData("birthdate", birthdate);
        query.addData("civilStatus", tfdCivilStatus.getText());
        query.addData("nationality", tfdNationality.getText());
        query.addData("religion", tfdReligion.getText());

        query.addData("contact", tfdContact.getText());
        query.addData("email", tfdEmail.getText());
        query.addData("landline", tfdLandline.getText());
        query.addData("others", tfdOthers.getText());

        query.addData("fatherLastName", tfdFatherLastName.getText());
        query.addData("fatherFirstName", tfdFatherFirstName.getText());
        query.addData("fatherMiddleName", tfdFatherMiddleName.getText());
        query.addData("fatherMobileNumber", tfdFatherMobile.getText());
        query.addData("fatherAddress", tfdFatherAddress.getText());

        query.addData("motherLastName", tfdMotherLastName.getText());
        query.addData("motherFirstName", tfdMotherFirstName.getText());
        query.addData("motherMiddleName", tfdMotherMiddleName.getText());
        query.addData("motherMobileNumber", tfdMotherMobile.getText());
        query.addData("motherAddress", tfdMotherAddress.getText());

        query.addData("guardianName", tfdGuardianName.getText());
        query.addData("guardianContactNumber", tfdGuardianContact.getText());
        query.addData("guardianAddress", tfdGuardianAddress.getText());
        query.addData("guardianEmail", tfdGuardianEmail.getText());

        query.addData("courseID", courseIDs.get(cbxYearLevel.getSelectedIndex()));
        query.addData("yearLevel", cbxYearLevel.getSelectedIndex() + 1);
        query.addData("lastSchool", tfdLastSchool.getText());

        query.create();

        JOptionPane.showMessageDialog(null, "Submitted successfully!", "MyCollege", JOptionPane.INFORMATION_MESSAGE);
        
        int studentId = query.getCreateId();
        
        new Signup(studentId);
        
        frame.dispose();
      }
      catch (SQLException e) {
        e.printStackTrace();
      } // try-catch
    }
    else {
      JOptionPane.showMessageDialog(null, "Please fill-up all required fields!", "MyCollege",
          JOptionPane.ERROR_MESSAGE);
    } // if-else
  } // submit()

  private void review() {
    for (JTextField optfd : optionalTextFields) {
      if (optfd.getText().isEmpty()) {
        optfd.setText("N/A");
      }
    }

    if (chxMiddleName.isSelected()) {
      tfdMiddleName.setText("N/A");
    }

    if (chxFather.isSelected()) {
      tfdFatherLastName.setText("N/A");
      tfdFatherFirstName.setText("N/A");
      tfdFatherMiddleName.setText("N/A");
      tfdFatherMobile.setText("N/A");
      tfdFatherAddress.setText("N/A");
    }

    if (chxMother.isSelected()) {
      tfdMotherLastName.setText("N/A");
      tfdMotherFirstName.setText("N/A");
      tfdMotherMiddleName.setText("N/A");
      tfdMotherMobile.setText("N/A");
      tfdMotherAddress.setText("N/A");
    }

    // Account Details
    txaAccountDetails.setText("");
    txaAccountDetails.append("School Year: " + cbxSchoolYear.getSelectedItem() + "\n");
    txaAccountDetails.append("Semester: " + cbxSemester.getSelectedItem() + "\n");
    txaAccountDetails.append("\n");
    txaAccountDetails.append("Last Name: " + tfdLastName.getText() + "\n");
    txaAccountDetails.append("First Name: " + tfdFirstName.getText() + "\n");
    txaAccountDetails.append("Middle Name: " + tfdMiddleName.getText() + "\n");
    txaAccountDetails.append("Suffix: " + tfdSuffix.getText());
    txaAccountDetails.append("\n");
    txaAccountDetails.append("Place of Birth: " + tfdBirthplace.getText() + "\n");
    txaAccountDetails.append("Permanent Address: " + tfdAddress.getText() + "\n");
    gender = rbnMale.isSelected() ? rbnMale.getText() : rbnFemale.getText();
    txaAccountDetails.append("Gender: " + gender + "\n");
    birthdate = String.format("%d-%d-%d", cbxBirthYear.getSelectedItem(), (cbxBirthMonth.getSelectedIndex() + 1),
        cbxBirthDay.getSelectedItem());
    txaAccountDetails.append("Birthdate: " + birthdate + "\n");
    txaAccountDetails.append("Civil Status: " + tfdCivilStatus.getText() + "\n");
    txaAccountDetails.append("Nationality: " + tfdNationality.getText() + "\n");
    txaAccountDetails.append("Religion: " + tfdReligion.getText());

    // Contact Information
    txaContactInfo.setText("");
    txaContactInfo.append("Contact number: " + tfdContact.getText() + "\n");
    txaContactInfo.append("E-mail address: " + tfdEmail.getText() + "\n");
    txaContactInfo.append("Landline: " + tfdLandline.getText() + "\n");
    txaContactInfo.append("Others: " + tfdOthers.getText());

    // Parents' Information
    txaParentsInfo.setText("");
    txaParentsInfo.append("Father\n");
    txaParentsInfo.append("Last name: " + tfdFatherLastName.getText() + "\n");
    txaParentsInfo.append("First name: " + tfdFatherFirstName.getText() + "\n");
    txaParentsInfo.append("Middle name: " + tfdFatherMiddleName.getText() + "\n");
    txaParentsInfo.append("Mobile number: " + tfdFatherMobile.getText() + "\n");
    txaParentsInfo.append("Address: " + tfdFatherAddress.getText() + "\n");
    txaParentsInfo.append("\n");

    txaParentsInfo.append("Mother\n");
    txaParentsInfo.append("Last name: " + tfdMotherLastName.getText() + "\n");
    txaParentsInfo.append("First name: " + tfdMotherFirstName.getText() + "\n");
    txaParentsInfo.append("Middle name: " + tfdMotherMiddleName.getText() + "\n");
    txaParentsInfo.append("Mobile number: " + tfdMotherMobile.getText() + "\n");
    txaParentsInfo.append("Address: " + tfdMotherAddress.getText());

    // Guardian's Information
    txaGuardianInfo.setText("");
    txaGuardianInfo.append("Last name: " + tfdGuardianName.getText() + "\n");
    txaGuardianInfo.append("First name: " + tfdGuardianContact.getText() + "\n");
    txaGuardianInfo.append("Address: " + tfdGuardianAddress.getText() + "\n");
    txaGuardianInfo.append("Middle name: " + tfdGuardianEmail.getText());

    // Course Level Information
    txaCourseInfo.setText("");
    txaCourseInfo.append("Course: " + cbxCourse.getSelectedItem() + "\n");
    txaCourseInfo.append("Year Level: " + cbxYearLevel.getSelectedItem() + "\n");
    txaCourseInfo.append("School Last Attended: " + tfdLastSchool.getText());

    for (JTextField optfd : optionalTextFields) {
      if (optfd.getText().isEmpty()) {
        optfd.setText("");
      }
    }
    
  } // review()
  
} // Registration
