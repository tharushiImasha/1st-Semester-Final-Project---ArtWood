package lk.ijse.FianlArtWood.controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.FianlArtWood.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockManagerDashboardController {

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
    private Pane rosewoodEnough;

    @FXML
    private Pane rosewoodLow;

    @FXML
    private Pane teakEnough;

    @FXML
    private Pane teakLow;

    @FXML
    private AnchorPane rootNode;

    public void initialize() throws SQLException {
        generateTime();
        getLog();
        getWood();
        getPending();
        getFinished();
        getName();

        rosewoodEnough.setVisible(false);
        rosewoodLow.setVisible(false);
        teakEnough.setVisible(false);
        teakLow.setVisible(false);

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
        int amount =  WoodPiecesStockModel.getWoodCountByType("Rosewood");
        if (amount < 10){
            rosewoodLow.setVisible(true);
            rosewoodEnough.setVisible(false);
        }else {
            rosewoodLow.setVisible(false);
            rosewoodEnough.setVisible(true);
        }
    }

    private void teakWoodStock() throws SQLException {
        int amount =  WoodPiecesStockModel.getWoodCountByType("Teak");
        if (amount < 10){
            teakLow.setVisible(true);
            teakEnough.setVisible(false);
        }else {
            teakLow.setVisible(false);
            teakEnough.setVisible(true);
        }
    }

    private void getName() throws SQLException {
        String name = OwnerEmployeeModel.getName("stock_manager");
        lblUser.setText(name);
    }

}
