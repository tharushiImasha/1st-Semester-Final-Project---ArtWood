package lk.ijse.FianlArtWood.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class OrderTm {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button btn;
}
