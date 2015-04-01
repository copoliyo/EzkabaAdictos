package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextFieldFijo extends JTextField implements KeyListener, ActionListener, FocusListener {
	
	private int longitudMaxima = 0;
        
        public JTextFieldFijo(){		
		this.addKeyListener(this);
		this.addActionListener(this);
		this.addFocusListener(this);
	}
	// Pasamos un parï¿½metro para indicar la longitud mï¿½xima de los datos que
	// puede albergar el JTextField.
	// Es en el constructor de la clase donde tenemos que aï¿½adir los Listeners.
	public JTextFieldFijo(int longitud){
		this.longitudMaxima = longitud;
		this.addKeyListener(this);
		this.addActionListener(this);
		this.addFocusListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.getText().length() > longitudMaxima)
			this.setText(Cadena.left(this.getText(), longitudMaxima));
		this.transferFocus();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (this.getText().length() > longitudMaxima)
			this.setText(Cadena.left(this.getText(), longitudMaxima));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (this.getText().length() > longitudMaxima)
			this.setText(Cadena.left(this.getText(), longitudMaxima));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Leemos la tecla
		if (this.getText().length() > longitudMaxima)
			this.setText(Cadena.left(this.getText(), longitudMaxima));
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		this.selectAll();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub

		if (this.getText().length() > longitudMaxima)
			this.setText(Cadena.left(this.getText(), longitudMaxima));
	}
	
	public void setTextoAyuda(String textoAyuda, int tamaño){
		this.setToolTipText("<html><font size='" + 
				            tamaño +
				            "'><strong>" + 
				            textoAyuda + 
				            "</strong></font></html>");
	}
	
}
