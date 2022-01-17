package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.teamfresh.domain.Compensation;
import work.teamfresh.dto.FindAllCompensateDto;
import work.teamfresh.dto.RequestCompensationDto;
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
     * 배상 요청 API
     */
    @PostMapping
    public ResponseEntity requestCompensation(@RequestBody RequestCompensationDto requestCompensationDto) {
        compensationService.requestCompensation(requestCompensationDto);

        return ResponseEntity.ok().build();
    }


}
