package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class DateDto {
    private int dayOfMonth;
    private String dayOfWeek;
    private  int month;
    private  int year;
}
