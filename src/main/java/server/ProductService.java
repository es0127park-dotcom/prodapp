package server;

import java.util.List;

public class ProductService implements ProductServiceInterface{
    @Override
    public void 상품등록(String name, int price, int qty) {

    }

    @Override
    public List<Product> 상품목록() {
        return List.of();
    }

    @Override
    public Product 상품상세(int id) {
        return null;
    }

    @Override
    public void 상품삭제(int id) {

    }
}
