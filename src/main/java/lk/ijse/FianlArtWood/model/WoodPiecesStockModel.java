package lk.ijse.FianlArtWood.model;

import javafx.scene.control.Alert;
import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.WoodPiecesDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WoodPiecesStockModel {

    public static String generateNextWoodId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT wood_piece_id FROM wood_pieces_stock ORDER BY wood_piece_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentWoodId = null;

        if (resultSet.next()) {
            currentWoodId = resultSet.getString(1);
            return splitWoodId(currentWoodId);
        }
        return splitWoodId(null);
    }

    private static String splitWoodId(String currentWoodId) {
        if (currentWoodId != null) {
            String[] split = currentWoodId.split("W");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "W" + id;
        }
        return "W1";    }

    public static List<WoodPiecesDto> getAllWood() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM wood_pieces_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<WoodPiecesDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String quality = resultSet.getString(2);
            int amount = Integer.parseInt(resultSet.getString(3));
            String type = resultSet.getString(4);
            String log_id = resultSet.getString(5);

            var dto = new WoodPiecesDto(id, quality, amount, type, log_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean deleteWood(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM wood_pieces_stock WHERE wood_piece_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveWood(WoodPiecesDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql2 = "select wood_type, logs_amount from log_stock where logs_id = ?";
        PreparedStatement pstm2 = connection.prepareStatement(sql2);

        pstm2.setString(1,dto.getLogs_id());

        ResultSet resultSet = pstm2.executeQuery();

        String wood_type = "";
        int amount = 0;

        if (resultSet.next()) {
            wood_type = resultSet.getString(1);
            amount = resultSet.getInt(2);
        }

        connection.setAutoCommit(false);

        String sql = "INSERT INTO wood_pieces_stock VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getWood_piece_id());
        pstm.setString(2, dto.getQuality());
        pstm.setString(3, String.valueOf(dto.getAmount()));
        pstm.setString(4, wood_type);
        pstm.setString(5, dto.getLogs_id());

        boolean isSaved1 = false;

        if (amount != 0){
            boolean isSaved = pstm.executeUpdate() > 0;
            isSaved1 = false;

            if (isSaved){
                String sql1 = "update log_stock set logs_amount = 0 where logs_id = ?";
                PreparedStatement pstm1 = connection.prepareStatement(sql1);

                pstm1.setString(1, dto.getLogs_id());

                isSaved1 = pstm1.executeUpdate() > 0;

                if (isSaved1){
                    connection.commit();
                }

            }
            connection.rollback();
            connection.setAutoCommit(true);
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Not Enough Log").show();
        }

        return isSaved1;
    }

    public boolean updateWood(WoodPiecesDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql2 = "select wood_type from log_stock where logs_id = ?";
        PreparedStatement pstm2 = connection.prepareStatement(sql2);

        pstm2.setString(1,dto.getLogs_id());

        ResultSet resultSet = pstm2.executeQuery();

        String wood_type = "";

        if (resultSet.next()) {
            wood_type = resultSet.getString(1);
        }

        String sql = "UPDATE wood_pieces_stock SET quality = ?, wood_amount = ?, wood_type = ?, logs_id = ? WHERE wood_piece_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getQuality());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3, wood_type);
        pstm.setString(4, dto.getLogs_id());
        pstm.setString(5, dto.getWood_piece_id());

        return pstm.executeUpdate() > 0;
    }

    public WoodPiecesDto searchWood(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM wood_pieces_stock WHERE wood_piece_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        WoodPiecesDto dto = null;

        if(resultSet.next()) {
            String wood_piece_id = resultSet.getString(1);
            String quality = resultSet.getString(2);
            int amount = Integer.parseInt(resultSet.getString(3));
            String type = resultSet.getString(4);
            String log_id = resultSet.getString(5);

            dto = new WoodPiecesDto(wood_piece_id, quality, amount, type, log_id);
        }

        return dto;
    }
}
