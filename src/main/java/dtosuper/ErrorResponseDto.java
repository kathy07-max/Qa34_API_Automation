package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class ErrorResponseDto {
    int code;
    String details;
    String message;
    String timestamp;
}
