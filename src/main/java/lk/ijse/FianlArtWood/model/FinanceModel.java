package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static double loadCash(String cash) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT amount FROM finance where pay_method = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, cash);

        ResultSet resultSet = pstm.executeQuery();

        double amount = 0;

        if(resultSet.next()) {
             amount = Double.parseDouble(resultSet.getString(1));
        }

        return amount;
    }

    public static double loadCard(String card) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT amount FROM finance where pay_method = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, card);

        ResultSet resultSet = pstm.executeQuery();

        double amount = 0;

        if(resultSet.next()) {
             amount = Double.parseDouble(resultSet.getString(1));
        }

        return amount;
    }
}
