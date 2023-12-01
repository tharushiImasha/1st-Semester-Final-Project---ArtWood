package lk.ijse.FianlArtWood.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.FianlArtWood.dto.EmployeeDto;
import lk.ijse.FianlArtWood.dto.LoginDto;
import lk.ijse.FianlArtWood.dto.tm.UserTm;
import lk.ijse.FianlArtWood.model.EditProfileFormModel;
import lk.ijse.FianlArtWood.model.OwnerEmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class EditProfileFormController {

    public TableColumn colEmpId;
    public TableColumn colUserName;
    public TableColumn colPw;

    @FXML
    private TableView<UserTm> tblUsers;

    @FXML
    private Label lblJob;

    @FXML
    private ComboBox<String> cmbId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    public void initialize() {
        setCellValueFactory();
        loadAllUsers();
        setListener();
        loadEmpId();
    }

    private void loadEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> list = OwnerEmployeeModel.getAllEmployees();

            for (EmployeeDto dto : list) {
                obList.add(dto.getEmp_id());
            }

            cmbId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colPw.setCellValueFactory(new PropertyValueFactory<>("pw"));
    }

    private void loadAllUsers() {
        var model = new EditProfileFormModel();

        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        try {
            List<LoginDto> dtoList;
            dtoList = model.getAllUsers();

            for(LoginDto dto : dtoList) {
                obList.add(new UserTm(dto.getUserName(), dto.getPw(), dto.getEmp_id()));
            }

            tblUsers.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.close();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String emp_id = (String) cmbId.getValue();
        var editModel = new EditProfileFormModel();
        try {
            boolean isDeleted = editModel.deleteUser(emp_id);

            if(isDeleted) {
                tblUsers.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "user deleted!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "user not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String emp_id = (String) cmbId.getValue();
        String user_name = txtUserName.getText();
        String pw = txtPassword.getText();

        var dto = new LoginDto(user_name,pw,emp_id);

        var model = new EditProfileFormModel();
        try {
            if (validateUser()) {

                boolean isSaved = model.saveUser(dto);
                if (isSaved) {
                    tblUsers.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "User not saved!").show();
                    clearFields();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private boolean validateUser() {
        String userName = txtUserName.getText();
        boolean isValid = Pattern.matches("[a-zA-Z]{3,}", userName);

        if (!isValid){

            if (txtUserName.getText().isEmpty()){
                flashBorder(txtUserName);
                return false;
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid UserName").show();
                return false;
            }
        }

        String pw = txtPassword.getText();
        boolean isValidPw = Pattern.matches("(^[a-zA-Z]\\w{3,14}$)", pw);

        if (!isValidPw){

            if (txtPassword.getText().isEmpty()){
                flashBorder(txtPassword);
                return false;
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
                return false;
            }
        }

        return true;
    }

    private void flashBorder(TextField textField) {
        textField.setStyle("-fx-border-color: #000000;-fx-background-color: rgba(255,0,0,0.13)");
        setBorderResetAnimation(textField);
    }

    private void setBorderResetAnimation(TextField textField) {

        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(textField.styleProperty(), "-fx-background-color:rgba(255,0,0,0.13);-fx-border-color: rgba(128,128,128,0.38);-fx-background-radius:10;-fx-border-radius:10")),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(textField.styleProperty(), "-fx-background-color: white;-fx-border-color: rgba(128,128,128,0.38);-fx-background-radius:10;-fx-border-radius:10")),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(textField.styleProperty(), "-fx-background-color:rgba(255,0,0,0.13);-fx-border-color: rgba(128,128,128,0.38);-fx-background-radius:10;-fx-border-radius:10")),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(textField.styleProperty(), "-fx-background-color: white;-fx-border-color: rgba(128,128,128,0.38);-fx-background-radius:10;-fx-border-radius:10")),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(textField.styleProperty(), "-fx-background-color:rgba(255,0,0,0.13);-fx-border-color: rgba(128,128,128,0.38);-fx-background-radius:10;-fx-border-radius:10")),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(textField.styleProperty(), "-fx-background-color: white;-fx-border-color: rgba(128,128,128,0.38);-fx-background-radius:10;-fx-border-radius:10"))
        );
        timeline1.play();
    }

    private void clearFields() {
        cmbId.setValue("");
        txtPassword.setText("");
        txtUserName.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String emp_id = cmbId.getValue();
        String user_name = txtUserName.getText();
        String pw = txtPassword.getText();

        var dto = new LoginDto(user_name, pw, emp_id);

        var model = new EditProfileFormModel();
        try {
            boolean isUpdated = model.updateUser(dto);

            if(isUpdated) {
                tblUsers.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "user updated!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "user not updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void setListener() {
        tblUsers.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new LoginDto(
                            newValue.getUser_name(),
                            newValue.getPw(),
                            newValue.getEmp_id()
                    );
                    setFields(dto);
                });
    }

    private void setFields(LoginDto dto) {
        cmbId.setValue(dto.getEmp_id());
        txtUserName.setText(dto.getUserName());
        txtPassword.setText(dto.getPw());
    }

    @FXML
    void cmbIdOnAction(ActionEvent event) throws SQLException {
        String emp_id = cmbId.getValue();

       String job_role = EditProfileFormModel.getJob(emp_id);

       lblJob.setText(job_role);
    }

}
