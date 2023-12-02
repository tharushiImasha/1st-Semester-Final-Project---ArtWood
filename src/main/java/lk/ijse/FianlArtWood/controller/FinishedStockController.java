package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.EmployeeDto;
import lk.ijse.FianlArtWood.dto.FinishedStockDto;
import lk.ijse.FianlArtWood.dto.ProductTypeDto;
import lk.ijse.FianlArtWood.dto.tm.FinishedStockTm;
import lk.ijse.FianlArtWood.model.FinishedStockModel;
import lk.ijse.FianlArtWood.model.OwnerEmployeeModel;
import lk.ijse.FianlArtWood.model.OwnerProductTypeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FinishedStockController {
    @FXML
    private ComboBox<String> cmbProductId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblId;

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


    public void initialize() {
        setCellValueFactory();
        loadAllFinishedStock();
        generateNextFinishedId();
        setListener();
        loadProductId();
    }

    private void loadProductId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ProductTypeDto> list = OwnerProductTypeModel.getAllProduct();

            for (ProductTypeDto dto : list) {
                obList.add(dto.getProduct_id());
            }

            cmbProductId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextFinishedId() {
        try {
            String finishedId = FinishedStockModel.generateNextFinishedId();
            lblId.setText(finishedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colFinishedId.setCellValueFactory(new PropertyValueFactory<>("finished_id"));
        colAmountId.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllFinishedStock() {
        try {
            List<FinishedStockDto> dtoList = FinishedStockModel.getAllFinishedStock();

            ObservableList<FinishedStockTm> obList = FXCollections.observableArrayList();

            for(FinishedStockDto dto : dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblFinished.getSelectionModel().getFocusedIndex();
                        String id = (String) colFinishedId.getCellData(index);

                        deleteFinishedItem(id);

                        obList.remove(index);
                        tblFinished.refresh();
                    }

                });

                var tm = new FinishedStockTm(dto.getFinished_id(), dto.getAmount(), dto.getProduct_id(), btn);

                obList.add(tm);

            }

            tblFinished.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFinishedItem(String id) {
        try {
            boolean isDeleted = FinishedStockModel.deleteFinished(id);

            if(isDeleted) {
                tblFinished.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Finished item deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.close();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    void clearFields() {
        cmbProductId.setValue("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

            String finished_id = lblId.getText();
            int amount = 0;
            String product_type = cmbProductId.getValue();

            var dto = new FinishedStockDto(finished_id, amount, product_type);

            var model = new FinishedStockModel();
            try {

                boolean isSaved = model.saveFinished(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "finished stock saved!").show();
                    clearFields();

                    tblFinished.refresh();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String finished_id = lblId.getText();
        int amount = 0;
        String product_id = cmbProductId.getValue();

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

    private void setListener() {
        tblFinished.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new FinishedStockDto(
                            newValue.getFinished_id(),
                            newValue.getAmount(),
                            newValue.getProduct_id()

                    );
                    setFields(dto);
                });
    }

    private void setFields(FinishedStockDto dto) {
        lblId.setText(dto.getFinished_id());
        cmbProductId.setValue(dto.getProduct_id());
    }

}
