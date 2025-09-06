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
        log.info("경로 완주 정보 저장 - 사용자: {}", user.getEmail());

        try {
            // waypoints를 JSON 문자열로 변환
            String waypointsJson = objectMapper.writeValueAsString(request.getWaypoints());

            RecentPath recentPath = RecentPath.builder()
                    .user(user)
                    .waypoints(waypointsJson)
                    .savedPolyline(request.getSavedPolyline())
                    .usedAt(LocalDateTime.now())
                    .build();

            RecentPath savedPath = recentPathRepository.save(recentPath);
            log.info("경로 완주 정보 저장 완료 - ID: {}", savedPath.getId());

            return RecentPathResponse.from(savedPath);

        } catch (JsonProcessingException e) {
            log.error("waypoints JSON 변환 실패", e);
            throw new RuntimeException("경로 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }

    public List<RecentPathResponse> getAllRecentPaths(User user) {
        log.info("최근 경로 목록 조회 - 사용자: {}", user.getEmail());

        List<RecentPath> recentPaths = recentPathRepository.findAllByUserOrderByUsedAtDesc(user);
        log.info("조회된 경로 수: {}", recentPaths.size());

        return recentPaths.stream()
                .map(RecentPathResponse::from)
                .collect(Collectors.toList());
    }
}