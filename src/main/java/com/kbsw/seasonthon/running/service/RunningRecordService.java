package com.kbsw.seasonthon.running.service;

import com.kbsw.seasonthon.crew.enums.SafetyLevel;
import com.kbsw.seasonthon.running.dto.response.RunningStatsResponse;
import com.kbsw.seasonthon.running.entity.RunningRecord;
import com.kbsw.seasonthon.running.repository.RunningRecordRepository;
import com.kbsw.seasonthon.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RunningRecordService {

    private final RunningRecordRepository runningRecordRepository;

    /**
     * 사용자의 러닝 통계 조회 (더미 데이터)
     */
    public RunningStatsResponse getRunningStats(User user) {
        log.info("러닝 통계 조회 - 사용자: {}, 더미 데이터 반환", user.getUsername());
        
        // 더미 데이터 생성
        List<RunningStatsResponse.RecentRun> recentRuns = List.of(
                RunningStatsResponse.RecentRun.builder()
                        .id(1L)
                        .safetyLevel(SafetyLevel.SAFE)
                        .distanceKm(5.2)
                        .durationMinutes(32)
                        .pace("6'09\"/km")
                        .startTime(LocalDateTime.now().minusDays(1))
                        .weather("맑음")
                        .build(),
                RunningStatsResponse.RecentRun.builder()
                        .id(2L)
                        .safetyLevel(SafetyLevel.MEDIUM)
                        .distanceKm(3.8)
                        .durationMinutes(25)
                        .pace("6'34\"/km")
                        .startTime(LocalDateTime.now().minusDays(3))
                        .weather("흐림")
                        .build(),
                RunningStatsResponse.RecentRun.builder()
                        .id(3L)
                        .safetyLevel(SafetyLevel.SAFE)
                        .distanceKm(7.1)
                        .durationMinutes(42)
                        .pace("5'55\"/km")
                        .startTime(LocalDateTime.now().minusDays(5))
                        .weather("맑음")
                        .build(),
                RunningStatsResponse.RecentRun.builder()
                        .id(4L)
                        .safetyLevel(SafetyLevel.SAFE)
                        .distanceKm(4.5)
                        .durationMinutes(28)
                        .pace("6'13\"/km")
                        .startTime(LocalDateTime.now().minusDays(7))
                        .weather("맑음")
                        .build(),
                RunningStatsResponse.RecentRun.builder()
                        .id(5L)
                        .safetyLevel(SafetyLevel.MEDIUM)
                        .distanceKm(6.3)
                        .durationMinutes(38)
                        .pace("6'02\"/km")
                        .startTime(LocalDateTime.now().minusDays(10))
                        .weather("비")
                        .build()
        );
        
        return RunningStatsResponse.builder()
                .totalRuns(15)
                .totalDistanceKm(87.5)
                .totalDurationMinutes(525)
                .averagePace("6'00\"/km")
                .bestPace("5'30\"/km")
                .averageDistanceKm(5.8)
                .averageDurationMinutes(35)
                .lastRunDate(LocalDateTime.now().minusDays(1))
                .recentRuns(recentRuns)
                .safetyLevel(SafetyLevel.SAFE)  // 전체 안전도 레벨
                .build();
    }

    /**
     * 페이스 포맷팅 (분/km -> '분'초"/km)
     */
    private String formatPace(Double paceMinutes) {
        if (paceMinutes == null || paceMinutes == 0) {
            return "0'00\"/km";
        }
        
        int minutes = (int) paceMinutes.doubleValue();
        int seconds = (int) ((paceMinutes - minutes) * 60);
        
        return String.format("%d'%02d\"/km", minutes, seconds);
    }

    /**
     * 러닝 기록 저장
     */
    @Transactional
    public RunningRecord saveRunningRecord(RunningRecord record) {
        // 페이스 자동 계산
        String calculatedPace = record.calculatePace();
        record = RunningRecord.builder()
                .id(record.getId())
                .user(record.getUser())
                .distanceKm(record.getDistanceKm())
                .durationMinutes(record.getDurationMinutes())
                .pace(calculatedPace)
                .bestPace(record.getBestPace())
                .startTime(record.getStartTime())
                .endTime(record.getEndTime())
                .routeData(record.getRouteData())
                .weather(record.getWeather())
                .notes(record.getNotes())
                .build();
        
        return runningRecordRepository.save(record);
    }

    /**
     * 사용자의 모든 러닝 기록 조회
     */
    public List<RunningRecord> getUserRunningRecords(Long userId) {
        return runningRecordRepository.findByUserOrderByStartTimeDesc(userId);
    }
}
