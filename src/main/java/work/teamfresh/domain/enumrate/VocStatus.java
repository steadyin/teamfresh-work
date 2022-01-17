package work.teamfresh.domain.enumrate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VocStatus {
    REQUESTED_CLAIM("100", "고객사로부터 클레임이 인입되었습니다."),
    REQUESTED_COMPENSATE("200", "고객사로부터 배상이 요청되었습니다."),
    REQUESTED_PENALTY("300", "운송기사님에게 페널티를 청구했습니다."),
    OBJECTED_PENALTY("400", "운송기사님이 이의제기 했습니다."),
    CONFIRMED_PENALTY("500", "운송기사님이 페널티를 인정했습니다."),
    COMPENSATED("600", "배상 시스템에 등록되었습니다."),
    CANCELD("900", "취소 되었습니다");

    private String code;
    private String description;

    VocStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
