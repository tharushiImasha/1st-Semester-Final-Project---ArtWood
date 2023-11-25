package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.LoginDto;
import lk.ijse.FianlArtWood.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {

    public static String generateNextCustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT salary_id FROM salary ORDER BY salary_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCustomerId = null;

        if (resultSet.next()) {
            currentCustomerId = resultSet.getString(1);
            return splitCustomerId(currentCustomerId);
        }
        return splitCustomerId(null);
    }

    private static String splitCustomerId(String currentCustomerId) {    //O008
        if (currentCustomerId != null) {
            String[] split = currentCustomerId.split("SE");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "SE" + id;
        }
        return "SE1";
    }

    public static boolean saveSalary(String empId) throws SQLException {

        double amount = 2000;

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO salary VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, generateNextCustomerId());
        pstm.setString(2, String.valueOf(amount));
        pstm.setString(3, empId);

        return pstm.executeUpdate() > 0;
    }

    public List<SalaryDto> getAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            double amount = Double.parseDouble(resultSet.getString(2));
            String emp_id = resultSet.getString(3);

            var dto = new SalaryDto(id, amount, emp_id);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
