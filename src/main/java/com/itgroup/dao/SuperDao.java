package com.itgroup.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SuperDao {
    private String driver;
    private String url = null; // 데이터 베이스 출처
    private String id = null; // 사용자 아이디
    private String password = null; // 사용자 비밀번호

    public SuperDao() {
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.url = "jdbc:oracle:thin:@localhost:1521:xe";
        this.id = "device";
        this.password = "lion";
        try {
            Class.forName(driver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected Connection getConnection() {
        Connection conn = null;
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                conn = DriverManager.getConnection(url, id, password);
                if (conn != null) {

                } else {
                    System.out.println("접속 실패");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return conn;
    }
}
