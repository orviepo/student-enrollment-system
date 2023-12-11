package com.mycollege.enrollment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import javax.sql.rowset.JdbcRowSet;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Let's the user pick subjects.
 *
 * @author      Jhon Jasser Garcia
 * @author      Bernales Adonis
 * @author      Carl Angelo Domingo
 * @author      Jules Vyen San Mateo
 * @author      Kurt Jelo Mendoza
 */
public class Subjects {

  /**
   * Create the application.
   * 
   * @throws SQLException
   */
  public Subjects(int studentId) {
    this.studentId = studentId;
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
          new Subjects(1);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private JFrame frame;

  private int studentId;

  private JLabel lblName;
  private JLabel lblSemester;
  private JLabel lblYearLevel;
  private JLabel lblCourse;

  /**
   * Custom JTable.
   */
  private JTablePlus table;

  /**
   * Initialize the contents of the frame.
   * 
   * @wbp.parser.entryPoint
   */
  private void initGUI() {
    frame = new JFrame();
    frame.setTitle("MyCollege");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setIconImage(Main.logo.getImage());

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(null);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    JPanel subjectPanel = new JPanel();
    subjectPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    scrollPane.setViewportView(subjectPanel);
    GridBagLayout gbl_subjectPanel = new GridBagLayout();
    gbl_subjectPanel.columnWidths = new int[] {400};
    gbl_subjectPanel.rowHeights = new int[] {30, 0, 0, 0, 0, 50};
    gbl_subjectPanel.columnWeights = new double[]{1.0};
    gbl_subjectPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    subjectPanel.setLayout(gbl_subjectPanel);

    JLabel lblTitle = new JLabel("Confirm your subjects");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
    GridBagConstraints gbc_lblTitle = new GridBagConstraints();
    gbc_lblTitle.anchor = GridBagConstraints.NORTHWEST;
    gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
    gbc_lblTitle.gridx = 0;
    gbc_lblTitle.gridy = 0;
    subjectPanel.add(lblTitle, gbc_lblTitle);
    
    lblName = new JLabel("Name: ");
    GridBagConstraints gbc_lblName = new GridBagConstraints();
    gbc_lblName.anchor = GridBagConstraints.WEST;
    gbc_lblName.insets = new Insets(0, 0, 5, 0);
    gbc_lblName.gridx = 0;
    gbc_lblName.gridy = 1;
    subjectPanel.add(lblName, gbc_lblName);
    
    lblSemester = new JLabel("Semester: ");
    GridBagConstraints gbc_lblSemester = new GridBagConstraints();
    gbc_lblSemester.anchor = GridBagConstraints.WEST;
    gbc_lblSemester.insets = new Insets(0, 0, 5, 0);
    gbc_lblSemester.gridx = 0;
    gbc_lblSemester.gridy = 2;
    subjectPanel.add(lblSemester, gbc_lblSemester);
    
    lblYearLevel = new JLabel("Year level: ");
    GridBagConstraints gbc_lblYearLevel = new GridBagConstraints();
    gbc_lblYearLevel.anchor = GridBagConstraints.WEST;
    gbc_lblYearLevel.insets = new Insets(0, 0, 5, 0);
    gbc_lblYearLevel.gridx = 0;
    gbc_lblYearLevel.gridy = 3;
    subjectPanel.add(lblYearLevel, gbc_lblYearLevel);
    
    lblCourse = new JLabel("Course: ");
    GridBagConstraints gbc_lblCourse = new GridBagConstraints();
    gbc_lblCourse.anchor = GridBagConstraints.WEST;
    gbc_lblCourse.insets = new Insets(0, 0, 5, 0);
    gbc_lblCourse.gridx = 0;
    gbc_lblCourse.gridy = 4;
    subjectPanel.add(lblCourse, gbc_lblCourse);

    JScrollPane subjectScroll = new JScrollPane();
    GridBagConstraints gbc_subjectScroll = new GridBagConstraints();
    gbc_subjectScroll.fill = GridBagConstraints.BOTH;
    gbc_subjectScroll.gridx = 0;
    gbc_subjectScroll.gridy = 5;
    subjectPanel.add(subjectScroll, gbc_subjectScroll);
    
    initDesc();

    subjectScroll.setViewportView(getSubjectsTable());

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  } // initializeGUI()
  
  private void initDesc() {
    try (
        MyQuery queryStudents = new MyQuery(Main.DATABASE, "students");
        ) {

      JdbcRowSet studentInfo = queryStudents.retrieve("studID", studentId);
      
      if (studentInfo.next()) {
        this.lblName.setText("Name: " + studentInfo.getString("name"));
        this.lblSemester.setText("Name: " + studentInfo.getString("semester"));
        this.lblYearLevel.setText("Name: " + studentInfo.getString("name"));
        this.lblCourse.setText("Name: " + studentInfo.getString("name"));
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private JTablePlus getSubjectsTable() {
    try (
        MyQuery queryStudents = new MyQuery(Main.DATABASE, "students");
        MyQuery querySubjects = new MyQuery(Main.DATABASE, "subjects");
        ) {

      DefaultTableModel model = new DefaultTableModel();
      
      model.addColumn("code");
      model.addColumn("title");
      model.addColumn("units");

      JdbcRowSet studentInfo = queryStudents.retrieve("studID", studentId);

      if (studentInfo.next()) {
        querySubjects.addData("semester", studentInfo.getString("semester"));
        querySubjects.addData("yearlevel", studentInfo.getString("yearLevel"));
        querySubjects.addData("courseMajID", studentInfo.getString("courseMajID"));
        
        JdbcRowSet subjects = querySubjects.retrieveWhere();

        while (subjects.next()) {
          List<Object> row = new ArrayList<>();
          row.add(subjects.getString("code"));
          row.add(subjects.getString("title"));
          row.add(subjects.getInt("units"));
          model.addRow(row.toArray());
        }
      }

      table = new JTablePlus() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean editCellAt(int row, int column, java.util.EventObject e) {
          return false;
        }
      };
      table.setModel(model);
      table.autoSize(50, 300, 15);

      return table;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  } // getSubjects()

} // Signup
