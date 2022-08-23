package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class ErrorResponseDto {
    int code;
    String details;
    String message;
    String timestamp;
}
