package com.itgroup.controller;

import com.itgroup.bean.Device;
import com.itgroup.dao.DeviceDao;
import com.itgroup.utility.Paging;
import com.itgroup.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeviceInformationController implements Initializable {
    private DeviceDao dao = null;
    private String mode = null;

    @FXML
    private TableView<Device> smartphoneTable;

    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> fieldSearch;
    @FXML
    private Pagination pagination;
    @FXML
    private Label pagingStatus;
    // 영희가 푸시하려고 시도함...
    // 오늘 점심은 시원한것으로 먹자
    // 철수가 먼저 푸시함

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new DeviceDao();
        pagination.currentPageIndexProperty().addListener(((observableValue, number, t1) -> updatePage()));
        setTableClumns();
        setPagination(0);

    }

    private void setPagination(int pageIndex) {
        pagination.setCurrentPageIndex(pageIndex);
        updatePage();
        imageView.setImage(null);


        ChangeListener<Device> tableListener = new ChangeListener<Device>() {
            @Override
            public void changed(ObservableValue<? extends Device> observableValue, Device oldValue, Device newValue) {
                if (newValue != null) {
                    System.out.println("디바이스 정보");
                    System.out.println(newValue);
                    String imageFile = "";
                    if (newValue.getImage01() != null) {
                        imageFile = Utility.IMAGE_PATH + newValue.getImage01().trim();
                    } else {
                        imageFile = Utility.IMAGE_PATH + "no image.png";
                    }

                    Image someImage = null;
                    if (getClass().getResource(imageFile) == null) {
                        imageView.setImage(null);
                    } else {
                        someImage = new Image(getClass().getResource(imageFile).toString());
                        imageView.setImage(someImage);
                    }
                }
            }
        };
        smartphoneTable.getSelectionModel().selectedItemProperty().addListener(tableListener);
        setContextMenu();
    }

    private void updatePage() {
        int totalCount = 0;
        totalCount = dao.getTotalCount(this.mode);
        Paging pageInfo = new Paging(String.valueOf(pagination.getCurrentPageIndex() + 1), "10", totalCount, null, this.mode, null);

        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);
    }

    private void setContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        smartphoneTable.setContextMenu(contextMenu);
    }


    private void fillTableData(Paging pageInfo) {
        List<Device> deviceList = dao.getPaginationData(pageInfo);
        ObservableList<Device> dataList = FXCollections.observableArrayList(deviceList);
        smartphoneTable.setItems(dataList);
        pagingStatus.setText(pageInfo.getPagingStatus());
    }

    private void setTableClumns() {
        String[] fields = {"dnum", "model", "brand", "releaseDate"};
        String[] colNames = {"모델 번호", "모델명", "제조회사", "출시일"};

        TableColumn tableColumn = null;

        for (int i = 0; i < fields.length; i++) {
            tableColumn = smartphoneTable.getColumns().get(i);
            tableColumn.setText(colNames[i]);

            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));
            tableColumn.setStyle("-fx-alignment:center");
        }
    }
    public void handleAdd(ActionEvent event) throws Exception {
        String fxmlFile = Utility.FXMl_PATH + "DeviceInsert.fxml";
        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent container = fxmlLoader.load();

        Scene scene = new Scene(container);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("디바이스 등록");
        stage.showAndWait();
        setPagination(0);
    }


    public void handleUpdate(ActionEvent event) throws Exception {
        int idx = smartphoneTable.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            System.out.println("선택된 색인 번호 : " + idx);
            String fxmlFile = Utility.FXMl_PATH + "DeviceUpdate.fxml";
            URL url = getClass().getResource(fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent container = fxmlLoader.load();

            Device bean = smartphoneTable.getSelectionModel().getSelectedItem();
            DeviceUpdateController controller = fxmlLoader.getController();
            controller.setBean(bean);

            Scene scene = new Scene(container);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("디바이스 수정");
            stage.showAndWait();
            setPagination(0);
        } else {
            String[] message = new String[]{"디바이스 선택 확인", "디바이스 미선택", "수정하고자 하는 디바이스를 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void handleDelete(ActionEvent event) {
        int idx = smartphoneTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제 확인 메세지");
            alert.setHeaderText("삭제 항목 대화 상자");
            alert.setContentText("이 항목을 정말로 삭제하겠습니까?");

            Optional<ButtonType> response = alert.showAndWait();

            if (response.get() == ButtonType.OK) {
                Device bean = smartphoneTable.getSelectionModel().getSelectedItem();
                int dnum = bean.getDnum();
                int cnt = -1;
                cnt = dao.deleteDate(dnum);
                if (cnt != -1) {
                    System.out.println("삭제 성공");
                    setPagination(0);
                } else {
                    System.out.println("삭제 실패");
                }
            } else {
                System.out.println("삭제를 취소하였습니다.");
            }

        } else {
            String[] message = new String[]{"삭제할 목록 확인", "삭제할 대상 미선택", "삭제할 행을 선택해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }
                                                                                                                                               
    }

    public void handleClose(ActionEvent event) {
        System.out.println("프로그램 종료");
        Platform.exit();
    }

    public void choiceSelect(ActionEvent event) {
        String brand = fieldSearch.getSelectionModel().getSelectedItem();
        System.out.println("검색 제조사 : [" + brand + "]");
        this.mode = Utility.getBrandName(brand, "value");
        setPagination(0);
    }
}
