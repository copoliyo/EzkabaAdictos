package ezkabamaniacos;

import general.MysqlConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.BaseDatos;

public class Utilidad {

    public static CoordenadaUTM geograficaToUtm(CoordenadaGeografica cg) {
        CoordenadaUTM cu = new CoordenadaUTM();

		// Dependiendo de si es ESTE u OESTE
        // De momento siempre OESTE
        // cg.longitud = cg.longitud * -1.0;
		// C�lculos sacados de la p�gina:
        //
        // http://www.gabrielortiz.com/index.asp?Info=058a
        // Semieje mayor
        final double a = 6378388.0;
        // Semieje menor
        final double b = 6356911.94613;
        // Excentricidad
        final double e = Math.sqrt((a * a) - (b * b)) / a;
        // Segunda excentricidad
        final double e2 = Math.sqrt((a * a) - (b * b)) / b;
        // Segunda excentricidad al cuadrado
        final double e2cuadrado = e2 * e2;
        // Radio Polar de Curvatura
        final double c = (a * a) / b;
        // Aplanamiento 
        final double alfa = (a - b) / a;
        // Pasar coordenadas geogr�ficas a radianes
        final double longitudRadianes = (cg.getLongitud() * Math.PI) / 180.0;
        final double latitudRadianes = (cg.getLatitud() * Math.PI) / 180.0;
        // Huso
        final int huso = (int) ((cg.getLongitud() / 6) + 31);
        // Meridiano central
        final int lambda = (huso * 6) - 183;
        // Distancia angular
        final double lambdaI = longitudRadianes - (lambda * Math.PI / 180.0);
        // A
        final double A = Math.cos(latitudRadianes) * Math.sin(lambdaI);
        // xi0
        final double xi0 = 0.5 * Math.log((1 + A) / (1 - A));
        // eta
        final double eta = Math.atan(Math.tan(latitudRadianes) / Math.cos(lambdaI)) - latitudRadianes;
        // ni
        final double ni = (c / Math.pow((1 + (e2cuadrado * (Math.cos(latitudRadianes) * Math.cos(latitudRadianes)))), 0.5)) * 0.9996;
        // xi
        final double xi = (e2cuadrado / 2) * (xi0 * xi0) * (Math.cos(latitudRadianes) * Math.cos(latitudRadianes));
        // A1
        final double A1 = Math.sin(2.0 * latitudRadianes);
        // A2
        final double A2 = A1 * (Math.cos(latitudRadianes) * Math.cos(latitudRadianes));
        // J2
        final double J2 = latitudRadianes + (A1 / 2.0);
        // J4
        final double J4 = ((3.0 * J2) + A2) / 4.0;
        // J6
        final double J6 = ((5.0 * J4) + (A2 * (Math.cos(latitudRadianes) * Math.cos(latitudRadianes)))) / 3.0;
        // alpha ???? Ojito no confundir con 'alfa'
        final double alpha = (3.0 / 4.0) * e2cuadrado;
        // beta
        final double beta = (5.0 / 3.0) * (alpha * alpha);
        // gamma
        final double gamma = (35.0 / 27.0) * (alpha * alpha * alpha);
        // BO
        final double B0 = 0.9996 * c * (latitudRadianes - (alpha * J2) + (beta * J4) - (gamma * J6));

        cu.setLongitud((xi0 * ni * (1 + (xi / 3.0))) + 500000.0);
        cu.setLatitud((eta * ni * (1 + xi)) + B0);

        // Devolvemos una coordenada UTM		
        return cu;
    }

    public static double distanciaDosCoordenadasUTM(CoordenadaUTM cu1, CoordenadaUTM cu2) {
        double distancia = 0.0;

        // Pit�goras despejado
        distancia = Math.sqrt(Math.pow(Math.abs(cu2.getLongitud() - cu1.getLongitud()), 2) + Math.pow(Math.abs(cu2.getLatitud() - cu1.getLatitud()), 2));

        return distancia;
    }

    public static String segundosHoraConFormato(long seg) {
        String horaConFormato = "";

        int horas, minutos, segundos;

        horas = (int) (seg / 3600);
        seg = seg - (horas * 3600);

        minutos = (int) (seg / 60);
        seg = seg - (minutos * 60);

        segundos = (int) seg;

        horaConFormato = String.format("%02d:%02d:%02d", horas, minutos, segundos);

        return horaConFormato;
    }
    
    public static int horaConformatoAsegundos(String strHora){
        int segundosTotales = -1;
        int hh, mm, ss;
        
        if(strHora.length() < 8)
            return segundosTotales;
        
        hh = Integer.valueOf(strHora.substring(0, 2));
        mm = Integer.valueOf(strHora.substring(3, 5));
        ss = Integer.valueOf(strHora.substring(6, 8));
        
        segundosTotales = (hh * 3600) + (mm * 60) + ss;
        
        return segundosTotales;
    }
    
    public static String descomponFechaHora(String fecha, int tipoDevuelto) {
        
        // tipoDevuelto == 0 -> devuelve dia
        // tipoDevuelto == 1 -> devuelve hora
        
        String dia, hora;
        Date date = null;
        fecha = fecha.replace("T", " ");
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            date = new Date();
        }
        hora = new SimpleDateFormat("HH:mm:ss").format(date); // 9:00
        dia = new SimpleDateFormat("yyyy-MM-dd").format(date); // 9:00	
        
        if(tipoDevuelto == 0)
            return dia;
        else
            return hora;        
    }

    public static Gpx cargaTrackDesdeBd(int idTrack){
        Gpx gpx = new Gpx();
        TrackPoint trkpt;
        ResultSet rs = null;
        MysqlConnect m = null;
        Statement s = null;
        int numeroDeFilas = 0;
        String strSql;

        m = MysqlConnect.getDbCon();
        
        // Primero tenemos que cargar los datos desde la tabla de cabeceras de track
        strSql = "SELECT * FROM EZKABAMANIACOS.NOMBRESDETRACK WHERE ID = " + idTrack;

        numeroDeFilas = BaseDatos.countRows(strSql);
        
        if (numeroDeFilas > 0) {
            try {                       
                rs = (ResultSet) m.query(strSql);
                
                // Leemos el registro con esa Id
                if (rs.next() == true) {
                    gpx.id = rs.getInt("ID");
                    gpx.nombreTrack = rs.getString("NOMBRE");
                    gpx.time = rs.getString("FECHA") + " " + rs.getString("HORA");
                    gpx.descripcion = rs.getString("DESCRIPCION");
                    gpx.autorNombre = rs.getString("CREADOR");
                    gpx.distanciaTotal = rs.getDouble("LONGITUDTOTAL");
                }
                rs.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        // Ahora ya podemos cargar los punto del track
        strSql = "SELECT * FROM EZKABAMANIACOS.TRACKS WHERE ID = " + idTrack +
                 " ORDER BY NUMEROPUNTO ASC";
        
        numeroDeFilas = BaseDatos.countRows(strSql);
        
        if (numeroDeFilas > 0) {
            try {        
                
                rs = (ResultSet) m.query(strSql);

                // Recorremos el recodSet para ir rellenando la tabla de marcas
                while (rs.next() == true) {
                    trkpt = new TrackPoint();
                    trkpt.latitud = rs.getDouble("LATITUD");
                    trkpt.longitud = rs.getDouble("LONGITUD");
                    trkpt.altura = rs.getDouble("ELEVACION");
                    trkpt.pulsaciones = rs.getInt("PULSACIONES");
                    trkpt.dia = rs.getString("FECHA");
                    trkpt.hora = rs.getString("HORA");
                    
                    
                    gpx.track.add(trkpt);
                    
                    //System.out.println("gpx.track.hora : " + gpx.track.get(gpx.track.size()-1).hora);
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        return gpx;
    }
}
