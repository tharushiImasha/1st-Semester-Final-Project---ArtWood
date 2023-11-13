package lk.ijse.FianlArtWood.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.EmployeeDto;
import lk.ijse.FianlArtWood.model.OwnerEmployeeModel;

import java.io.IOException;
import java.sql.SQLException;

public class OwnerEmployeeController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    private void clearFields() {
        txtAddress.setText("");
        txtId.setText("");
        txtJobRole.setText("");
        txtName.setText("");
        txtTel.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String emp_id = txtId.getText();
        var editModel = new OwnerEmployeeModel();
        try {
            boolean isDeleted = editModel.deleteEmployee(emp_id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "employee not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String emp_id = txtId.getText();
        String name = txtName.getText();
        int tel = Integer.parseInt(txtTel.getText());
        String address = txtAddress.getText();
        String job_role = txtJobRole.getText();

        var dto = new EmployeeDto(emp_id, name, address, tel, job_role);

        var model = new OwnerEmployeeModel();
        try {
            boolean isSaved = model.saveUser(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                clearFields();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee not saved!").show();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String emp_id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        int tel = Integer.parseInt(txtTel.getText());
        String job_role = txtJobRole.getText();

        var dto = new EmployeeDto(emp_id,name,address,tel,job_role);

        var model = new OwnerEmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "user updated!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "user not updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String emp_id = txtId.getText();

        var model = new OwnerEmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(emp_id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(EmployeeDto dto) {
        txtId.setText(dto.getEmp_id());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(String.valueOf(dto.getTel()));
        txtJobRole.setText(dto.getJob_role());
    }
}
