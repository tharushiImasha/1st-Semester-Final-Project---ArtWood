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

public class OwnerDashboardController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane secondaryNode;

    @FXML
    private ImageView rectAccounts;

    @FXML
    private ImageView rectCustomers;

    @FXML
    private ImageView rectDashboard;

    @FXML
    private ImageView rectEmployees;

    @FXML
    private ImageView rectOrders;

    @FXML
    private ImageView rectProduct;

    @FXML
    private ImageView rectStocks;

    @FXML
    private ImageView rectUsers;

    @FXML
    private ImageView borderAccounts;

    @FXML
    private ImageView borderCustomers;

    @FXML
    private ImageView borderDashboard;

    @FXML
    private ImageView borderEmployees;

    @FXML
    private ImageView borderOrders;

    @FXML
    private ImageView borderProducts;

    @FXML
    private ImageView borderStocks;

    @FXML
    private ImageView borderUsers;

    public void initialize(){

        rectAccounts.setVisible(false);
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);

        borderAccounts.setVisible(false);
        borderEmployees.setVisible(false);
        borderDashboard.setVisible(false);
        borderCustomers.setVisible(false);
        borderOrders.setVisible(false);
        borderProducts.setVisible(false);
        borderStocks.setVisible(false);
        borderUsers.setVisible(false);
    }

    @FXML
    void lblAccountOnAction(MouseEvent event) {
        rectAccounts.setVisible(true);
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);
    }

    @FXML
    void lblAccountsEntered(MouseEvent event) {
        borderAccounts.setVisible(true);
    }

    @FXML
    void lblAccountsExited(MouseEvent event) {
        borderAccounts.setVisible(false);
    }

    @FXML
    void lblCustomerEntered(MouseEvent event) {
        borderCustomers.setVisible(true);
    }

    @FXML
    void lblCustomerExited(MouseEvent event) {
        borderCustomers.setVisible(false);
    }

    @FXML
    void lblCustomerOnAction(MouseEvent event) {
        rectCustomers.setVisible(true);
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);
        rectAccounts.setVisible(false);
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
    void lblDashboardOnAction(MouseEvent event) {
        rectDashboard.setVisible(true);
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectAccounts.setVisible(false);
    }

    @FXML
    void lblEmployeeOnAction(MouseEvent event) {
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(true);
        rectDashboard.setVisible(false);
        rectAccounts.setVisible(false);
    }

    @FXML
    void lblEmployeesEntered(MouseEvent event) {
        borderEmployees.setVisible(true);
    }

    @FXML
    void lblEmployeesExited(MouseEvent event) {
        borderEmployees.setVisible(false);
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
    void lblOrdersOnAction(MouseEvent event) {
        rectUsers.setVisible(false);
        rectOrders.setVisible(true);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);
        rectAccounts.setVisible(false);
    }

    @FXML
    void lblProductEntered(MouseEvent event) {
        borderProducts.setVisible(true);
    }

    @FXML
    void lblProductExited(MouseEvent event) {
        borderProducts.setVisible(false);
    }

    @FXML
    void lblProductOnAction(MouseEvent event) {
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(true);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);
        rectAccounts.setVisible(false);
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
    void lblStockOnAction(MouseEvent event) {
        rectUsers.setVisible(false);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(true);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);
        rectAccounts.setVisible(false);
    }

    @FXML
    void lblUsersEntered(MouseEvent event) {
        borderUsers.setVisible(true);
    }

    @FXML
    void lblUsersExited(MouseEvent event) {
        borderUsers.setVisible(false);
    }

    @FXML
    void lblUsersOnAction(MouseEvent event) {
        rectUsers.setVisible(true);
        rectOrders.setVisible(false);
        rectCustomers.setVisible(false);
        rectStocks.setVisible(false);
        rectProduct.setVisible(false);
        rectEmployees.setVisible(false);
        rectDashboard.setVisible(false);
        rectAccounts.setVisible(false);
    }


}
