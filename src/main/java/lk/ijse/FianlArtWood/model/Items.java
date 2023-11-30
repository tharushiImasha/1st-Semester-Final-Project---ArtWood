package lk.ijse.FianlArtWood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Items {
    private String name;
    private String imgSrc;
    private double price;
    private String color;
}
