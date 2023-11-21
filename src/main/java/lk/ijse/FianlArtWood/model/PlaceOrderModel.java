package lk.ijse.FianlArtWood.model;

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

            boolean isOrderSaved = OwnerOrderModel.saveOrder(pDto.getOrderId(), pDto.getDate(), pDto.getPay_meth(), pDto.getCusId());
            if (isOrderSaved) {
                boolean isUpdated = finishedStockModel.updateItem(pDto.getTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetail(pDto.getOrderId(), pDto.getTmList());
                    if(isOrderDetailSaved) {
                        connection.commit();
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}
