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
import lk.ijse.FianlArtWood.dto.ProductTypeDto;
import lk.ijse.FianlArtWood.dto.tm.ProductTypeTm;
import lk.ijse.FianlArtWood.model.OwnerProductTypeModel;

import java.sql.SQLException;
import java.util.List;

public class OwnerProductTypeController {
    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQuality;

    @FXML
    private TableView<ProductTypeTm> tblProduct;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuality;

    public void initialize() {
        setCellValueFactory();
        loadAllProducts();
        setListener();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        colQuality.setCellValueFactory(new PropertyValueFactory<>("quality"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadAllProducts() {
        var model = new OwnerProductTypeModel();

        ObservableList<ProductTypeTm> obList = FXCollections.observableArrayList();

        try {
            List<ProductTypeDto> dtoList;
            dtoList = model.getAllProduct();

            for(ProductTypeDto dto : dtoList) {
                obList.add(new ProductTypeTm(dto.getProduct_id(), dto.getProduct_name(), dto.getQuality(), dto.getPrice()));
            }

            tblProduct.setItems(obList);
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
        txtPrice.setText("");
        txtQuality.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new OwnerProductTypeModel();
        try {
            boolean isDeleted = model.deleteProduct(id);

            if(isDeleted) {
                tblProduct.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "product deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String quality = txtQuality.getText();
        double price = Double.parseDouble(txtPrice.getText());

        var dto = new ProductTypeDto(id, name, quality, price);

        var model = new OwnerProductTypeModel();
        try {
            boolean isSaved = model.saveProduct(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "product saved!").show();
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
        String quality = txtQuality.getText();
        double price = Double.parseDouble(txtPrice.getText());

        var dto = new ProductTypeDto(id, name, quality, price);

        var model = new OwnerProductTypeModel();
        try {
            boolean isUpdated = model.updateProduct(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new OwnerProductTypeModel();
        try {
            ProductTypeDto dto = model.searchProduct(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Product not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(ProductTypeDto dto) {
        txtId.setText(dto.getProduct_id());
        txtName.setText(dto.getProduct_name());
        txtQuality.setText(dto.getQuality());
        txtPrice.setText(String.valueOf(dto.getPrice()));
    }

    private void setListener() {
        tblProduct.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new ProductTypeDto(
                            newValue.getProduct_id(),
                            newValue.getProduct_name(),
                            newValue.getQuality(),
                            newValue.getPrice()
                    );
                    setFields(dto);
                });
    }

    private void setFields(ProductTypeDto dto) {
        txtId.setText(dto.getProduct_id());
        txtName.setText(dto.getProduct_name());
        txtQuality.setText(dto.getQuality());
        txtPrice.setText(String.valueOf(dto.getPrice()));
    }

}
