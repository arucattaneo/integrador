/*
 * AdministradorDeConexiones.java
 *
 *
 */

package ar.com.educacionit.vehiculos.util;

import java.sql.Connection;
import java.sql.DriverManager;


public abstract class AdministradorDeConexiones {
    
    /**
     * Creates a new instance of AdministradorDeConexiones
     */
    public AdministradorDeConexiones() {
    }

    public static Connection getConnection() throws Exception
    {

        // Establece la conexion a utilizar contra la base de datos
        String dbConnString = "jdbc:mysql://127.0.0.1/j2se"; //"jdbc:h2:~/j2se;INIT=RUNSCRIPT FROM 'classpath:ddl/autos-h2.sql'";
        
        // Establece el usuario de la base de datos
        String dbUser = "root";
        
        // Establece la contraseña de la base de datos
        String dbPassword = "";

        // Retorna la conexion
        return DriverManager.getConnection(dbConnString, dbUser, dbPassword);
    }    
    
}
