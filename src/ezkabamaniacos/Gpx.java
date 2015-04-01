package ezkabamaniacos;

import java.util.ArrayList;
import java.util.List;

public class Gpx {

    int id;
    String time;
    String nombreTrack;
    String autorNombre;
    String autorEmail;
    double distanciaTotal;
    String descripcion;
    List<TrackPoint> track;

    Gpx() {
        id = 0;
        time = "";
        nombreTrack = "";
        autorNombre = "";
        autorEmail = "";
        distanciaTotal = 0.0;
        descripcion = "";
        track = new ArrayList<TrackPoint>();
    }
}
