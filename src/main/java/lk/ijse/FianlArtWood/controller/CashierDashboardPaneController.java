package lk.ijse.FianlArtWood.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class CashierDashboardPaneController {
    @FXML
    private ImageView borderCustomers;

    @FXML
    private ImageView borderDashboard;

    @FXML
    private ImageView borderOrders;

    @FXML
    private ImageView rectCustomers;

    @FXML
    private ImageView rectDashboard;

    @FXML
    private ImageView rectOrders;

    @FXML
    private ImageView rootNode;

    @FXML
    private AnchorPane secondaryNode;


    @FXML
    void lblCustomerEntered(MouseEvent event) {
        borderCustomers.setVisible(true);
    }

    @FXML
    void lblCustomerExited(MouseEvent event) {
        borderCustomers.setVisible(false);
    }

    @FXML
    void lblCustomerOnAction(MouseEvent event) throws IOException {
        rectCustomers.setVisible(true);
        rectOrders.setVisible(false);
        rectDashboard.setVisible(false);

        secondaryNode.getChildren().clear();
        secondaryNode.getChildren().add(FXMLLoader.load(secondaryNode.getClass().getResource("/view/cashier_customer.fxml")));
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
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);

        secondaryNode.getChildren().clear();
        secondaryNode.getChildren().add(FXMLLoader.load(secondaryNode.getClass().getResource("/view/cashier_dashboard.fxml")));

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
    void lblOrdersEntered(MouseEvent event) {
        borderOrders.setVisible(true);
    }

    @FXML
    void lblOrdersExited(MouseEvent event) {
        borderOrders.setVisible(false);
    }

    @FXML
    void lblOrdersOnAction(MouseEvent event) throws IOException {
        rectOrders.setVisible(true);
        rectCustomers.setVisible(false);
        rectDashboard.setVisible(false);

        secondaryNode.getChildren().clear();
        secondaryNode.getChildren().add(FXMLLoader.load(secondaryNode.getClass().getResource("/view/cashier_order.fxml")));
    }

}
