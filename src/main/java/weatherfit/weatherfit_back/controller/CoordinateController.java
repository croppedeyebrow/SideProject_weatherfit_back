package weatherfit.weatherfit_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import weatherfit.weatherfit_back.entity.Coordinate;
import weatherfit.weatherfit_back.service.CoordinateService;

@RestController
@Slf4j
@RequestMapping("/coordinate")
@RequiredArgsConstructor



public class CoordinateController {
    private final CoordinateService coordinateService;


    //착장 정보 한번에 조회
    @GetMapping("/list")
    public ResponseEntity<List<Coordinate>> getCoordinateList() {
        List<Coordinate> coordinateList = coordinateService.getCoordinateList();
        return ResponseEntity.ok(coordinateList);
    }

    ///////////////////

    // 착장 정보 중 preference 컬럼 조회
    @GetMapping("/preference")
    public ResponseEntity<List<String>> getPreferenceList() {
        List<String> preferenceList = coordinateService.getPreferenceList();
        return ResponseEntity.ok(preferenceList);
    }
 
    // 착장 정보 중 preference 컬럼 중 미니멀만 조회.    
    @GetMapping("/preference/minimal")
    public ResponseEntity<List<String>> getMinimalPreferenceList() {
        List<String> minimalPreferenceList = coordinateService.getMinimalPreferenceList();
        return ResponseEntity.ok(minimalPreferenceList);
    }

    // 착장 정보 중 preference 컬럼 중 모던만 조회.    
    @GetMapping("/preference/modern")
    public ResponseEntity<List<String>> getModernPreferenceList() {
        List<String> modernPreferenceList = coordinateService.getModernPreferenceList();
        return ResponseEntity.ok(modernPreferenceList);
    }

    // 착장 정보 중 preference 컬럼 중 캐주얼만 조회.    
    @GetMapping("/preference/casual")
    public ResponseEntity<List<String>> getCasualPreferenceList() {
        List<String> casualPreferenceList = coordinateService.getCasualPreferenceList();
        return ResponseEntity.ok(casualPreferenceList);
    }

    // 착장 정보 중 preference 컬럼 중 스트릿만 조회.    
    @GetMapping("/preference/street")
    public ResponseEntity<List<String>> getStreetPreferenceList() {
        List<String> streetPreferenceList = coordinateService.getStreetPreferenceList();
        return ResponseEntity.ok(streetPreferenceList);
    }

    // 착장 정보 중 preference 컬럼 중 러블리만 조회.    
    @GetMapping("/preference/lively")
    public ResponseEntity<List<String>> getLivelyPreferenceList() {
        List<String> livelyPreferenceList = coordinateService.getLivelyPreferenceList();
        return ResponseEntity.ok(livelyPreferenceList);
    }
    
    //착장 정보 중 preference 컬럼 중 럭셔리만 조회.    
    @GetMapping("/preference/luxury")
    public ResponseEntity<List<String>> getLuxuryPreferenceList() {
        List<String> luxuryPreferenceList = coordinateService.getLuxuryPreferenceList();
        return ResponseEntity.ok(luxuryPreferenceList);
    }
    
    
    
    
    
}
