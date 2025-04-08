package weatherfit.weatherfit_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import weatherfit.weatherfit_back.service.WeatherService;
import weatherfit.weatherfit_back.dto.WeatherDTO;
import org.springframework.http.ResponseEntity;
import weatherfit.weatherfit_back.dto.WeatherForecastDTO;
import weatherfit.weatherfit_back.dto.CoordinateDTO;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    // 기존 날씨 조회 API
    @GetMapping("/current")
    public ResponseEntity<WeatherDTO> getCurrentWeather() {
        log.info("현재 날씨 조회 요청");
        WeatherDTO weather = weatherService.getCurrentWeather();
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherForecastDTO>> getWeatherForecast() {
        log.info("날씨 예보 조회 요청");
        List<WeatherForecastDTO> forecast = weatherService.getWeatherForecast();
        return ResponseEntity.ok(forecast);
    }

    // 날씨 기반 스타일 추천 API
    @GetMapping("/styles/{weatherCondition}")
    public ResponseEntity<List<CoordinateDTO>> getWeatherBasedStyles(
            @PathVariable String weatherCondition) {
        log.info("날씨 기반 스타일 추천 요청: {}", weatherCondition);
        List<CoordinateDTO> styles = weatherService.getWeatherBasedStyles(weatherCondition);
        return ResponseEntity.ok(styles);
    }

    @GetMapping("/styles/{weatherCondition}/likes")
    public ResponseEntity<List<CoordinateDTO>> getWeatherBasedLikes(
            @PathVariable String weatherCondition,
            @RequestParam Long userId) {
        log.info("날씨 기반 좋아요 스타일 요청: {}, userId: {}", weatherCondition, userId);
        List<CoordinateDTO> likes = weatherService.getWeatherBasedLikes(weatherCondition, userId);
        return ResponseEntity.ok(likes);
    }

    @PostMapping("/styles/{weatherCondition}/{styleId}/like")
    public ResponseEntity<String> toggleWeatherStyleLike(
            @PathVariable String weatherCondition,
            @PathVariable Long styleId,
            @RequestParam Long userId) {
        log.info("날씨 기반 스타일 좋아요 토글 요청: {}, styleId: {}, userId: {}", 
                weatherCondition, styleId, userId);
        String result = weatherService.toggleWeatherStyleLike(weatherCondition, styleId, userId);
        return ResponseEntity.ok(result);
    }
}
