package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.teamfresh.common.VocStatusConverter;
import work.teamfresh.domain.enumrate.VocStatus;
import work.teamfresh.domain.enumrate.VocType;

import javax.persistence.*;

/**
 * VOC
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

    //VOC상태 100:클레임인입 200:고객사배상요청 300:패널티청구 400:이의제기 500:패널티인정 600:배상시스템등록
    @Convert(converter = VocStatusConverter.class)
    private VocStatus vocStatus; 

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
    public boolean possibleCompensation() {
        if (vocType.equals(VocType.VENDOR) && vocStatus.equals(VocStatus.REQUESTED_COMPENSATE)) return true;
        else if (vocType.equals(VocType.DRIVER) && vocStatus.equals(VocStatus.CONFIRMED_PENALTY)) return true;
        return false;
    }

    // 패널티 등록 가능 조건
    // 1. 운송사 귀책 VOC -> 배상 요청 상태
    public boolean possiblePenalty() {
        if (vocType.equals(VocType.DRIVER) && vocStatus.equals(VocStatus.REQUESTED_COMPENSATE)) return true;
        return false;
    }
}

