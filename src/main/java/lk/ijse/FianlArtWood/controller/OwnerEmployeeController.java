package lk.ijse.FianlArtWood.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.EmployeeDto;
import lk.ijse.FianlArtWood.dto.tm.EmployeeTm;
import lk.ijse.FianlArtWood.model.OwnerEmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class OwnerEmployeeController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colJobRole;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTelNo;

    @FXML
    private ComboBox<String> cmbJobRole;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
        setListener();
        loadJobRole();
    }

    private void loadJobRole() {
        cmbJobRole.getItems().add("cashier");
        cmbJobRole.getItems().add("stockManager");
        cmbJobRole.getItems().add("carveEmployee");
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("job_role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllEmployees() {
        var model = new OwnerEmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList;
            dtoList = model.getAllEmployees();

            for(EmployeeDto dto : dtoList) {
                obList.add(new EmployeeTm(dto.getEmp_id(), dto.getName(), dto.getAddress(), dto.getTel(), dto.getJob_role(), dto.getStatus()));
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtAddress.setText("");
        txtId.setText("");
        cmbJobRole.setValue("");
        txtName.setText("");
        txtTel.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String emp_id = txtId.getText();
        var editModel = new OwnerEmployeeModel();
        try {
            boolean isDeleted = editModel.deleteEmployee(emp_id);

            if(isDeleted) {
                tblEmployee.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "employee not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String emp_id = txtId.getText();
        String name = txtName.getText();
        int tel = Integer.parseInt(txtTel.getText());
        String address = txtAddress.getText();
        String job_role = cmbJobRole.getValue();
        String status = "Available";

        var dto = new EmployeeDto(emp_id, name, address, tel, status, job_role);

        var model = new OwnerEmployeeModel();
        try {
            if (validateEmployee()) {

                boolean isSaved = model.saveUser(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee not saved!").show();
                    clearFields();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateEmployee() {
        String id = txtId.getText();
        boolean isValid = Pattern.matches("[E][0-9]{1,}", id);

        if (!isValid){

            if (txtId.getText().isEmpty()){
                flashBorder(txtId);
                return false;
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Invalid ID").show();
                return false;
            }
        }

        String name = txtName.getText();
        boolean isValidName = Pattern.matches("([a-zA-Z\\s]+)", name);

        if (!isValidName){

            if (txtName.getText().isEmpty()){
                flashBorder(txtName);
                return false;
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Invalid Name").show();
                return false;
            }
        }

        String address = txtAddress.getText();
        boolean isValidAddress = Pattern.matches("([a-zA-Z0-9\\s]+)", address);

        if (!isValidAddress){

            if (txtAddress.getText().isEmpty()){
                flashBorder(txtAddress);
                return false;
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Invalid Address").show();
                return false;
            }
        }

        String tel = txtTel.getText();
        boolean isValidTel = Pattern.matches("[0-9]{10}", tel);

        if (!isValidTel){

            if (txtTel.getText().isEmpty()){
                flashBorder(txtTel);
                return false;
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Invalid Tel").show();
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

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String emp_id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        int tel = Integer.parseInt(txtTel.getText());
        String job_role = cmbJobRole.getValue();

        var dto = new EmployeeDto(emp_id,name,address,tel,job_role);

        var model = new OwnerEmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);

            if(isUpdated) {
                tblEmployee.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee not updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String emp_id = txtId.getText();

        var model = new OwnerEmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(emp_id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(EmployeeDto dto) {
        txtId.setText(dto.getEmp_id());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(String.valueOf(dto.getTel()));
        cmbJobRole.setValue(dto.getJob_role());
    }

    private void setListener() {
        tblEmployee.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new EmployeeDto(
                            newValue.getEmp_id(),
                            newValue.getName(),
                            newValue.getAddress(),
                            newValue.getTel(),
                            newValue.getJob_role()

                    );
                    setFields(dto);
                });
    }

    private void setFields(EmployeeDto dto) {
        txtId.setText(dto.getEmp_id());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        cmbJobRole.setValue(dto.getJob_role());
        txtTel.setText(String.valueOf(dto.getTel()));
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/EmployeeArtWood.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText("SELECT*FROM employee");
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());

            JFrame frame = new JFrame("Jasper Report Viewer");
            JRViewer viewer = new JRViewer(jasperPrint);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer);
            frame.setSize(new Dimension(1200, 800));
            frame.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
