package lk.ijse.FianlArtWood.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FinishedStockTm {
    private String finished_id;
    private int amount;
    private String product_id;
    private Button btn;
}
