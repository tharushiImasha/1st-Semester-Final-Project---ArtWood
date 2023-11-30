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
import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.*;
import lk.ijse.FianlArtWood.dto.tm.PendingStockTm;
import lk.ijse.FianlArtWood.model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PendingStockController {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private ComboBox<String> cmbFinshedId;

    @FXML
    private ComboBox<String> cmbWoodId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colIsFinished;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colFinishedId;

    @FXML
    private TableColumn<?, ?> colPendingId;

    @FXML
    private TableColumn<?, ?> colWoodPieceId;

    @FXML
    private Label lblId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<PendingStockTm> tblPending;

    public void initialize() {
        setCellValueFactory();
        loadAllLogs();
        generateNextPendingId();
        loadEmpId();
        loadFinishedId();
        loadWoodId();
        setListener();
    }

    private void loadEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> list = OwnerEmployeeModel.getAllEmployees();

            for (EmployeeDto dto : list) {
                obList.add(dto.getEmp_id());
            }

            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFinishedId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<FinishedStockDto> list = FinishedStockModel.getAllFinishedStock();

            for (FinishedStockDto dto : list) {
                obList.add(dto.getFinished_id());
            }

            cmbFinshedId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadWoodId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<WoodPiecesDto> list = WoodPiecesStockModel.getAllWood();

            for (WoodPiecesDto dto : list) {
                obList.add(dto.getWood_piece_id());
            }

            cmbWoodId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextPendingId() {
        try {
            String pendingId = PendingStockModel.generateNextPendingId();
            lblId.setText(pendingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colPendingId.setCellValueFactory(new PropertyValueFactory<>("pending_id"));
        colFinishedId.setCellValueFactory(new PropertyValueFactory<>("finished_id"));
        colWoodPieceId.setCellValueFactory(new PropertyValueFactory<>("wood_piece_id"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colIsFinished.setCellValueFactory(new PropertyValueFactory<>("btn1"));
    }

    private void loadAllLogs() {
        try {
            List<PendingStockDto> dtoList = PendingStockModel.getAllPendings();

            ObservableList<PendingStockTm> obList = FXCollections.observableArrayList();

            for(PendingStockDto dto : dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                Button btn1 = new Button("Finished");
                btn1.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblPending.getSelectionModel().getFocusedIndex();
                        String id = (String) colPendingId.getCellData(index);

                        deletePending(id);

                        obList.remove(index);
                        tblPending.refresh();
                    }

                });

                btn1.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to finished?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblPending.getSelectionModel().getFocusedIndex();
                        String id = (String) colPendingId.getCellData(index);

                        try {
                            finishedPending(id);
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                            throw new RuntimeException(ex);
                        }

                        tblPending.refresh();
                    }

                });

                var tm = new PendingStockTm(dto.getPending_id(), dto.getEmp_id(), dto.getWood_piece_id(), dto.getFinished_id(), btn, btn1);

                obList.add(tm);

            }

            tblPending.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void finishedPending(String id) throws SQLException {
        String finished_id = cmbFinshedId.getValue();
        String emp_id = cmbEmpId.getValue();

        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isUpdateFinished = FinishedStockModel.updateFinishedFromP(finished_id);

            if (isUpdateFinished){
                boolean isSalarySaved = SalaryModel.saveSalary(emp_id);

                if (isSalarySaved) {
                    boolean isFinance = FinanceModel.reduceFinance("cash", 2000);

                    if (isFinance) {
                        deletePending(id);
                        connection.commit();
                    }

                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }

    }

    private void deletePending(String id) {
        try {
            boolean isDeleted = PendingStockModel.deletePending(id);

            if(isDeleted) {
                tblPending.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Pending item deleted!").show();
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
        lblId.setText("");
        cmbWoodId.setValue("");
        cmbFinshedId.setValue("");
        cmbEmpId.setValue("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String pending_id = lblId.getText();
        String emp_id = cmbEmpId.getValue();
        String wood_piece_id = cmbWoodId.getValue();
        String finished_id = cmbFinshedId.getValue();

        var dto = new PendingStockDto(pending_id, emp_id, wood_piece_id, finished_id);

        var model = new PendingStockModel();
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = model.savePending(dto);
            if (isSaved) {
                boolean isWoodUpdated = WoodPiecesStockModel.reduceWood(wood_piece_id);

                if (isWoodUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "pending saved!").show();
                    connection.commit();
                    clearFields();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String pending_id = lblId.getText();
        String emp_id = cmbEmpId.getValue();
        String wood_piece_id = cmbWoodId.getValue();
        String finished_id = cmbFinshedId.getValue();
        int amount = 1;

        var dto = new PendingStockDto(pending_id, amount, emp_id, wood_piece_id, finished_id);

        var model = new PendingStockModel();
        try {
            boolean isUpdated = model.updatePending(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "pending updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setListener() {
        tblPending.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new PendingStockDto(
                            newValue.getPending_id(),
                            newValue.getEmp_id(),
                            newValue.getWood_piece_id(),
                            newValue.getFinished_id()
                    );
                    setFields(dto);
                });
    }

    private void setFields(PendingStockDto dto) {
        lblId.setText(dto.getPending_id());
        cmbFinshedId.setValue(dto.getFinished_id());
        cmbWoodId.setValue(dto.getWood_piece_id());
        cmbEmpId.setValue(dto.getEmp_id());
    }

}
