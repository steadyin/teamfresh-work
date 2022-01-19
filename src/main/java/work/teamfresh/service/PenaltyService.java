package work.teamfresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Penalty;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocStatus;
import work.teamfresh.dto.ObjectPenaltyDto;
import work.teamfresh.dto.RegisterPenaltyDto;
import work.teamfresh.dto.RequestCompensationDto;
import work.teamfresh.error.exception.ObjectNotFoundException;
import work.teamfresh.error.exception.VocStatuaException;
import work.teamfresh.repository.DriverRepository;
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
    private final DriverRepository driverRepository;

    /**
     * 페널티 등록
     *
     * 선행조건 : VOC 상태 -> REQUEST_COMPENSATION
     *
     */
    public Long registerPenalty(RegisterPenaltyDto registerPenaltyDto) {
        Long vocId = registerPenaltyDto.getVocId();
        BigDecimal amount = registerPenaltyDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 VOC ID 입니다"));

        Penalty penalty = Penalty.createPenalty(voc, amount);
        penaltyRepository.save(penalty);
        return penalty.getId();
    }

    /**
     * 페널티 확인 여부 등록
     *
     * 선행조건 : VOC 상태 -> REQUESTED_PENALTY
     *
     */
    public void confirmPenalty(Long penaltyId) {
        Penalty penalty = penaltyRepository.findOne(penaltyId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 페널티 입니다"));

        // 패널티 확인 처리, VOC 상태 변경 처리
        penalty.confirmed();

        RequestCompensationDto requestCompensationDto = new RequestCompensationDto();
        requestCompensationDto.setVocId(penalty.getVoc().getId());
        requestCompensationDto.setAmount(penalty.getAmount());

        // 배송기사 페널티 확인 후 배상시스템 바로 등록
        compensationService.registerCompensation(requestCompensationDto);
    }

    /**
     * 페널티 이의 여부 등록
     *
     * 선행조건 : VOC 상태 -> REQUESTED_PENALTY
     *
     */
    public void objectedPenalty(Long penaltyId, ObjectPenaltyDto objectPenaltyDto) {
        Penalty penalty = penaltyRepository.findOne(penaltyId).orElseThrow(()->new ObjectNotFoundException("존재하지 않는 페널티 입니다"));

        Voc voc = penalty.getVoc();

        String objectContent = objectPenaltyDto.getObjectContent();

        penalty.objected(objectContent);
    }
}
