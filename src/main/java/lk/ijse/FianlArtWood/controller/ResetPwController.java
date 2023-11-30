package lk.ijse.FianlArtWood.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private JFXCheckBox chkNew;

    @FXML
    private JFXCheckBox chkVer;

    @FXML
    private PasswordField passNewPw;

    @FXML
    private PasswordField passVerPw;

    @FXML
    private TextField txtNewPw;

    @FXML
    private TextField txtVerifyPw;

    public void initialize() {
        txtNewPw.setVisible(false);
        txtVerifyPw.setVisible(false);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {

        System.out.println(passNewPw.getText());
        System.out.println(passVerPw.getText());

        if (passNewPw.getText().equals(passVerPw.getText())){
            Connection connection = DbConnection.getInstance().getConnection();

            String pw = passVerPw.getText();

            String sql = "UPDATE login SET pw = ? WHERE user_name = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, pw);
            pstm.setString(2, "admin");

             if(pstm.executeUpdate() > 0){
                 new Alert(Alert.AlertType.INFORMATION, "Password Reset Successful").show();
                 Stage stage = (Stage) this.rootNode.getScene().getWindow();

                 stage.close();

             }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Password Reset not Successfully").show();
        }
    }
    @FXML
    void chkShowNew(ActionEvent event) {
        if (chkNew.isSelected()){
            String password = passNewPw.getText();
            txtNewPw.setText(password);

            passNewPw.setVisible(false);
            txtNewPw.setVisible(true);
        } else {
            txtNewPw.setVisible(false);
            passNewPw.setVisible(true);
        }
    }

    @FXML
    void chkShowVer(ActionEvent event) {
        if (chkVer.isSelected()){
            String password = passVerPw.getText();
            txtVerifyPw.setText(password);

            passVerPw.setVisible(false);
            txtVerifyPw.setVisible(true);
        } else {
            txtVerifyPw.setVisible(false);
            passVerPw.setVisible(true);
        }
    }

    @FXML
    void newPwOnReleased(KeyEvent event) {

    }

    @FXML
    void newTxtOnReleased(KeyEvent event) {

    }

    @FXML
    void verPwOnReleased(KeyEvent event) {

    }

    @FXML
    void verTxtOnReleased(KeyEvent event) {

    }

}
