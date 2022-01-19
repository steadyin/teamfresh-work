package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.teamfresh.domain.Compensation;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.dto.FindAllCompensateDto;
import work.teamfresh.dto.RequestCompensationDto;
import work.teamfresh.service.CompensationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compensation")
@RequiredArgsConstructor
public class CompensationController {

    private final CompensationService compensationService;

    /**
     * 배상 목록 API
     */
    @GetMapping
    public ResponseEntity<List<FindAllCompensateDto>> findAllCompensation() {
        List<Compensation> compensationList = compensationService.findAllCompensation();
        List<FindAllCompensateDto> findAllCompensateDto = compensationList.stream().map(FindAllCompensateDto::EntityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(findAllCompensateDto);
    }

    /**
     * 배상 요청 API
     */
    @PostMapping
    public ResponseEntity requestCompensation(@RequestBody RequestCompensationDto requestCompensationDto) {
        // 배상 요청 처리
        Voc voc = compensationService.requestCompensation(requestCompensationDto);
        
        // 고객사 귀책이면 바로 배상 등록 처리
        if(voc.getVocType().equals(VocType.VENDOR))
            compensationService.registerCompensation(requestCompensationDto);

        return ResponseEntity.ok().build();
    }


}
