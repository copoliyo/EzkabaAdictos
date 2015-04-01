package util;


import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fecha{
	
	

	private int anio, mes, dia;
	
	public Fecha(){
		anio = Calendar.getInstance().get(Calendar.YEAR);
		mes = Calendar.getInstance().get(Calendar.MONTH) + 1;		
		dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);			
	}		
	
	public Fecha(String strFechaDDpMMpAA){
		
	}
	
	
        public int fechaDiaHoy(){
		int fecha = 0;
		int anio, mes, dia;
		
		anio = Calendar.getInstance().get(Calendar.YEAR);
		mes = Calendar.getInstance().get(Calendar.MONTH) + 1;		
		dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);	
		
		String fechaStr = String.format("%04d%02d%02d", anio, mes + 1, dia);
		fecha = Integer.valueOf(fechaStr);
		
		return fecha;
	}
        
	public static int fechaDia(){
		int fecha = 0;
		int anio, mes, dia;
		
		anio = Calendar.getInstance().get(Calendar.YEAR);
		mes = Calendar.getInstance().get(Calendar.MONTH) + 1;		
		dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);	
		
		String fechaStr = String.format("%04d%02d%02d", anio, mes + 1, dia);
		fecha = Integer.valueOf(fechaStr);
		
		return fecha;
	}
	
	public static boolean fechaValida(String fecha){
		boolean fechaOk = true;
		
		int anio, mes, dia, diasMes, fechaInt;
		String fechaStr;
		
		if(fecha.length() == 0 || fecha.equalsIgnoreCase("0") ){
			fecha = "00.00.00";			
		}
		// Permitimos que la fecha sea "00.00.00" ó "0"
		if(!fecha.equalsIgnoreCase("00.00.00") && !fecha.equalsIgnoreCase("0")){
			fechaInt = Cadena.cadenaAfecha(fecha);
			//fechaStr = String.valueOf(fechaInt);
			fechaStr = Cadena.fechaAcadena(fechaInt);
			if(fechaStr.contains(".")){
				if(fechaStr.length() > 1)
					dia = Integer.valueOf(fechaStr.substring(0, 2));
				else
					dia = 0;

				if(fechaStr.length() > 4)
					mes  = Integer.valueOf(fechaStr.substring(3, 5));
				else
					mes = 0;

				if(fechaStr.length() > 7)
					anio  = Integer.valueOf(fechaStr.substring(6, 8));
				else
					anio = 0;

			}else{					
				anio = Integer.valueOf(fechaStr.substring(0, 4));
				mes  = Integer.valueOf(fechaStr.substring(4, 6));
				dia  = Integer.valueOf(fechaStr.substring(6, 8));
			}
			// Creamos un objeto calendario del año y mes deseados		 
			Calendar cal = new GregorianCalendar(anio, mes - 1, 1); 
			// Obtenemos el número de dias de ese mes en ese año
			diasMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
			// Si hemos puesto un mes que no sea entre 1 y 12 = error
			if(mes < 1 || mes > 12)
				fechaOk = false;
			// Si hemos puesto un día mayor que el número máximo de dias posibles en ese mes = error
			if (dia > diasMes)
				fechaOk = false;
		}else{
			fechaOk = true;
		}


		return fechaOk;
	}
	
        // fechaAcadena pasa la fecha de la instancia a una cadena de
	// texto en formato DD.MM.AA
	public String fechaAcadena(){	
		
            String fechaStr;		
            fechaStr = String.format("%02d.%02d.%02d", this.dia, this.mes,  this.anio%1000);
		
            return fechaStr;
	}

        
        
	// fechaAcadena pasa una fecha tal como se almacenará en la base de datos
	// es decir, un número de 8 cifras en el formato AAAAMMDD a una cadena de
	// texto en formato DDMMAA
	public static String fechaAcadena(int fechaInt){
		String fechaStr = "";
		int dd, mm, aa;
		
		// Primero dividimos entre 100 para sacar el resto, que serán los días
		dd = fechaInt % 100;
		
		fechaInt /= 100;
		
		// Dividimos entre 100 para obtener los meses
		mm = fechaInt % 100;
		
		fechaInt /= 100;
		
		// Volvemos a dividir entre 100 para obtener el año
		aa = fechaInt % 100;
		
		fechaStr = String.format("%02d.%02d.%02d", dd, mm, aa);
		
		
		return fechaStr;
	}

	// cadenaAfecha 
	// Se le pasa una cadena de texto que será una fecha, que puede ser en formato
	// ddmmaa ó dd.mm.aa
	// La salida será un entero preparado para guadarse como una fecha en 
	// formato AAAAMMDD
	public static int cadenaAfecha(String fechaStr){
		int fechaInt = 0;
		int dd, mm, aa = 0;
		String auxStr = null;
		String auxStrFecha = null;
		
		// Primero quitamos los puntos o barras que se pueden usar para separar una fecha
		fechaStr = fechaStr.replaceAll("\\.", "");
		auxStr = fechaStr.replaceAll("/", "");
		
		dd = Integer.valueOf(auxStr.substring(0, 2));
		mm = Integer.valueOf(auxStr.substring(2, 4));
		aa = Integer.valueOf(auxStr.substring(4, 6));
		
		if(aa < 50)
			aa += 2000;
		else
			aa += 1900;
		
		auxStrFecha = String.format("%04d%02d%02d", aa, mm, dd);
		fechaInt = Integer.valueOf(auxStrFecha);
		
		return fechaInt;
	}
	
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}
}
