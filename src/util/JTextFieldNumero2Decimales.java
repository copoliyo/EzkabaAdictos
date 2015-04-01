package util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JTextField;

public class JTextFieldNumero2Decimales extends JTextField implements KeyListener, ActionListener, FocusListener{
	
	private double valorDouble = 0.0;
	private String valorString = "";
	private double valorDoubleFocusGained = 0.0;

	// Es en el constructor de la clase donde tenemos que aï¿½adir los Listeners.
	public JTextFieldNumero2Decimales(){
		this.addKeyListener(this);
		this.addActionListener(this);
		this.addFocusListener(this);
		this.setHorizontalAlignment (JTextField.RIGHT);
		this.setForeground(Color.BLACK);
		//stringToDouble();
		
		//super.setText(doubleToString());
	}
	
	
	@Override
	public String getText(){
			
		String str = super.getText();
		
		if (str.length() > 0){
			if(str.indexOf(",") != -1){
				str = str.replace(".", "");
				str = str.replace(",", ".");
			}
		}		

		return str;
	}
	
	@Override
	public void setText(String str){
		
		if (str.length() > 0){
			if(str.indexOf(",") != -1){
				str = str.replace(".", "");
				str = str.replace(",", ".");
				
				valorString = str;
			}
			else{
				valorString = str;
			}
			try{	
				valorDouble = Double.valueOf(valorString);
			}catch(NumberFormatException nfe){
				valorDouble = valorDoubleFocusGained;
				valorString = doubleToStrComa(valorDouble);
			}
		}
				
	
		if(valorDouble < 0.0)
			super.setForeground(Color.RED);
		else
			super.setForeground(Color.BLACK);
		
		super.setText(doubleToStrComa(valorDouble));
		
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {			
		
		valorDoubleFocusGained = valorDouble;
		super.setText(doubleToStrPunto(valorDouble));
		
		this.selectAll();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		String str = this.getText();
		
		if(str.length() > 0 && str.charAt(str.length() - 1) == '-'){
			str = "-" + str.substring(0, str.length() - 1);
			this.setText(str);
		}
		if(str.length() > 0){
			try{	
				valorDouble = Double.valueOf(this.getText().replaceAll(",", "\\."));
			}catch(NumberFormatException nfe){
				valorDouble = valorDoubleFocusGained;
				valorString = doubleToStrComa(valorDouble);
			}			
		}
		else
			valorDouble = 0.0;
		
		
		this.setText(doubleToStrComa(valorDouble));
		this.select(0, 0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){			
			this.select(0, 0);
			this.transferFocus();
		}else				
			this.requestFocus(true);						
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		// Leemos la tecla
		char caracter = arg0.getKeyChar();
		
		// Si es algo distinto de un nï¿½mero,el caracter de borrado, el tabulador, el punto
		// o la tecla Intro, lo ignoramos.
		if(((caracter < '0') ||	(caracter > '9')) &&
				(caracter != KeyEvent.VK_BACK_SPACE && 
				caracter != KeyEvent.VK_TAB && 
				caracter != KeyEvent.VK_ENTER && 
				caracter != KeyEvent.VK_PERIOD &&
				caracter != KeyEvent.VK_MINUS))
		{
			arg0.consume();
		}		
		
		if(super.getText().indexOf(',') != -1)
			arg0.consume();
	}
	
	public double getDouble(){
		return valorDouble;
	}
	
	
	

	
	private String doubleToStrPunto(double valorDouble){
		String str = "";
		
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		DecimalFormat myFormatter = new DecimalFormat("########0.00", simbolos);
	    str = myFormatter.format(valorDouble);	     		
		
		return str;
	}
	
	private String doubleToStrComa(double valorDouble){
		String str = "";
		
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator(',');
		DecimalFormat myFormatter = new DecimalFormat("###,###,##0.00", simbolos);
	    str = myFormatter.format(valorDouble);
	    
		return str;
	}
	
	public void setTextoAyuda(String textoAyuda, int tamaño){
		this.setToolTipText("<html><font size='" + 
				            tamaño +
				            "'><strong>" + 
				            textoAyuda + 
				            "</strong></font></html>");
	}
}
