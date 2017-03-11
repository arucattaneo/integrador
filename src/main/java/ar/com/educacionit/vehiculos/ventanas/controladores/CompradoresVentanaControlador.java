package ar.com.educacionit.vehiculos.ventanas.controladores;

import ar.com.educacionit.vehiculos.entidades.Auto;
import ar.com.educacionit.vehiculos.entidades.Comprador;
import ar.com.educacionit.vehiculos.util.AdministradorDeConexiones;
import ar.com.educacionit.vehiculos.ventanas.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class CompradoresVentanaControlador extends ConcesionariaControlador implements Initializable {

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_new;

    @FXML
    private Button btn_edit;

    @FXML
    private Button btn_delete;
    
    
    @FXML
    private TableColumn<Comprador, String> clmn_nombre;

    @FXML
    private TableColumn<Comprador, String> clmn_apellido;
    
    @FXML
    private TableView<Auto> tbl_compradores;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_edit.setDisable(true);
        btn_delete.setDisable(true);
        
        clmn_nombre.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
        clmn_apellido.setCellValueFactory(cellData->cellData.getValue().apellidoProperty());
        
        tbl_compradores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> cargarCompradoresSeleccionado(newValue));
        
        tbl_compradores.setItems(compradoresData);
        llenarTablaDeCompradores();
    }
    
    private void cargarCompradoresSeleccionado(Comprador c) {
        if (c != null) {
            this.lbl_alto_valor.setText(String.valueOf(c.getAltura()));
            this.lbl_ancho_valor.setText(String.valueOf(c.getAncho()));
            this.lbl_largo_valor.setText(String.valueOf(c.getLargo()));
            this.lbl_marca_valor.setText(a.getMarca());
            this.lbl_modelo_valor.setText(a.getModelo());
            this.lbl_color_valor.setText(a.getColor());
            this.lbl_precio_valor.setText(String.valueOf(a.getPrecio()));
            this.txt_descripcion.setText(a.getEquipamiento());
        } else {
            this.lbl_alto_valor.setText("");
            this.lbl_ancho_valor.setText("");
            this.lbl_largo_valor.setText("");
            this.lbl_marca_valor.setText("");
            this.lbl_modelo_valor.setText("");
            this.lbl_color_valor.setText("");
            this.lbl_precio_valor.setText("");
            this.txt_descripcion.setText("");
        }
        btn_editar.setDisable(false);
        btn_borrar.setDisable(false);
    }
    /* public void initialize(URL url, ResourceBundle rb) {
        btn_editar.setDisable(true);
        btn_borrar.setDisable(true);
        txt_descripcion.setEditable(false);

        // Initialize the person table with the two columns.
        clmn_marca.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        clmn_modelo.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());

        // Listener para detectar el cambio de seleccion
        tbl_autos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> cargarAutoSeleccionado(newValue));

        tbl_autos.setItems(autosData);
        llenarTablaDeAutos();
    }*/
    @FXML
    private void abrirEdicion(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        super.mainApp.mostrarVentanaModal(Ventanas.CompradoresEdicion, stage, null);
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
    
    private void llenarTablaDeCompradores() {
        try {
            // Obtiene la conexion
            Connection conn = AdministradorDeConexiones.getConnection();

            // Caso #1 -- Obtener compradores, e informarlos
            ArrayList compradores = Comprador.obtenerTodos(conn);
            Iterator it = compradores.iterator();
            while (it.hasNext()) {
                Comprador c = (Comprador) it.next();
                compradores.add(c);
            }
        } catch (Exception ex) {
            Logger.getLogger(CompradoresVentanaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
