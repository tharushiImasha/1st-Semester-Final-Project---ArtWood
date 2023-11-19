package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.LoginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditProfileFormModel {

    public boolean saveUser(LoginDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql1 = "select job_role from employee where emp_id = ?";
        PreparedStatement pstm1 = connection.prepareStatement(sql1);

        pstm1.setString(1, dto.getEmp_id());

        ResultSet resultSet = pstm1.executeQuery();

        String job_role = "Not Employee";

        if (resultSet.next()) {
            job_role = resultSet.getString(1);
        }

        boolean isSaved = false;

        if (job_role.equals("cashier")||job_role.equals("stock_manager")) {

            String sql = "INSERT INTO login VALUES(?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getUserName());
            pstm.setString(2, dto.getPw());
            pstm.setString(3, dto.getEmp_id());

            isSaved = pstm.executeUpdate() > 0;
        }
        return isSaved;

    }

    public boolean deleteUser(String emp_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM login WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, emp_id);

        return pstm.executeUpdate() > 0;
    }

    public LoginDto searchCustomer(String emp_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM login WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, emp_id);

        ResultSet resultSet = pstm.executeQuery();

        LoginDto dto = null;

        if(resultSet.next()) {
            String user_name = resultSet.getString(1);
            String pw = resultSet.getString(2);
            String log_emp_id = resultSet.getString(3);

            dto = new LoginDto(user_name, pw, log_emp_id);
            System.out.println(user_name);
            System.out.println(emp_id);
        }

        return dto;
    }

    public boolean updateUser(LoginDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE login SET user_name = ?, pw = ? WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getUserName());
        pstm.setString(2, dto.getPw());
        pstm.setString(3, dto.getEmp_id());

        return pstm.executeUpdate() > 0;
    }

    public List<LoginDto> getAllUsers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM login";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<LoginDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String user_name = resultSet.getString(1);
            String pw = resultSet.getString(2);
            String emp_id = resultSet.getString(3);

            var dto = new LoginDto(user_name, pw, emp_id);
            dtoList.add(dto);
        }
        return dtoList;

    }

    public static String getJob(String emp_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql1 = "select job_role from employee where emp_id = ?";
        PreparedStatement pstm1 = connection.prepareStatement(sql1);

        pstm1.setString(1, emp_id);

        ResultSet resultSet = pstm1.executeQuery();

        String job_role = "Not Employee";

        if (resultSet.next()) {
            job_role = resultSet.getString(1);
        }
        return job_role;
    }
}
