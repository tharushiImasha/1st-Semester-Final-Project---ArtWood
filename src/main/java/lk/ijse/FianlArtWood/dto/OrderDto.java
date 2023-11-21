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
    private double total;
    private List<OrderTm> tmList = new ArrayList<>();

    public OrderDto(String orderId, LocalDate date, String pay_meth, String cusId){
        this.orderId = orderId;
        this.date = date;
        this.pay_meth = pay_meth;
        this.cusId = cusId;
    }

}
