package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.tm.LogsTm;
import lk.ijse.FianlArtWood.dto.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SupOrderDetailModel {
    public static boolean saveSupOrderDetail(String orderId, List<LogsTm> tmList) throws SQLException {
        for (LogsTm cartTm : tmList) {
            if(!saveSupOrderDetail(orderId, cartTm)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveSupOrderDetail(String orderId, LogsTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO sup_order_details VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cartTm.getLogs_id());

        return pstm.executeUpdate() > 0;
    }
}
