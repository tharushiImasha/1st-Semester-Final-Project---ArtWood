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
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.FinishedStockDto;
import lk.ijse.FianlArtWood.dto.tm.CustomerTm;
import lk.ijse.FianlArtWood.dto.tm.FinishedStockTm;
import lk.ijse.FianlArtWood.model.FinishedStockModel;
import lk.ijse.FianlArtWood.model.OwnerCustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FinishedStockController {
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

    @FXML
    private TextField txtAmountId;

    @FXML
    private TextField txtProductId;

    public void initialize() {
        setCellValueFactory();
        loadAllFinishedStock();
        generateNextFinishedId();
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
        txtProductId.setText("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String finished_id = lblId.getText();
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
        String finished_id = lblId.getText();
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

}
