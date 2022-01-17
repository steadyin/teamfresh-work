package work.teamfresh.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.teamfresh.domain.enumrate.VocType;

@Getter
@Setter
@NoArgsConstructor
public class RegisterVocDto {

    private Long vendorId;
    private Long driverId;
    private VocType vocType;
    private String vocContent;
}
