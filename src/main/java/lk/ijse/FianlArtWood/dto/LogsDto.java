package lk.ijse.FianlArtWood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LogsDto {
    private String logs_id;
    private String wood_type;
    private int log_amount;
}
