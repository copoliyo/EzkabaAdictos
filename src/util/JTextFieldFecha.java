package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

/* 	Control personalizado que hereda de JTextField para poder aceptar fechas con unos ciertos
 *  requisitos de validación. No nos dejará pasar con fechas inválidas y si pulsamos la
 *  tecla ESCAPE, nos pondrá la fecha del día actual.
 * */
public class JTextFieldFecha extends javax.swing.JTextField implements KeyListener, ActionListener, FocusListener {

    private String strFechaAAAAMMDD = "";
    private int fechaAAAAMMDD = 0;

    // Es en el constructor de la clase donde tenemos que añadir los Listeners.
    public JTextFieldFecha() {
        this.addKeyListener(this);
        this.addActionListener(this);
        this.addFocusListener(this);
    }

    public void setTextoAyuda(String textoAyuda, int tamaño) {
        this.setToolTipText("<html><font size='"
                + tamaño
                + "'><strong>"
                + textoAyuda
                + "</strong></font></html>");
    }

    public String getStrFechaAAAAMMDD() {
        if (Fecha.fechaValida(this.getText())) {
            // Un pequeño lío vale para formatear la cadena al formato DD.MM.AA
            this.setText(Cadena.fechaAcadena(Cadena.cadenaAfecha(this.getText())));
            this.fechaAAAAMMDD = Fecha.cadenaAfecha(this.getText().trim());
            this.strFechaAAAAMMDD = String.valueOf(this.fechaAAAAMMDD);
        }
        return strFechaAAAAMMDD;
    }

    public int getFechaAAAAMMDD() {
        if (Fecha.fechaValida(this.getText())) {
            // Un pequeño lío vale para formatear la cadena al formato DD.MM.AA
            this.setText(Cadena.fechaAcadena(Cadena.cadenaAfecha(this.getText())));
            this.fechaAAAAMMDD = Fecha.cadenaAfecha(this.getText().trim());
            this.strFechaAAAAMMDD = String.valueOf(this.fechaAAAAMMDD);
        }
        return fechaAAAAMMDD;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Fecha.fechaValida(this.getText())) {
            // Un pequeño lío vale para formatear la cadena al formato DD.MM.AA
            this.setText(Cadena.fechaAcadena(Cadena.cadenaAfecha(this.getText())));
            this.fechaAAAAMMDD = Fecha.cadenaAfecha(this.getText().trim());
            this.strFechaAAAAMMDD = String.valueOf(this.fechaAAAAMMDD);
            //System.out.println("Fecha en actionEvent: " + fechaAAAAMMDD);
            this.select(0, 0);
        } else {
            this.requestFocus(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Fecha.fechaValida(this.getText())) {
                // Un pequeño lío
                this.setText(Cadena.fechaAcadena(Cadena.cadenaAfecha(this.getText())));
                this.strFechaAAAAMMDD = String.valueOf(Fecha.cadenaAfecha(this.getText().trim()));
                this.fechaAAAAMMDD = Fecha.cadenaAfecha(strFechaAAAAMMDD);
                this.select(0, 0);
                this.transferFocus();
            } else {
                this.requestFocus(true);
            }
        }

        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setText(Cadena.fechaAcadena(Fecha.fechaDia()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // Leemos la tecla
        char caracter = arg0.getKeyChar();

		// Si es algo distinto de un número,el caracter de borrado, el tabulador, el punto
        // o la tecla Intro, lo ignoramos.
        if (((caracter < '0') || (caracter > '9'))
                && (caracter != KeyEvent.VK_BACK_SPACE
                && caracter != KeyEvent.VK_TAB
                && caracter != KeyEvent.VK_ENTER
                && caracter != KeyEvent.VK_PERIOD)) {
            arg0.consume();
        }
    }

    @Override
    public void focusGained(FocusEvent arg0) {
        // TODO Auto-generated method stub
        this.selectAll();
    }

    @Override
    public void focusLost(FocusEvent arg0) {
        // TODO Auto-generated method stub

        if (Fecha.fechaValida(this.getText())) {
            // Un pequeño lío vale para formatear la cadena al formato DD.MM.AA
            this.setText(Cadena.fechaAcadena(Cadena.cadenaAfecha(this.getText())));
            this.fechaAAAAMMDD = Fecha.cadenaAfecha(this.getText().trim());
            this.strFechaAAAAMMDD = String.valueOf(this.fechaAAAAMMDD);
			//System.out.println("Fecha en focuslost: " + fechaAAAAMMDD);

            this.select(0, 0);
        } else {
            this.requestFocus(true);
        }
    }

}
