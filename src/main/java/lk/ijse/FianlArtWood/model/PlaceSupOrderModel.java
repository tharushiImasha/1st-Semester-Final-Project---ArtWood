package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.OrderDto;
import lk.ijse.FianlArtWood.dto.SupOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceSupOrderModel {
    public static boolean placeSupOrder(SupOrderDto pDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = SupOrdersModel.saveOrder(pDto.getSup_order_id(), pDto.getPrice(), pDto.getType(), pDto.getSup_id(), pDto.getPay_meth());
            if (isOrderSaved) {
                    boolean isOrderDetailSaved = SupOrderDetailModel.saveSupOrderDetail(pDto.getSup_order_id(), pDto.getTmList());

                    if(isOrderDetailSaved) {
                        boolean isFinanceUpdated = FinanceModel.reduceFinance(pDto.getPay_meth(), pDto.getPrice());

                        if (isFinanceUpdated) {
                            System.out.println(isFinanceUpdated);
                            connection.commit();
                            result = true;
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
