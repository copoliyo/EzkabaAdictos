package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MiRender extends DefaultTableCellRenderer
{
	Font normal = new Font( "Arial",Font.PLAIN,12 );
	Font negrilla = new Font( "Helvetica",Font.BOLD,18 );
	Font cursiva = new Font( "Times new roman",Font.ITALIC,12 );

	@Override 
	public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column ) 
	{
		setEnabled(table == null || table.isEnabled()); 
     
		//si la celda contiene números
		if(String.valueOf(table.getValueAt(row,column)).startsWith("-") && column == 2)        	
			table.setForeground(Color.RED);
		else
			table.setForeground(Color.BLACK);        	       

		
		/*
		if(row % 2 != 0)
			table.setBackground(Color.LIGHT_GRAY);
		else
			table.setBackground(Color.WHITE);
		*/
		
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);  
		
		return this;
	}
}

