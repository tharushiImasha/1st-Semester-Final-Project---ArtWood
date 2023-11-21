package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public boolean saveOrderDetail(String orderId, List<OrderTm> tmList) throws SQLException {
        for (OrderTm cartTm : tmList) {
            if(!saveOrderDetail(orderId, cartTm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(String orderId, OrderTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_details VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cartTm.getCode());
        pstm.setInt(3, cartTm.getQty());
        pstm.setDouble(4, cartTm.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }
}
