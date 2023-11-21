package lk.ijse.FianlArtWood.dto;

import lk.ijse.FianlArtWood.dto.tm.LogsTm;
import lk.ijse.FianlArtWood.dto.tm.OrderTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupOrderDto {
    private String sup_order_id;
    private  double price;
    private String type;
    private String sup_id;
    private String pay_meth;
    private List<LogsTm> tmList = new ArrayList<>();
}
