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
import lk.ijse.FianlArtWood.dto.LogsDto;
import lk.ijse.FianlArtWood.dto.tm.LogsTm;
import lk.ijse.FianlArtWood.model.LogsStockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogsStockController {
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
    private TextField txtId;

    @FXML
    private TextField txtType;

    public void initialize() {
        setCellValueFactory();
        loadAllLogs();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("logs_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("log_amount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("wood_type"));
    }

    private void loadAllLogs() {
        var model = new LogsStockModel();

        ObservableList<LogsTm> obList = FXCollections.observableArrayList();

        try {
            List<LogsDto> dtoList;
            dtoList = model.getAllLogs();

            for(LogsDto dto : dtoList) {
                obList.add(new LogsTm(dto.getLogs_id(), dto.getWood_type(), dto.getLog_amount()));
            }

            tblLog.setItems(obList);
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
        txtId.setText("");
        txtAmount.setText("");
        txtType.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new LogsStockModel();
        try {
            boolean isDeleted = model.deleteLogs(id);

            if(isDeleted) {
                tblLog.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "log deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
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
        String id = txtId.getText();
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

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new LogsStockModel();
        try {
            LogsDto dto = model.searchLogs(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(LogsDto dto) {
        txtId.setText(dto.getLogs_id());
        txtAmount.setText(String.valueOf(dto.getLog_amount()));
        txtType.setText(dto.getWood_type());
    }
}
