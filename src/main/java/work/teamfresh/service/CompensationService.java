package work.teamfresh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Compensation;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocStatus;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.dto.RequestCompensationDto;
import work.teamfresh.error.exception.ObjectNotFoundException;
import work.teamfresh.error.exception.VocStatuaException;
import work.teamfresh.repository.CompensationRepository;
import work.teamfresh.repository.VocRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompensationService {
    private final CompensationRepository compensationRepository;
    private final VocRepository vocRepository;

    /**
     * 배상 요청 처리
     *
     * 선행조건 : VOC 상태 -> REQUEST_CLAIM 전환
     *
     */
    public Voc requestCompensation(RequestCompensationDto requestCompensationDto) {
        Long vocId = requestCompensationDto.getVocId();
        BigDecimal amount = requestCompensationDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(() -> new ObjectNotFoundException("존재하지 않는 VOC 입니다"));

        voc.changeVocStatus(VocStatus.REQUESTED_COMPENSATE);

        return voc;
    }

    /**
     * 배상 등록
     *
     * 선행조건 : 고객사 귀책 - VOC 상태 -> REQUEST_COMPENSATION
     *           운송사 귀책 - VOC 상태 -> PENALTY_CONFIRMED_PENALTY
     */
    public Long registerCompensation(RequestCompensationDto requestCompensationDto) {
        Long vocId = requestCompensationDto.getVocId();
        BigDecimal amount = requestCompensationDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(() -> new ObjectNotFoundException("존재하지 않는 VOC 입니다"));

        // 운송사 귀책이 패널티가 등록되지 않은 경우
        voc.changeVocStatus(VocStatus.COMPENSATED);

        Compensation compensation = Compensation.createCompensation(voc, amount);
        compensationRepository.save(compensation);

        return compensation.getId();
    }

    /**
     * 배상 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Compensation> findAllCompensation() {
        return compensationRepository.findAllCompensationAndVoc();
    }
}
