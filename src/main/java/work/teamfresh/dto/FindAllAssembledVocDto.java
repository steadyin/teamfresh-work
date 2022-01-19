package work.teamfresh.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocStatus;
import work.teamfresh.domain.enumrate.VocType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssembledVocDto {
    //VOC
    private Long vocId;

    private VocType vocType;

    private VocStatus vocStatus;

    private String content;

    //VENDOR
    private String vendorName;

    private String vendorChargeName;

    private String vendorChargePhone;

    //DRIVER
    private String driverName;

    private String driverCompany;

    //COMPENSATION
    private BigDecimal compensationAmount;

    //PENALTY
    private BigDecimal penaltyAmount;

    private Boolean confirmed;

    private Boolean objected;

    private String objectionContent;

    public static FindAllAssembledVocDto EntityToDto(Voc voc) {
        FindAllAssembledVocDto vocDto = new FindAllAssembledVocDto();
        vocDto.vocId = voc.getId();
        vocDto.vocType = voc.getVocType();
        vocDto.vocStatus = voc.getVocStatus();
        vocDto.content = voc.getContent();

        vocDto.vendorName = voc.getVendor().getName();
        vocDto.vendorChargeName = voc.getVendor().getChargeName();
        vocDto.vendorChargePhone = voc.getVendor().getChargePhone();

        vocDto.driverName = voc.getDriver().getName();
        vocDto.driverCompany = voc.getDriver().getCompany();

        if (voc.getCompensation() != null) {
            vocDto.compensationAmount = voc.getCompensation().getAmount();
        }
        if (voc.getPenalty() != null) {
            vocDto.penaltyAmount = voc.getPenalty().getAmount();
            vocDto.confirmed = voc.getPenalty().getConfirmed();
            vocDto.objected = voc.getPenalty().getObjected();
            vocDto.objectionContent = voc.getPenalty().getObjectContent();
        }

        return vocDto;
    }
}