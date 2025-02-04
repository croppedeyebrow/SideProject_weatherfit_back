package weatherfit.weatherfit_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weatherfit.weatherfit_back.dto.UserReqDTO;
import weatherfit.weatherfit_back.entity.User;
import weatherfit.weatherfit_back.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;


    //회원 정보 수정정
    public void updateUser(UserReqDTO userReqDTO) {
        User user = userRepository.findByEmail(userReqDTO.getEmail())
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        // 사용자 정보 업데이트
        user.setName(userReqDTO.getName());
        user.setAgeGroup(userReqDTO.getAgeGroup());
        user.setProfileImage(userReqDTO.getProfileImage());
        
        userRepository.save(user);
    }

    //회원 탈퇴
    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
    
        return "정상적으로 탈퇴되었습니다.";
    }

    //비밀번호 조회
    public String getPassword(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("해당 계정이 존재하지 않습니다."));
        return user.getPassword();
    }
}
