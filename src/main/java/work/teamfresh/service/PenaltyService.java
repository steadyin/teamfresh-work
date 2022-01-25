package work.teamfresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Penalty;
import work.teamfresh.domain.Voc;
import work.teamfresh.dto.ObjectPenaltyDto;
import work.teamfresh.dto.ReceiveCompensationDto;
import work.teamfresh.dto.RegisterPenaltyDto;
import work.teamfresh.error.exception.ObjectNotFoundException;
import work.teamfresh.repository.PenaltyRepository;
import work.teamfresh.repository.VocRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class PenaltyService {
    private final CompensationService compensationService;
    private final PenaltyRepository penaltyRepository;
    private final VocRepository vocRepository;

    /**
     * 페널티 등록
     */
    public Long registerPenalty(Long pathVocId, RegisterPenaltyDto registerPenaltyDto) {
        Long vocId = pathVocId;
        BigDecimal amount = registerPenaltyDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 VOC ID 입니다"));

        // 페널티 등록 조건 체크
        voc.possiblePenalty();

        Penalty penalty = Penalty.createPenalty(voc, amount);
        penaltyRepository.save(penalty);
        return penalty.getId();
    }

    /**
     * 페널티 확인 여부 등록
     */
    public void confirmPenalty(Long penaltyId) {
        Penalty penalty = penaltyRepository.findOne(penaltyId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 페널티 입니다"));

        // 패널티 확인 처리, VOC 상태 변경 처리
        penalty.confirm();

        ReceiveCompensationDto receiveCompensationDto = new ReceiveCompensationDto();
        receiveCompensationDto.setAmount(penalty.getAmount());

        // 배송기사 페널티 확인 후 배상시스템 바로 등록
        compensationService.registerCompensation(penalty.getVoc().getId(), receiveCompensationDto);
    }

    /**
     * 페널티 이의 여부 등록
     */
    public void objectedPenalty(Long penaltyId, ObjectPenaltyDto objectPenaltyDto) {
        Penalty penalty = penaltyRepository.findOne(penaltyId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 페널티 입니다"));

        String objectContent = objectPenaltyDto.getObjectContent();
        penalty.object(objectContent);
    }
}
