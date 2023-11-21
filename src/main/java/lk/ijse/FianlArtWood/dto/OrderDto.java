package lk.ijse.FianlArtWood.dto;

import lk.ijse.FianlArtWood.dto.tm.OrderTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDto {
    private String orderId;
    private LocalDate date;
    private String pay_meth;
    private String cusId;
    private List<OrderTm> tmList = new ArrayList<>();
}
