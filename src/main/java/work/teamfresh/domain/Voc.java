package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.teamfresh.common.VocStatusConverter;
import work.teamfresh.domain.enumrate.VocStatus;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.error.exception.VocStatuaException;

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

    public void changeVocStatus(VocStatus toVocStatus) {
        checkChangeStatus(toVocStatus);
        this.vocStatus = toVocStatus;
    }

    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }

    /**
     * VOC 상태 변경 전 상태 체크를 위한 메소드
     */
    public void checkChangeStatus(VocStatus toVocStatus) {
        boolean check = true;
        switch (toVocStatus) {
            case REQUESTED_CLAIM: // 클레임 인입 변경 시
                break;
            case REQUESTED_COMPENSATE: // 배상 요청 상태 변경 시
                if (this.vocStatus.equals(VocStatus.REQUESTED_CLAIM)) check = false;
                break;
            case REQUESTED_PENALTY: // 패널티 요청 상태 변경 시 -> IF 운송사 귀책 VOC -> 보상 요청 상태
                if (this.vocType.equals(VocType.DRIVER) && this.vocStatus.equals(VocStatus.REQUESTED_COMPENSATE)) check = false;
                break;
            case OBJECTED_PENALTY:
            case CONFIRMED_PENALTY:
                if (this.vocType.equals(VocType.DRIVER) && this.vocStatus.equals(VocStatus.REQUESTED_PENALTY)) check = false;
                break;
            case COMPENSATED: // 배상 완료 상태 변경 시 -> IF 고객사 귀책 VOC -> 배상 요청 상태, IF 운송사 귀책 VOC -> 패널티 확인 상태
                if (this.vocType.equals(VocType.VENDOR) && this.vocStatus.equals(VocStatus.REQUESTED_COMPENSATE)) check = false;
                else if (this.vocType.equals(VocType.DRIVER) && this.vocStatus.equals(VocStatus.CONFIRMED_PENALTY)) check = false;
                break;
            case CANCELD:
                //현재 취소 처리 기능 미구현
                break;
            default: check = true;
        }

        if(check) throw new VocStatuaException(String.format("VOC 상태 [ %s ] 전환할 수 없습니다. 현재 VOC 상태가 [ %s ] 입니다.",toVocStatus, this.vocStatus));
    }
}

