package application;
// Author : Robert C.
// July 2023

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
        sceneManager.updateDimensions(MainStage);
        MainStage.show();
    }



    private class SceneManager {
        private Scene mainscene;
        private Scene codeselection;
        private Scene inputscreen;

        private Scene IOmethodscreen;


        public double[] currentDimensions = new double[2];

        public Scene getMainScene() {
            return this.mainscene;
        }

        public void setMainScene(Scene scene) {
            this.mainscene = scene;
        }

        public Scene getCodeSelection() {
            return this.codeselection;
        }

        public void setCodeSelection(Scene scene) {
            this.codeselection = scene;
        }

        public Scene getInputScreen() {
            return this.inputscreen;
        }

        public void setInputScreen(Scene scene) {
            this.inputscreen = scene;
        }

        public SceneManager(Stage stage) {

            this.createCodeSelectionScene(stage);
            this.createMainScene(stage);

        }

        Scene createIOMethodScene(Stage mainStage){
            VBox menu = new VBox();

            HBox inputSelect = new HBox();

            Text inputTxt = new Text("Choose the input method");
            inputTxt.setFont(Font.font("Arial", 10));
            addElement(inputSelect, inputTxt);


            return this.IOmethodscreen;
        }

        Scene createCodeSelectionScene(Stage mainStage) {

            VBox codes = new VBox();

            Button hamming47 = new Button("Hamming [4,7]");
            hamming47.setFont(Font.font("Arial", 20));
            addElement(codes, hamming47);

            Button back = new Button("Back");
            back.setFont(Font.font("Arial", 20));
            back.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            mainStage.setScene(mainscene);
                            updateDimensions(mainStage);
                        }
                    }
            );
            addElement(codes, back);
            addElement(codes, this.getExitButton());

            codes.setAlignment(Pos.CENTER);
            codes.setSpacing(10);

            this.codeselection = new Scene(codes, 500, 500);
            return this.codeselection;
        }

        Scene createMainScene(Stage mainStage) {
            VBox menu = new VBox();

            //Choosing the linear encoding method changes scenes
            Button chooseCode = new Button("Choose Code");
            chooseCode.setFont(Font.font("Arial", 20));
            chooseCode.setOnAction(

                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            mainStage.setScene(codeselection);
                            updateDimensions(mainStage);
                        }
                    }
            );
            //Exit button for exiting the application.
            Button exitButton = this.getExitButton();

            Text menuName = new Text("Hamming Encoder");
            menuName.setFont(Font.font("Courier New", 35));

            menu.getChildren().addAll(menuName, chooseCode, exitButton);
            menu.setAlignment(Pos.CENTER);

            VBox.setMargin(menuName, new Insets(0, 10, 50, 10));

            menu.setSpacing(20);

            this.mainscene = new Scene(menu, 500, 500);

            return this.mainscene;
        }

        private boolean updateDimensions(Stage mainStage) {
            try {
                this.currentDimensions[0] = mainStage.getScene().getWidth();
                this.currentDimensions[1] = mainStage.getScene().getHeight();
                return true;
            } catch (Exception e) {
                System.out.println("Window dimension update error.");
                System.out.println(e);
                Platform.exit();
            }
            return false;
        }

        private Button getExitButton(){
            Button exit = new Button ("EXIT");
            exit.setFont(Font.font("Arial", 20));
            exit.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Platform.exit();
                        }
                    }
            );
            return exit;
        }

        private MenuButton getIOSelectionButton(Stage mainStage, String type){
            MenuItem manual = new MenuItem("Manual " + type);
         return null;
        }

    }


    public static void main(String[] args) {
        launch();
    }
}
