package lk.ijse.FianlArtWood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierDto {
    private String id;
    private String name;
    private String address;
    private String email;
}
