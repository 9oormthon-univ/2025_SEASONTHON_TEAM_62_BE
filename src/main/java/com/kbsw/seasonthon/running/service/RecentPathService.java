package com.kbsw.seasonthon.running.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbsw.seasonthon.running.dto.request.CompleteRouteRequest;
import com.kbsw.seasonthon.running.dto.response.RecentPathResponse;
import com.kbsw.seasonthon.running.entity.RecentPath;
import com.kbsw.seasonthon.running.repository.RecentPathRepository;
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
public class RecentPathService {

    private final RecentPathRepository recentPathRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public RecentPathResponse completeRoute(User user, CompleteRouteRequest request) {
        log.info("경로 완주 정보 저장 - 사용자: {} (AWS: 더미 데이터 반환)", user.getEmail());

        // AWS 배포용: DB 저장 없이 더미 데이터 반환
        try {
            String waypointsJson = objectMapper.writeValueAsString(request.getWaypoints());
            
            // 더미 RecentPath 엔티티 생성 (DB 저장 없이)
            RecentPath dummyPath = RecentPath.builder()
                    .id(1L) // 더미 ID
                    .user(user)
                    .waypoints(waypointsJson)
                    .savedPolyline(request.getSavedPolyline())
                    .usedAt(LocalDateTime.now())
                    .build();

            log.info("경로 완주 정보 더미 저장 완료 - ID: {}", dummyPath.getId());
            return RecentPathResponse.from(dummyPath);

        } catch (JsonProcessingException e) {
            log.error("waypoints JSON 변환 실패", e);
            throw new RuntimeException("경로 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }

    public List<RecentPathResponse> getAllRecentPaths(User user) {
        log.info("최근 경로 목록 조회 - 사용자: {} (AWS: 더미 데이터 반환)", user.getEmail());

        // AWS 배포용: DB 조회 없이 더미 데이터 반환
        List<RecentPath> dummyPaths = List.of(
                RecentPath.builder()
                        .id(1L)
                        .user(user)
                        .waypoints("[{\"lat\": 35.8714, \"lng\": 128.6014}]")
                        .savedPolyline("더미 폴리라인 데이터")
                        .usedAt(LocalDateTime.now().minusHours(1))
                        .build(),
                RecentPath.builder()
                        .id(2L)
                        .user(user)
                        .waypoints("[{\"lat\": 35.8714, \"lng\": 128.6014}, {\"lat\": 35.8724, \"lng\": 128.6024}]")
                        .savedPolyline("더미 폴리라인 데이터 2")
                        .usedAt(LocalDateTime.now().minusHours(2))
                        .build()
        );
        
        log.info("더미 경로 수: {}", dummyPaths.size());

        return dummyPaths.stream()
                .map(RecentPathResponse::from)
                .collect(Collectors.toList());
    }
}