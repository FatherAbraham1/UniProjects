/*     */ package de.uni_leipzig.asv.toolbox.jLanI.commonTable;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultCellEditor;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ 
/*     */ public class CommonTable extends JScrollPane
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JTable commonTable;
/*     */   private TableSorter tableSorter;
/*     */   private int columns;
/*     */   private String[] header;
/*     */   private TableCellRenderer cellRend;
/*     */   private DefaultCellEditor cellEdit;
/*     */   private boolean langTab;
/*     */   private int[] colWidth;
/*     */ 
/*     */   public CommonTable()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CommonTable(String[] header, int[] colWidth, Vector data, TableCellRenderer cellRend, DefaultCellEditor cellEdit, boolean langTab)
/*     */   {
/*  54 */     this.cellRend = cellRend;
/*  55 */     this.cellEdit = cellEdit;
/*  56 */     this.langTab = langTab;
/*  57 */     this.colWidth = colWidth;
/*     */ 
/*  61 */     if (((this.header != null) || (header != null)) && (data != null))
/*     */     {
/*  63 */       int actDatum = 0;
/*  64 */       this.header = header;
/*  65 */       this.columns = this.header.length;
/*  66 */       String[][] rows = new String[data.size()][this.columns];
/*  67 */       Iterator it = data.iterator();
/*     */ 
/*  70 */       while (it.hasNext())
/*     */       {
/*  72 */         String[] o = (String[])it.next();
/*  73 */         int l = o.length;
/*     */ 
/*  75 */         for (int i = 0; i < l; i++)
/*     */         {
/*  77 */           rows[actDatum][i] = o[i];
/*     */         }
/*     */ 
/*  80 */         actDatum++;
/*     */       }
/*     */ 
/*  83 */       DefaultTableModel model = new DefaultTableModel(rows, this.header);
/*  84 */       this.tableSorter = new TableSorter(model);
/*  85 */       this.commonTable = new JTable(this.tableSorter);
/*     */ 
/*  87 */       this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/*     */ 
/*  90 */       setCellRendEdit(this.commonTable, this.tableSorter);
/*  91 */       getViewport().add(this.commonTable, null);
/*     */     }
/*  96 */     else if ((data == null) && ((this.header != null) || (header != null)))
/*     */     {
/*  98 */       if (header != null) {
/*  99 */         this.header = header;
/*     */       }
/*     */ 
/* 102 */       this.columns = this.header.length;
/*     */ 
/* 104 */       DefaultTableModel model = new DefaultTableModel(null, this.header);
/* 105 */       this.tableSorter = new TableSorter(model);
/* 106 */       this.commonTable = new JTable(this.tableSorter);
/*     */ 
/* 108 */       this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/*     */ 
/* 110 */       setCellRendEdit(this.commonTable, this.tableSorter);
/* 111 */       getViewport().add(this.commonTable, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setCellRendEdit(JTable tab, TableSorter tableSorter)
/*     */   {
/* 117 */     if (this.langTab)
/*     */     {
/* 119 */       tableSorter.langTab = this.langTab;
/* 120 */       TableColumn tc = tab.getColumn(this.header[1]);
/* 121 */       tc.setCellRenderer(this.cellRend);
/* 122 */       tc.setCellEditor(this.cellEdit);
/*     */     }
/*     */     else
/*     */     {
/* 126 */       tab.setAutoResizeMode(0);
/* 127 */       TableColumn tc = tab.getColumn(this.header[0]);
/* 128 */       tc.setPreferredWidth(this.colWidth[0]);
/* 129 */       tc = tab.getColumn(this.header[1]);
/* 130 */       tc.setPreferredWidth(this.colWidth[1]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void createTable(String[] header, Vector data)
/*     */   {
/* 143 */     if ((this.header == null) && (header == null))
/* 144 */       return;
/* 145 */     if (data == null)
/*     */     {
/* 147 */       if (header != null) {
/* 148 */         this.header = header;
/*     */       }
/*     */ 
/* 151 */       this.columns = this.header.length;
/*     */ 
/* 153 */       DefaultTableModel model = new DefaultTableModel(null, this.header);
/* 154 */       this.tableSorter = new TableSorter(model);
/* 155 */       this.commonTable = new JTable(this.tableSorter);
/*     */ 
/* 157 */       this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/*     */ 
/* 159 */       setCellRendEdit(this.commonTable, this.tableSorter);
/* 160 */       getViewport().add(this.commonTable, null);
/*     */ 
/* 162 */       return;
/*     */     }
/*     */ 
/* 168 */     if (header != null) {
/* 169 */       this.header = header;
/*     */     }
/*     */ 
/* 172 */     int actDatum = 0;
/* 173 */     this.columns = this.header.length;
/* 174 */     String[][] rows = new String[data.size()][this.columns];
/* 175 */     Iterator it = data.iterator();
/*     */ 
/* 177 */     while (it.hasNext())
/*     */     {
/* 179 */       String[] o = (String[])it.next();
/* 180 */       int l = o.length;
/*     */ 
/* 182 */       for (int i = 0; i < l; i++)
/*     */       {
/* 184 */         rows[actDatum][i] = o[i];
/*     */       }
/*     */ 
/* 187 */       actDatum++;
/*     */     }
/*     */ 
/* 190 */     DefaultTableModel model = new DefaultTableModel(rows, this.header);
/* 191 */     this.tableSorter = new TableSorter(model);
/* 192 */     this.commonTable = new JTable(this.tableSorter);
/*     */ 
/* 194 */     this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/*     */ 
/* 197 */     setCellRendEdit(this.commonTable, this.tableSorter);
/* 198 */     getViewport().add(this.commonTable, null);
/*     */   }
/*     */ 
/*     */   public void setHeader(String[] header)
/*     */   {
/* 214 */     this.header = header;
/* 215 */     this.columns = this.header.length;
/*     */   }
/*     */ 
/*     */   public void addData(Vector data)
/*     */   {
/* 226 */     if (data == null) {
/* 227 */       return;
/*     */     }
/*     */ 
/* 230 */     int actDatum = 0;
/* 231 */     String[][] rows = new String[data.size()][this.columns];
/* 232 */     Iterator it = data.iterator();
/*     */ 
/* 234 */     while (it.hasNext())
/*     */     {
/* 236 */       String[] o = (String[])it.next();
/* 237 */       int l = o.length;
/*     */ 
/* 239 */       for (int i = 0; i < l; i++)
/*     */       {
/* 241 */         rows[actDatum][i] = o[i];
/*     */       }
/*     */ 
/* 244 */       actDatum++;
/*     */     }
/*     */ 
/* 247 */     DefaultTableModel model = new DefaultTableModel(rows, this.header);
/* 248 */     this.tableSorter = new TableSorter(model);
/* 249 */     this.commonTable = new JTable(this.tableSorter);
/*     */ 
/* 251 */     this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/*     */ 
/* 254 */     setCellRendEdit(this.commonTable, this.tableSorter);
/* 255 */     getViewport().add(this.commonTable, null);
/*     */   }
/*     */ 
/*     */   public void addRow(String[] row)
/*     */   {
/* 265 */     if (row == null) {
/* 266 */       return;
/*     */     }
/*     */ 
/* 269 */     Vector v = getTableData();
/* 270 */     if (v == null) {
/* 271 */       v = new Vector();
/*     */     }
/*     */ 
/* 274 */     v.add(row);
/*     */ 
/* 276 */     addData(v);
/*     */ 
/* 278 */     getViewport().add(this.commonTable, null);
/*     */ 
/* 280 */     setVisible(true);
/* 281 */     repaint();
/*     */   }
/*     */ 
/*     */   public void deleteSelected()
/*     */   {
/* 290 */     Vector v = getTableData();
/* 291 */     Iterator it = v.iterator();
/* 292 */     int rowCount = this.commonTable.getSelectedRowCount();
/* 293 */     int[] selectedRows = this.commonTable.getSelectedRows();
/* 294 */     int columnCount = this.commonTable.getColumnCount();
/*     */ 
/* 296 */     int tmp = 0;
/* 297 */     boolean deleteThis = false;
/*     */ 
/* 299 */     if (v == null);
/* 306 */     for (int i = 0; i < rowCount; i++)
/*     */     {
/* 308 */       String[] delItem = new String[columnCount];
/*     */ 
/* 313 */       for (int j = 0; j < columnCount; j++)
/*     */       {
/* 315 */         delItem[j] = new String();
/* 316 */         Object val = this.commonTable.getValueAt(selectedRows[i], j);
/* 317 */         if ((val instanceof String))
/* 318 */           delItem[j] = ((String)val);
/*     */         else {
/* 320 */           delItem[j] = val.toString();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 330 */       tmp = 0;
/*     */ 
/* 335 */       while (it.hasNext())
/*     */       {
/* 337 */         String[] test = (String[])it.next();
/*     */ 
/* 339 */         for (int k = 0; k < columnCount; k++)
/*     */         {
/* 341 */           if (test[k].equals(delItem[k])) {
/* 342 */             deleteThis = true;
/*     */           }
/*     */           else {
/* 345 */             deleteThis = false;
/*     */ 
/* 347 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 356 */         if (deleteThis)
/*     */         {
/* 359 */           v.remove(tmp);
/* 360 */           tmp--;
/*     */ 
/* 362 */           it = v.iterator();
/* 363 */           deleteThis = false;
/*     */ 
/* 365 */           break;
/*     */         }
/*     */ 
/* 369 */         deleteThis = false;
/* 370 */         tmp++;
/*     */       }
/*     */     }
/*     */ 
/* 374 */     addData(v);
/*     */ 
/* 376 */     setCellRendEdit(this.commonTable, this.tableSorter);
/* 377 */     getViewport().add(this.commonTable, null);
/*     */ 
/* 379 */     setVisible(true);
/* 380 */     repaint();
/*     */   }
/*     */ 
/*     */   public Vector getTableData()
/*     */   {
/* 390 */     if (this.commonTable == null) {
/* 391 */       return null;
/*     */     }
/*     */ 
/* 394 */     Vector result = new Vector();
/* 395 */     int rowCount = this.commonTable.getRowCount();
/* 396 */     int columnCount = this.commonTable.getColumnCount();
/*     */ 
/* 398 */     for (int i = 0; i < rowCount; i++)
/*     */     {
/* 400 */       String[] value = new String[columnCount];
/*     */ 
/* 402 */       for (int j = 0; j < columnCount; j++)
/*     */       {
/* 404 */         value[j] = new String();
/* 405 */         Object val = this.commonTable.getValueAt(i, j);
/* 406 */         if ((val instanceof String))
/* 407 */           value[j] = ((String)val);
/*     */         else {
/* 409 */           value[j] = ((Boolean)val).toString();
/*     */         }
/*     */       }
/*     */ 
/* 413 */       result.add(value);
/*     */     }
/*     */ 
/* 416 */     return result;
/*     */   }
/*     */   public JTable getTable() {
/* 419 */     return this.commonTable;
/*     */   }
/*     */ }

/* Location:           C:\ASV\Tools\jlani2_wordscompatible\bin\jlani2\
 * Qualified Name:     de.uni_leipzig.asv.toolbox.jLanI.commonTable.CommonTable
 * JD-Core Version:    0.6.0
 */