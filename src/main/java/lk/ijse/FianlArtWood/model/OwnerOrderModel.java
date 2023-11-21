package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;

public class OwnerOrderModel {

    public static boolean saveOrder(String orderId, LocalDate date, String pay_meth, String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setDate(2, Date.valueOf(date));
        pstm.setString(3, pay_meth);
        pstm.setString(4, cusId);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "O" + id;
        }
        return "O1";
    }
}
