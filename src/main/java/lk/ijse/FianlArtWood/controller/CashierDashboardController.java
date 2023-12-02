package lk.ijse.FianlArtWood.controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.FianlArtWood.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashierDashboardController {

    @FXML
    private Pane elepahntLow;

    @FXML
    private Pane elephantEnough;

    @FXML
    private Pane statueEnough;

    @FXML
    private Pane statueLow;

    public Label lblUser;

    public Label lblJobRole;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblFinished;

    @FXML
    private Label lblLogs;

    @FXML
    private Label lblPending;

    @FXML
    private Label lblWood;

    @FXML
    private AnchorPane rootNode;

    public void initialize() throws SQLException {
        generateTime();
        getLog();
        getWood();
        getPending();
        getFinished();
        getName();

        elepahntLow.setVisible(false);
        elephantEnough.setVisible(false);
        statueLow.setVisible(false);
        statueEnough.setVisible(false);

        roseWoodStock();
        teakWoodStock();

    }

    public void generateTime(){
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void getLog() throws SQLException {
       int amount =  LogsStockModel.getLogCount();
       lblLogs.setText(String.valueOf(amount));
    }

    private void getWood() throws SQLException {
        int amount =  WoodPiecesStockModel.getWoodCount();
        lblWood.setText(String.valueOf(amount));
    }

    private void getPending() throws SQLException {
        int amount =  PendingStockModel.getPendingCount();
        lblPending.setText(String.valueOf(amount));
    }

    private void getFinished() throws SQLException {
        int amount =  FinishedStockModel.getFinishedCount();
        lblFinished.setText(String.valueOf(amount));
    }

    private void roseWoodStock() throws SQLException {
        int amount =  FinishedStockModel.getProductCountByType("Statue");
        if (amount < 4){
            statueLow.setVisible(true);
            statueEnough.setVisible(false);
        }else {
            statueLow.setVisible(false);
            statueEnough.setVisible(true);
        }
    }

    private void teakWoodStock() throws SQLException {
        int amount =  FinishedStockModel.getProductCountByType("Elephant");
        if (amount < 4){
            elepahntLow.setVisible(true);
            elephantEnough.setVisible(false);
        }else {
            elepahntLow.setVisible(false);
            elephantEnough.setVisible(true);
        }
    }

    private void getName() throws SQLException {
        String name = OwnerEmployeeModel.getName("cashier");
        lblUser.setText(name);
    }

}
