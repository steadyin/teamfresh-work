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
     * 배상 요청
     *
     * 배상 요청이 오면 고객사 귀책은 배상 시스템 바로 등록
     *               운송사 귀책은 운송기사 확인 시 배송 시스템 등록
     */
    public Optional<Long> requestCompensation(RequestCompensationDto requestCompensationDto) {
        Long vocId = requestCompensationDto.getVocId();
        BigDecimal amount = requestCompensationDto.getAmount();

        Voc voc = vocRepository.findOne(vocId).orElseThrow(ObjectNotFoundException::new);

        if (voc.getVocStatus().equals(VocStatus.REQUESTED_CLAIM))
            voc.changeVocStatus(VocStatus.REQUESTED_COMPENSATE);
        else
            throw new VocStatuaException("클레임 요청 상태인 VOC만 배상 요청이 가능합니다.");

        // 고객사 귀책 배상 등록 처리
        if (voc.isPossibleCompensation()) return Optional.ofNullable(registerCompensation(vocId, amount));
        else return null;
    }

    /**
     * 배상 등록
     */
    public Long registerCompensation(Long vocId, BigDecimal amount) {
        Voc voc = vocRepository.findOne(vocId).orElseThrow(ObjectNotFoundException::new);

        // VOC 상태 배상 조건 성립
        if (voc.isPossibleCompensation()) {
            Compensation compensation = Compensation.createCompensation(voc, amount);
            compensationRepository.save(compensation);

            return compensation.getId();
        }
        // VOC 상태 배상 조건 불성립
        else {
            throw new VocStatuaException();
        }
    }

    /**
     * 배상 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Compensation> findAllCompensation() {
        return compensationRepository.findAllCompensationAndVoc();
    }
}
