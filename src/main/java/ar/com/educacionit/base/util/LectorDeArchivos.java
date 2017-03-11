/*
 * LectorDeArchivos.java
 *
 */

package ar.com.educacionit.base.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class LectorDeArchivos {
    
    /** Creates a new instance of LectorDeArchivos */
    public LectorDeArchivos() {
    }
    
    public static List obtenerContenidoDeArchivoComoVector(String archivoALeer) throws Exception {
        
        // Obtiene un stream al archivo a leer
        InputStreamReader isr = new InputStreamReader( LectorDeArchivos.class.getClassLoader().getResourceAsStream(archivoALeer) );
        
        // Construye un BufferedReader
        BufferedReader readerMejorado = new BufferedReader( isr );
        
        // Define variables
        boolean eof = false;
        String lineaLeida = "";
        ArrayList items = new ArrayList();
        
        // Lee el archivo de forma eficiente y guarda la informacion leida
        while ( !eof )
        {
            // Lee una linea entera
            lineaLeida = readerMejorado.readLine();
            
            // Guarda la linea
            if( lineaLeida != null )
                items.add(lineaLeida);
            
            // Si llega al final del archivo, termina la ejecuci�n
            else
                eof = true;
        }

        // Cierra el BufferedReader
        readerMejorado.close();

        // Retorna la coleccion
        return items;
        
    }
            
    
}
