package lk.ijse.FianlArtWood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class PendingStockDto {
    private String pending_id;
    private int amount;
    private String emp_id;
    private String wood_piece_id;
    private String finished_id;

    public PendingStockDto(String pending_id, String emp_id, String wood_piece_id, String finished_id){
        this.finished_id = finished_id;
        this.pending_id = pending_id;
        this.emp_id = emp_id;
        this.wood_piece_id = wood_piece_id;
    }
}
