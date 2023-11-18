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
import lk.ijse.FianlArtWood.dto.LogsDto;
import lk.ijse.FianlArtWood.dto.tm.CustomerTm;
import lk.ijse.FianlArtWood.dto.tm.LogsTm;
import lk.ijse.FianlArtWood.model.LogsStockModel;
import lk.ijse.FianlArtWood.model.OwnerCustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LogsStockController {
    @FXML
    private Label lblId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<LogsTm> tblLog;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtType;

    public void initialize() {
        setCellValueFactory();
        loadAllLogs();
        generateNextCustomerId();
    }

    private void generateNextCustomerId() {
        try {
            String logId = LogsStockModel.generateNextLogId();
            lblId.setText(logId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("logs_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("log_amount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("wood_type"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllLogs() {
        try {
            List<LogsDto> dtoList = LogsStockModel.getAllLogs();

            ObservableList<LogsTm> obList = FXCollections.observableArrayList();

            for(LogsDto dto : dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblLog.getSelectionModel().getFocusedIndex();
                        String id = (String) colId.getCellData(index);

                        deleteLog(id);

                        obList.remove(index);
                        tblLog.refresh();
                    }

                });

                var tm = new LogsTm(dto.getLogs_id(), dto.getWood_type(), dto.getLog_amount(), btn);

                obList.add(tm);

            }

            tblLog.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteLog(String id) {
        try {
            boolean isDeleted = LogsStockModel.deleteLogs(id);

            if(isDeleted) {
                tblLog.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Log deleted!").show();
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
        txtAmount.setText("");
        txtType.setText("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblId.getText();
        String type = txtType.getText();
        int amount = Integer.parseInt(txtAmount.getText());

        var dto = new LogsDto(id, type, amount);

        var model = new LogsStockModel();
        try {
            boolean isSaved = model.saveLogs(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "logs saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblId.getText();
        String type = txtType.getText();
        int amount = Integer.parseInt(txtAmount.getText());

        var dto = new LogsDto(id, type, amount);

        var model = new LogsStockModel();
        try {
            boolean isUpdated = model.updateLogs(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
