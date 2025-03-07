package weatherfit.weatherfit_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weatherfit.weatherfit_back.dto.UserReqDTO;
import weatherfit.weatherfit_back.dto.UserResDTO;
import weatherfit.weatherfit_back.entity.User;
import weatherfit.weatherfit_back.repository.UserRepository;
import weatherfit.weatherfit_back.constant.Authority;
import jakarta.servlet.http.HttpSession;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;  // 세션 주입

    // user 이메일 중복 확인
    public Boolean checkUnique(int type, String info) {
        switch (type) {
            case 0:
                return userRepository.existsByEmail(info);
            case 1:
                return !userRepository.existsByEmail(info);
            default:
                return true;
        }
    }

    // user 회원 가입
    public UserResDTO join(UserReqDTO userReqDTO) {
        if (userRepository.existsByEmail(userReqDTO.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
            .email(userReqDTO.getEmail())
            .password(passwordEncoder.encode(userReqDTO.getPassword()))
            .name(userReqDTO.getName())
            .ageGroup(userReqDTO.getAgeGroup())
            .preferences(userReqDTO.getPreferencesAsString())  // JSON 문자열로 변환하여 저장
            .authority(Authority.ROLE_USER)
            .isDeleted(false)
            .build();

        return UserResDTO.of(userRepository.save(user));
    }

    // user 로그인
    public UserResDTO login(UserReqDTO userReqDTO) {
        User user = userRepository.findByEmail(userReqDTO.getEmail())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
            
        if (!passwordEncoder.matches(userReqDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 세션에 저장 (프로필 이미지는 null일 수 있음)
        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("USER_EMAIL", user.getEmail());
        session.setAttribute("USER_NAME", user.getName());
        session.setAttribute("USER_AGEGROUP", user.getAgeGroup());
        // session.setAttribute("USER_PROFILEIMAGE", user.getProfileImage());  // null 허용
        
        return UserResDTO.of(user);
    }

    // user 로그인 상태 확인
    public boolean isLoggedIn() {
        try {
            // USER_EMAIL만으로 로그인 상태 확인
            return session.getAttribute("USER_EMAIL") != null;
        } catch (Exception e) {
            return false;
        }
    }

    // user 로그아웃
    public void logout() {
        // 세션 무효화
        session.invalidate();
    }
}
