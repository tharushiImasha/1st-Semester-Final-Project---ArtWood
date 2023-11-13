package lk.ijse.FianlArtWood.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StockManagerDashboardPaneController {
    @FXML
    private ImageView borderDashboard;

    @FXML
    private ImageView borderStocks;

    @FXML
    private ImageView rectDashboard;

    @FXML
    private ImageView rectStocks;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane secondaryNode;

    public void initialize(){

        rectStocks.setVisible(false);
        rectDashboard.setVisible(true);

        borderDashboard.setVisible(false);
        borderStocks.setVisible(false);

    }

    @FXML
    void lblDashboardEntered(MouseEvent event) {
        borderDashboard.setVisible(true);

    }

    @FXML
    void lblDashboardExited(MouseEvent event) {
        borderDashboard.setVisible(false);
    }

    @FXML
    void lblDashboardOnAction(MouseEvent event) throws IOException {
        rectDashboard.setVisible(true);
        rectStocks.setVisible(false);

        secondaryNode.getChildren().clear();
        secondaryNode.getChildren().add(FXMLLoader.load(secondaryNode.getClass().getResource("/view/stock_manager_dashboard.fxml")));
    }

    @FXML
    void lblLogoutOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void lblStockEntered(MouseEvent event) {
        borderStocks.setVisible(true);
    }

    @FXML
    void lblStockExited(MouseEvent event) {
        borderStocks.setVisible(false);
    }

    @FXML
    void lblStockOnAction(MouseEvent event) throws IOException {
        rectDashboard.setVisible(false);
        rectStocks.setVisible(true);

        secondaryNode.getChildren().clear();
        secondaryNode.getChildren().add(FXMLLoader.load(secondaryNode.getClass().getResource("/view/stock_manager_stock.fxml")));
    }
}
