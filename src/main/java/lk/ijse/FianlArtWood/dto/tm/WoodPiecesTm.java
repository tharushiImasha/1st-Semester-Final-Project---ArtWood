package lk.ijse.FianlArtWood.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WoodPiecesTm {
    private String wood_piece_id;
    private String quality;
    private int amount;
    private String wood_type;
    private String logs_id;
}
