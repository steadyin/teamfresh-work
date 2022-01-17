package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.teamfresh.common.VocStatusConverter;
import work.teamfresh.domain.enumrate.VocStatus;
import work.teamfresh.domain.enumrate.VocType;

import javax.persistence.*;

/**
 * VOC - Compensation
 * - Penalty
 * <p>
 * 운송사에게 기사를 채용하는 것이다. 따라서 운송사 클래스가 필요없어보인다.
 * <p>
 * 운송사의 귀책이란 운송사의 기사님이 배송을 잘못 보냈거나 기사님의 실수로 배송이 늦어지는 경우 운송사의 귀책으로 잡히는 경우
 * <p>
 * <p>
 * 다음과 같은 상속 구조
 * <p>
 * 클레임 인입 -> VOC 등록 -> 귀책 사유 판단 -> (귀책당사자) 고객사 귀책 -> 더이상 프로세스 없음
 * -> (귀책당사자) 운송사 귀책
 * <p>
 * 배상 인입 -> 운송사에 대한 귀책 -> 배상 시스템 등록
 * 운송기사에 대한 귀책 -> 페널티(비용 청구) 발급 -> 인정 -> 배송시스템 등록
 * 이의제기 -> 보류
 * <p>
 * <p>
 * VOC에 대한 배상은 아래와 같은 고려 사항이 필요합니다.
 * <p>
 * 클레임 인입여부, 베싱 요청 여부, 배송 건 VOC 귀책 여부에 따른 프로세스, 귀책 불인정시 처리 프로세스
 * <p>
 * <p>
 * 고객사, 기사, 운송사 상속 관계로 구현
 * <p>
 * VOC, 배상, 페널티 서로 일대일 관계로 구현 ( 상송관계 구현 x)
 * <p>
 * <p>
 * 귀책당사자 -> 고객사, 운송사 2가지
 * <p>
 * VOC 정보 -> VOC 내용, 귀책 사유 포함
 * <p>
 * 고객사 정보 -> 고객사 및 고객사 담당자 정보, 연락처 포함
 * <p>
 * 운송사 정보 -> 운송사, 기사 정보
 * <p>
 * API 리스트
 * <p>
 * VOC 목록 조회 - VOC 귀책 당사자, 귀책 내용, 페널티 내용, 기사 확인 여부, 이의제기 여부, 배상정보
 * <p>
 * 배상 목록 조회 - VOC 정보, 배상 금액
 * <p>
 * VOC 등록
 * <p>
 * 패널티 등록
 * <p>
 * 배송기사의 패널티 확인 여부 등록
 * <p>
 * 배상 정보 등록 - 배상정보의 부모 정보인 VOC 정보를 참조할 수 있게 관계를 고려해서 개발
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voc extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voc_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private VocType vocType;

    @Convert(converter = VocStatusConverter.class)
    private VocStatus vocStatus; //VOC상태(1.클레임인입, 2.배상요청, 3.운송기사확인요청, 4.운송기사확인, 5.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    // 귀책 내용
    private String content;

    // 귀책 당사자
    @OneToOne(mappedBy = "voc")
    private Compensation compensation;

    @OneToOne(mappedBy = "voc")
    private Penalty penalty;

    public static Voc createVoc(VocType vocType, Vendor vendor, Driver driver, String content) {
        Voc voc = new Voc();
        voc.vocType = vocType;
        voc.vendor = vendor;
        voc.driver = driver;
        voc.content = content;
        voc.vocStatus = VocStatus.REQUESTED_CLAIM;

        return voc;
    }

    public void changeVocStatus(VocStatus vocStatus) {
        this.vocStatus = vocStatus;
    }

    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }

    // 배상 시스템 등록 가능 조건
    // 1. 고객사 귀책 VOC -> 배상 요청 상태
    // 2. 운송사 귀책 VOC -> 패널티 확인 상태
    public boolean isPossibleCompensation() {
        if (vocType.equals(VocType.VENDOR) && vocStatus.equals(VocStatus.REQUESTED_COMPENSATE)) return true;
        else if (vocType.equals(VocType.DRIVER) && vocStatus.equals(VocStatus.CONFIRMED_PENALTY)) return true;
        return false;
    }

    // 패널티 등록 가능 조건
    // 1. 운송사 귀책 VOC -> 배상 요청 상태
    public boolean isPossiblePenalty() {
        if (vocType.equals(VocType.DRIVER) && vocStatus.equals(VocStatus.REQUESTED_COMPENSATE)) return true;
        return false;
    }
}

