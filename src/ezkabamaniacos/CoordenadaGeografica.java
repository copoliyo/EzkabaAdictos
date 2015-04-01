package ezkabamaniacos;


public class CoordenadaGeografica{
	
    private double longitud;
    private double latitud;

    public CoordenadaGeografica() {
        this.longitud = 0.0;
        this.latitud = 0.0;
    }

    public CoordenadaGeografica(double longitud, double latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
