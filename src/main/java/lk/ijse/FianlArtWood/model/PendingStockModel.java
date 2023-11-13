package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.LogsDto;
import lk.ijse.FianlArtWood.dto.PendingStockDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PendingStockModel {

    public List<PendingStockDto> getAllPendings() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM pending_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PendingStockDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String pending_id = resultSet.getString(1);
            String emp_id = resultSet.getString(2);
            String wood_piece_id = resultSet.getString(3);
            String finished_id = resultSet.getString(4);
            String product_id = resultSet.getString(5);

            var dto = new PendingStockDto(pending_id, emp_id, wood_piece_id, finished_id, product_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deletePending(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM pending_stock WHERE pending_stock_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean savePending(PendingStockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO pending_stock VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPending_id());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3, String.valueOf(dto.getEmp_id()));
        pstm.setString(4, String.valueOf(dto.getWood_piece_id()));
        pstm.setString(5, String.valueOf(dto.getFinished_id()));
        pstm.setString(6, String.valueOf(dto.getProduct_id()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updatePending(PendingStockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE pending_stock SET emp_id = ?, wood_piece_id = ?, finished_stock_id = ?, product_id = ? WHERE pending_stock_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2, dto.getWood_piece_id());
        pstm.setString(3, dto.getFinished_id());
        pstm.setString(4, dto.getProduct_id());
        pstm.setString(5, dto.getPending_id());

        return pstm.executeUpdate() > 0;
    }

    public PendingStockDto searchPending(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM pending_stock WHERE pending_stock_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        PendingStockDto dto = null;

        if(resultSet.next()) {
            String pending_id = resultSet.getString(1);
            String emp_id = resultSet.getString(3);
            String wood_piece_id = resultSet.getString(4);
            String finished_id = resultSet.getString(5);
            String product_id = resultSet.getString(5);

            dto = new PendingStockDto(pending_id, emp_id, wood_piece_id, finished_id, product_id);
        }

        return dto;
    }
}