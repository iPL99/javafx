package com.itgroup.dao;

import com.itgroup.bean.Device;
import com.itgroup.utility.Paging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceDao extends SuperDao {

    private Device makeBean(ResultSet rs) {
        Device bean = new Device();
        try {
            bean.setDnum(rs.getInt("dnum"));
            bean.setModel(rs.getString("model"));
            bean.setBrand(rs.getString("brand"));
            bean.setOpSystem(rs.getString("opSystem"));
            bean.setReleaseDate(rs.getString("releaseDate"));
            bean.setPrice(rs.getInt("price"));
            bean.setImage01(rs.getString("image01"));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bean;

    }

    public int insertData(Device bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " insert into devices(dnum, model, brand, opSystem , releaseDate , price, image01) ";

        sql += " values(seqdevice.nextval, ?, ?, ?, ?, ?, ?) ";


        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getModel());
            pstmt.setString(2, bean.getBrand());
            pstmt.setString(3, bean.getOpSystem());
            pstmt.setString(4, bean.getReleaseDate());
            pstmt.setInt(5, bean.getPrice());
            pstmt.setString(6, bean.getImage01());


            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                ;
                if (conn != null) {
                    conn.close();
                }
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        return cnt;
    }

    public int getTotalCount(String brand) {
        int totalCount = 0;

        String sql = "select count(*) as mycnt from devices";

        boolean bool = brand == null || brand.equals("all");
        if (!bool) {
            sql += " where brand = ?";
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Device bean = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, brand);
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalCount = rs.getInt("mycnt");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public List<Device> getPaginationData(Paging pageInfo) {
        Connection conn = null;
        String sql = " select dnum, model, brand, opSystem, releaseDate, price, image01 ";
        sql += " from (";
        sql += " select dnum, model, brand, opSystem, releaseDate, price, image01, ";
        sql += " rank() over(order by dnum asc) as ranking ";
        sql += " from devices ";

        String mode = pageInfo.getMode();
        boolean bool = mode.equals(null) || mode.equals("null") || mode.equals("") || mode.equals("all");

        if (!bool) {
            sql += " where brand = ? ";
        }

        sql += " ) ";
        sql += " where ranking between ? and ? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Device> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, mode);
                pstmt.setInt(2, pageInfo.getBeginRow());
                pstmt.setInt(3, pageInfo.getEndRow());
            } else {
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2, pageInfo.getEndRow());
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Device bean = this.makeBean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }

    public int deleteDate(int dnum) {
        System.out.println("기본 키 = " + dnum);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " delete from devices ";
        sql += " where dnum = ? ";


        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, dnum);

            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                ;
                if (conn != null) {
                    conn.close();
                }
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }

    public int updateData(Device bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " update devices set model = ?, brand = ?, opSystem = ?, releaseDate = ?, price = ?, image01 = ? ";
        sql += " where dnum = ?";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getModel());
            pstmt.setString(2, bean.getBrand());
            pstmt.setString(3, bean.getOpSystem());
            pstmt.setString(4, bean.getReleaseDate());
            pstmt.setInt(5, bean.getPrice());
            pstmt.setString(6, bean.getImage01());
            pstmt.setInt(7, bean.getDnum());

            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }
}

