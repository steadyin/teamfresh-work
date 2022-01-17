package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.teamfresh.domain.enumrate.VocStatus;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 페널티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voc_id")
    private Voc voc;

    //페널티금액
    private BigDecimal amount;

    //확인여부
    private Boolean confirmed;

    //이의제기여부
    private Boolean objected;

    //이의제기내용
    private String objectContent;

    //운송기사 확인
    public void confirmed() {
        this.confirmed = true;
        this.voc.changeVocStatus(VocStatus.CONFIRMED_PENALTY);
    }

    //이의제기
    public void objected(String objectContent) {
        this.objected = true;
        this.objectContent = objectContent;
        this.voc.changeVocStatus(VocStatus.OBJECTED_PENALTY);
    }

    //생성 메소드
    public static Penalty createPenalty(Voc voc, BigDecimal amount) {
        Penalty penalty = new Penalty();
        penalty.setVoc(voc);
        penalty.amount = amount;
        penalty.confirmed = false;
        penalty.objected = false;
        return penalty;
    }

    //연관관계 메소드
    public void setVoc(Voc voc) {
        this.voc = voc;
        voc.setPenalty(this);
    }
}
