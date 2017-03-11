/*
 * Auto.java
 *
 */
package ar.com.educacionit.vehiculos.entidades;

import ar.com.educacionit.base.entidades.Vehiculo;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Auto extends Vehiculo {

    // Atributos
    private Integer id;
    private StringProperty marca;
    private StringProperty modelo;
    private StringProperty color;
    private StringProperty equipamiento;
    private DoubleProperty precio;
    private boolean modificado = false;
    
    /**
     * Creates a new instance of Auto
     */
    public Auto() {
        this(null, null, null, 0, 0, 0, null, 0D);
    }

    public Auto(String marca, String modelo, String color, Integer largo, Integer ancho, Integer altura, String equipamiento, Double precio) {
        super(ancho, largo, altura);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.color = new SimpleStringProperty(color);
        this.equipamiento = new SimpleStringProperty(equipamiento);
        this.precio = new SimpleDoubleProperty(precio);
    }

    public String toString() {
        return marca.get() + " " + modelo.get() + " " + color.get() + " [" + super.toString() + "]";
    }

    public boolean estaModificado() {
        return modificado;
    }

    public void setModificado() {
        this.modificado = true;
    }

    
    
    public String getMarca() {
        return marca.get();
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public String getModelo() {
        return modelo.get();
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public StringProperty modeloProperty() {
        return modelo;
    }

    public StringProperty marcaProperty() {
        return marca;
    }

    public StringProperty equipamientoProperty() {
        return equipamiento;
    }

    public String getEquipamiento() {
        return equipamiento.get();
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento.set(equipamiento);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public Double getPrecio() {
        return precio.get();
    }

    public void setPrecio(Double precio) {
        this.precio.set(precio);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static List<Auto> obtenerTodos(Connection conn) throws Exception {
        // Arma la consulta y la ejecuta
        String laConsulta = "SELECT * FROM autos";
        Statement stmtConsulta = conn.createStatement();
        ResultSet rs = stmtConsulta.executeQuery(laConsulta);

        // Informa la insercion a realizar
        System.out.println(">>SQL: " + laConsulta);

        // Construye la coleccion de autos
        List<Auto> autos = new ArrayList<>();

        // Muestra los datos
        while (rs.next()) {
            // Arma el objeto auto con los datos de la consulta
            Auto a = new Auto();
            a.setId(rs.getInt("au_id"));
            a.setMarca(rs.getString("au_marca"));
            a.setModelo(rs.getString("au_modelo"));
            a.setPrecio(rs.getDouble("au_precio"));
            a.setColor(rs.getString("au_color"));
            a.setLargo(rs.getInt("au_largo"));
            a.setAncho(rs.getInt("au_ancho"));
            a.setAltura(rs.getInt("au_altura"));
            a.setEquipamiento(rs.getString("au_equipamiento"));

            // Agrega el auto a la coleccion
            autos.add(a);
        }
        // Cierra el Statement
        stmtConsulta.close();

        // Retorna la coleccion
        return autos;
    }

    public Integer insertar(Connection conn) throws Exception {
        // Arma la sentencia de inserción
        String laInsercion = "INSERT INTO autos "
                + "(au_marca, au_modelo, au_precio, au_color, au_largo, au_ancho, au_altura, au_equipamiento) "
                + " VALUES (?,?,?,?,?,?,?,?)";

        // Informa la insercion a realizar
        System.out.println(">>SQL: " + laInsercion);

        // Ejecuta la insercion
        PreparedStatement ps = conn.prepareStatement(laInsercion, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getMarca());
        ps.setString(2, getModelo());
        ps.setDouble(3, getPrecio());
        ps.setString(4, getColor());
        ps.setInt(5, getLargo());
        ps.setInt(6, getAncho());
        ps.setInt(7, getAltura());
        ps.setString(8, getEquipamiento());
        ps.execute();
        // Obtiene el ID generado
        Integer id = null;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        // Cierra el Statement
        ps.close();
        return id;
    }

    public void actualizar(Connection conn) throws Exception {
        // Arma la sentencia de actualizacion
        String laActualizacion = "UPDATE autos "
                + "SET au_marca = ?, au_modelo = ?, "
                + "au_precio = ?, au_color = ?, "
                + "au_largo = ?, au_ancho = ?, "
                + "au_altura = ?, "
                + "au_equipamiento = ? "
                + "WHERE au_id = ?";

        // Informa la actualizacion a realizar
        System.out.println(">>SQL: " + laActualizacion);

        // Ejecuta la actualizacion
        PreparedStatement ps = conn.prepareStatement(laActualizacion);
        ps.setString(1, getMarca());
        ps.setString(2, getModelo());
        ps.setDouble(3, getPrecio());
        ps.setString(4, getColor());
        ps.setInt(5, getLargo());
        ps.setInt(6, getAncho());
        ps.setInt(7, getAltura());
        ps.setString(8, getEquipamiento());
        ps.setInt(9, getId());
        ps.execute();

        // Cierra el Statement
        ps.close();

    }

    public void eliminar(Connection conn) throws Exception {
        // Arma la sentencia de eliminacion
        String laEliminacion = "DELETE FROM autos WHERE au_id = " + getId();

        // Informa la eliminacion a realizar
        System.out.println(">>SQL: " + laEliminacion);

        // Ejecuta la eliminacion
        Statement stmtEliminacion = conn.createStatement();
        stmtEliminacion.execute(laEliminacion);

        // Cierra el Statement
        stmtEliminacion.close();
    }

}
