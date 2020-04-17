package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SeePicture extends BorderPane {
    private ArrayList<File> pictureFiles = new ArrayList<File>(); //图片的
    private int clickCount;                     // 计数器
    private int changeNum = 0;                  //缩放系数

    public SeePicture(File file, String nodePane) {

        load(file);     //加载图片信息
        if ("".equals(nodePane)) {
            clickCount = 0;
        } else {
            for (int i = 0; i < pictureFiles.size(); i++) {
                if (nodePane.equals(pictureFiles.get(i).getName())) {
                    clickCount = i;
                    break;
                }
            }
        }   //判断图片

        ///////////////组件////////////
        ImageView iv = new ImageView(new Image("file:" + this.pictureFiles.get(clickCount), 600, 600, true, true));
        Button previous = new Button("previous");
        Button next = new Button("next");
        Button enlarge = new Button("enlarge");
        Button small = new Button("small");
        Button ppt = new Button("ppt");
        previous.setPadding(new Insets(10, 10, 10, 10));
        next.setPadding(new Insets(10, 10, 10, 10));
        enlarge.setPadding(new Insets(10, 10, 10, 10));
        small.setPadding(new Insets(10, 10, 10, 10));
        ppt.setPadding(new Insets(10, 10, 10, 10));
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().addAll(small, ppt, enlarge);

        ///////////////监听器//////////////////
        previous.setOnMouseClicked(e -> {
            this.clickCount--;
            previous_next_action();
        });
        next.setOnMouseClicked(e -> {
            this.clickCount++;
            previous_next_action();
        });
        ppt.setOnMouseClicked(e -> {
        });
        small.setOnMouseClicked(e -> {
            this.changeNum--;
            enlarge_small_action();
        });
        enlarge.setOnMouseClicked(e -> {
            this.changeNum++;
            enlarge_small_action();
        });

        this.setCenter(iv);
        this.setLeft(previous);
        this.setRight(next);
        this.setTop(hBox);
        this.setAlignment(this.getLeft(), Pos.CENTER);
        this.setAlignment(this.getRight(), Pos.CENTER);
        previous.setContentDisplay(ContentDisplay.CENTER);
        next.setContentDisplay(ContentDisplay.CENTER);
        Scene scene = new Scene(this, 1000, 1000);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    //上一张下一张
    private void previous_next_action() {
        if (this.clickCount < 0) {
            Label label = new Label("这是第一张图片");
            Pane root = new Pane(label);
            Scene scene = new Scene(root);
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            this.clickCount++;
        } else if (this.clickCount > this.pictureFiles.size() - 1) {
            Label label = new Label("这是最后一张图片");
            Pane root = new Pane(label);
            Scene scene = new Scene(root);
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            this.clickCount--;
        } else {
            this.changeNum = 0;
            System.out.println(this.clickCount);
            ImageView iv = new ImageView(new Image("file:" + this.pictureFiles.get(clickCount), 600, 600, true, true));
            this.setCenter(iv);
        }
    }

    //加载图片文件
    private void load(File file) {
        System.out.println(file.getAbsolutePath());
        try {
            pictureFiles.clear();
            String parentPath = file.getParentFile().getAbsolutePath();
            String[] imageNames = getImageNames(parentPath);
            for (String imageName : imageNames) {
                String imagePath = parentPath + "\\" + imageName;
                pictureFiles.add(new File(imagePath));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //缩放功能（未完成）
    private void enlarge_small_action() {
        if (this.changeNum <= -5) {
            Label label = new Label("已是最小");
            Pane root = new Pane(label);
            Scene scene = new Scene(root);
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            this.changeNum = 0;
        } else if (this.changeNum >= 5) {
            Label label = new Label("已是最大");
            Pane root = new Pane(label);
            Scene scene = new Scene(root);
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            this.changeNum = 0;
        }
        ImageView iv = (ImageView) this.getCenter();
        iv.setFitWidth(600 * (changeNum * 0.1 + 1));
        iv.setFitHeight(600 * (changeNum * 0.1 + 1));
        iv.setPreserveRatio(true);
    }

    //筛选图片文件
    public String[] getImageNames(String parentPath) throws URISyntaxException {
        File parentDir = new File(parentPath);
        String[] pngs = parentDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith("jpg") || name.endsWith("png")
                        || name.endsWith("jpeg") || name.endsWith("gif")
                        || name.endsWith("bmp")) {
                    return true;
                }
                return false;
            }
        });
        return pngs;
    }
}