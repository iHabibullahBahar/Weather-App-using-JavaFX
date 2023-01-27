/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/* Author : Habibullah Bahar 
 * GitHub : https://github.com/iHabibullahBahar
 * Facebook: https://www.facebook.com/iHabibullahBahar/
 * Linkedin: https://www.linkedin.com/in/ihabibullah/
 */
package weatherchecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Habib
 */
public class Main extends Application{
    public static Parent root;
    public static Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        scene =new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Weather Checker FX");
        stage.setResizable(false);
      
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
