package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.FinishedStockDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinishedStockModel {

    public List<FinishedStockDto> getAllFinishedStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM finished_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<FinishedStockDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String finished_id = resultSet.getString(1);
            int amount = Integer.parseInt(resultSet.getString(2));
            String product_id = resultSet.getString(3);

            var dto = new FinishedStockDto(finished_id, amount, product_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteFinished(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM finished_stock WHERE finished_stock_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveFinished(FinishedStockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO finished_stock VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getFinished_id());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3, dto.getProduct_id());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateFinished(FinishedStockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE finished_stock SET finished_amount = ? WHERE finished_stock_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, String.valueOf(dto.getAmount()));
        pstm.setString(2, dto.getFinished_id());

        return pstm.executeUpdate() > 0;
    }

    public FinishedStockDto searchFinished(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM finished_stock WHERE finished_stock_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        FinishedStockDto dto = null;

        if(resultSet.next()) {
            String finished_id = resultSet.getString(1);
            int amount = Integer.parseInt(resultSet.getString(2));
            String product_id = resultSet.getString(3);

            dto = new FinishedStockDto(finished_id, amount, product_id);
        }

        return dto;
    }
}
