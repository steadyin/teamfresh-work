package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.dto.FindAllAssembledVocDto;
import work.teamfresh.dto.ReceiveCompensationDto;
import work.teamfresh.dto.RegisterPenaltyDto;
import work.teamfresh.dto.RegisterVocDto;
import work.teamfresh.service.CompensationService;
import work.teamfresh.service.PenaltyService;
import work.teamfresh.service.VocService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voc")
@RequiredArgsConstructor
public class VocController {

    private final VocService vocService;

    private final CompensationService compensationService;

    private final PenaltyService penaltyService;

    /**
     * Voc 목록 조회 API
     */
    @GetMapping
    public ResponseEntity<List<FindAllAssembledVocDto>> findAllVoc() {
        List<Voc> vocList = vocService.findAllVoc();
        List<FindAllAssembledVocDto> findAllVocDtoList = vocList.stream().map(FindAllAssembledVocDto::EntityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(findAllVocDtoList);
    }

    /**
     * Voc 등록 API
     */
    @PostMapping
    public ResponseEntity registerVoc(@RequestBody RegisterVocDto registerVocDto) {
        vocService.registerVoc(registerVocDto);

        return ResponseEntity.ok().build();
    }


    /**
     * VOC 배상 접수 API
     */
    @PostMapping("/{vocId}/compensation")
    public ResponseEntity receiveCompensation(@PathVariable Long vocId, @RequestBody ReceiveCompensationDto receiveCompensationDto) {
        // 배상 접수 처리
        Voc voc = compensationService.receiveCompensation(vocId, receiveCompensationDto);

        // 고객사 귀책이면 바로 배상 시스템 등록 처리
        if (voc.getVocType() == VocType.VENDOR)
            compensationService.registerCompensation(vocId, receiveCompensationDto);

        return ResponseEntity.ok().build();
    }

    /**
     * VOC 페널티 등록 API
     */
    @PostMapping("/{vocId}/penalty")
    public ResponseEntity registerPenalty(@PathVariable Long vocId, @RequestBody RegisterPenaltyDto registerPenaltyDto) {
        penaltyService.registerPenalty(vocId, registerPenaltyDto);
        return ResponseEntity.ok().build();
    }

}



