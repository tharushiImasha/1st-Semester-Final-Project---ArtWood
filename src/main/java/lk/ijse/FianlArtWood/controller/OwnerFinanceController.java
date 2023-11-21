package lk.ijse.FianlArtWood.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OwnerFinanceController {
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colFinanceId;

    @FXML
    private TableColumn<?, ?> colPendingId;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private Label lblCard;

    @FXML
    private Label lblCash;

    @FXML
    private TableView<?> tblFinance;
}
