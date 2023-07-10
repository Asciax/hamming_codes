package application;
// Author : Robert C.
// July 2023

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

import static application.AppUtilities.*;

public class HammingApplication extends Application{

    @Override
    public void start(Stage MainStage) {


        SceneManager sceneManager = new SceneManager(MainStage);
        Scene scene = sceneManager.getMainScene();

        MainStage.setTitle("Hamming Application");
        MainStage.setScene(scene);
        MainStage.show();
    }



    private class SceneManager{
        private Scene mainscene;
        private Scene codeselection;
        private Scene inputscreen;

        public Scene getMainScene(){
            return this.mainscene;
        }

        public void setMainScene(Scene scene){
            this.mainscene = scene;
        }

        public Scene getCodeSelection(){
            return this.codeselection;
        }
        public void setCodeSelection(Scene scene){
            this.codeselection = scene;
        }

        public Scene getInputScreen(){
            return this.inputscreen;
        }

        public void setInputScreen(Scene scene) {
            this.inputscreen = scene;
        }

        public SceneManager(Stage stage){
            this.createCodeSelectionScene();
            this.createMainScene(stage);

        }

        Scene createCodeSelectionScene(){
            Group codeSelectGroup = new Group();
            this.codeselection = new Scene(codeSelectGroup, 500, 500);
            return this.codeselection;
        }

        Scene createMainScene(Stage mainStage){
            Text menuName = new Text(0, 200, "Main Menu");
            menuName.setFont(Font.font("Times New Roman", 20));

            Group root = new Group();
            addElement(root, menuName);
            VBox menu = mainMenu(mainStage);
            addElement(root, menu);

            this.mainscene = new Scene(root, 600, 600);
            menu.relocate(this.mainscene.getWidth()/2.0, this.mainscene.getHeight()/2.0);
            return this.mainscene;
        }

        private VBox mainMenu(Stage MainStage){
            VBox menu = new VBox();
            //menu.relocate(300.0, 300.0);

            //Choosing the linear encoding method changes scenes
            Button chooseCode = new Button("Choose Code");
            Scene codeselection = this.codeselection;
            chooseCode.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            MainStage.setScene(codeselection);
                        }
                    }
            );
            //Exit button for exiting the application.
            Button exitButton = new Button("EXIT");
            exitButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Platform.exit();
                        }
                    }
            );

            menu.getChildren().addAll(chooseCode,exitButton);
            return menu;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
