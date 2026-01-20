package server;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
}
