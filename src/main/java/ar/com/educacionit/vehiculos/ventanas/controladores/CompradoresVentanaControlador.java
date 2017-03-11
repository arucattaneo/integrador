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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class CompradoresVentanaControlador extends ConcesionariaControlador implements Initializable {

     @FXML
    private Button btn_edit;

    @FXML
    private Button btn_delete;
    
    @FXML
    private Button btn_close;

    @FXML
    private Button btn_new;
    
    @FXML
    private Label lbl_nombre;
    
    @FXML
    private Label lbl_apellido;
    
    @FXML
    private Label lbl_documento;
    
    @FXML
    private Label lbl_presupuesto;
    
    @FXML
    private TableColumn<Comprador, String> clmn_nombre;

    @FXML
    private TableColumn<Comprador, String> clmn_apellido;
    
    @FXML
    private TableView<Comprador> tbl_compradores;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    private ObservableList<Comprador> compradoresData = FXCollections.observableArrayList();
    
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
            this.lbl_nombre.setText(c.getNombre());
            this.lbl_apellido.setText(c.getApellido());
            this.lbl_documento.setText(c.getNumeroDocumento());
            this.lbl_presupuesto.setText(String.valueOf(c.getPresupuesto()));
            
        } else {
            this.lbl_nombre.setText("");
            this.lbl_apellido.setText("");
            this.lbl_documento.setText("");
            this.lbl_presupuesto.setText("");

        }
        btn_edit.setDisable(false);
        btn_delete.setDisable(false);
    }

    @FXML
    private void abrirEdicion(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        super.mainApp.mostrarVentanaModal(Ventanas.CompradoresEdicion, stage, null);
    }

    @FXML
    private void editarComprador(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        Comprador c = tbl_compradores.getSelectionModel().getSelectedItem();
        super.mainApp.mostrarVentanaModal(Ventanas.CompradoresEdicion, stage, c);
        try {
            Connection conn = AdministradorDeConexiones.getConnection();
            c.actualizar(conn);
            conn.close();
            cargarCompradoresSeleccionado(c);
        } catch (Exception ex) {
            // Muestra el mensaje de error
            super.mostrarMensajeDeError("Error al conectarse con la base de datos", ex);
        }

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
            Comprador.obtenerTodos(conn).forEach(c -> compradoresData.add(c));
        } catch (Exception ex) {
            Logger.getLogger(AutosVentanaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    private void eliminarComprador() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Esta a punto de eliminar al comprador...");
        alert.setContentText("desea continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Comprador c = tbl_compradores.getSelectionModel().getSelectedItem();

            try {
                Connection conn = AdministradorDeConexiones.getConnection();
                c.eliminar(conn);
                conn.close();
                int selectedIndex = tbl_compradores.getSelectionModel().getSelectedIndex();
                tbl_compradores.getItems().remove(selectedIndex);
            } catch (Exception ex) {
                // Muestra el mensaje de error
                super.mostrarMensajeDeError("Error al conectarse con la base de datos", ex);
            }

        }
    }
}
