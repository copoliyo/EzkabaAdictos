package util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cadena {

    public static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";

	// fechaAcadena pasa una fecha tal como se almacenará en la base de datos
    // es decir, un número de 8 cifras en el formato AAAAMMDD a una cadena de
    // texto en formato DDMMAA
    public static String fechaAcadena(int fechaInt) {
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
    public static int cadenaAfecha(String fechaStr) {
        int fechaInt = 0;
        int dd, mm, aa = 0;
        String auxStr = null;
        String auxStrFecha = null;

        // Primero quitamos los puntos o barras que se pueden usar para separar una fecha
        fechaStr = fechaStr.replaceAll("\\.", "");
        auxStr = fechaStr.replaceAll("/", "");

        if (auxStr.length() < 6) {
            dd = mm = aa = fechaInt = 0;
        } else {
            dd = Integer.valueOf(auxStr.substring(0, 2));
            mm = Integer.valueOf(auxStr.substring(2, 4));
            aa = Integer.valueOf(auxStr.substring(4, 6));

            if (dd == 0 && mm == 0 && aa == 0) {
                fechaInt = 0;
            } else {
                if (aa < 50) {
                    aa += 2000;
                } else {
                    aa += 1900;
                }

                auxStrFecha = String.format("%04d%02d%02d", aa, mm, dd);
                fechaInt = Integer.valueOf(auxStrFecha);
            }
        }

        return fechaInt;
    }

	// Metodo: 			left
    // Argumentos:		String str 	- cadena de la que se extraerá la parte izquierda
    //					int n		- numero de caracteres a extraer.
    public static String left(String str, int n) {
        String strResultado = "";

		// Si pretendemos obtener mas caracteres que la longitud de la cadena,
        // devolvemos la cadena completa.
        if (str.length() > n) {
            strResultado = str.substring(0, n);
        } else {
            strResultado = str;
        }

        return strResultado;
    }

    /**
     * Devuelve la letra del NIF partir de un DNI.
     *
     * @param dni dni al que se quiere añadir la letra del NIF
     * @return NIF completo.
     */
    public static String letraDNI(long dni) {
        int indice = (int) (dni % (long) 23);

        return NIF_STRING_ASOCIATION.substring(indice, indice + 1);
    }

    public static String compruebaCifNif(String strCifNif, int pais, boolean verbose) {
        String strCifNifCorrecto = "";

        int numeroLetras = 0;
        strCifNif = strCifNif.trim().toUpperCase();

        if (pais == 0) {
            for (int i = 0; i < strCifNif.length(); i++) {
                if (Cadena.esLetra(strCifNif.charAt(i))) {
                    numeroLetras++;
                }
            }

            if ((numeroLetras != 1 || strCifNif.length() != 9) && verbose) {
                Apariencia.mensajeInformativo(5, "Formato de NIF incorrecto, revísalo!!!");
            } else {
                if (Cadena.esLetra(strCifNif.charAt(0))) {
                    strCifNifCorrecto = compruebaCif(strCifNif, verbose);
                }
                if (Cadena.esLetra(strCifNif.charAt(strCifNif.length() - 1))) {
                    strCifNifCorrecto = compruebaNif(strCifNif, verbose);
                }
            }
        } else {
            if (verbose) {
                Apariencia.mensajeInformativo(5, "Parece que estás introduciendo un NIF/CIF de otro pais.<BR>"
                        + "Asegurate de hacerlo correctamente!!!");
            }
        }

        return strCifNifCorrecto;
    }

    private static String compruebaCif(String strCif, boolean verbose) {
        String letrasPermitidasEnCif = "ABCDEFGHKLMNPQS";
        String caracterControl = "";
        List<Integer> provincias = Arrays.asList(1, 2, 3, 53, 54, 4, 5, 6, 7, 57,
                8, 58, 59, 60, 61, 62, 63, 64, 9, 10,
                11, 72, 12, 13, 14, 56, 15, 70, 16, 17,
                55, 18, 19, 20, 71, 21, 22, 23, 24, 25,
                26, 27, 28, 78, 79, 80, 81, 82, 83, 84,
                29, 92, 93, 30, 73, 31, 32, 33, 74, 34,
                35, 76, 36, 94, 37, 38, 75, 39, 40, 41,
                91, 42, 43, 77, 44, 45, 46, 96, 97, 98,
                47, 48, 95, 49, 50, 99, 51, 52);
        String strCifCorrecto = strCif;

        // Salcamos la letra inicial
        char ch = strCif.charAt(0);
        // Comprobamos si está entre las permitidas
        if (letrasPermitidasEnCif.indexOf(ch) == -1 && verbose) {
            Apariencia.mensajeInformativo(5, "Formato de CIF incorrecto, la primera letra no es correcta!!!");
        } else {
            // Si es así, tenemos que comprobar que el código de la provincia esté entre los permitidos
            int codigoProvincial = Integer.valueOf(strCif.substring(1, 3));
            if (!provincias.contains(codigoProvincial) && verbose) {
                Apariencia.mensajeInformativo(5, "Formato de CIF incorrecto, la provincia no es correcta!!!");
            } else {
					// Aquí ya sabemos que la provincia es correcta
                // Ejemplo : B22196414
                //           B           -> Tipo de empresa
                //            22         -> Codigo provincial
                //            2219641    -> Numero secuencial, dado por Hacienda
                //                   4   -> Dígito de control
                //
                // A = suma digitos pares; A = 2 + 9 + 4 = 15
                // B = 'suma' posiciones impares multiplicadas por 2
                // 2 * 2 = 4
                // 1 * 2 = 2
                // 6 * 2 = 12 -> 1 + 2 = 3
                // 1 * 2 = 2
                // B = 4 + 2 + 3 + 1 = 11
                // Tenemos que calcular el valor de C = A + B = 15 + 11 = 26
                // Cojemos el dígito de las unidades de C (6) y se lo restamos a 10
                // -> 10 - 6 = 4
                // Si el Nif ha de ser una letra (casos K, P, Q ó S), la letra es ABCD(E)FGHI -> E
                // 
                int a = 0;
                int b = 0;
                int bAux = 0;
                for (int i = 2; i < 7; i += 2) {
                    a += Integer.valueOf(strCif.substring(i, i + 1));
                }
                for (int i = 1; i < 8; i += 2) {
                    bAux = Integer.valueOf(strCif.substring(i, i + 1)) * 2;
                    if (bAux > 9) {
                        bAux = bAux - 9;
                    }
                    b += bAux;
                }

                int c = a + b;
                int unidades = c % 10;
                // Si la letra es K, P, Q ó S, el digito de control es una letra;
                if ("KPQS".indexOf(ch) == -1) {
                    // Tiene que ser número
                    caracterControl = String.valueOf(unidades);
                } else {
                    // Tiene que ser letra
                    caracterControl = String.valueOf("JABCDEFGHI".charAt(unidades));
                }
                if (!strCif.subSequence(8, 9).equals(caracterControl)) {
                    if (verbose) {
                        Apariencia.mensajeInformativo(5, "CIF incorrecto, el último caracter debe ser '" + caracterControl + "'");
                    }
                    strCifCorrecto = strCif.substring(0, 8) + caracterControl;
                }
            }
        }
        return strCifCorrecto;
    }

    private static String compruebaNif(String strNif, boolean verbose) {
        String letraNif = strNif.substring(8, 9);
        String letraNifCorrecta = "";
        long digitosNif = Long.valueOf(strNif.substring(0, 8));
        String strNifCorrecto = strNif;

        letraNifCorrecta = Cadena.letraDNI(digitosNif);

        if (!letraNif.equalsIgnoreCase(letraNifCorrecta)) {
            if (verbose) {
                Apariencia.mensajeInformativo(5, "NIF incorrecto, la letra debe ser '" + letraNifCorrecta + "'");
            }
            strNifCorrecto = strNif.substring(0, 8) + letraNifCorrecta;
        }

        return strNifCorrecto;
    }

    public static boolean esLetra(char ch) {
        boolean letraSiNo = false;

        if (ch >= 'A' && ch <= 'Z') {
            letraSiNo = true;
        }

        if (ch >= 'a' && ch <= 'z') {
            letraSiNo = true;
        }

        return letraSiNo;
    }

    public static boolean esNumero(char ch) {
        boolean numeroSiNo = false;

        if (ch >= '0' && ch <= '9') {
            numeroSiNo = true;
        }

        return numeroSiNo;
    }

    public static String formatoConComaDecimal(String strDouble) {

        String strDoubleFormateado = "";

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,##0.00", simbolos);
        strDoubleFormateado = myFormatter.format(Double.valueOf(strDouble));

        return strDoubleFormateado;
    }

    public static String formatoConComaDecimal(double doubleSinFormato) {

        String strDoubleFormateado = "";

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,##0.00", simbolos);
        strDoubleFormateado = myFormatter.format(doubleSinFormato);

        return strDoubleFormateado;
    }

    public static String formatoDobleEntero(double doubleSinFormato) {

        String strEntero = "";

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,##0", simbolos);
        strEntero = myFormatter.format((int) doubleSinFormato);

        if (strEntero.equalsIgnoreCase("0")) {
            strEntero = "";
        }

        return strEntero;
    }

    public static String enteroCerosIzquierda(int entero, int numeroDigitosTotales) {
        String strEnteroConCerosIzquierda = "";

        strEnteroConCerosIzquierda = String.format("%0" + numeroDigitosTotales + "d", entero);

        return strEnteroConCerosIzquierda;
    }

    public static String enteroCerosIzquierda(long entero, int numeroDigitosTotales) {
        String strEnteroConCerosIzquierda = "";

        strEnteroConCerosIzquierda = String.format("%0" + numeroDigitosTotales + "d", entero);

        return strEnteroConCerosIzquierda;
    }

    public static String numeroClienteACuentaContable(int codigoCliente) {
        String strCuentaContable = "43";

        strCuentaContable += Cadena.enteroCerosIzquierda(codigoCliente, 7);

        return strCuentaContable;
    }

    public static int cuentaContableANumeroCliente(String strCuenta) {
        int numeroCliente = -1;

        if (strCuenta.length() == 9) {
            numeroCliente = Integer.valueOf(strCuenta.substring(2, 8));
        }

        return numeroCliente;
    }

    public static String redondear2Decimales(double numero) {

        double finalValue = 0.0;

        String strDouble = "";

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat myFormatter = new DecimalFormat("########0.00", simbolos);
        strDouble = myFormatter.format(numero);       
        
        return strDouble;
    }
    
    public static String formatear2Decimales(double numero) {

        double finalValue = 0.0;

        String strDouble = "";

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,##0.00", simbolos);
        strDouble = myFormatter.format(numero);       
        
        return strDouble;
    }
    
    public static String formateaCuentaContable(String strCuenta){
        String strCuentaFormateada = "";
        
        // Tenemos una cuenta de grado 1? - > 1 - 3 dígitos
        if (strCuenta.length() <= 3) {
            strCuentaFormateada = strCuenta;
        } else {
            // Tenemos una Cuenta de grado 2? -> 4 - 5
            if (strCuenta.length() == 4 || strCuenta.length() == 5) {
                strCuentaFormateada = strCuenta.substring(0, 3) + " " + strCuenta.substring(3);
            } else {
                // Tenemos una cuenta con más de 5 dígitos
                strCuentaFormateada = strCuenta.substring(0, 3) + " " + strCuenta.substring(3, 5) + " " + strCuenta.substring(5);
            }
        }
        
        return strCuentaFormateada;
    }
}
