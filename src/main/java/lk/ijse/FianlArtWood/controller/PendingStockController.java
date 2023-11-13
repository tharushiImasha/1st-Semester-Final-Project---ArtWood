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
import lk.ijse.FianlArtWood.dto.PendingStockDto;
import lk.ijse.FianlArtWood.dto.tm.PendingStockTm;
import lk.ijse.FianlArtWood.model.LogsStockModel;
import lk.ijse.FianlArtWood.model.PendingStockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PendingStockController {
    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colFinishedId;

    @FXML
    private TableColumn<?, ?> colPendingId;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colWoodPieceId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<PendingStockTm> tblPending;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtFinishedId;

    @FXML
    private TextField txtPendingId;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtWoodPieceId;

    public void initialize() {
        setCellValueFactory();
        loadAllLogs();
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colPendingId.setCellValueFactory(new PropertyValueFactory<>("pending_id"));
        colFinishedId.setCellValueFactory(new PropertyValueFactory<>("finished_id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colWoodPieceId.setCellValueFactory(new PropertyValueFactory<>("wood_piece_id"));
    }

    private void loadAllLogs() {
        var model = new PendingStockModel();

        ObservableList<PendingStockTm> obList = FXCollections.observableArrayList();

        try {
            List<PendingStockDto> dtoList;
            dtoList = model.getAllPendings();

            for(PendingStockDto dto : dtoList) {
                obList.add(new PendingStockTm(dto.getPending_id(), dto.getEmp_id(), dto.getWood_piece_id(), dto.getFinished_id(), dto.getProduct_id()));
            }

            tblPending.setItems(obList);
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
        txtEmpId.setText("");
        txtPendingId.setText("");
        txtFinishedId.setText("");
        txtWoodPieceId.setText("");
        txtProductId.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtPendingId.getText();

        var model = new PendingStockModel();
        try {
            boolean isDeleted = model.deletePending(id);

            if(isDeleted) {
                tblPending.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "pending item deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String pending_id = txtPendingId.getText();
        String emp_id = txtEmpId.getText();
        String wood_piece_id = txtWoodPieceId.getText();
        String finished_id = txtFinishedId.getText();
        String product_id = txtProductId.getText();

        var dto = new PendingStockDto(pending_id, emp_id, wood_piece_id, finished_id, pending_id);

        var model = new PendingStockModel();
        try {
            boolean isSaved = model.savePending(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "pending saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String pending_id = txtPendingId.getText();
        String emp_id = txtEmpId.getText();
        String wood_piece_id = txtWoodPieceId.getText();
        String finished_id = txtFinishedId.getText();
        String product_id = txtProductId.getText();
        int amount = 1;

        var dto = new PendingStockDto(pending_id, amount, emp_id, wood_piece_id, finished_id, product_id);

        var model = new PendingStockModel();
        try {
            boolean isUpdated = model.updatePending(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "pending updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtPendingIdOnAction(ActionEvent event) {
        String id = txtPendingId.getText();

        var model = new PendingStockModel();
        try {
            PendingStockDto dto = model.searchPending(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "pending id not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(PendingStockDto dto) {
        txtPendingId.setText(dto.getPending_id());
        txtProductId.setText(String.valueOf(dto.getProduct_id()));
        txtEmpId.setText(dto.getEmp_id());
        txtFinishedId.setText(dto.getFinished_id());
        txtWoodPieceId.setText(dto.getWood_piece_id());

    }

}
