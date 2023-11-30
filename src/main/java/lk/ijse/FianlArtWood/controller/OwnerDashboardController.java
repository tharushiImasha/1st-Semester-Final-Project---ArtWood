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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.FianlArtWood.model.FinishedStockModel;
import lk.ijse.FianlArtWood.model.LogsStockModel;
import lk.ijse.FianlArtWood.model.PendingStockModel;
import lk.ijse.FianlArtWood.model.WoodPiecesStockModel;


import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OwnerDashboardController{

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

    @FXML
    void btnEditProfileOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/edit_profile_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();

        stage.setTitle("Edit Profiles");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void initialize() throws SQLException {
        generateTime();
        getLog();
        getWood();
        getPending();
        getFinished();
    }

    public void generateTime(){
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
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

}
