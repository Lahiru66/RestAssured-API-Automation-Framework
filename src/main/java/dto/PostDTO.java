package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {

    private String title;
    private String body;
    private String userId;
}



