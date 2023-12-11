package com.mycollege.enrollment;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * The enrollment system's main class which also declares universal variables
 * for other classes. Launches the program.
 *
 * @author Jhon Jasser Garcia
 * @author Bernales Adonis
 * @author Carl Angelo Domingo
 * @author Jules Vyen San Mateo
 * @author Kurt Jelo Mendoza
 */
public class Main {

  /**
   * The database name for the class.
   */
  protected static final String DATABASE = "mycollege_enrollment";

  /**
   * The default border Look-and-Feel.
   */
  public static final Border DEFAULT_BORDER = new JTextField().getBorder();

  /*
   * Class ImageIcons
   */
  private static URL logoPath = Main.class.getResource("/com/mycollege/enrollment/images/logo.png");
  private static URL loginPath = Main.class.getResource("/com/mycollege/enrollment/images/user.png");
  public static final ImageIcon logo = resize(logoPath, 300, 300);
  public static final ImageIcon loginIcon = resize(loginPath, 20, 20);

  /**
   * The main method.
   *
   * @param args
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
          new Main();
          new StudentHome();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private static ImageIcon resize(URL imagePath, int width, int height) {
    ImageIcon base = new ImageIcon(imagePath);
    Image image = base.getImage();
    Image scaled = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    ImageIcon imageIcon = new ImageIcon(scaled);
    return imageIcon;
  }

  /**
   *
   *
   * @param database
   */
  public Main() {
  }

}
