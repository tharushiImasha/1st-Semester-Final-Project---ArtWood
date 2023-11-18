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
        private Button btn;

        public CustomerTm(String id, String name, String address){
                this.address = address;
                this.id = id;
                this.name = name;
        }
}
