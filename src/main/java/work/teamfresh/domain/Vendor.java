package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 고객사
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vendor extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long id;

    //고객사명
    private String name;

    //담당직원명
    private String chargeName;

    //담당직원 연락처
    private String chargePhone;

    //생성 메소드
    public static Vendor createVendor(String name, String chargeName, String chargePhone) {
        Vendor vendor = new Vendor();
        vendor.name = name;
        vendor.chargeName = chargeName;
        vendor.chargePhone = chargePhone;

        return vendor;
    }

}
