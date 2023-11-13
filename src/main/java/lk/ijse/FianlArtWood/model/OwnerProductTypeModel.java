package lk.ijse.FianlArtWood.model;

import lk.ijse.FianlArtWood.db.DbConnection;
import lk.ijse.FianlArtWood.dto.CustomerDto;
import lk.ijse.FianlArtWood.dto.ProductTypeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerProductTypeModel {
    public List<ProductTypeDto> getAllProduct() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM product_type";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ProductTypeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String product_id = resultSet.getString(1);
            String product_name = resultSet.getString(2);
            String quality = resultSet.getString(3);
            double price = Double.parseDouble(resultSet.getString(4));

            var dto = new ProductTypeDto(product_id, product_name, quality, price);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteProduct(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM product_type WHERE product_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveProduct(ProductTypeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO product_type VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProduct_id());
        pstm.setString(2, dto.getProduct_name());
        pstm.setString(3, dto.getQuality());
        pstm.setString(4, String.valueOf(dto.getPrice()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateProduct(ProductTypeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE product_type SET product_name = ?, quality = ?, price = ? WHERE product_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProduct_name());
        pstm.setString(2, dto.getQuality());
        pstm.setString(3, String.valueOf(dto.getPrice()));
        pstm.setString(4, dto.getProduct_id());

        return pstm.executeUpdate() > 0;
    }

    public ProductTypeDto searchProduct(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM product_type WHERE product_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ProductTypeDto dto = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String quality = resultSet.getString(3);
            double price = resultSet.getDouble(4);

            dto = new ProductTypeDto(cus_id, cus_name, quality, price);
        }

        return dto;
    }
}