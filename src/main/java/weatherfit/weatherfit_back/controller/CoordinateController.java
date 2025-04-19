package weatherfit.weatherfit_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;
import java.nio.charset.StandardCharsets;
import weatherfit.weatherfit_back.entity.Coordinate;
import weatherfit.weatherfit_back.service.CoordinateService;
import org.springframework.web.bind.annotation.CrossOrigin;
import weatherfit.weatherfit_back.dto.CoordinateDTO;

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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(coordinateList, headers, HttpStatus.OK);
    }

    ///////////////////

    // 착장 정보 중 preference 컬럼 조회
    @GetMapping("/preference")
    public ResponseEntity<List<String>> getPreferenceList() {
        List<String> preferenceList = coordinateService.getPreferenceList();
        log.info("조회된 preference 값: {}", preferenceList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(preferenceList, headers, HttpStatus.OK);
    }
    
    // 착장 정보 중 preference 컬럼 직접 조회 (디버깅용)
    @GetMapping("/preference/debug")
    public ResponseEntity<?> getPreferenceDebug() {
        List<Coordinate> coordinates = coordinateService.getCoordinateList();
        StringBuilder debug = new StringBuilder();
        
        for (Coordinate coordinate : coordinates) {
            debug.append("ID: ").append(coordinate.getId())
                .append(", Preference: ").append(coordinate.getPreference())
                .append(", TargetAgeGroup: ").append(coordinate.getTargetAgeGroup())
                .append(", Bytes (Preference): ");
            
            if (coordinate.getPreference() != null) {
                byte[] bytes = coordinate.getPreference().getBytes(StandardCharsets.UTF_8);
                for (byte b : bytes) {
                    debug.append(String.format("%02X ", b));
                }
            }
            
            debug.append(", Bytes (TargetAgeGroup): ");
            
            if (coordinate.getTargetAgeGroup() != null) {
                byte[] bytes = coordinate.getTargetAgeGroup().getBytes(StandardCharsets.UTF_8);
                for (byte b : bytes) {
                    debug.append(String.format("%02X ", b));
                }
            }
            
            debug.append("\n");
        }
        
        log.info("디버깅 정보: {}", debug.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
        return new ResponseEntity<>(debug.toString(), headers, HttpStatus.OK);
    }

    // 착장 정보 중 preference 컬럼이 미니멀인 값들의 coordinateImg 조회
    @GetMapping("/preference/minimal")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<CoordinateDTO>> getMinimalPreferenceList() {
        List<CoordinateDTO> minimalPreferenceList = coordinateService.getMinimalPreferenceList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(minimalPreferenceList, headers, HttpStatus.OK);
    }

    // 착장 정보 중 preference 컬럼 중 모던의 coordinateImg 조회.    
    @GetMapping("/preference/modern")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<CoordinateDTO>> getModernPreferenceList() {
        List<CoordinateDTO> modernPreferenceList = coordinateService.getModernPreferenceList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(modernPreferenceList, headers, HttpStatus.OK);
    }

    // 착장 정보 중 preference 컬럼 중 캐주얼의 coordinateImg 조회.   
    @GetMapping("/preference/casual")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<CoordinateDTO>> getCasualPreferenceList() {
        List<CoordinateDTO> casualPreferenceList = coordinateService.getCasualPreferenceList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(casualPreferenceList, headers, HttpStatus.OK);
    }

    // 착장 정보 중 preference 컬럼 중 스트릿의 coordinateImg 조회.    
    @GetMapping("/preference/street")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<CoordinateDTO>> getStreetPreferenceList() {
        List<CoordinateDTO> streetPreferenceList = coordinateService.getStreetPreferenceList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(streetPreferenceList, headers, HttpStatus.OK);
    }

    // 착장 정보 중 preference 컬럼 중 러블리의 coordinateImg 조회.    
    @GetMapping("/preference/lively")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<CoordinateDTO>> getLivelyPreferenceList() {
        List<CoordinateDTO> livelyPreferenceList = coordinateService.getLivelyPreferenceList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(livelyPreferenceList, headers, HttpStatus.OK);
    }
    
    //착장 정보 중 preference 컬럼 중 럭셔리의 coordinateImg 조회.    
    @GetMapping("/preference/luxury")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<CoordinateDTO>> getLuxuryPreferenceList() {
        List<CoordinateDTO> luxuryPreferenceList = coordinateService.getLuxuryPreferenceList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(luxuryPreferenceList, headers, HttpStatus.OK);
    }
    
    
    
        ///////////////////
        /// 
        
        // 착장 정보 중 tpo 컬럼 조회
        @GetMapping("/tpo")
        public ResponseEntity<List<String>> getTpoList() {
            List<String> tpoList = coordinateService.getTpoList();
            return new ResponseEntity<>(tpoList, HttpStatus.OK);
        }

        // 착장 정보 중 tpo 컬럼 중 daily의 coordinateImg 조회
        @GetMapping("/tpo/daily")
        public ResponseEntity<List<CoordinateDTO>> getDailyTpoList() {
            List<CoordinateDTO> dailyTpoList = coordinateService.getDailyTpoList();
            return new ResponseEntity<>(dailyTpoList, HttpStatus.OK);
        }

       // 착장 정보 중 tpo 컬럼 중 meeting의 coordinateImg 조회
       @GetMapping("/tpo/meeting")
       public ResponseEntity<List<CoordinateDTO>> getMeetingTpoList() {
        List<CoordinateDTO> meetingTpoList = coordinateService.getMeetingTpoList();
        return new ResponseEntity<>(meetingTpoList, HttpStatus.OK);
       }

       // 착장 정보 중 tpo 컬럼 중 date의 coordinateImg 조회
       @GetMapping("/tpo/date")
       public ResponseEntity<List<CoordinateDTO>> getDateTpoList() {
        List<CoordinateDTO> dateTpoList = coordinateService.getDateTpoList();
        return new ResponseEntity<>(dateTpoList, HttpStatus.OK);
       }

       // 착장 정보 중 tpo 컬럼 중 exercise의 coordinateImg 조회
       @GetMapping("/tpo/exercise")
       public ResponseEntity<List<CoordinateDTO>> getExerciseTpoList() {
        List<CoordinateDTO> exerciseTpoList = coordinateService.getExerciseTpoList();
        return new ResponseEntity<>(exerciseTpoList, HttpStatus.OK);
       }

       // 착장 정보 중 tpo 컬럼 중 work의 coordinateImg 조회
       @GetMapping("/tpo/work")
       public ResponseEntity<List<CoordinateDTO>> getWorkTpoList() {
        List<CoordinateDTO> workTpoList = coordinateService.getWorkTpoList();
        return new ResponseEntity<>(workTpoList, HttpStatus.OK);
       }
       
       
       // 착장 정보 중 tpo 컬럼 중 party의 coordinateImg 조회
       @GetMapping("/tpo/party")
       public ResponseEntity<List<CoordinateDTO>> getPartyTpoList() {
        List<CoordinateDTO> partyTpoList = coordinateService.getPartyTpoList();
        return new ResponseEntity<>(partyTpoList, HttpStatus.OK);
       }
       
       // 착장 정보 중 tpo 컬럼 중 travel의 coordinateImg 조회
       @GetMapping("/tpo/travel")
       public ResponseEntity<List<CoordinateDTO>> getTravelTpoList() {
        List<CoordinateDTO> travelTpoList = coordinateService.getTravelTpoList();
        return new ResponseEntity<>(travelTpoList, HttpStatus.OK);
       }
       
       // 착장 정보 중 tpo 컬럼 중 wedding의 coordinateImg 조회
       @GetMapping("/tpo/wedding")
       public ResponseEntity<List<CoordinateDTO>> getWeddingTpoList() {
        List<CoordinateDTO> weddingTpoList = coordinateService.getWeddingTpoList();
        return new ResponseEntity<>(weddingTpoList, HttpStatus.OK);
       }
       
   
    
}
