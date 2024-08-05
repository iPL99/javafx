package com.itgroup.jdbc;

import com.itgroup.bean.Device;
import com.itgroup.dao.DeviceDao;


import java.util.Scanner;

public class InsertMain {
    public static void main(String[] args) {

        DeviceDao dao = new DeviceDao();
        Device bean = new Device();

        Scanner scan = new Scanner(System.in);
        System.out.print("모델명 : ");
        String name = scan.next();


        bean.setModel(name);
        bean.setBrand("삼성");
        bean.setOpSystem("Android");
        bean.setReleaseDate("2017/02/21");
        bean.setPrice(999999);
        bean.setImage01("s7.jpg");




        int cnt = -1;
        cnt = dao.insertData(bean);

        if (cnt == -1) {
            System.out.println("상품 등록에 실패했습니다.");
        } else {
            System.out.println("상품 등록에 성공했습니다.");
        }
    }

}
