package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerEmployeeModel {

    public static boolean employeeAvailability(String emp_id, String availability) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET status = ? WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, availability);
        pstm.setString(2, emp_id);

        return pstm.executeUpdate() > 0;
    }

    public static String getName(String job) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select name from employee where job_role = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, job);

        ResultSet resultSet = pstm.executeQuery();

        String name = "";

        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    public boolean deleteEmployee(String empId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, empId);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveUser(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, String.valueOf(dto.getTel()));
        pstm.setString(5, dto.getStatus());
        pstm.setString(6, dto.getJob_role());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name = ?, address = ?, tel = ?, job_role = ? WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, String.valueOf(dto.getTel()));
        pstm.setString(4, dto.getJob_role());
        pstm.setString(5, dto.getEmp_id());

        return pstm.executeUpdate() > 0;
    }

    public EmployeeDto searchEmployee(String empId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, empId);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int tel = Integer.parseInt(resultSet.getString(4));
            String job_role = resultSet.getString(6);

            dto = new EmployeeDto(emp_id, name, address, tel, job_role);

        }

        return dto;
    }

    public static List<EmployeeDto> getAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int tel = Integer.parseInt(resultSet.getString(4));
            String status = resultSet.getString(5);
            String job_role = resultSet.getString(6);

            var dto = new EmployeeDto(emp_id, name, address, tel, status, job_role);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<EmployeeDto> getAllEmployeesForCombo() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE status = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, "Available");

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int tel = Integer.parseInt(resultSet.getString(4));
            String status = resultSet.getString(5);
            String job_role = resultSet.getString(6);

            var dto = new EmployeeDto(emp_id, name, address, tel, status, job_role);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
