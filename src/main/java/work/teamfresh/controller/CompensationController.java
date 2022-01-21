package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.teamfresh.domain.Compensation;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.dto.FindAllCompensateDto;
import work.teamfresh.dto.ReceiveCompensationDto;
import work.teamfresh.service.CompensationService;

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
     * 배상 접수 API
     */
    @PostMapping
    public ResponseEntity receiveCompensation(@RequestBody ReceiveCompensationDto receiveCompensationDto) {
        // 배상 접수 처리
        Voc voc = compensationService.receiveCompensation(receiveCompensationDto);
        
        // 고객사 귀책이면 바로 배상 시스템 등록 처리
        if(voc.getVocType()==VocType.VENDOR)
            compensationService.registerCompensation(receiveCompensationDto);

        return ResponseEntity.ok().build();
    }


}
