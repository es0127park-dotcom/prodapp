package server;

import java.util.List;

public class ProductService implements ProductServiceInterface{

    ProductRepository pr = new ProductRepository();

    @Override
    public void 상품등록(String name, int price, int qty) {
        // 1. product_tb에 insert
        pr.insert(name, price, qty);
    }

    @Override
    public List<Product> 상품목록() {
        List<Product> p = pr.findAll();
        return p;
    }

    @Override
    public Product 상품상세(int id) {
        Product p = pr.findById(id);

        if(p == null) throw new RuntimeException("id is not found");

        return p;
    }

    @Override
    public void 상품삭제(int id) {
        pr.deleteById(id);
    }
}
