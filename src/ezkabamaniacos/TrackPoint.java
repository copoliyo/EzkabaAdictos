package ezkabamaniacos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TrackPoint {
	double latitud;
	double longitud;
	double altura;
	String dia;
	String hora;
	int pulsaciones;
	
	TrackPoint(){
		this.latitud = 0.0;
		this.longitud = 0.0;
		this.altura = 0.0;
		this.pulsaciones = 0;
		this.dia = "";
		this.hora = "";
	}
	
	public void descomponFecha(String fecha){
		Date date = null;
		fecha = fecha.replace("T", " ");
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.hora = new SimpleDateFormat("HH:mm:ss").format(date); // 9:00
		this.dia = new SimpleDateFormat("yyyy-MM-dd").format(date); // 9:00	
	}
}
