package ar.com.educacionit.vehiculos.ventanas.controladores;

import ar.com.educacionit.vehiculos.entidades.Auto;
import ar.com.educacionit.vehiculos.util.AdministradorDeConexiones;
import ar.com.educacionit.vehiculos.ventanas.Ventanas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class AutosVentanaControlador extends ConcesionariaControlador implements Initializable {

    @FXML
    private Label lbl_alto_valor;

    @FXML
    private Label lbl_ancho_valor;

    @FXML
    private Label lbl_largo_valor;

    @FXML
    private Label lbl_marca_valor;

    @FXML
    private Label lbl_modelo_valor;

    @FXML
    private Label lbl_color_valor;

    @FXML
    private Button btn_cerrar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_borrar;

    @FXML
    private Label lbl_precio_valor;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private TableView<Auto> tbl_autos;

    @FXML
    private TableColumn<Auto, String> clmn_marca;

    @FXML
    private TableColumn<Auto, String> clmn_modelo;

    private ObservableList<Auto> autosData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_editar.setDisable(true);
        btn_borrar.setDisable(true);
        txt_descripcion.setEditable(false);

        // Initialize the person table with the two columns.
        clmn_marca.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        clmn_modelo.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());

        // Listener para detectar el cambio de seleccion (cuando selecciono un item completa el formulario de al lado)
        tbl_autos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> cargarAutoSeleccionado(newValue));

        tbl_autos.setItems(autosData);
        llenarTablaDeAutos();

    }

    private void cargarAutoSeleccionado(Auto a) {
        if (a != null) {
            this.lbl_alto_valor.setText(String.valueOf(a.getAltura()));
            this.lbl_ancho_valor.setText(String.valueOf(a.getAncho()));
            this.lbl_largo_valor.setText(String.valueOf(a.getLargo()));
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

    @FXML
    private void autoNuevo(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        Auto autoNuevo = new Auto();
        
        super.mainApp.mostrarVentanaModal(Ventanas.AutosEdicion, stage, autoNuevo);
        if (autoNuevo.estaModificado()) {
            try {
                Connection conn = AdministradorDeConexiones.getConnection();
                autoNuevo.setId(autoNuevo.insertar(conn));
                conn.close();
                autosData.add(autoNuevo);
                tbl_autos.getSelectionModel().select(autoNuevo);
            } catch (Exception ex) {
                // Muestra el mensaje de error
                super.mostrarMensajeDeError("Error al conectarse con la base de datos", ex);
            }
        }
    }

    @FXML
    private void editarAuto(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        Auto a = tbl_autos.getSelectionModel().getSelectedItem();
        super.mainApp.mostrarVentanaModal(Ventanas.AutosEdicion, stage, a);
        try {
            Connection conn = AdministradorDeConexiones.getConnection();
            a.actualizar(conn);
            conn.close();
            cargarAutoSeleccionado(a);
        } catch (Exception ex) {
            // Muestra el mensaje de error
            super.mostrarMensajeDeError("Error al conectarse con la base de datos", ex);
        }

    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void eliminarAuto() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Esta a punto de eliminar el Auto...");
        alert.setContentText("desea continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Auto a = tbl_autos.getSelectionModel().getSelectedItem();

            try {
                Connection conn = AdministradorDeConexiones.getConnection();
                a.eliminar(conn);
                conn.close();
                int selectedIndex = tbl_autos.getSelectionModel().getSelectedIndex();
                tbl_autos.getItems().remove(selectedIndex);
            } catch (Exception ex) {
                // Muestra el mensaje de error
                super.mostrarMensajeDeError("Error al conectarse con la base de datos", ex);
            }

        }
    }

    private void llenarTablaDeAutos() {
        try {
            // Obtiene la conexion
            Connection conn = AdministradorDeConexiones.getConnection();
            Auto.obtenerTodos(conn).forEach(a -> autosData.add(a));
        } catch (Exception ex) {
            Logger.getLogger(AutosVentanaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
