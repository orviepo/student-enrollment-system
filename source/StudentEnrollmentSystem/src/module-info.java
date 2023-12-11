@SuppressWarnings("module")
module enrollment {
  exports com.mycollege.enrollment;

  requires transitive java.desktop;
  requires miglayout15.swing;
  requires transitive java.sql.rowset;
  requires java.sql;
}