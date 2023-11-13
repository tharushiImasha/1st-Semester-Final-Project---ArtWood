package lk.ijse.FianlArtWood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class ProductTypeDto {
    private String product_id;
    private String product_name;
    private  String quality;
    private double price;
}
