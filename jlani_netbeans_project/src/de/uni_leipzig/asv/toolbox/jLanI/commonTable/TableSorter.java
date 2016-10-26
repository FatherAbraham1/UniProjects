/*     */ package de.uni_leipzig.asv.toolbox.jLanI.commonTable;
/*     */ 

/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class TableSorter extends AbstractTableModel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected TableModel tableModel;
/*     */   public static final int DESCENDING = -1;
/*     */   public static final int NOT_SORTED = 0;
/*     */   public static final int ASCENDING = 1;
/*  94 */   private static Directive EMPTY_DIRECTIVE = new Directive(-1, 0);
/*     */ 
/*  96 */   public static final Comparator COMPARABLE_COMAPRATOR = new Comparator() {
/*     */     public int compare(Object o1, Object o2) {
/*  98 */       return ((Comparable)o1).compareTo(o2);
/*     */     }
/*  96 */   };
/*     */ 
/* 101 */   public static final Comparator LEXICAL_COMPARATOR = new Comparator() {
/*     */     public int compare(Object o1, Object o2) {
/* 103 */       return o1.toString().compareTo(o2.toString());
/*     */     }
/* 101 */   };
/*     */   private Row[] viewToModel;
/*     */   private int[] modelToView;
/*     */   private JTableHeader tableHeader;
/*     */   private MouseListener mouseListener;
/*     */   private TableModelListener tableModelListener;
/* 113 */   private Map columnComparators = new HashMap();
/* 114 */   private List sortingColumns = new ArrayList();
/*     */ 
/* 116 */   public boolean langTab = false;
/*     */ 
/*     */   public TableSorter() {

/*     */   }
/*     */ 
/*     */   public TableSorter(TableModel tableModel) {
/* 124 */     this();
/* 125 */     setTableModel(tableModel);
/*     */   }
/*     */ 
/*     */   public TableSorter(TableModel tableModel, JTableHeader tableHeader) {
/* 129 */     this();
/* 130 */     setTableHeader(tableHeader);
/* 131 */     setTableModel(tableModel);
/*     */   }
/*     */ 
/*     */   private void clearSortingState() {
/* 135 */     this.viewToModel = null;
/* 136 */     this.modelToView = null;
/*     */   }
/*     */ 
/*     */   public TableModel getTableModel() {
/* 140 */     return this.tableModel;
/*     */   }
/*     */ 
/*     */   public void setTableModel(TableModel tableModel) {
/* 144 */     if (this.tableModel != null) {
/* 145 */       this.tableModel.removeTableModelListener(this.tableModelListener);
/*     */     }
/*     */ 
/* 148 */     this.tableModel = tableModel;
/* 149 */     if (this.tableModel != null) {
/* 150 */       this.tableModel.addTableModelListener(this.tableModelListener);
/*     */     }
/*     */ 
/* 153 */     clearSortingState();
/* 154 */     fireTableStructureChanged();
/*     */   }
/*     */ 
/*     */   public JTableHeader getTableHeader() {
/* 158 */     return this.tableHeader;
/*     */   }
/*     */ 
/*     */   public void setTableHeader(JTableHeader tableHeader) {
/* 162 */     if (this.tableHeader != null) {
/* 163 */       this.tableHeader.removeMouseListener(this.mouseListener);
/* 164 */       TableCellRenderer defaultRenderer = this.tableHeader.getDefaultRenderer();
/* 165 */       if ((defaultRenderer instanceof SortableHeaderRenderer)) {
/* 166 */         this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer)defaultRenderer).tableCellRenderer);
/*     */       }
/*     */     }
/* 169 */     this.tableHeader = tableHeader;
/* 170 */     if (this.tableHeader != null) {
/* 171 */       this.tableHeader.addMouseListener(this.mouseListener);
/* 172 */       this.tableHeader.setDefaultRenderer(
/* 173 */         new SortableHeaderRenderer(this.tableHeader.getDefaultRenderer()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isSorting() {
/* 178 */     return this.sortingColumns.size() != 0;
/*     */   }
/*     */ 
/*     */   private Directive getDirective(int column) {
/* 182 */     for (int i = 0; i < this.sortingColumns.size(); i++) {
/* 183 */       Directive directive = (Directive)this.sortingColumns.get(i);
/* 184 */       if (directive.column == column) {
/* 185 */         return directive;
/*     */       }
/*     */     }
/* 188 */     return EMPTY_DIRECTIVE;
/*     */   }
/*     */ 
/*     */   public int getSortingStatus(int column) {
/* 192 */     return getDirective(column).direction;
/*     */   }
/*     */ 
/*     */   private void sortingStatusChanged() {
/* 196 */     clearSortingState();
/* 197 */     fireTableDataChanged();
/* 198 */     if (this.tableHeader != null)
/* 199 */       this.tableHeader.repaint();
/*     */   }
/*     */ 
/*     */   public void setSortingStatus(int column, int status)
/*     */   {
/* 204 */     Directive directive = getDirective(column);
/* 205 */     if (directive != EMPTY_DIRECTIVE) {
/* 206 */       this.sortingColumns.remove(directive);
/*     */     }
/* 208 */     if (status != 0) {
/* 209 */       this.sortingColumns.add(new Directive(column, status));
/*     */     }
/* 211 */     sortingStatusChanged();
/*     */   }
/*     */ 
/*     */   protected Icon getHeaderRendererIcon(int column, int size) {
/* 215 */     Directive directive = getDirective(column);
/* 216 */     if (directive == EMPTY_DIRECTIVE) {
/* 217 */       return null;
/*     */     }
/* 219 */     return new Arrow(directive.direction == -1, size, this.sortingColumns.indexOf(directive));
/*     */   }
/*     */ 
/*     */   private void cancelSorting() {
/* 223 */     this.sortingColumns.clear();
/* 224 */     sortingStatusChanged();
/*     */   }
/*     */ 
/*     */   public void setColumnComparator(Class type, Comparator comparator) {
/* 228 */     if (comparator == null)
/* 229 */       this.columnComparators.remove(type);
/*     */     else
/* 231 */       this.columnComparators.put(type, comparator);
/*     */   }
/*     */ 
/*     */   protected Comparator getComparator(int column)
/*     */   {
/* 236 */     Class columnType = this.tableModel.getColumnClass(column);
/* 237 */     Comparator comparator = (Comparator)this.columnComparators.get(columnType);
/* 238 */     if (comparator != null) {
/* 239 */       return comparator;
/*     */     }
/* 241 */     if (Comparable.class.isAssignableFrom(columnType)) {
/* 242 */       return COMPARABLE_COMAPRATOR;
/*     */     }
/* 244 */     return LEXICAL_COMPARATOR;
/*     */   }
/*     */ 
/*     */   private Row[] getViewToModel() {
/* 248 */     if (this.viewToModel == null) {
/* 249 */       int tableModelRowCount = this.tableModel.getRowCount();
/* 250 */       this.viewToModel = new Row[tableModelRowCount];
/* 251 */       for (int row = 0; row < tableModelRowCount; row++) {
/* 252 */         this.viewToModel[row] = new Row(row);
/*     */       }
/*     */ 
/* 255 */       if (isSorting()) {
/* 256 */         Arrays.sort(this.viewToModel);
/*     */       }
/*     */     }
/* 259 */     return this.viewToModel;
/*     */   }
/*     */ 
/*     */   public int modelIndex(int viewIndex) {
/* 263 */     return getViewToModel()[viewIndex].modelIndex;
/*     */   }
/*     */ 
/*     */   private int[] getModelToView() {
/* 267 */     if (this.modelToView == null) {
/* 268 */       int n = getViewToModel().length;
/* 269 */       this.modelToView = new int[n];
/* 270 */       for (int i = 0; i < n; i++) {
/* 271 */         this.modelToView[modelIndex(i)] = i;
/*     */       }
/*     */     }
/* 274 */     return this.modelToView;
/*     */   }
/*     */ 
/*     */   public int getRowCount()
/*     */   {
/* 280 */     return this.tableModel == null ? 0 : this.tableModel.getRowCount();
/*     */   }
/*     */ 
/*     */   public int getColumnCount() {
/* 284 */     return this.tableModel == null ? 0 : this.tableModel.getColumnCount();
/*     */   }
/*     */ 
/*     */   public String getColumnName(int column)
/*     */   {
/* 289 */     return this.tableModel.getColumnName(column);
/*     */   }
/*     */ 
/*     */   public Class getColumnClass(int column)
/*     */   {
/* 294 */     return this.tableModel.getColumnClass(column);
/*     */   }
/*     */ 
/*     */   public boolean isCellEditable(int row, int column)
/*     */   {
/* 299 */     return this.tableModel.isCellEditable(modelIndex(row), column);
/*     */   }
/*     */ 
/*     */   public Object getValueAt(int row, int column) {
/* 303 */     return this.tableModel.getValueAt(modelIndex(row), column);
/*     */   }
/*     */ 
/*     */   public void setValueAt(Object aValue, int row, int column)
/*     */   {
/* 308 */     this.tableModel.setValueAt(aValue, modelIndex(row), column);
/*     */   }
/*     */ 
/*     */   private static class Arrow
/*     */     implements Icon
/*     */   {
/*     */     private boolean descending;
/*     */     private int size;
/*     */     private int priority;
/*     */ 
/*     */     public Arrow(boolean descending, int size, int priority)
/*     */     {
/* 436 */       this.descending = descending;
/* 437 */       this.size = size;
/* 438 */       this.priority = priority;
/*     */     }
/*     */ 
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/* 442 */       Color color = c == null ? Color.GRAY : c.getBackground();
/*     */ 
/* 445 */       int dx = (int)(this.size / 2 * Math.pow(0.8D, this.priority));
/* 446 */       int dy = this.descending ? dx : -dx;
/*     */ 
/* 448 */       y = y + 5 * this.size / 6 + (this.descending ? -dy : 0);
/* 449 */       int shift = this.descending ? 1 : -1;
/* 450 */       g.translate(x, y);
/*     */ 
/* 453 */       g.setColor(color.darker());
/* 454 */       g.drawLine(dx / 2, dy, 0, 0);
/* 455 */       g.drawLine(dx / 2, dy + shift, 0, shift);
/*     */ 
/* 458 */       g.setColor(color.brighter());
/* 459 */       g.drawLine(dx / 2, dy, dx, 0);
/* 460 */       g.drawLine(dx / 2, dy + shift, dx, shift);
/*     */ 
/* 463 */       if (this.descending)
/* 464 */         g.setColor(color.darker().darker());
/*     */       else {
/* 466 */         g.setColor(color.brighter().brighter());
/*     */       }
/* 468 */       g.drawLine(dx, 0, 0, 0);
/*     */ 
/* 470 */       g.setColor(color);
/* 471 */       g.translate(-x, -y);
/*     */     }
/*     */ 
/*     */     public int getIconWidth() {
/* 475 */       return this.size;
/*     */     }
/*     */ 
/*     */     public int getIconHeight() {
/* 479 */       return this.size;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Directive
/*     */   {
/*     */     private int column;
/*     */     private int direction;
/*     */ 
/*     */     public Directive(int column, int direction)
/*     */     {
/* 513 */       this.column = column;
/* 514 */       this.direction = direction;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MouseHandler extends MouseAdapter
/*     */   {
/*     */     private MouseHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/* 412 */       JTableHeader h = (JTableHeader)e.getSource();
/* 413 */       TableColumnModel columnModel = h.getColumnModel();
/* 414 */       int viewColumn = columnModel.getColumnIndexAtX(e.getX());
/* 415 */       int column = columnModel.getColumn(viewColumn).getModelIndex();
/* 416 */       if (column != -1) {
/* 417 */         int status = TableSorter.this.getSortingStatus(column);
/* 418 */         if (!e.isControlDown()) {
/* 419 */           TableSorter.this.cancelSorting();
/*     */         }
/*     */ 
/* 423 */         status += (e.isShiftDown() ? -1 : 1);
/* 424 */         status = (status + 4) % 3 - 1;
/* 425 */         TableSorter.this.setSortingStatus(column, status);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class Row
/*     */     implements Comparable
/*     */   {
/*     */     private int modelIndex;
/*     */ 
/*     */     public Row(int index)
/*     */     {
/* 317 */       this.modelIndex = index;
/*     */     }
/*     */ 
/*     */     public int compareTo(Object o) {
/* 321 */       int row1 = this.modelIndex;
/* 322 */       int row2 = ((Row)o).modelIndex;
/*     */ 
/* 324 */       for (Iterator it = TableSorter.this.sortingColumns.iterator(); it.hasNext(); ) {
/* 325 */         TableSorter.Directive directive = (TableSorter.Directive)it.next();
/* 326 */         int column = directive.column;
/* 327 */         Object o1 = TableSorter.this.tableModel.getValueAt(row1, column);
/* 328 */         Object o2 = TableSorter.this.tableModel.getValueAt(row2, column);
/*     */ 
/* 330 */         int comparison = 0;
/*     */ 
/* 332 */         if ((o1 == null) && (o2 == null))
/* 333 */           comparison = 0;
/* 334 */         else if (o1 == null)
/* 335 */           comparison = -1;
/* 336 */         else if (o2 == null)
/* 337 */           comparison = 1;
/*     */         else {
/* 339 */           comparison = TableSorter.this.getComparator(column).compare(o1, o2);
/*     */         }
/* 341 */         if (comparison != 0) {
/* 342 */           return directive.direction == -1 ? -comparison : comparison;
/*     */         }
/*     */       }
/* 345 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class SortableHeaderRenderer
/*     */     implements TableCellRenderer
/*     */   {
/*     */     private TableCellRenderer tableCellRenderer;
/*     */ 
/*     */     public SortableHeaderRenderer(TableCellRenderer tableCellRenderer)
/*     */     {
/* 487 */       this.tableCellRenderer = tableCellRenderer;
/*     */     }
/*     */ 
/*     */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/*     */     {
/* 496 */       Component c = this.tableCellRenderer.getTableCellRendererComponent(table, 
/* 497 */         value, isSelected, hasFocus, row, column);
/* 498 */       if ((c instanceof JLabel)) {
/* 499 */         JLabel l = (JLabel)c;
/* 500 */         l.setHorizontalTextPosition(2);
/* 501 */         int modelColumn = table.convertColumnIndexToModel(column);
/* 502 */         l.setIcon(TableSorter.this.getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
/*     */       }
/* 504 */       return c;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class TableModelHandler
/*     */     implements TableModelListener
/*     */   {
/*     */     private TableModelHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void tableChanged(TableModelEvent e)
/*     */     {
/* 352 */       if (!TableSorter.this.isSorting()) {
/* 353 */         TableSorter.this.clearSortingState();
/* 354 */         TableSorter.this.fireTableChanged(e);
/* 355 */         return;
/*     */       }
/*     */ 
/* 361 */       if (e.getFirstRow() == -1) {
/* 362 */         TableSorter.this.cancelSorting();
/* 363 */         TableSorter.this.fireTableChanged(e);
/* 364 */         return;
/*     */       }
/*     */ 
/* 385 */       int column = e.getColumn();
/* 386 */       if ((e.getFirstRow() == e.getLastRow()) && 
/* 387 */         (column != -1) && 
/* 388 */         (TableSorter.this.getSortingStatus(column) == 0) && 
/* 389 */         (TableSorter.this.modelToView != null)) {
/* 390 */         int viewIndex = TableSorter.this.getModelToView()[e.getFirstRow()];
/* 391 */         TableSorter.this.fireTableChanged(
/* 393 */           new TableModelEvent(TableSorter.this, 
/* 392 */           viewIndex, viewIndex, 
/* 393 */           column, e.getType()));
/* 394 */         return;
/*     */       }
/*     */ 
/* 398 */       TableSorter.this.clearSortingState();
/* 399 */       TableSorter.this.fireTableDataChanged();
/*     */ 
/* 401 */      
/*     */   }
/*     */ }
}

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.commonTable.TableSorter
 * JD-Core Version:    0.6.0
 */