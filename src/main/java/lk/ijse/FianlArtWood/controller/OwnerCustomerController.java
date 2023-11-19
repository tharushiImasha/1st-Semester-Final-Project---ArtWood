package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.tm.CustomerTm;
import lk.ijse.FianlArtWood.model.OwnerCustomerModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OwnerCustomerController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblCusId;

    @FXML
    private TextField txtName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    private final OwnerCustomerModel customerModel = new OwnerCustomerModel();

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        generateNextCustomerId();
        setListener();
    }

    private void generateNextCustomerId() {
        try {
            String orderId = OwnerCustomerModel.generateNextCustomerId();
            lblCusId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllCustomers() {

        try {
           List<CustomerDto> dtoList = OwnerCustomerModel.getAllCustomers();

           ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

            for(CustomerDto dto : dtoList){
               Button btn = new Button("Remove");
               btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblCustomer.getSelectionModel().getFocusedIndex();
                        String id = (String) colId.getCellData(index);

                        deleteCustomer(id);

                        obList.remove(index);
                        tblCustomer.refresh();
                    }

                });

                var tm = new CustomerTm(dto.getId(), dto.getName(), dto.getAddress(), btn);

                obList.add(tm);

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
        txtName.setText("");
        txtAddress.setText("");
    }

    private void deleteCustomer(String id){
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
        String id = lblCusId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        var dto = new CustomerDto(id, name, address);

        var model = new OwnerCustomerModel();
        try {
            boolean isSaved = model.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                tblCustomer.refresh();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblCusId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        var dto = new CustomerDto(id, name, address);

        var model = new OwnerCustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                tblCustomer.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setListener() {
        tblCustomer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new CustomerDto(
                            newValue.getId(),
                            newValue.getName(),
                            newValue.getAddress()
                    );
                    setFields(dto);
                });
    }

    private void setFields(CustomerDto dto) {
        lblCusId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
    }

}
