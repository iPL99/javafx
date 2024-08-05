package com.itgroup.controller;

import com.itgroup.bean.Device;
import com.itgroup.dao.DeviceDao;
import com.itgroup.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DeviceInsertController implements Initializable {
    @FXML
    private TextField fxmlModel, fxmlOpSystem, fxmlPrice, fxmlImage;
    @FXML
    private ComboBox<String> fxmlBrand;
    @FXML
    private DatePicker fxmlReleaseDate;

    DeviceDao dao = null;
    Device bean = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new DeviceDao();

        fxmlBrand.getSelectionModel().select(0);
    }

    public void onDeviceInsert(ActionEvent event) {
        System.out.println(event);
        boolean bool = validationCheck();
        if (bool == true) {
            int cnt = -1;
            cnt = insertDatabase();
            if (cnt == 1) {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } else {
            System.out.println("등록 실패");
        }
    }
    private int insertDatabase() {
        int cnt = -1;
        cnt = dao.insertData(this.bean);
        if (cnt == -1) {
            String[] message = new String[]{"디바이스 등록", "디바이스 등록 실패", "디바이스 등록을 실패하였습니다."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
        return cnt;
    }

    private boolean validationCheck() {
        String[] message = null;

        String model = fxmlModel.getText().trim();
        if (model.length() <= 1 || model.length() >= 11) {
            message = new String[]{"유효성 검사 : 모델명", "길이 제한 위배", "모델명은 2글자 이상 10글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int selectedIndex = fxmlBrand.getSelectionModel().getSelectedIndex();
        String brand = null;

        if (selectedIndex == 0) {
            message = new String[]{"유효성 검사 : 브랜드", "브랜드 미선택", "브랜드를 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        } else {
            brand = fxmlBrand.getSelectionModel().getSelectedItem();
            System.out.println("선택된 항목");
            System.out.println(brand);
        }

        String opSystem = fxmlOpSystem.getText().trim();
        if (opSystem.length() <= 2 || opSystem.length() >= 16) {
            message = new String[]{"유효성 검사 : 운영체제", "길이 제한 위배", "운영체제는 3글자 이상 15글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        LocalDate _releaseDate = fxmlReleaseDate.getValue();
        String releaseDate = null;

        if (_releaseDate == null) {
            message = new String[]{"유효성 검사 : 입고일자", "무효한 날짜 형식", "올바른 입고 일자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;

        } else {
            releaseDate = _releaseDate.toString();
            releaseDate = releaseDate.replace("-", "/");
        }

        int price = 0;
        try {
            String _price = fxmlPrice.getText().trim();
            price = Integer.valueOf(_price);

            if (price < 100000 || price > 3000000) {
                message = new String[]{"유효성 검사 : 출고가", "허용 숫자 위반", "출고가는 100,000원이상 3,000,000원 이하로 입력해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 출고가", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }


        String image = fxmlImage.getText().trim();

        if (image == null || image.length() < 5) {
            message = new String[]{"유효성 검사 : 이미지", "필수 입력 체크", "이미지는 필수 입력 사항니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        boolean bool = false;
        bool = image.endsWith(".jpg") || image.endsWith(".png");
        if (!bool) {
            message = new String[]{"유효성 검사 : 이미지", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }


        this.bean = new Device();
        bean.setModel(model);
        bean.setBrand(Utility.getBrandName(brand, "value"));
        bean.setOpSystem(opSystem);
        bean.setReleaseDate(releaseDate);
        bean.setPrice(price);
        bean.setImage01(image);

        return true;
    }
}

