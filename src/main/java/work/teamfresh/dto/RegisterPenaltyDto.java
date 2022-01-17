package work.teamfresh.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RegisterPenaltyDto {
    private Long vocId;
    private BigDecimal amount;
}

