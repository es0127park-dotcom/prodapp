package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private String msg; // ok, 예외메시지
    private T body;
}
