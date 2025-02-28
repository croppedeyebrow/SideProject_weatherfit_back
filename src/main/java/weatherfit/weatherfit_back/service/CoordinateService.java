package weatherfit.weatherfit_back.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import weatherfit.weatherfit_back.entity.Coordinate;
import weatherfit.weatherfit_back.repository.CoordinateRepository;
import weatherfit.weatherfit_back.entity.User;
import weatherfit.weatherfit_back.repository.LikeRepository;
import weatherfit.weatherfit_back.repository.UserRepository;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional

public class CoordinateService {
    private final CoordinateRepository coordinateRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    //착장 정보 조회
    public List<Coordinate> getCoordinateList() {
        return coordinateRepository.findAll();
    }

    //착장 정보 중 preference 컬럼 조회
    public List<String> getPreferenceList() {
        return coordinateRepository.findAll().stream()
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 정보 중 preference 컬럼 중 미니멀만 조회.    
    public List<String> getMinimalPreferenceList() {
        return coordinateRepository.findAll().stream()
            .filter(coordinate -> coordinate.getPreference().equals("미니멀"))
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 정보 중 preference 컬럼 중 모던만 조회.    
    public List<String> getModernPreferenceList() {
        return coordinateRepository.findAll().stream()
            .filter(coordinate -> coordinate.getPreference().equals("모던"))
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 정보 중 preference 컬럼 중 캐주얼만 조회.    
    public List<String> getCasualPreferenceList() {
        return coordinateRepository.findAll().stream()
            .filter(coordinate -> coordinate.getPreference().equals("캐주얼"))
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 정보 중 preference 컬럼 중 스트릿만 조회.    
    public List<String> getStreetPreferenceList() {
        return coordinateRepository.findAll().stream()
            .filter(coordinate -> coordinate.getPreference().equals("스트릿"))
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 정보 중 preference 컬럼 중 러블리만 조회.    
    public List<String> getLivelyPreferenceList() {
        return coordinateRepository.findAll().stream()
            .filter(coordinate -> coordinate.getPreference().equals("러블리"))
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 정보 중 preference 컬럼 중 럭셔리만 조회.    
    public List<String> getLuxuryPreferenceList() {
        return coordinateRepository.findAll().stream()
            .filter(coordinate -> coordinate.getPreference().equals("럭셔리"))
            .map(Coordinate::getPreference)
            .collect(Collectors.toList());
    }

    //착장 좋아요 여부 확인
    public boolean isLikedByUser(Long userId, Long coordinateId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Coordinate coordinate = coordinateRepository.findById(coordinateId)
            .orElseThrow(() -> new RuntimeException("착장을 찾을 수 없습니다."));
            
        return likeRepository.existsByUserAndCoordinate(user, coordinate);
    }


    

}

