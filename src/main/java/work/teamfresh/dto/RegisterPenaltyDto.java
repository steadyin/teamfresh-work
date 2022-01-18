package work.teamfresh.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RegisterPenaltyDto {
    @NotBlank
    private Long vocId;
    private BigDecimal amount;
}

