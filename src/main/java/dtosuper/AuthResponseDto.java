package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class AuthResponseDto {
    private boolean registration;
    private String status;
    private String token;
}
