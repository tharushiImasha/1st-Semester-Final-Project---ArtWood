package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.LoginDto;
import lk.ijse.FianlArtWood.dto.tm.UserTm;
import lk.ijse.FianlArtWood.model.EditProfileFormModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditProfileFormController {


    public TableColumn colEmpId;
    public TableColumn colUserName;
    public TableColumn colPw;
    @FXML
    private TableView<UserTm> tblUsers;

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
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_pane.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Owner Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String emp_id = txtEmpId.getText();
        var editModel = new EditProfileFormModel();
        try {
            boolean isDeleted = editModel.deleteUser(emp_id);

            if(isDeleted) {
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
        String emp_id = txtEmpId.getText();
        String user_name = txtUserName.getText();
        String pw = txtPassword.getText();

        var dto = new LoginDto(user_name,pw,emp_id);

        var model = new EditProfileFormModel();
        try {
            boolean isSaved = model.saveUser(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                clearFields();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "User not saved!").show();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtEmpId.setText("");
        txtPassword.setText("");
        txtUserName.setText("");
    }

    private void fillFields(LoginDto dto) {
        txtEmpId.setText(dto.getEmp_id());
        txtUserName.setText(dto.getUserName());
        txtPassword.setText(dto.getPw());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String emp_id = txtEmpId.getText();
        String user_name = txtUserName.getText();
        String pw = txtPassword.getText();

        var dto = new LoginDto(user_name, pw, emp_id);

        var model = new EditProfileFormModel();
        try {
            boolean isUpdated = model.updateUser(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "user updated!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "user not updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        String emp_id = txtEmpId.getText();

        var model = new EditProfileFormModel();
        try {
            LoginDto dto = model.searchCustomer(emp_id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "user not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setListener() {
        tblUsers.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new LoginDto(
                            newValue.getEmp_id(),
                            newValue.getUser_name(),
                            newValue.getPw()
                    );
                    setFields(dto);
                });
    }

    private void setFields(LoginDto dto) {
        txtEmpId.setText(dto.getEmp_id());
        txtUserName.setText(dto.getUserName());
        txtPassword.setText(dto.getPw());
    }

}
