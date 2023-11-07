package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.LoginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormModel {

    public LoginDto login(String userName, String pw) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select e.job_role, l.* from employee e join login l on e.emp_id = l.emp_id where user_name = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet resultSet = pstm.executeQuery();

        LoginDto dto = null;

        if (resultSet.next()) {

            if (pw.equals(resultSet.getString(3))){

                String job_role = resultSet.getString(1);
                String log_user_name = resultSet.getString(2);
                String log_pw = resultSet.getString(3);
                String emp_id = resultSet.getString(4);

                dto = new LoginDto(log_user_name, log_pw, emp_id, job_role);
            }
            System.out.println(dto.getJob_role());
        }

        return dto;
    }
}
