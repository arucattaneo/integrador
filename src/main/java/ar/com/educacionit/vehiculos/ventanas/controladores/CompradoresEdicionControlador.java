/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.educacionit.vehiculos.ventanas.controladores;

import ar.com.educacionit.vehiculos.entidades.Comprador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class CompradoresEdicionControlador extends EdicionControlador implements Initializable {
    
    @FXML
    private Button btn_cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setearEntidad(Object entidad) {
        Comprador c = (Comprador) entidad;
        this.comprador = c;
        if (c.getId() != null) {
            fld_alto.setText(String.valueOf(a.getAltura()));
            fld_ancho.setText(String.valueOf(a.getAncho()));
            fld_largo.setText(String.valueOf(a.getLargo()));
            cmb_marca.setValue(a.getMarca());
            cmb_modelo.setValue(a.getModelo());
            cmb_color.setValue(a.getColor());
            fld_precio.setText(String.valueOf(a.getPrecio()));
            txt_equipamiento.setText(a.getEquipamiento());
        }
    }
    
}
