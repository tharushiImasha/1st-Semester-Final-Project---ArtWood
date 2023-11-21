package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.LogsDto;
import lk.ijse.FianlArtWood.dto.OrderDto;
import lk.ijse.FianlArtWood.dto.SupOrderDto;
import lk.ijse.FianlArtWood.dto.SupplierDto;
import lk.ijse.FianlArtWood.dto.tm.LogsTm;
import lk.ijse.FianlArtWood.dto.tm.OrderTm;
import lk.ijse.FianlArtWood.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogsStockController {
    @FXML
    private ComboBox<String> cmbPatMethod;

    @FXML
    private ComboBox<String> cmbSupId;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Label lblId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<LogsTm> tblLog;

    @FXML
    private TextField txtPrice;

    private final ObservableList<LogsTm> obList = FXCollections.observableArrayList();

    private int amount = 1;

    public void initialize() {
        setCellValueFactory();
        loadAllLogs();
        generateNextCustomerId();
        setListener();
        loadType();
        loadPayMethod();
        loadSupId();
    }
    private void loadType() {
       cmbType.getItems().add("Teak");
       cmbType.getItems().add("Rosewood");
    }

    private void loadPayMethod() {
        cmbPatMethod.getItems().add("card");
        cmbPatMethod.getItems().add("cash");
    }

    private void loadSupId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> list = OwnerSupplierModel.getAllSuppliers();

            for (SupplierDto dto : list) {
                obList.add(dto.getId());
            }

            cmbSupId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.close();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    void clearFields() {
        cmbType.setValue("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        generateNextSupOrderId();

        String id = lblId.getText();
        String type = cmbType.getValue();

        String sup_id = cmbSupId.getValue();
        String pay_meth = cmbPatMethod.getValue();
        double price = Double.parseDouble(txtPrice.getText());

        List<LogsTm> tmList = new ArrayList<>();

        for (LogsTm logsTm : obList) {
            tmList.add(logsTm);
        }

        var placeOrderDto = new SupOrderDto(supOrderId, price, type, sup_id, pay_meth, tmList);

        try {
            boolean isSuccess = PlaceSupOrderModel.placeSupOrder(placeOrderDto);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();

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

            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "order not completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    String supOrderId = "";

    private void generateNextSupOrderId() {
        try {
             supOrderId = SupOrdersModel.generateNextOrderId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblId.getText();
        String type = cmbType.getValue();

        var dto = new LogsDto(id, type, amount);

        var model = new LogsStockModel();
        try {
            boolean isUpdated = model.updateLogs(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Log updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setListener() {
        tblLog.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new LogsDto(
                            newValue.getLogs_id(),
                            newValue.getWood_type(),
                            newValue.getLog_amount()
                    );
                    setFields(dto);
                });
    }

    private void setFields(LogsDto dto) {
        lblId.setText(dto.getLogs_id());
        cmbType.setValue(dto.getWood_type());
    }

}
