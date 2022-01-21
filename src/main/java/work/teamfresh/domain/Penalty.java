package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.teamfresh.error.exception.VocStatuaException;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voc_id")
    private Voc voc;

    // 페널티금액
    private BigDecimal amount;

    // 확인여부
    private boolean isConfirmed;

    // 이의제기여부
    private boolean isObjected;

    // 이의제기내용
    private String objectContent;

    // 운송기사 확인
    public void confirm() {
        possibleConfirm();
        this.isConfirmed = true;
    }

    // 이의제기
    public void object(String objectContent) {
        possibleObject();
        this.isObjected = true;
        this.objectContent = objectContent;
    }

    // 생성 메소드
    public static Penalty createPenalty(Voc voc, BigDecimal amount) {
        Penalty penalty = new Penalty();
        penalty.setVoc(voc);
        penalty.amount = amount;
        penalty.isConfirmed = false;
        penalty.isObjected = false;
        return penalty;
    }

    // 연관관계 메소드
    public void setVoc(Voc voc) {
        this.voc = voc;
        voc.setPenalty(this);
    }

    // 조건 체크 메소드
    public void possibleConfirm() {
        if(this.isConfirmed)
            throw new VocStatuaException("이미 확인 된 페널티입니다");
        else if(this.isObjected)
            throw new VocStatuaException("이미 이의제기 된 페널티입니다");
    }

    public void possibleObject() {
        if(this.isConfirmed)
            throw new VocStatuaException("이미 확인 된 페널티입니다");
        else if(this.isObjected)
            throw new VocStatuaException("이미 이의제기 된 페널티입니다");
    }
}
