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
import javafx.stage.WindowEvent;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.WoodPiecesDto;
import lk.ijse.FianlArtWood.dto.tm.WoodPiecesTm;
import lk.ijse.FianlArtWood.model.OwnerCustomerModel;
import lk.ijse.FianlArtWood.model.WoodPiecesStockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WoodPiecesStockController {
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLogId;

    @FXML
    private TableColumn<?, ?> colQuality;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<WoodPiecesTm> tblWood;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLogId;

    @FXML
    private TextField txtQuality;

    @FXML
    private TextField txtType;

    public void initialize() {
        setCellValueFactory();
        loadAllWood();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("wood_piece_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("wood_type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colQuality.setCellValueFactory(new PropertyValueFactory<>("quality"));
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logs_id"));
    }

    private void loadAllWood() {
        var model = new WoodPiecesStockModel();

        ObservableList<WoodPiecesTm> obList = FXCollections.observableArrayList();

        try {
            List<WoodPiecesDto> dtoList;
            dtoList = model.getAllWood();

            for(WoodPiecesDto dto : dtoList) {
                obList.add(new WoodPiecesTm(dto.getWood_piece_id(), dto.getQuality(), dto.getAmount(), dto.getWood_type(), dto.getLogs_id()));
            }

            tblWood.setItems(obList);
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
        txtLogId.setText("");
        txtQuality.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new WoodPiecesStockModel();
        try {
            boolean isDeleted = model.deleteWood(id);

            if(isDeleted) {
                tblWood.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "wood pieces deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String quality = txtQuality.getText();
        int amount = Integer.parseInt(txtAmount.getText());
        String type = txtType.getText();
        String log_id = txtLogId.getText();

        var dto = new WoodPiecesDto(id, quality, amount, type, log_id);

        var model = new WoodPiecesStockModel();
        try {
            boolean isSaved = model.saveWood(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "wood saved!").show();
                clearFields();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String quality = txtQuality.getText();
        int amount = Integer.parseInt(txtAmount.getText());
        String type = txtType.getText();
        String log_id = txtLogId.getText();

        var dto = new WoodPiecesDto(id, quality, amount, type, log_id);

        var model = new WoodPiecesStockModel();
        try {
            boolean isUpdated = model.updateWood(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "wood updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new WoodPiecesStockModel();
        try {
            WoodPiecesDto dto = model.searchWood(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "wood piece not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(WoodPiecesDto dto) {
        txtId.setText(dto.getWood_piece_id());
        txtQuality.setText(dto.getQuality());
        txtAmount.setText(dto.getQuality());
        txtType.setText(dto.getWood_type());
        txtLogId.setText(dto.getLogs_id());
    }
}
