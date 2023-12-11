package com.mycollege.enrollment;

import java.awt.Component;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * 
 */
public class JTablePlus extends JTable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * 
   */
  public JTablePlus() {
    this.setAutoResizeMode(AUTO_RESIZE_OFF);
  }
  
  /**
   * 
   * 
   * @param minWidth
   * @param maxWidth
   */
  public void autoSize(int minWidth, int maxWidth, int padding) {
    
    final TableColumnModel columnModel = this.getColumnModel();
    
    for (int column = 0; column < this.getColumnCount(); column++) {
      
      for (HierarchyBoundsListener hbl : this.getHierarchyBoundsListeners()) {
        this.removeHierarchyBoundsListener(hbl);
      }
      
      // Get preferred width of table header
      TableColumn tableColumn = columnModel.getColumn(column);
      Object header = tableColumn.getHeaderValue();
      TableCellRenderer headerRenderer = this.getTableHeader().getDefaultRenderer();
      
      Component headerComp = headerRenderer.getTableCellRendererComponent(this, header, false, false, -1, column);
      
      int colWidth = Math.max(headerComp.getPreferredSize().width + padding, minWidth);
      
      // Check width of each row per column
      for (int row = 0; row < this.getRowCount(); row++) {
        
        TableCellRenderer cellRenderer = this.getCellRenderer(row, column);
        Component comp = this.prepareRenderer(cellRenderer, row, column);
        
        colWidth = Math.max(comp.getPreferredSize().width + padding, colWidth);
        
        if (colWidth > maxWidth) {
        }
        
      } // for row
      
      if (colWidth > maxWidth) {
        colWidth = maxWidth;
        tableColumn.setCellRenderer(new CellWrapper());
      }
      
      tableColumn.setPreferredWidth(colWidth);
      
      this.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
        int i = 0;
        @Override
        public void ancestorResized(HierarchyEvent e) {
          int columnWidth = columnModel.getTotalColumnWidth();
          int parentWidth = JTablePlus.this.getParent().getWidth();
          
          if (i < 6) i++;
          else {
            if (columnWidth > parentWidth) {
              JTablePlus.this.setAutoResizeMode(AUTO_RESIZE_OFF);
            }
            else {
              JTablePlus.this.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
            }
          }
        }
      });
      
    } // for column
    
  } // autoSize()
  
  private class CellWrapper extends JTextArea implements TableCellRenderer {
    
    private static final long serialVersionUID = 1L;
    
    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    
    private ButtonModel model = null;
    
    private CellWrapper() {
      model = new JCheckBox().getModel();
      setLineWrap(true);
      setWrapStyleWord(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      
      setText(String.valueOf(value));
      
      setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
      
      if (table.getRowHeight(row) != getPreferredSize().height) {
        table.setRowHeight(row, getPreferredSize().height);
      }
      
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        super.setBackground(table.getSelectionBackground());
      }
      else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      }
      model.setSelected((value != null && (Boolean.valueOf((String) value)).booleanValue()));
  
      if (hasFocus) {
        setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
      } else {
        setBorder(noFocusBorder);
      }
  
      return this;
    }
    
  } // CellWrapper

} // JTablePlus
