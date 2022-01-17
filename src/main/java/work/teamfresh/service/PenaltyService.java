package work.teamfresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Penalty;
import work.teamfresh.domain.Voc;
import work.teamfresh.dto.ObjectPenaltyDto;
import work.teamfresh.dto.RegisterPenaltyDto;
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
     */
    public Long registerPenalty(RegisterPenaltyDto registerPenaltyDto) {
        Long vocId = registerPenaltyDto.getVocId();
        BigDecimal amount = registerPenaltyDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(ObjectNotFoundException::new);

        if (voc.isPossiblePenalty()) {
            Penalty penalty = Penalty.createPenalty(voc, amount);
            penaltyRepository.save(penalty);
            return penalty.getId();
        } else {
            throw new VocStatuaException();
        }
    }

    /**
     * 페널티 확인 여부 등록
     */
    public void confirmPenalty(Long penaltyId) {
        Penalty penalty = penaltyRepository.findOne(penaltyId).orElseThrow(ObjectNotFoundException::new);

        penalty.confirmed();

        // 배송기사 페널티 확인 후 배상시스템 바로 등록
        compensationService.registerCompensation(penalty.getVoc().getId(), penalty.getAmount());
    }

    /**
     * 페널티 이의 여부 등록
     */
    public void objectedPenalty(Long penaltyId, ObjectPenaltyDto objectPenaltyDto) {
        String objectContent = objectPenaltyDto.getObjectContent();

        Penalty penalty = penaltyRepository.findOne(penaltyId).orElseThrow(ObjectNotFoundException::new);

        penalty.objected(objectContent);

        // 배송기사 페널티 확인 후 배상시스템 바로 등록
        compensationService.registerCompensation(penalty.getVoc().getId(), penalty.getAmount());
    }
}
