// App.java
package com.dev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var url = getClass().getResource("/fxml/mainView.fxml");
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root, 380, 540);
        // charge le CSS (dossier resources/css/mainCss.css)
        scene.getStylesheets().add(
            getClass().getResource("/css/mainCss.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Key Finders");
        stage.initStyle(StageStyle.UNDECORATED); 
        stage.setResizable(false);
        stage.show();
    }
}
