package lk.ijse.FianlArtWood.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierTm {
    private String sup_id;
    private String sup_name;
    private String address;
    private String email;
}
