package com.itgroup.jdbc;

import com.itgroup.bean.Device;
import com.itgroup.dao.DeviceDao;


import java.util.Scanner;

public class SelectTotalCount {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("all, 삼성, 애플, 구글, 중 1개 입력 : ");

        String brand = scan.next();


        DeviceDao dao = new DeviceDao();
        int totalCount = dao.getTotalCount(brand);

        if (brand.equals("all")) {
            System.out.println("상품 전체 개수 : " + totalCount);
        } else {
            String message = "브랜드 %s의 개수 : %d\n";
            System.out.printf(message, brand, totalCount);
        }
    }
}
