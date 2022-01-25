package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    // 귀책 내용
    private String content;

    // 배상
    @OneToOne(mappedBy = "voc")
    private Compensation compensation;

    @OneToOne(mappedBy = "voc")
    private Penalty penalty;

    // 배상 접수 여부
    private boolean isReceivedCompensation;

    // 생성 메소드
    public static Voc createVoc(VocType vocType, Vendor vendor, Driver driver, String content) {
        Voc voc = new Voc();
        voc.vocType = vocType;
        voc.vendor = vendor;
        voc.driver = driver;
        voc.content = content;
        voc.isReceivedCompensation = false;

        return voc;
    }

    // 연관관계 메소드
    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }

    // VOC 배상 접수
    public void receiveCompensation() {
        possibleReceiveCompensation();
        this.isReceivedCompensation = true;
    }

    // 조건 체크 메소드
    
    // 배상 접수 가능 조건
    public void possibleReceiveCompensation() {
        if(this.isReceivedCompensation)
            throw new VocStatuaException("이미 배상이 접수 된 VOC입니다");
    }

    // 페널티 등록 가능 조건
    public void possiblePenalty() {
        if(this.vocType==VocType.VENDOR)
            throw new VocStatuaException("고객사 귀책은 페널티를 등록할 수 없습니다");
        else if(this.vocType==VocType.DRIVER && !this.isReceivedCompensation)
            throw new VocStatuaException("배상이 접수 되지 않은 VOC는 페널티를 등록할 수 없습니다.");
        else if(this.vocType==VocType.DRIVER && this.penalty!=null)
            throw new VocStatuaException("이미 페널티가 발급 된 VOC입니다");

    }

    // 배상 시스템 등록 가능 조건
    public void possibleCompensation() {
        if(this.vocType==VocType.VENDOR && !this.isReceivedCompensation)
            throw new VocStatuaException("배상이 접수 되지 않은 VOC는 배상 시스템에 등록할 수 없습니다.");
        else if(this.vocType==VocType.DRIVER && this.penalty==null)
            throw new VocStatuaException("페널티가 등록 되지 않은 VOC는 배상 시스템에 등록할 수 없습니다.");
        else if(this.vocType==VocType.DRIVER && !this.penalty.isConfirmed())
            throw new VocStatuaException("기사님이 확인하지 않은 VOC는 배상 시스템에 등록할 수 없습니다.");
    }
}

