package lk.ijse.FianlArtWood.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerTm {
        private String id;
        private String name;
        private String address;
        private int tel;
        private Button btn;

}
