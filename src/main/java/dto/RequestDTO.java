package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class RequestDTO {
    private String method;
    private Map<String, Integer> querystring;
    private Body body;

    @AllArgsConstructor
    @Data
    public class Body {
        private String name;
        private Integer price;
        private Integer qty;
    }
}