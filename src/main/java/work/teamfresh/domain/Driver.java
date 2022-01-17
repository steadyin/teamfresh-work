package work.teamfresh.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 운송기사
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Driver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long id;

    //운송기사명
    private String name;

    //소속 운송사명
    private String company;

    //생성 메소드
    public static Driver createDriver(String name, String company) {
        Driver driver = new Driver();
        driver.name = name;
        driver.company = company;

        return driver;
    }
}
