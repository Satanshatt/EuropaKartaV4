import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class PathFinder extends Application {

    Scanner input = new Scanner(System.in);
    Menu fileMenu;
    Pane center;
    Stage stage;
    Scene scene;

    public void start(Stage stage){
        this.stage = stage;
        BorderPane root = new BorderPane();
        center = new Pane();
        root.setCenter(center);

        VBox vBox = new VBox();
        root.setTop(vBox);

        MenuBar menuBar = new MenuBar();
        vBox.getChildren().add(menuBar);

        fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);


        FlowPane top = new FlowPane();
        vBox.getChildren().add(top);
        root.setStyle("-fx-font-size:16");
        Button findPathButton = new Button("Find path");
        Button showConnectionButton = new Button("Show connection");
        Button newPlaceButton = new Button("New Place");
        Button newConnectionButton = new Button("New Connection");
        Button changeConnectionButton = new Button("Change Connection");

        top.getChildren().addAll(findPathButton, showConnectionButton, newPlaceButton, newConnectionButton, changeConnectionButton);


        findPathButton.setOnAction(new Handler());

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    class Handler implements EventHandler<ActionEvent>{

        public void handle(ActionEvent eventHandle){
            Pane root = new FlowPane();
            Label nameOfPlaceLabel = new Label("Name of place: ");
            TextField nameField = new TextField();
            root.getChildren().addAll(nameOfPlaceLabel, nameField);

            Scene scene = new Scene(root);


        }



    }



}

