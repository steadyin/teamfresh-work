package work.teamfresh.common;

import work.teamfresh.domain.enumrate.VocStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class VocStatusConverter implements AttributeConverter<VocStatus, String> {
    @Override
    public String convertToDatabaseColumn(VocStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public VocStatus convertToEntityAttribute(String dbData) {
        return VocStatus.ofCode(dbData);
    }
}
