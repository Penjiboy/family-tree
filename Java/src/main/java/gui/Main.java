package gui;//package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private int windowWidth = 600;
    private int windowHeight = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage window = primaryStage;
        window.setHeight(windowHeight);
        window.setWidth(windowWidth);
        Scene homeScene = new Scene(setupHomeLayout());

        window.setScene(homeScene);
        window.show();
    }

    /**
     * Setup the layout for the home screen
     * @return AnchorPane containing all the elements of the home screen
     * @throws IllegalArgumentException if image cannot be opened
     */
    private AnchorPane setupHomeLayout() throws IllegalArgumentException {
        AnchorPane layout = new AnchorPane();

        VBox menu = new VBox();
        HBox menuContainer = new HBox();
        StackPane menuStackPane = new StackPane();

        Text menuHeader = new Text("Main Menu");
        Button existingTreesButton  = new Button("Existing Trees");
        Button newTreeButton = new Button("New Tree");
        ImageView treeImage = new ImageView("src/resources/images/tree.jpg");
        Label text = new Label("Know your roots!");

        //Organizing the Content
        menuStackPane.getChildren().add()

        return layout;
    }
}
