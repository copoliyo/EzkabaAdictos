package ezkabamaniacos;


public class CoordenadaGeografica{
	
    private double longitud;
    private double latitud;

    public CoordenadaGeografica() {
        this.longitud = 0.0;
        this.latitud = 0.0;
    }

<<<<<<< HEAD
    public CoordenadaGeografica(double latitud, double longitud) {
=======
    public CoordenadaGeografica(double longitud, double latitud) {
>>>>>>> c38e6c145f4d02f36413839bd98bce8263f1f7cc
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
