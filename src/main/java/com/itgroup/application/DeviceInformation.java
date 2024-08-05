package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeviceInformation extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            String fxmlFile = Utility.FXMl_PATH + "smartphone.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

            Parent container = fxmlLoader.load();
            Scene scene = new Scene(container);
            stage.setTitle("스마트폰 정보");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
