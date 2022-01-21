package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 배상
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Compensation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compensation_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voc_id")
    private Voc voc;

    // 배상 금액
    private BigDecimal amount;

    // 연관관계 메소드
    public void setVoc(Voc voc) {
        this.voc = voc;
        voc.setCompensation(this);
    }

    // 생성 메소드
    public static Compensation createCompensation(Voc voc, BigDecimal amount) {
        Compensation compensation = new Compensation();
        compensation.setVoc(voc);
        compensation.amount = amount;
        return compensation;
    }
}


