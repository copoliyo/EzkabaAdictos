/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezkabamaniacos;

import general.MysqlConnect;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import util.Apariencia;
import util.BaseDatos;
import util.Cadena;

/**
 *
 * @author Txus
 */
public class IndiceTracks extends javax.swing.JDialog {

    public enum Columna {
        ID(0),
        FECHA(1),
        HORA(2),
        NOMBRE(3), 
        LONGITUD(4);
        
        private int value;

        private Columna(int value) {
            this.value = value;
        }
    }
    
    JTable jtTrack;
    JScrollPane spTrack;

    DefaultTableCellRenderer tcr;
    TableCellRenderer tcr2;
    
    static ResultSet rs = null;
    static MysqlConnect m = null;

    // Id del track que devolveremos
    int idTrack = 0;
    // Consulta SQL
    String strSql = "";

    DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Para que no podamos editar los datos de la tabla
            return false;
        }
    };
    
    
    
    /**
     * Creates new form IndiceTracks
     */
    public IndiceTracks(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        m = MysqlConnect.getDbCon();
        
        initComponents();
        initMisComponentes();                
        
    }

    private void initMisComponentes(){
        // La tabla contendrá estas columnas
        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Longitud (m.)");        
        
        jtTrack = new JTable(modeloTabla);
        //jtTrack.setFont(Apariencia.cambiaFuente(Font.PLAIN, 13));

        TableColumn columna = new TableColumn();
        // Establecemos el ancho
        jtTrack.getColumn("Id").setMaxWidth(60);
        jtTrack.getColumn("Fecha").setMaxWidth(100);
        jtTrack.getColumn("Hora").setMaxWidth(80);
        jtTrack.getColumn("Nombre").setMaxWidth(300);
        jtTrack.getColumn("Longitud (m.)").setMaxWidth(120);
        
         // Hacemos que las columnas se alineen a la DERECHA
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        jtTrack.getColumn("Id").setCellRenderer(tcr);
        jtTrack.getColumn("Fecha").setCellRenderer(tcr);
        jtTrack.getColumn("Hora").setCellRenderer(tcr);
        jtTrack.getColumn("Longitud (m.)").setCellRenderer(tcr);
        
        // Hace que el fondo sea completamente blanco
        jtTrack.setFillsViewportHeight(true);
        
        jtTrack.addKeyListener(new EscapeListener());
        
        // Creamos un JscrollPane y le agregamos la JTable
        spTrack = new JScrollPane(jtTrack);
        // Si quisieramos barra horizontal, descomentar la linea siguiente
        spTrack.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // Agregamos el JScrollPane al contenedor
        spTrack.setBounds(10, 10, 660, 400);
        spTrack.setFont(Apariencia.cambiaFuente());
        spTrack.setBackground(Color.yellow);
        
        jtTrack.addMouseListener(new TablaListener());
        
        getContentPane().add(spTrack);
        limpiarTabla();
        cargaTabla();
        this.setVisible(true);
        
    }
    
    private void limpiarTabla(){        
        modeloTabla.setRowCount(0);
    }
    
    private void cargaTabla(){
        int numeroDeFilas = 0;

        Object fila[] = {"", "", "", "", ""};

        /*
        if (m.conn == null) {
            getProvincia();
        }
        */

        strSql = "";

        strSql = "SELECT * FROM EZKABAMANIACOS.NOMBRESDETRACK ORDER BY ID ASC";

        numeroDeFilas = BaseDatos.countRows(strSql);
        if (numeroDeFilas > 0) {
            try {
                
                
                rs = (ResultSet) m.query(strSql);

                // Recorremos el recodSet para ir rellenando la tabla de marcas
                while (rs.next() == true) {                    
                    fila[0] = String.valueOf(rs.getInt("ID"));
                    fila[1] = rs.getString("FECHA");
                    fila[2] = rs.getString("HORA");
                    fila[3] = rs.getString("NOMBRE");
                    fila[4] = Cadena.formatoConComaDecimal(rs.getDouble("LONGITUDTOTAL"));

                    modeloTabla.addRow(fila);
                }
                rs.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    
    class TablaListener implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            dispose();
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            // Si hacemos doble click fijamos el valor de la marca y salimos
            if (arg0.getClickCount() == 2) {
                JTable target = (JTable) arg0.getSource();
                int row = target.getSelectedRow();
                idTrack = Integer.valueOf((String)target.getValueAt(row, 0));
                dispose();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
    
    class EscapeListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent arg0) {
            // TODO Auto-generated method stub
            int id = arg0.getKeyCode();

            if (id == KeyEvent.VK_ESCAPE) {
                getId();
            }
        }

        @Override
        public void keyReleased(KeyEvent arg0) {

        }

        @Override
        public void keyTyped(KeyEvent arg0) {

        }
    }
    
    public int getId() {
        try {
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.dispose();
        return idTrack;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SALIR.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(664, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(429, Short.MAX_VALUE)
                .addComponent(jbSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbSalir;
    // End of variables declaration//GEN-END:variables
}
