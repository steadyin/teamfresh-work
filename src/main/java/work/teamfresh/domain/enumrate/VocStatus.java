package work.teamfresh.domain.enumrate;

import lombok.Getter;
import work.teamfresh.error.exception.VocStatuaException;

import java.util.Arrays;

/**
 * VOC 상태
 * 
 * 처리 프로세스 
 * 
 * 클레임 인입(REQUESTED_CLAIM) -> 고객사 배상 요청(REQUESTED_COMPENSATE) -> 패널티 등록(REQUESTED_PENALTY)
 *
 * 선택지 1 -> 패널티 이의 제기 (OBJECTED_PENALTY)
 * 선택지 2 -> 패널티 승인 (CONFIRMED_PENALTY) -> 배상 처리 완료(COMPENSATED)
 */
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

    public static VocStatus ofCode(String vocStatus) {
         return Arrays.stream(VocStatus.values())
                .filter(v -> v.getCode().equals(vocStatus))
                .findAny()
                .orElseThrow(() -> new VocStatuaException(String.format("상태코드에 존재하지 않는 코드 입니다. [%s]", vocStatus)));
    }
}
