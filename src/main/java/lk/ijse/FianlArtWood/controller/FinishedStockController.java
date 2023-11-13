package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.FinishedStockDto;
import lk.ijse.FianlArtWood.dto.tm.FinishedStockTm;
import lk.ijse.FianlArtWood.model.FinishedStockModel;
import lk.ijse.FianlArtWood.model.OwnerCustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FinishedStockController {
    @FXML
    private TableColumn<?, ?> colAmountId;

    @FXML
    private TableColumn<?, ?> colFinishedId;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<FinishedStockTm> tblFinished;

    @FXML
    private TextField txtAmountId;

    @FXML
    private TextField txtFinishedId;

    @FXML
    private TextField txtProductId;

    public void initialize() {
        setCellValueFactory();
        loadAllFinishedStock();
    }

    private void setCellValueFactory() {
        colFinishedId.setCellValueFactory(new PropertyValueFactory<>("finished_id"));
        colAmountId.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
    }

    private void loadAllFinishedStock() {
        var model = new FinishedStockModel();

        ObservableList<FinishedStockTm> obList = FXCollections.observableArrayList();

        try {
            List<FinishedStockDto> dtoList;
            dtoList = model.getAllFinishedStock();

            for(FinishedStockDto dto : dtoList) {
                obList.add(new FinishedStockTm(dto.getFinished_id(), dto.getAmount(), dto.getProduct_id()));
            }

            tblFinished.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_pane.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Owner Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    void clearFields() {
        txtAmountId.setText("");
        txtFinishedId.setText("");
        txtProductId.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtFinishedId.getText();

        var model = new FinishedStockModel();
        try {
            boolean isDeleted = model.deleteFinished(id);

            if(isDeleted) {
                tblFinished.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "finished stock deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String finished_id = txtFinishedId.getText();
        int amount = Integer.parseInt(txtAmountId.getText());
        String product_type = txtProductId.getText();

        var dto = new FinishedStockDto(finished_id, amount, product_type);

        var model = new FinishedStockModel();
        try {
            boolean isSaved = model.saveFinished(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "finished stock saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String finished_id = txtFinishedId.getText();
        int amount = Integer.parseInt(txtAmountId.getText());
        String product_id = txtProductId.getText();

        var dto = new FinishedStockDto(finished_id, amount, product_id);

        var model = new FinishedStockModel();
        try {
            boolean isUpdated = model.updateFinished(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "finished stock updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtFinishedIdOnAction(ActionEvent event) {
        String id = txtFinishedId.getText();

        var model = new FinishedStockModel();
        try {
            FinishedStockDto dto = model.searchFinished(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "stock not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(FinishedStockDto dto) {
        txtProductId.setText(dto.getProduct_id());
        txtAmountId.setText(String.valueOf(dto.getAmount()));
        txtFinishedId.setText(dto.getFinished_id());

    }
}
