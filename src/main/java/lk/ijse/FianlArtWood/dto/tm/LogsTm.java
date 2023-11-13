package lk.ijse.FianlArtWood.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LogsTm {
    private String logs_id;
    private String wood_type;
    private int log_amount;
}
