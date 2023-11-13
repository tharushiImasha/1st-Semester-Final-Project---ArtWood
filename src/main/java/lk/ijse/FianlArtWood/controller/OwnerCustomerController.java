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
import javafx.scene.layout.AnchorPane;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.tm.CustomerTm;
import lk.ijse.FianlArtWood.model.OwnerCustomerModel;

import java.sql.SQLException;
import java.util.List;

public class OwnerCustomerController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadAllCustomers() {
        var model = new OwnerCustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList;
            dtoList = model.getAllCustomers();

            for(CustomerDto dto : dtoList) {
                obList.add(new CustomerTm(dto.getId(), dto.getName(), dto.getAddress()));
            }

            tblCustomer.setItems(obList);
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
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var customerModel = new OwnerCustomerModel();
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);

            if(isDeleted) {
                tblCustomer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
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

        var dto = new CustomerDto(id, name, address);

        var model = new OwnerCustomerModel();
        try {
            boolean isSaved = model.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
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

        var dto = new CustomerDto(id, name, address);

        var model = new OwnerCustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new OwnerCustomerModel();
        try {
            CustomerDto dto = model.searchCustomer(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(CustomerDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
    }
}
