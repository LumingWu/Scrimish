package scrimish;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.ScrimishUI;


/*
This is not a complete implementation of Scrimish.
You should use this as a way to learn how to code with JavaFX.
Focus on:
(1) How I create and mutate Button and Label, especially the action handler.
(2) How I divided up the work to each .java file and how they interact by calling the others' methods.
(2) My game screen because that is where you earn your credit.
(3) Your code reading skill. There is no straight answer in my code for your assignment. But it definitely has enough
clues for you to finish assignment even in CSE219.

Warning: Do not copy my GUI code directly, only the structure(Passing the object at creation of another
object, intialization, etc). Professor Fodor will give you the GUI.
What his/your GUI needs to do is similar but different to my GUI. I warned you, don't get tilted when you lose points.

Topics not included in this demo:
(1) Transitions.
(2) JavaFX geometry objects, Canvas, and property binding.
(3) Other event handling and timers.
(4) Of course, TextArea and ScrollPane are not in here.
*/
public class Scrimish extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        /* Get screen width. */
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        /* Create scene with UI */
        ScrimishUI UI = new ScrimishUI(primaryStage, primaryScreenBounds.getMaxX(), primaryScreenBounds.getMaxY());
        Scene scene = new Scene(UI.getRoot());
        
        /* Add stylesheet */
        scene.getStylesheets().add("resource/style/ScrimishStyle.css");
        
        /* Display stage */
        primaryStage.setTitle("Scrimish");
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
