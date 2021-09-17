package Interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase de Interfaz, la cual tiene como objetivo, ejecutar el controlador principal del proyecto JavaFX.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("urna.png"));
        primaryStage.setTitle("Escrutinio Definitivo - PASO 2019");
        primaryStage.setScene(new Scene(root, 650, 685));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

}
