package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.controller.OwnerOrderController;
import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {
    private final OwnerOrderModel orderModel = new OwnerOrderModel();
    private final FinishedStockModel finishedStockModel = new FinishedStockModel();
    private final OrderDetailModel orderDetailModel = new OrderDetailModel();


    public boolean placeOrder(OrderDto pDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = OwnerOrderModel.saveOrder(pDto.getOrderId(), pDto.getDate(), pDto.getPay_meth(), String.valueOf(pDto.getTel()));

            if (isOrderSaved) {
                boolean isUpdated = finishedStockModel.updateItem(pDto.getTmList());

                if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetail(pDto.getOrderId(), pDto.getTmList());
                    if(isOrderDetailSaved) {
                        boolean isFinanceUpdated = FinanceModel.IncreaseFinance(pDto.getPay_meth(), pDto.getTotal());
                        if (isFinanceUpdated) {
                            connection.commit();
                            result = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return result;
    }
}
