package weatherfit.weatherfit_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weatherfit.weatherfit_back.entity.Like;
import weatherfit.weatherfit_back.entity.User;
import weatherfit.weatherfit_back.entity.Coordinate;

import java.util.Optional;
import java.util.List;
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndCoordinate(User user, Coordinate coordinate);
    Optional<Like> findByUserAndCoordinate(User user, Coordinate coordinate);
    List<Like> findByUserId(Long userId);
    Optional<Like> findByUserIdAndCoordinateId(Long userId, Long coordinateId);
} 