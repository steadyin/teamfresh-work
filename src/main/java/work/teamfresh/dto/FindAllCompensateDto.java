package work.teamfresh.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.teamfresh.domain.enumrate.VocType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FindAllCompensateDto {

    //VOC
    private Long vocId;

    private VocType vocType;

    private String content;

    //VENDOR
    private String vendorName;

    //DRIVER
    private String driverName;

    private String driverCompany;

    //COMPENSATION
    private Long compensationId;

    private BigDecimal compensationAmount;

    public FindAllCompensateDto(Long vocId, VocType vocType, String content, String vendorName, String driverName, String driverCompany, Long compensationId, BigDecimal compensationAmount) {
        this.vocId = vocId;
        this.vocType = vocType;
        this.content = content;
        this.vendorName = vendorName;
        this.driverName = driverName;
        this.driverCompany = driverCompany;
        this.compensationId = compensationId;
        this.compensationAmount = compensationAmount;
    }
}