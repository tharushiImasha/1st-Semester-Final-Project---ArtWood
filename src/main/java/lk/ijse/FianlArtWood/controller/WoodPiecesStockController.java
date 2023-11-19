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
import lk.ijse.FianlArtWood.dto.WoodPiecesDto;
import lk.ijse.FianlArtWood.dto.tm.WoodPiecesTm;
import lk.ijse.FianlArtWood.model.WoodPiecesStockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WoodPiecesStockController {
    @FXML
    private Label lblAmount;

    @FXML
    private Label lblQuality;

    @FXML
    private Label lblWoodId;

    @FXML
    private TextField txtLength;

    @FXML
    private TextField txtRadius;

    @FXML
    private TextField txtWeight;

    @FXML
    private TableColumn<?, ?> colAction;

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
    private TextField txtLogId;


    public void initialize() {
        setCellValueFactory();
        loadAllWood();
        generateNextWoodId();
        setListener();
    }

    private void generateNextWoodId() {
        try {
            String orderId = WoodPiecesStockModel.generateNextWoodId();
            lblWoodId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("wood_piece_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("wood_type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colQuality.setCellValueFactory(new PropertyValueFactory<>("quality"));
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logs_id"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllWood() {
        try {
            List<WoodPiecesDto> dtoList = WoodPiecesStockModel.getAllWood();

            ObservableList<WoodPiecesTm> obList = FXCollections.observableArrayList();

            for(WoodPiecesDto dto : dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblWood.getSelectionModel().getFocusedIndex();
                        String id = (String) colId.getCellData(index);

                        deleteWood(id);

                        obList.remove(index);
                        tblWood.refresh();
                    }

                });

                var tm = new WoodPiecesTm(dto.getWood_piece_id(), dto.getQuality(), dto.getAmount(), dto.getWood_type(), dto.getLogs_id(), btn);

                obList.add(tm);

            }

            tblWood.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteWood(String id) {
        try {
            boolean isDeleted = WoodPiecesStockModel.deleteWood(id);

            if(isDeleted) {
                tblWood.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "wood piece deleted!").show();
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
        lblQuality.setText("");
        txtLogId.setText("");
        lblAmount.setText("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblWoodId.getText();
        String quality = lblQuality.getText();
        int amount = Integer.parseInt(lblAmount.getText());
        String type = "";
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
        String id = lblWoodId.getText();
        String quality = lblQuality.getText();
        int amount = Integer.parseInt(lblAmount.getText());
        String type = "";
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
    void btnEnterOnAction(ActionEvent event) {
        double length = Double.parseDouble(txtLength.getText());
        double radius = Double.parseDouble(txtRadius.getText());
        double weight = Double.parseDouble(txtWeight.getText());

        int size = 1;

        try {
            int amount = (int) ((2*3.14*radius*(length+radius))/size);
            String quality = "";

            if (weight > 30){
                quality = "High Quality";
            } else{
                quality = "Bad Quality";
            }

            lblAmount.setText(String.valueOf(amount));
            lblQuality.setText(quality);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void setListener() {
        tblWood.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new WoodPiecesDto(
                            newValue.getWood_piece_id(),
                            newValue.getQuality(),
                            newValue.getAmount(),
                            newValue.getWood_type(),
                            newValue.getLogs_id()
                    );
                    setFields(dto);
                });
    }

    private void setFields(WoodPiecesDto dto) {
        lblWoodId.setText(dto.getWood_piece_id());
        lblAmount.setText(String.valueOf(dto.getAmount()));
        lblQuality.setText(dto.getQuality());
        txtLogId.setText(dto.getLogs_id());
    }

}
