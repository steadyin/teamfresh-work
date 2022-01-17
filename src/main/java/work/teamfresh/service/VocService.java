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

    public Long registerVoc(RegisterVocDto registerVocDto) {
        Long vendorId = registerVocDto.getVendorId();
        Long driverId = registerVocDto.getDriverId();
        VocType vocType = registerVocDto.getVocType();
        String vocContent = registerVocDto.getVocContent();

        Vendor vendor = vendorRepository.findOne(vendorId).orElseThrow(ObjectNotFoundException::new);

        Driver driver = driverRepository.findOne(driverId).orElseThrow(ObjectNotFoundException::new);

        Voc voc = Voc.createVoc(vocType, vendor, driver, vocContent);

        vocRepository.save(voc);

        return voc.getId();
    }

    @Transactional(readOnly = true)
    public Voc findVoc(Long vocId) {
        return vocRepository.findOne(vocId).orElseThrow(ObjectNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Voc> findAllVoc() {
        return vocRepository.findAllVocAndCompensationAndPenalty();
    }
}
