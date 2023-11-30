package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class OwnerOrderModel {

    public static boolean saveOrder(String orderId, LocalDate date, String pay_meth, String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setDate(2, Date.valueOf(date));
        pstm.setString(3, cusId);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        int max = 0;
        while (resultSet.next()){
           String x = resultSet.getString(1);
            String[] y = x.split("O");
            int id = Integer.parseInt(y[1]);

            if (max < id){
                max = id;
            }

        }

        return "O" + ++max;
    }

}
