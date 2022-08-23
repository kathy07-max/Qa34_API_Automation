package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class GetAllRecRequestDto {
    private int monthFrom;
    private int monthTo;
    private int yearFrom;
    private int yearTo;
}
