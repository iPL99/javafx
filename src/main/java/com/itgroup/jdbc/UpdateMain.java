package com.itgroup.jdbc;

import com.itgroup.bean.Device;
import com.itgroup.dao.DeviceDao;


import java.util.Scanner;

public class UpdateMain {
    public static void main(String[] args) {

        DeviceDao dao = new DeviceDao();
        Device bean = new Device();

        Scanner scan = new Scanner(System.in);
        System.out.print("모델 번호 : ");
        int dnum = scan.nextInt();

        System.out.print("모델명 : ");
        String model = scan.next();

        bean.setDnum(dnum);
        bean.setModel(model);
        bean.setBrand("삼성");
        bean.setOpSystem("Android");
        bean.setReleaseDate("2022/02/13");
        bean.setPrice(111133);
        bean.setImage01("model.jpg");


        int cnt = -1;
        cnt = dao.updateData(bean);

        if (cnt == -1) {
            System.out.println("상품 수정에 실패하였습니다.");
        } else {
            System.out.println("상품 수정에 성공하였습니다.");
        }
    }
}
