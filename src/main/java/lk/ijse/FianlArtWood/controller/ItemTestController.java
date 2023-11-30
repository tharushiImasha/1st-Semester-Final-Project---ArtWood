package lk.ijse.FianlArtWood.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.FianlArtWood.MyListener;
import lk.ijse.FianlArtWood.model.Items;

public class ItemTestController {
    @FXML
    private ImageView img;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private void click(MouseEvent mouseEvent){
        myListener.onClickListener(items);
    }

    private Items items;
    private MyListener myListener;

    public void  setData(Items items, MyListener myListener){
        this.items = items;
        this.myListener = myListener;

        lblName.setText(items.getName());
        lblPrice.setText(String.valueOf("Rs. "+items.getPrice()));
        Image image = new Image(getClass().getResourceAsStream(items.getImgSrc()));
        img.setImage(image);
    }
}
