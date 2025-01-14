package dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class TestDTO {

    private String title;

    private String body;

    private String userId;
}



