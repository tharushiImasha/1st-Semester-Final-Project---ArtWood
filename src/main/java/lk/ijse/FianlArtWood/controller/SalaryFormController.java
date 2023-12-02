package lk.ijse.FianlArtWood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.EmployeeDto;
import lk.ijse.FianlArtWood.dto.OtherSalaryDto;
import lk.ijse.FianlArtWood.dto.tm.CustomerTm;
import lk.ijse.FianlArtWood.dto.tm.OtherSalaryTm;
import lk.ijse.FianlArtWood.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SalaryFormController {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private ComboBox<String> cmbPatMethod;

    @FXML
    private Label lblJobRole;

    @FXML
    private TextField txtAmount;

    @FXML
    private Label lblSalaryid;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private TableView<OtherSalaryTm> tblFinance;

    public void initialize() {
        loadEmpId();
        loadPayMethod();
        setCellValueFactory();
        loadAllUsers();
        setListener();
        generateNextSalaryId();
    }

    private void generateNextSalaryId() {
        try {
            String orderId = OtherEmployeeSalaryModel.generateNextSalaryId();
            lblSalaryid.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("other_salary_id"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllUsers() {
        try {
            List<OtherSalaryDto> dtoList = OtherEmployeeSalaryModel.getAllSalary();

            ObservableList<OtherSalaryTm> obList = FXCollections.observableArrayList();

            for(OtherSalaryDto dto : dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        int index = tblFinance.getSelectionModel().getFocusedIndex();
                        String id = (String) colSalaryId.getCellData(index);

                        deleteSalary(id);

                        obList.remove(index);
                        tblFinance.refresh();
                    }

                });

                var tm = new OtherSalaryTm(dto.getOther_salary_id(), dto.getAmount(), dto.getEmp_id(), btn);

                obList.add(tm);

            }

            tblFinance.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteSalary(String id) {
        try {
            boolean isDeleted = OtherEmployeeSalaryModel.deleteSalary(id);

            if(isDeleted) {
                tblFinance.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "salary deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadPayMethod() {
        cmbPatMethod.getItems().add("card");
        cmbPatMethod.getItems().add("cash");
    }

    private void loadEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> list = OwnerEmployeeModel.getAllEmployees();

            for (EmployeeDto dto : list) {
                obList.add(dto.getEmp_id());
            }

            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPayOnAction(ActionEvent event) throws SQLException {
        String emp_id = cmbEmpId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        String pay_method = cmbPatMethod.getValue();

        boolean isSalarySaved = OtherEmployeeSalaryModel.saveSalary(emp_id, amount);

        if (isSalarySaved) {
            boolean isFinance = FinanceModel.reduceFinanceOtherSalary(pay_method, amount);

            if (isFinance) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary payed").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary not payed").show();
            }
        }
    }

    @FXML
    void cmbIdOnAction(ActionEvent event) throws SQLException {
        String emp_id = cmbEmpId.getValue();

        String job_role = EditProfileFormModel.getJob(emp_id);

        lblJobRole.setText(job_role);
    }

    private void setListener() {
        tblFinance.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new OtherSalaryDto(
                            newValue.getOther_salary_id(),
                            newValue.getAmount(),
                            newValue.getEmp_id()
                    );
                    setFields(dto);
                });
    }

    private void setFields(OtherSalaryDto dto) {
        cmbEmpId.setValue(dto.getEmp_id());
        txtAmount.setText(String.valueOf(dto.getAmount()));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        cmbPatMethod.setValue("");
        txtAmount.setText("");
        cmbEmpId.setValue("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String emp_id = cmbEmpId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        String id = lblSalaryid.getText();

        var dto = new OtherSalaryDto(id, amount, emp_id);

        var model = new OtherEmployeeSalaryModel();
        try {
            boolean isUpdated = model.updateSalary(dto);

            System.out.println(isUpdated);

            if(isUpdated) {
                tblFinance.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "salary updated!").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "salary not updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.close();
    }

}
