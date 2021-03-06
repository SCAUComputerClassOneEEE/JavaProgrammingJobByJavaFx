package model;

import toolpane.FunctionBar;
import toolpane.MenuPane;
import mainpane.SeePicturePane;
import mainpane.ViewerPane;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)//调用父类构造方法
@Data
@Setter
@Getter
public class PictureNode extends Label{
    private File file;//图片对应的文件
    private Image image;//由文件加载出来的Image
    private ImageView imageView;
    private Text pictureName;
    private MenuPane menuPane = new MenuPane();
    public int count = 0;//点击次数
    private boolean isLocked = false;

    //保存所以被选中的图片节点，图片节点中包含图片数据
    protected static ArrayList<PictureNode> selectedPictures = new ArrayList<>();

    public static ArrayList<File> getSelectedPictureFiles() {
        return selectedPictureFiles;
    }

    protected static ArrayList<File> selectedPictureFiles = new ArrayList<>();

    public PictureNode(File aPictureFile) {

        //初始化图片
        initializeAPicture(aPictureFile);

        //为图片节点添加监听器
        addListener2PictureNode();
    }

    //为图片节点添加监听器
    private void addListener2PictureNode(){
        this.setContextMenu(menuPane.getContextMenu());
        this.setOnMouseClicked(e -> {

            //如果是左键点击
            if (e.getButton() == MouseButton.PRIMARY) {
                System.out.println(this.file.getName());

                //记录该节点被点击的总次数
                this.count += e.getClickCount();

                //如果control键没有按下，先清空所有被选择的图片
                if (!e.isControlDown()) {
                    for (PictureNode each : selectedPictures) {
//                        each.setStyle("-fx-background-color: transparent;");
//                        each.setCount(0);
                        if (!each.getLocked()) {
                            each.setStyle("-fx-background-color: transparent;");
                        }else{
                            each.setStyle("-fx-background-color: lightgray;");
                        }
                        each.setCount(0);
                    }
                    selectedPictures.clear();
                }

                //判断当前节点是否被选中
                if (!selectedPictures.contains(this) && this.count % 2 == 1) {
                    this.setStyle("-fx-background-color: #8bb9ff;");
                    this.count = 0; selectedPictures.add(this);
                } else {
                    if (!this.isLocked) {
                        this.setStyle("-fx-background-color: transparent;");
                    } else {
                        this.setStyle("-fx-background-color: lightgray");
                    }
                    this.setCount(0);
                    selectedPictures.remove(this);
                }

                    System.out.println("选中" + selectedPictures.size()+"张");

                showSelectedPictureNumber();//更新被选中的数量
            }

            //双击图片进入查看界面
            if (e.getClickCount() == 2&&e.getButton()==MouseButton.PRIMARY) {
                //e.getClickCount() == 2,双击
                //创建一个SeePicture类，传参数进去
                new SeePicturePane(this.file, this.file.getName());
            }
            if(selectedPictures.size()>0){
                FunctionBar.upLoad.setDisable(false);
            }
            else {
                FunctionBar.upLoad.setDisable(true);
            }
        });
    }

    //显示选中多少张
    private void showSelectedPictureNumber(){
        ViewerPane.selectedNumberOfPicture.setText(String.format("-选中%d张",PictureNode.selectedPictures.size()));
    }

    //初始化一张图片
    private void initializeAPicture(File aPictureFile){
        this.setWrapText(true);
        this.file = aPictureFile;
        if(!aPictureFile.exists()){
            System.out.println(aPictureFile.getAbsolutePath()+"不存在");
        }
        this.setPickOnBounds(true);
        this.setGraphicTextGap(10);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setContentDisplay(ContentDisplay.TOP);
        this.setMaxSize(120,150);
        this.setMinSize(120,150);
        this.image = new Image("file:" + aPictureFile.getAbsolutePath(), 100, 100,
                true, true);
        this.imageView = new ImageView(image);
        this.pictureName = new Text(aPictureFile.getName());
        this.setText(pictureName.getText());
        this.setGraphic(imageView);
    }

    public static ArrayList<PictureNode> getSelectedPictures() {
        return selectedPictures;
    }

    public boolean getLocked(){
        return this.isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

}
