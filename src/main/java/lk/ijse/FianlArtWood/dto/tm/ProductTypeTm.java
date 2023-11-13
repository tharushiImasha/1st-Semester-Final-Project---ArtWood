package lk.ijse.FianlArtWood.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class ProductTypeTm {
    private String product_id;
    private String product_name;
    private  String quality;
    private double price;
}
