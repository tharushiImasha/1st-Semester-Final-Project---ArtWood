package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.FianlArtWood.dto.SupplierDto;
import lk.ijse.FianlArtWood.dto.tm.SupplierTm;
import lk.ijse.FianlArtWood.model.OwnerSupplierModel;

import java.sql.SQLException;
import java.util.List;

public class OwnerSupplierController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
        setListener();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("sup_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllSuppliers() {
        var model = new OwnerSupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList;
            dtoList = model.getAllSuppliers();

            for(SupplierDto dto : dtoList) {
                obList.add(new SupplierTm(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail()));
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var supModel = new OwnerSupplierModel();
        try {
            boolean isDeleted = supModel.deleteSupplier(id);

            if(isDeleted) {
                tblSupplier.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        var dto = new SupplierDto(id, name, address, email);

        var model = new OwnerSupplierModel();
        try {
            boolean isSaved = model.saveSupplier(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        var dto = new SupplierDto(id, name, address, email);

        var model = new OwnerSupplierModel();
        try {
            boolean isUpdated = model.updateSupplier(dto);
            System.out.println(isUpdated);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new OwnerSupplierModel();
        try {
            SupplierDto dto = model.searchSupplier(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(SupplierDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtEmail.setText(dto.getEmail());
    }

    private void setListener() {
        tblSupplier.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new SupplierDto(
                            newValue.getSup_id(),
                            newValue.getSup_name(),
                            newValue.getAddress(),
                            newValue.getEmail()
                    );
                    setFields(dto);
                });
    }

    private void setFields(SupplierDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtEmail.setText(dto.getEmail());
    }

}
