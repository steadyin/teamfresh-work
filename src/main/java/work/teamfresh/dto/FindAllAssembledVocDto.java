package work.teamfresh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssembledVocDto {
    //VOC
    private Long vocId;

    private VocType vocType;

    @JsonProperty("isReceivedCompensation")
    private boolean isReceivedCompensation;

    private String content;

    //VENDOR
    private String vendorName;

    private String vendorChargeName;

    private String vendorChargePhone;

    //DRIVER
    private String driverName;

    private String driverCompany;

    //COMPENSATION
    private Long compensationId;

    private BigDecimal compensationAmount;

    //PENALTY
    private Long penaltyId;

    private BigDecimal penaltyAmount;

    @JsonProperty("isConfirmed")
    private boolean isConfirmed;

    @JsonProperty("isObjected")
    private boolean isObjected;

    private String objectionContent;

    public static FindAllAssembledVocDto EntityToDto(Voc voc) {
        FindAllAssembledVocDto vocDto = new FindAllAssembledVocDto();
        vocDto.vocId = voc.getId();
        vocDto.vocType = voc.getVocType();
        vocDto.isReceivedCompensation = voc.isReceivedCompensation();
        vocDto.content = voc.getContent();

        vocDto.vendorName = voc.getVendor().getName();
        vocDto.vendorChargeName = voc.getVendor().getChargeName();
        vocDto.vendorChargePhone = voc.getVendor().getChargePhone();

        vocDto.driverName = voc.getDriver().getName();
        vocDto.driverCompany = voc.getDriver().getCompany();

        if (voc.getCompensation() != null) {
            vocDto.compensationId = voc.getCompensation().getId();
            vocDto.compensationAmount = voc.getCompensation().getAmount();
        }
        if (voc.getPenalty() != null) {
            vocDto.penaltyId = voc.getPenalty().getId();
            vocDto.penaltyAmount = voc.getPenalty().getAmount();
            vocDto.isConfirmed = voc.getPenalty().isConfirmed();
            vocDto.isObjected = voc.getPenalty().isObjected();
            vocDto.objectionContent = voc.getPenalty().getObjectContent();
        }

        return vocDto;
    }
}