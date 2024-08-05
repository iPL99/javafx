package com.itgroup.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static final String FXMl_PATH = "/com/itgroup/fxml/"; // fxml 파일이 있는 경로
    public static final String IMAGE_PATH = "/com/itgroup/images/"; // 이미지 파일이 있는 경로
    public static final String CSS_PATH = "/com/itgroup/CSS/"; // CSS 파일이 있는 경로
    public static final String DATA_PATH = "\\src\\main\\java\\com\\itgroup\\data\\";

    private static Map<String, String> brandMap = new HashMap<>();


    private static String makeMapData(String brand, String mode) {
        brandMap.put("삼성", "Samsung");
        brandMap.put("애플", "Apple");
        brandMap.put("구글", "Google");
        if (mode.equals("value")) {
            return brandMap.get(brand);
        } else {
            for (String key : brandMap.keySet()) {
                if (brandMap.get(key).equals(brand)) {
                    return key;
                }
            }
            return null;
        }
    }

    public static String getBrandName(String brand, String mode) {
        return makeMapData(brand, mode);
    }

    public static LocalDate getDatePicker(String releaseDate) {

        System.out.println(releaseDate);

        String datePart = releaseDate.split(" ")[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {

            return LocalDate.parse(datePart, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }

    public static void showAlert(Alert.AlertType alertType, String[] message) {

        Alert alert = new Alert(alertType);
        alert.setTitle(message[0]);
        alert.setHeaderText(message[1]);
        alert.setContentText(message[2]);
        alert.showAndWait();
    }

}
