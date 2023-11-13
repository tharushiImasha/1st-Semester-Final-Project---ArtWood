package lk.ijse.FianlArtWood.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;

public class OwnerDashboardController{

    public Label lblUser;
    public Label lblJobRole;
    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnEditProfileOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/edit_profile_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Edit Profiles");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void initialize(){

    }
}
