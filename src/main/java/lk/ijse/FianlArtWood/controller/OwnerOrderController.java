package lk.ijse.FianlArtWood.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.FinishedStockDto;
import lk.ijse.FianlArtWood.dto.OrderDto;
import lk.ijse.FianlArtWood.dto.ProductTypeDto;
import lk.ijse.FianlArtWood.dto.tm.OrderTm;
import lk.ijse.FianlArtWood.dto.tm.ProductTypeTm;
import lk.ijse.FianlArtWood.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerOrderController {
    private final OwnerCustomerModel customerModel = new OwnerCustomerModel();
    private final FinishedStockModel itemModel = new FinishedStockModel();
    private final OwnerOrderModel orderModel = new OwnerOrderModel();
    private final ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnNew;

    @FXML
    private ComboBox<String> cmbCusId;

    @FXML
    private ComboBox<String> cmbPayMethod;

    @FXML
    private ComboBox<String> cmbProductId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblQtyOn;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblCusName;

    @FXML
    private TableView<OrderTm> tblOrderCart;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    private final PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerIds();
        loadItemCodes();
        loadPayMethod();
    }

    private void loadPayMethod() {
        cmbPayMethod.getItems().add("card");
        cmbPayMethod.getItems().add("cash");
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            String orderId = OwnerOrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<FinishedStockDto> itemList = FinishedStockModel.getAllFinishedStock();

            for (FinishedStockDto itemDto : itemList) {
                obList.add(itemDto.getFinished_id());
            }

            cmbProductId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = OwnerCustomerModel.getAllCustomers();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getId());
            }
            cmbCusId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblOrderDate.setText(date);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbProductId.getValue();
        String description = lblDesc.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblPrice.getText());
        double total = qty * unitPrice;
        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblOrderCart.refresh();

                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if (code.equals(colItemCode.getCellData(i))) {
                qty += (int) colQty.getCellData(i);
                total = qty * unitPrice;

                obList.get(i).setQty(qty);
                obList.get(i).setTot(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                return;
            }
        }

        obList.add(new OrderTm(
                code,
                description,
                qty,
                unitPrice,
                total,
                btn
        ));

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQty.clear();
    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnNewOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/owner_customer.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String cusId = cmbCusId.getValue();
        String pay_meth = cmbPayMethod.getValue();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());

        List<OrderTm> tmList = new ArrayList<>();

        for (OrderTm cartTm : obList) {
            tmList.add(cartTm);
        }

        var placeOrderDto = new OrderDto(orderId, date, pay_meth, cusId, tmList);

        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "order not completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event)  {
        String code = cmbProductId.getValue();

        txtQty.requestFocus();

        try {
            FinishedStockDto dto = itemModel.searchFinished(code);

            String desc = OwnerProductTypeModel.getName(dto.getProduct_id());
            double price = OwnerProductTypeModel.getPrice(dto.getProduct_id());

            lblDesc.setText(desc);
            lblPrice.setText(String.valueOf(price));
            lblQtyOn.setText(String.valueOf(dto.getAmount()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String id = cmbCusId.getValue();
        CustomerDto dto = customerModel.searchCustomer(id);

        lblCusName.setText(dto.getName());
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {

    }
}
