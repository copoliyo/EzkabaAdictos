package general;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;

import java.awt.*;
 
 
public class Cambiar_Apariencia extends JFrame
{
    String lista_temas[] = {"Sahara", "Business", "Raven", "RavenGraphite", "ChallengerDeep", "MistSilver", "BusinessBlueSteel", "BusinessBlackSteel", "Magma", "MistAqua", "CremeCoffee", "OfficeBlue2007" };
    JComboBox temas=new JComboBox(lista_temas);
    JLabel etiqueta=new JLabel("Selecciones Tema");
 
    public Cambiar_Apariencia() 
    {
        super("Cambiar Apariencia en Java");
        setLayout(new FlowLayout());
        add(etiqueta);
        add(temas);
         
        temas.addActionListener(new java.awt.event.ActionListener() 
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                String look=temas.getSelectedItem()+"";
                cambiar(look);
            }
        });
        Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((pantalla.width-500)/2,(pantalla.height-500)/2);
        setSize(500,500);
        setVisible(true);       
    }
    public void cambiar(String look)
    { 
        try
        {
            UIManager.setLookAndFeel("org.jvnet.substance.skin.Substance"+look+"LookAndFeel");        	    	
        } catch(Exception e)
        {
            System.out.println("Falló la carga del tema");
        }    
    }
     
    public static void main (String[] args) 
    {
        new Cambiar_Apariencia();
    }
}