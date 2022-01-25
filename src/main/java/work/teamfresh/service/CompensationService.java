package work.teamfresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Compensation;
import work.teamfresh.domain.Voc;
import work.teamfresh.dto.FindAllCompensateDto;
import work.teamfresh.dto.ReceiveCompensationDto;
import work.teamfresh.error.exception.ObjectNotFoundException;
import work.teamfresh.repository.CompensationRepository;
import work.teamfresh.repository.VocRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompensationService {
    private final CompensationRepository compensationRepository;
    private final VocRepository vocRepository;

    /**
     * 배상 접수
     */
    public Voc receiveCompensation(Long pathVocId, ReceiveCompensationDto receiveCompensationDto) {
        Long vocId = pathVocId;
        BigDecimal amount = receiveCompensationDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(() -> new ObjectNotFoundException("존재하지 않는 VOC 입니다"));

        voc.receiveCompensation();

        return voc;
    }

    /**
     * 배상 등록
     */
    public Long registerCompensation(Long pathVocId, ReceiveCompensationDto receiveCompensationDto) {
        Long vocId = pathVocId;
        BigDecimal amount = receiveCompensationDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(() -> new ObjectNotFoundException("존재하지 않는 VOC 입니다"));
        voc.possibleCompensation();
        Compensation compensation = Compensation.createCompensation(voc, amount);
        compensationRepository.save(compensation);
        return compensation.getId();
    }

    /**
     * 배상 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FindAllCompensateDto> findAllCompensation() {
        return compensationRepository.findAllCompensationAndVoc();
    }
}
