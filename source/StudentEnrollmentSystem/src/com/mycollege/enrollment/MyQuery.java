package com.mycollege.enrollment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class MyQuery implements AutoCloseable {
  
  /**
   * 
   */
  private String table;
  
  /**
   * 
   */
  private HashMap<String, Object> dataset;
  
  /**
   * 
   */
  private JdbcRowSet rset;

  /**
   * 
   * 
   * @param database
   * @param table
   * @throws SQLException
   */
  public MyQuery(String database, String table) throws SQLException {
    this.table = table;
    
    RowSetFactory rsetFactory = RowSetProvider.newFactory();
    rset = rsetFactory.createJdbcRowSet();
    rset.setUrl("jdbc:mysql://localhost:3306/" + database);
    rset.setUsername("root");
    rset.setPassword("");
    
    dataset = new HashMap<>();
    }

  /**
   * 
   * 
   * @param column
   * @param data
   */
  public void addData(String column, Object data) {
    this.dataset.put(column, data);
  }

  @Override
  public void close() throws SQLException {
    rset.close();
  }

  /**
   * 
   * 
   * @throws SQLException
   */
  public void create() throws SQLException {

    retrieveAll();
    
    rset.moveToInsertRow();
    
    for (Entry<String, Object> set : dataset.entrySet()) {
      rset.updateObject(set.getKey(), set.getValue());
    }

    rset.insertRow();
    rset.last();
  }

  /**
   * 
   * 
   * @return
   * @throws SQLException
   */
  public JdbcRowSet retrieveAll() throws SQLException {
    
    String cmd = "SELECT * FROM " + table;
    
    rset.setCommand(cmd);
    rset.execute();
    
    return rset;
  }

  /**
   * 
   * 
   * @return
   * @throws SQLException
   */
  public JdbcRowSet retrieve(String column, Object data) throws SQLException {
    
    String cmd = "SELECT * FROM " + table + " WHERE " + column + "=?";
    
    rset.setCommand(cmd);
    rset.setObject(1, data);
    rset.execute();
    
    return rset;
  }

  /**
   * 
   * 
   * @return
   * @throws SQLException
   */
  public JdbcRowSet retrieveWhere() throws SQLException {
    
    String cols = "";
    
    for (int i = 0; i < dataset.size(); i++) {
      cols =  // condition ? true : false
          (i > 0) 
          ? (cols + " AND &=?") 
          : ("&=?");
    }
    
    String cmd = "SELECT * FROM " + table + " WHERE " + cols;
    
    for (Entry<String, Object> set : dataset.entrySet()) {
      cmd = cmd.replaceFirst("&", set.getKey());
    }
    
    rset.setCommand(cmd);
    
    int d = 0;
    for (Entry<String, Object> set : dataset.entrySet()) {
      rset.setObject(++d, set.getValue());
    }
    
    rset.execute();
    
    return rset;
  }

  /**
   * 
   * 
   * @param pattern
   * @return a rowset containing data obtained from the database
   * @throws SQLException
   */
  public JdbcRowSet search(String[] columns, String pattern) throws SQLException {
    
    String cols = String.join(",", columns);
    String cmd = "SELECT * FROM " + table + " WHERE MATCH (" + cols + ") AGAINST (?)";
    
    rset.setCommand(cmd);
    rset.setString(1, pattern);
    rset.execute();
    
    return rset;
  }

  /**
   * @throws SQLException 
   * 
   */
  public void update(String column, Object data) throws SQLException {
    
    String cmd = "SELECT * FROM " + table + " WHERE " + column + "=?";
    
    rset.setCommand(cmd);
    rset.setObject(1, data);
    rset.execute();
    
    while (rset.next()) {
      for (Entry<String, Object> set : dataset.entrySet()) {
        rset.updateObject(set.getKey(), set.getValue());
      }
      rset.updateRow();
    }
  }

  /**
   * 
   * 
   * @throws SQLException 
   */
  public void delete(String column, Object data) throws SQLException {
    
    String cmd = "SELECT * FROM " + table + " WHERE " + column + "=?";
    
    rset.setCommand(cmd);
    rset.setObject(1, data);
    rset.execute();
    
    while (rset.next()) {
      rset.deleteRow();
    }
  }
  
  public int getCreateId() throws SQLException {
    return rset.getInt(1);
  }

}
