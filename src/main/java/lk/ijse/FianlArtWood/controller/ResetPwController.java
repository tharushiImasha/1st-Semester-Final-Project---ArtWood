package lk.ijse.FianlArtWood.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetPwController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtNewPw;

    @FXML
    private TextField txtVerifyPw;

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        if (txtNewPw.getText().equals(txtVerifyPw.getText())){
            Connection connection = DbConnection.getInstance().getConnection();

            String pw = txtVerifyPw.getText();

            String sql = "UPDATE login SET pw = ? WHERE user_name = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, pw);
            pstm.setString(2, "admin");

             if(pstm.executeUpdate() > 0){
                 new Alert(Alert.AlertType.INFORMATION, "Password Reset Successful");
                 Stage stage = (Stage) this.rootNode.getScene().getWindow();

                 stage.close();

             } else {
                 new Alert(Alert.AlertType.INFORMATION, "Password Reset not Successfully");
             }
        }
    }
}
