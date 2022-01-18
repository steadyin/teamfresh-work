package work.teamfresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Driver;
import work.teamfresh.domain.Vendor;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.dto.RegisterVocDto;
import work.teamfresh.error.exception.ObjectNotFoundException;
import work.teamfresh.repository.DriverRepository;
import work.teamfresh.repository.VendorRepository;
import work.teamfresh.repository.VocRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VocService {
    private final VocRepository vocRepository;
    private final DriverRepository driverRepository;
    private final VendorRepository vendorRepository;

    /**
     * VOC 등록
     */
    public Long registerVoc(RegisterVocDto registerVocDto) {
        Long vendorId = registerVocDto.getVendorId();
        Long driverId = registerVocDto.getDriverId();
        VocType vocType = registerVocDto.getVocType();
        String vocContent = registerVocDto.getVocContent();

        Vendor vendor = vendorRepository.findOne(vendorId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 고객사 입니다"));

        Driver driver = driverRepository.findOne(driverId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 운전기사 입니다"));

        Voc voc = Voc.createVoc(vocType, vendor, driver, vocContent);

        vocRepository.save(voc);

        return voc.getId();
    }

    /**
     * VOC 단건 조회
     */
    @Transactional(readOnly = true)
    public Voc findVoc(Long vocId) {
        return vocRepository.findOne(vocId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 VOC 입니다"));
    }

    /**
     * VOC 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Voc> findAllVoc() {
        return vocRepository.findAllVocAndCompensationAndPenalty();
    }
}
