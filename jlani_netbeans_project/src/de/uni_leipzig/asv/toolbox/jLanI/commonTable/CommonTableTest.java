/*     */ package de.uni_leipzig.asv.toolbox.jLanI.commonTable;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ public class CommonTableTest extends JFrame
/*     */ {
/*     */   private JTable commonTable;
/*     */   private TableSorter tableSorter;
/*     */   private int columnWidth;
/*     */   private int columns;
/*     */   private String[] header;
/*  32 */   private JPanel jContentPane = null;
/*     */ 
/*  34 */   private JMenuBar jJMenuBar = null;
/*     */ 
/*  36 */   private JMenu fileMenu = null;
/*     */ 
/*  38 */   private JMenu editMenu = null;
/*     */ 
/*  40 */   private JMenu helpMenu = null;
/*     */ 
/*  42 */   private JMenuItem exitMenuItem = null;
/*     */ 
/*  44 */   private JMenuItem aboutMenuItem = null;
/*     */ 
/*  46 */   private JMenuItem cutMenuItem = null;
/*     */ 
/*  48 */   private JMenuItem copyMenuItem = null;
/*     */ 
/*  50 */   private JMenuItem pasteMenuItem = null;
/*     */ 
/*  52 */   private JMenuItem saveMenuItem = null;
/*     */ 
/*  54 */   private JMenuItem deleteSelectedItem = null;
/*     */ 
/*  56 */   private JScrollPane jScrollPane = null;
/*     */ 
/*  58 */   private JTable jTable = null;
/*     */ 
/*     */   public void createTable(String[] header, Vector data)
/*     */   {
/*  62 */     int actDatum = 0;
/*     */ 
/*  64 */     this.columns = header.length;
/*  65 */     this.header = header;
/*     */ 
/*  67 */     String[][] rows = new String[data.size()][this.columns];
/*     */ 
/*  69 */     Iterator it = data.iterator();
/*     */ 
/*  71 */     while (it.hasNext())
/*     */     {
/*  73 */       String[] o = (String[])it.next();
/*  74 */       int l = o.length;
/*     */ 
/*  76 */       for (int i = 0; i < l; i++)
/*     */       {
/*  78 */         rows[actDatum][i] = o[i];
/*     */       }
/*     */ 
/*  81 */       actDatum++;
/*     */     }
/*     */ 
/*  84 */     DefaultTableModel model = new DefaultTableModel(rows, this.header);
/*  85 */     this.tableSorter = new TableSorter(model);
/*  86 */     this.commonTable = new JTable(this.tableSorter);
/*     */ 
/*  88 */     this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/*  89 */     this.commonTable.getColumnModel().getColumn(0).setWidth(this.columnWidth);
/*     */ 
/*  91 */     this.jScrollPane.getViewport().add(this.commonTable, null);
/*     */   }
/*     */ 
/*     */   public Vector getTableData()
/*     */   {
/*  96 */     Vector result = new Vector();
/*     */ 
/*  98 */     int rowCount = this.commonTable.getRowCount();
/*  99 */     int columnCount = this.commonTable.getColumnCount();
/*     */ 
/* 101 */     for (int i = 0; i < rowCount; i++)
/*     */     {
/* 103 */       String[] value = new String[columnCount];
/*     */ 
/* 105 */       for (int j = 0; j < columnCount; j++)
/*     */       {
/* 107 */         value[j] = new String();
/* 108 */         value[j] = ((String)this.commonTable.getValueAt(i, j));
/*     */       }
/*     */ 
/* 111 */       result.add(value);
/*     */     }
/*     */ 
/* 114 */     return result;
/*     */   }
/*     */ 
/*     */   public void deleteSelected()
/*     */   {
/* 120 */     Vector v = getTableData();
/*     */ 
/* 122 */     int rowCount = this.commonTable.getSelectedRowCount();
/* 123 */     int[] selectedRows = this.commonTable.getSelectedRows();
/* 124 */     int columnCount = this.commonTable.getColumnCount();
/*     */ 
/* 126 */     for (int i = 0; i < rowCount; i++)
/*     */     {
/* 128 */       String[] delItem = new String[columnCount];
/*     */ 
/* 130 */       for (int j = 0; j < columnCount; j++)
/*     */       {
/* 132 */         delItem[j] = new String();
/* 133 */         delItem[j] = ((String)this.commonTable.getValueAt(selectedRows[i], j));
/*     */       }
/*     */ 
/* 136 */       if (v.contains(delItem)) {
/* 137 */         v.remove(delItem);
/*     */       }
/*     */     }
/*     */ 
/* 141 */     addData(v);
/*     */ 
/* 143 */     this.jScrollPane.add(this.commonTable, null);
/*     */ 
/* 145 */     this.jScrollPane.setVisible(true);
/* 146 */     this.jScrollPane.repaint();
/*     */   }
/*     */ 
/*     */   public void addData(Vector data)
/*     */   {
/* 151 */     int actDatum = 0;
/*     */ 
/* 153 */     String[][] rows = new String[data.size()][this.columns];
/*     */ 
/* 155 */     Iterator it = data.iterator();
/*     */ 
/* 157 */     while (it.hasNext())
/*     */     {
/* 159 */       String[] o = (String[])it.next();
/* 160 */       int l = o.length;
/*     */ 
/* 162 */       for (int i = 0; i < l; i++)
/*     */       {
/* 164 */         rows[actDatum][i] = o[i];
/*     */       }
/*     */ 
/* 167 */       actDatum++;
/*     */     }
/*     */ 
/* 170 */     DefaultTableModel model = new DefaultTableModel(rows, this.header);
/* 171 */     this.tableSorter = new TableSorter(model);
/* 172 */     this.commonTable = new JTable(this.tableSorter);
/*     */ 
/* 174 */     this.tableSorter.setTableHeader(this.commonTable.getTableHeader());
/* 175 */     this.commonTable.getColumnModel().getColumn(0).setWidth(this.columnWidth);
/*     */ 
/* 177 */     this.jScrollPane.add(this.commonTable, null);
/*     */   }
/*     */ 
/*     */   public void setColumnWidth(int columnWidth)
/*     */   {
/* 182 */     this.columnWidth = columnWidth;
/*     */   }
/*     */ 
/*     */   public void setHeader(String[] header)
/*     */   {
/* 187 */     this.header = header;
/*     */   }
/*     */ 
/*     */   private JScrollPane getJScrollPane()
/*     */   {
/* 196 */     if (this.jScrollPane == null) {
/* 197 */       this.jScrollPane = new JScrollPane();
/* 198 */       this.jScrollPane.setViewportView(getJTable());
/*     */     }
/* 200 */     return this.jScrollPane;
/*     */   }
/*     */ 
/*     */   private JTable getJTable()
/*     */   {
/* 209 */     if (this.jTable == null) {
/* 210 */       this.jTable = new JTable();
/*     */     }
/* 212 */     return this.jTable;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 220 */     CommonTableTest application = new CommonTableTest();
/* 221 */     application.show();
/*     */   }
/*     */ 
/*     */   public CommonTableTest()
/*     */   {
/* 229 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/* 238 */     setDefaultCloseOperation(3);

/* 240 */     setSize(439, 391);

/* 242 */     setTitle("Application");
/*     */ 
/* 244 */     setColumnWidth(50);
/*     */ 
/* 246 */     Vector v = new Vector();
/*     */ 
/* 248 */     String[] s1 = new String[2];
/* 249 */     s1[0] = new String(); s1[1] = new String();
/* 250 */     s1[0] = "test1"; s1[1] = "1";
/* 251 */     v.add(s1);
/*     */ 
/* 253 */     String[] s2 = new String[2];
/* 254 */     s2[0] = new String(); s2[1] = new String();
/* 255 */     s2[0] = "test2"; s2[1] = "2";
/* 256 */     v.add(s2);
/*     */ 
/* 258 */     String[] s3 = new String[2];
/* 259 */     s3[0] = new String(); s3[1] = new String();
/* 260 */     s3[0] = "test3"; s3[1] = "3";
/* 261 */     v.add(s3);
/*     */ 
/* 263 */     String[] s = new String[2];
/* 264 */     s[0] = new String(); s[1] = new String();
/* 265 */     s[0] = "header1"; s[1] = "header2";
/*     */ 
/* 267 */     createTable(s, v);
/*     */ 
/* 269 */     Vector test = getTableData();
/* 270 */     Iterator it = test.iterator();
/* 271 */     while (it.hasNext())
/*     */     {
/* 273 */       String[] array = (String[])it.next();
/* 274 */       int l = array.length;
/* 275 */       for (int i = 0; i < l; i++) {
/* 276 */         System.out.println("value[" + i + "] = " + array[i] + " ");
/*     */       }
/* 278 */       System.out.println("\n");
/*     */     }
/*     */ 
/* 281 */     System.out.println("-------------------------------------------------------");
/*     */ 
/* 283 */     this.commonTable.selectAll();
/* 284 */     deleteSelected();
/*     */ 
/* 286 */     Vector test2 = getTableData();
/* 287 */     Iterator it2 = test2.iterator();
/* 288 */     while (it2.hasNext())
/*     */     {
/* 290 */       String[] array = (String[])it2.next();
/* 291 */       int l = array.length;
/* 292 */       for (int i = 0; i < l; i++) {
/* 293 */         System.out.println("value[" + i + "] = " + array[i] + " ");
/*     */       }
/* 295 */       System.out.println("\n");
/*     */     }
/*     */ 
/* 298 */     System.out.println("-------------------------------------------------------");
/*     */   }
}