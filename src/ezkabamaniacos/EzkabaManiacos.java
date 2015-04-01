/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezkabamaniacos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Txus
 */
public class EzkabaManiacos {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Establecemos el LookAndFeel
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {      
                    //
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(EzkabaManiacos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(EzkabaManiacos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(EzkabaManiacos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }           
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }
    
}
