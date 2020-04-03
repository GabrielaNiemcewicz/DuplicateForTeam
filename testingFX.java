import javafx.application.Application;
import javafx.stage.Stage;

public class testingFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        UserInterface ui = new UserInterface();
        ui.displayStage(primaryStage);
    }

}