package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.teamfresh.dto.FindAllCompensateDto;
import work.teamfresh.service.CompensationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compensation")
public class CompensationController {

    private final CompensationService compensationService;

    /**
     * 배상 목록 API
     */
    @GetMapping
    public ResponseEntity<List<FindAllCompensateDto>> findAllCompensation() {
        List<FindAllCompensateDto> compensationList = compensationService.findAllCompensation();
        return ResponseEntity.ok(compensationList);
    }
}
