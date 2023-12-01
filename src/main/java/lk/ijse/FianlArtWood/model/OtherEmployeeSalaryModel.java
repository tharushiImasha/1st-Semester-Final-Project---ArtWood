package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.OtherSalaryDto;
import lk.ijse.FianlArtWood.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OtherEmployeeSalaryModel {
    public static String generateNextSalaryId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT other_salary_id FROM otherSalary";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        int max = 0;
        while (resultSet.next()){
            String x = resultSet.getString(1);
            String[] y = x.split("OSE");
            int id = Integer.parseInt(y[1]);

            if (max < id){
                max = id;
            }

        }

        return "OSE" + ++max;
    }

    public static boolean saveSalary(String empId, double amount) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO otherSalary VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, generateNextSalaryId());
        pstm.setString(2, String.valueOf(amount));
        pstm.setString(3, empId);

        return pstm.executeUpdate() > 0;
    }

    public static List<OtherSalaryDto> getAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM otherSalary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<OtherSalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            double amount = Double.parseDouble(resultSet.getString(2));
            String emp_id = resultSet.getString(3);

            var dto = new OtherSalaryDto(id, amount, emp_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean updateSalary(OtherSalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE otherSalary SET emp_id = ?, amount = ? WHERE other_salary_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3, dto.getOther_salary_id());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteSalary(String salaryId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM otherSalary WHERE other_salary_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, salaryId);

        return pstm.executeUpdate() > 0;
    }
}
