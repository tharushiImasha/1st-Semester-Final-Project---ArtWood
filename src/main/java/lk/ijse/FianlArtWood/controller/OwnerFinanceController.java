package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.FinanceDto;
import lk.ijse.FianlArtWood.dto.SalaryDto;
import lk.ijse.FianlArtWood.dto.tm.SalaryTm;
import lk.ijse.FianlArtWood.model.FinanceModel;
import lk.ijse.FianlArtWood.model.SalaryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OwnerFinanceController {
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private Label lblCard;

    @FXML
    private Label lblCash;

    @FXML
    private TableView<SalaryTm> tblFinance;

    @FXML
    private AnchorPane rootNode;

    @FXML
    void salaryOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/salaryForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();

        stage.setTitle("Salary Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllUsers();
        loadCash();
        loadCard();
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salary_id"));
    }

    private void loadAllUsers() {
        var model = new SalaryModel();

        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<SalaryDto> dtoList;
            dtoList = model.getAllSalary();

            for(SalaryDto dto : dtoList) {
                obList.add(new SalaryTm(dto.getSalary_id(), dto.getAmount(), dto.getEmp_id()));
            }

            tblFinance.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCash() throws SQLException {
         double amount = FinanceModel.loadCash("cash");
         lblCash.setText(String.valueOf(amount));

    }

    private void loadCard() throws SQLException {
        double amount = FinanceModel.loadCard("card");
        lblCard.setText(String.valueOf(amount));

    }

}
