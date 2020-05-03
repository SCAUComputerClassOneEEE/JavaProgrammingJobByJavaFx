//{
//  "test": {
//    "host": "https://httpbin.org",
//    "show_env": "1",
//
//    // Define all sensitive information in http-client.private.env.json
//    "username": "",
//    "password": ""
//  }
//}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 package controller;
package controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.TreeNode;

import java.io.File;

public class PPT {

    private Timeline timeline;
    private int count = 0;
    private ImageView imageView = new ImageView();
    private BorderPane borderPane;
    private Stage stage;
    private Button star = createButton("star");
    private Button end = createButton("end");
    private Button leave = createButton("leave");
    private TreeNode treeNode;
    private SimpleObjectProperty<TreeNode> selectedFolderProperty;

    public PPT(TreeNode treeNode){
        this.treeNode = treeNode;
        this.treeNode.setImages();
        selectedFolderProperty = new SimpleObjectProperty<>(treeNode);
        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().add(star);
        hBox.getChildren().add(end);
        borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        borderPane.setBottom(hBox);
        borderPane.setLeft(leave);
        borderPane.setStyle("-fx-background-color: White;");
        Scene scene = new Scene(borderPane);
        stage = new Stage();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        show();
        star.setOnMouseClicked(e-> timeline.play() );
        end.setOnMouseClicked(e -> timeline.pause() );
        leave.setOnMouseClicked(e -> stage.close() );
    }

    public void show() {

        timeline = new Timeline();

        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyValue keyValue = new KeyValue(imageView.scaleXProperty(), 2);
        KeyValue keyValue2 = new KeyValue(imageView.scaleYProperty(), 2);
        Duration duration = Duration.seconds(3);

        EventHandler<ActionEvent> onFinished = (ActionEvent t) -> {

            if (count < selectedFolderProperty.getValue().getImages().size()) {
                imageView.setImage(new Image("file:" + selectedFolderProperty.getValue().getImages().get(count)));
                imageView.setFitHeight(500);
                imageView.setFitWidth(600);
            } else if (count == selectedFolderProperty.getValue().getImages().size()) {
                count = 0;
                timeline.stop();
            }
            count++;
        };


        KeyFrame keyFrame1 = new KeyFrame(duration, onFinished, keyValue, keyValue2);

        timeline.getKeyFrames().add(keyFrame1);
    }

    private Button createButton(String buttonName) {
        Button button = new Button();
        button.setId(buttonName);
        button.setPadding(new Insets(10, 10, 10, 10));
        //button.setText(buttonName);
        button.setStyle("-fx-background-color:#ffffff;");
        button.setGraphic(new ImageView(new Image("file:"+new File("icon/"+buttonName+".png"),30, 30,
                true, true)));
        return button;
    }

}
