package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // DBMS와 연결된 소켓
    Connection conn = DBConnection.getConnection();

    // 1. insert(String name, int price, int qty)
    public int insert(String name, int price, int qty) {
        String sql = "insert into product_tb(name, price, qty) values(?,?,?)";
        try {
            // (1) 버퍼 달기
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setInt(3, qty);

            // (2) 쿼리 전송
            int result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 2. deleteById(int id)
    public int deleteById(int id) {
        String sql = "delete from product_tb where id = ?";
        try {
            // (1) 버퍼 달기
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            // (2) 쿼리 전송
            int result = pstmt.executeUpdate();
            return  result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 3. findById(int id)
    public Product findById(int id) {
        String sql = "select * from product_tb where id = ?";
        try {
            // (1) 버퍼 달기
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            // (2) 쿼리 전송
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int pId = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int qty = rs.getInt("qty");
                Product product = new Product(pId, name, price, qty);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 4. findAll()
    public List<Product> findAll() {
        String sql = "select * from product_tb";
        try {
            // (1) 버퍼 달기
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // (2) 쿼리 전송
            ResultSet rs = pstmt.executeQuery();

            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int qty = rs.getInt("qty");
                Product product = new Product(id, name, price, qty);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
