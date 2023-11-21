package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.FinanceDto;
import lk.ijse.FianlArtWood.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerFinanceModel {
    public List<FinanceDto> getAllFinance() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM finance";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<FinanceDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String finance_id = resultSet.getString(1);
            double amount = Double.parseDouble(resultSet.getString(2));
            String type = resultSet.getString(3);
            String pay_method_id = resultSet.getString(4);

            var dto = new FinanceDto(finance_id, amount, type, pay_method_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteFinance(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE sup_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
}
