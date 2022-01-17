package work.teamfresh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.teamfresh.domain.Voc;
import work.teamfresh.dto.FindAllAssembledVocDto;
import work.teamfresh.dto.RegisterVocDto;
import work.teamfresh.service.VocService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voc")
@RequiredArgsConstructor
public class VocCotnroller {

    private final VocService vocService;

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
}



