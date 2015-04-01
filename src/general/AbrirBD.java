package general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Si se puede conectar a la BD devolverá la conexión en caso contrario devolverá null

public class AbrirBD {

	Connection conexion = null;

	protected Connection abreBaseDeDatos() throws SQLException {

		try {
			// Declaramos el tipo de driver que vamos a utilizar
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Problema con el driver!!!");
			e.printStackTrace();
		}

		try {
			// Conectamos a la base de datos
			conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "copoliyo");
		} catch (Exception e) {
			System.out.println("No se puede conectar a la base de datos!!!");
			e.printStackTrace();
		}

		return conexion;
	}

	public ResultSet EjecutaConsulta(String strConsulta, Connection conexion) {

		this.conexion = conexion;
		// Con la conexión, leemos el registro que corresponde a la empresa
		ResultSet rs = null;
		Statement s = null;
		String strSQL = "";

		try {
			// Creamos el 'Statement'
			s = conexion.createStatement();
		} catch (SQLException err) {
			err.printStackTrace();
		}

		// Hacemos la consulta
		try {
			strSQL = strConsulta;
			// strSQL="SELECT * FROM SISTEM WHERE EMPRESA = '" + empresa + "'";
			rs = s.executeQuery(strSQL);
		} catch (SQLException err) {
			err.printStackTrace();
		}

		return rs;
	}
}
