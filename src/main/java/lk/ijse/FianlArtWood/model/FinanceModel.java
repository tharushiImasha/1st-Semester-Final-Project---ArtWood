package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FinanceModel {
    public static boolean reduceFinance(String pay_method, double amount) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE finance SET amount = amount - ? WHERE pay_method = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDouble(1, amount);
        pstm.setString(2, pay_method);

        return pstm.executeUpdate() > 0;
    }

    public static boolean IncreaseFinance(String pay_method, double amount) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE finance SET amount = amount + ? WHERE pay_method = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDouble(1, amount);
        pstm.setString(2, pay_method);

        return pstm.executeUpdate() > 0;
    }
}
