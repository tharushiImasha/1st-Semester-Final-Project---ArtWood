package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.LogsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogsStockModel {

    public static List<LogsDto> getAllLogs() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM log_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<LogsDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String log_id = resultSet.getString(1);
            String wood_type = resultSet.getString(2);
            int log_amount = Integer.parseInt(resultSet.getString(3));

            var dto = new LogsDto(log_id, wood_type, log_amount);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean deleteLogs(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM log_stock WHERE logs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextLogId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT logs_id FROM log_stock ORDER BY logs_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentLogId = null;

        if (resultSet.next()) {
            currentLogId = resultSet.getString(1);
            return splitLogId(currentLogId);
        }
        return splitLogId(null);
    }

    private static String splitLogId(String currentLogId) {    //O008
        if (currentLogId != null) {
            String[] split = currentLogId.split("L");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "L" + id;
        }
        return "L1";
    }

    public boolean saveLogs(LogsDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO log_stock VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getLogs_id());
        pstm.setString(2, dto.getWood_type());
        pstm.setString(3, String.valueOf(dto.getLog_amount()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateLogs(LogsDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE log_stock SET wood_type = ?, logs_amount = ? WHERE logs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getWood_type());
        pstm.setString(2, String.valueOf(dto.getLog_amount()));
        pstm.setString(3, dto.getLogs_id());

        return pstm.executeUpdate() > 0;
    }

    public LogsDto searchLogs(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM log_stock WHERE logs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        LogsDto dto = null;

        if(resultSet.next()) {
            String log_id = resultSet.getString(1);
            String log_type = resultSet.getString(2);
            int log_amount = Integer.parseInt(resultSet.getString(3));

            dto = new LogsDto(log_id, log_type, log_amount);
        }

        return dto;
    }
}
