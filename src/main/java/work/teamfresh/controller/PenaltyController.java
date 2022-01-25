package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.teamfresh.dto.ObjectPenaltyDto;
import work.teamfresh.service.PenaltyService;

@RestController
@RequestMapping("/api/penalty")
@RequiredArgsConstructor
public class PenaltyController {
    private final PenaltyService penaltyService;

    /**
     * 페널티 확인 API
     */
    @PostMapping("/{penaltyId}/confirm")
    public ResponseEntity confirmPenalty(@PathVariable Long penaltyId) {
        penaltyService.confirmPenalty(penaltyId);
        return ResponseEntity.ok().build();
    }

    /**
     * 페널티 이의제기 API
     */
    @PostMapping("/{penaltyId}/object")
    public ResponseEntity objectPenalty(@PathVariable Long penaltyId, @RequestBody ObjectPenaltyDto objectPenaltyDto) {
        penaltyService.objectedPenalty(penaltyId, objectPenaltyDto);
        return ResponseEntity.ok().build();
    }
}


