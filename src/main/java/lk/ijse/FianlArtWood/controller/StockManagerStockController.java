package lk.ijse.FianlArtWood.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StockManagerStockController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnFinishedOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/finished_stock.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Finished stock");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnLogsOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/logs_stock.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Logs stock");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnPendingOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/pending_stock.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Pending stock");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnWoodPiecesOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/wood_pieces_stock.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Wood pieces stock");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
