package lk.ijse.FianlArtWood.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lk.ijse.FianlArtWood.MyListener;
import lk.ijse.FianlArtWood.model.Items;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    @FXML
    private VBox chosenCard;

    @FXML
    private ImageView chosenImage;

    @FXML
    private Label chosenName;

    @FXML
    private Label chosenPrice;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private List<Items> items = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private List<Items> getData(){
        List<Items> items = new ArrayList<>();
        Items items1;

        items1 = new Items();
        items1.setName("Fisherman Teak");
        items1.setPrice(7000);
        items1.setImgSrc("/images/fisherm.png");
        items1.setColor("BBAB8C");
        items.add(items1);

        items1 = new Items();
        items1.setName("Elephant Teak");
        items1.setPrice(5000);
        items1.setImgSrc("/images/elephant.png");
        items1.setColor("A77458");
        items.add(items1);

        items1 = new Items();
        items1.setName("Statue Teak");
        items1.setPrice(3000);
        items1.setImgSrc("/images/temple.png");
        items1.setColor("E48F45");
        items.add(items1);

        items1 = new Items();
        items1.setName("Elephant Rosewood");
        items1.setPrice(3000);
        items1.setImgSrc("/images/elephant2.png");
        items1.setColor("F0DBAF");
        items.add(items1);

        items1 = new Items();
        items1.setName("Fisherman Rosewood");
        items1.setPrice(7000);
        items1.setImgSrc("/images/fisherman2.png");
        items1.setColor("B0A695");
        items.add(items1);

        items1 = new Items();
        items1.setName("Statue Rosewood");
        items1.setPrice(3000);
        items1.setImgSrc("/images/temple2.png");
        items1.setColor("FCE09B");
        items.add(items1);



        items1 = new Items();
        items1.setName("Statue Teak");
        items1.setPrice(3000);
        items1.setImgSrc("/images/temple.png");
        items1.setColor("E48F45");
        items.add(items1);

        items1 = new Items();
        items1.setName("Elephant Rosewood");
        items1.setPrice(3000);
        items1.setImgSrc("/images/elephant2.png");
        items1.setColor("F0DBAF");
        items.add(items1);

        items1 = new Items();
        items1.setName("Fisherman Rosewood");
        items1.setPrice(7000);
        items1.setImgSrc("/images/fisherman2.png");
        items1.setColor("B0A695");
        items.add(items1);

        items1 = new Items();
        items1.setName("Statue Rosewood");
        items1.setPrice(3000);
        items1.setImgSrc("/images/temple2.png");
        items1.setColor("FCE09B");
        items.add(items1);

        items1 = new Items();
        items1.setName("Fisherman Rosewood");
        items1.setPrice(7000);
        items1.setImgSrc("/images/fisherman2.png");
        items1.setColor("B0A695");
        items.add(items1);

        items1 = new Items();
        items1.setName("Statue Rosewood");
        items1.setPrice(3000);
        items1.setImgSrc("/images/temple2.png");
        items1.setColor("FCE09B");
        items.add(items1);

        items1 = new Items();
        items1.setName("Fisherman Teak");
        items1.setPrice(7000);
        items1.setImgSrc("/images/fisherm.png");
        items1.setColor("BBAB8C");
        items.add(items1);

        items1 = new Items();
        items1.setName("Elephant Teak");
        items1.setPrice(5000);
        items1.setImgSrc("/images/elephant.png");
        items1.setColor("A77458");
        items.add(items1);

        items1 = new Items();
        items1.setName("Statue Teak");
        items1.setPrice(3000);
        items1.setImgSrc("/images/temple.png");
        items1.setColor("E48F45");
        items.add(items1);


        return items;
    }


    private void setChosenCard(Items items){
        chosenName.setText(items.getName());
        chosenPrice.setText(String.valueOf(items.getPrice()));
        image = new Image(getClass().getResourceAsStream(items.getImgSrc()));
        chosenImage.setImage(image);
        chosenCard.setStyle("-fx-background-color: #"+ items.getColor() +";\n" +
                "      -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        items.addAll(getData());
        scroll.contentProperty();

        if (items.size()>0){
            setChosenCard(items.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Items items) {
                    setChosenCard(items);
                }
            };
        }

        int column = 0;
        int raw = 0;

        try {
            for (int i = 0; i < items.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/item_test.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();


                ItemTestController itemTestController = fxmlLoader.getController();
                itemTestController.setData(items.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    raw++;
                }

                grid.add(anchorPane, column++, raw);


                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));

            }

        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }
}
