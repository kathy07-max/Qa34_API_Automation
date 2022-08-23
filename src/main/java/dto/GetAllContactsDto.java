package dto;

import dto.ContactDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
@Builder
public class GetAllContactsDto {
    List<ContactDto> contacts;
}
