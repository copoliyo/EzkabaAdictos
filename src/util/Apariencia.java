package util;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Apariencia {

	public static void cambiaLook(){
		
		try
        {
            //UIManager.setLookAndFeel("org.jvnet.substance.skin.Substance"+"CremeCoffee"+"LookAndFeel");
			UIManager.setLookAndFeel("org.jvnet.substance.skin.Substance"+"OfficeBlue2007"+"LookAndFeel");
        } catch(Exception e)
        {
            System.out.println("Falló la carga del tema");
        } 
         
	}
	
	public static Font cambiaFuente(){
		return new Font("MS Reference Sans Serif", Font.BOLD, 14);
		//return new Font("MS Reference Sans Serif", Font.PLAIN, 10);		
	}
	
	public static Font cambiaFuente(int atributo, int tamaño){
		return new Font("MS Reference Sans Serif", atributo, tamaño);
		//return new Font("MS Reference Sans Serif", Font.PLAIN, 10);
	}
	
	public static void mensajeInformativo(int tamañoFuente, String strMensaje){
		JOptionPane.showMessageDialog(null, "<html><font face='Courier New'><font size='" + tamañoFuente + "'><strong>" + 
				strMensaje + "</strong></font></html>");	
	}
}
