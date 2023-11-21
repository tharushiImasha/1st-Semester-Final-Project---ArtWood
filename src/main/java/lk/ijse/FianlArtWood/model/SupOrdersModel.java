package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;

import java.sql.*;

public class SupOrdersModel {

    public static boolean saveOrder(String supOrderId, double price, String type, String supId, String payMeth) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO sup_orders VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, supOrderId);
        pstm.setDouble(2, price);
        pstm.setString(3, type);
        pstm.setString(4, supId);
        pstm.setString(4, payMeth);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT sup_order_id FROM sup_orders ORDER BY sup_order_id DESC LIMIT 1";
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
            String[] split = currentOrderId.split("S");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "S0" + id;
        }
        return "S01";
    }
}
