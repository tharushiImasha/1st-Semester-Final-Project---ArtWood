package lk.ijse.FianlArtWood.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class PendingStockTm {
    private String pending_id;
    private String emp_id;
    private String wood_piece_id;
    private String finished_id;
    private Button btn;
    private Button btn1;
}
