package work.teamfresh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.teamfresh.domain.Compensation;
import work.teamfresh.domain.Voc;
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


    public static FindAllCompensateDto EntityToDto(Compensation compensation) {
        FindAllCompensateDto vocDto = new FindAllCompensateDto();

        Voc voc = compensation.getVoc();

        vocDto.vocId = voc.getId();
        vocDto.vocType = voc.getVocType();
        vocDto.content = voc.getContent();

        vocDto.vendorName = voc.getVendor().getName();
        vocDto.driverName = voc.getDriver().getName();
        vocDto.driverCompany = voc.getDriver().getCompany();

        vocDto.compensationId = voc.getCompensation().getId();
        vocDto.compensationAmount = voc.getCompensation().getAmount();

        return vocDto;
    }
}