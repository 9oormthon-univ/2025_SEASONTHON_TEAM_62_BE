package com.kbsw.seasonthon.running.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbsw.seasonthon.running.entity.RecentPath;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Slf4j
public class RecentPathResponse {
    private Long id;
    private List<List<Double>> waypoints;
    private String savedPolyline;
    private LocalDateTime usedAt;

    public static RecentPathResponse from(RecentPath recentPath) {
        return RecentPathResponse.builder()
                .id(recentPath.getId())
                .waypoints(parseWaypoints(recentPath.getWaypoints()))
                .savedPolyline(recentPath.getSavedPolyline())
                .usedAt(recentPath.getUsedAt())
                .build();
    }

    private static List<List<Double>> parseWaypoints(String waypointsJson) {
        if (waypointsJson == null || waypointsJson.trim().isEmpty()) {
            return List.of();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<List<Double>>> typeRef = new TypeReference<List<List<Double>>>() {};
            return objectMapper.readValue(waypointsJson, typeRef);
        } catch (JsonProcessingException e) {
            log.error("Waypoints JSON 파싱 실패: {}", waypointsJson, e);
            return List.of();
        }
    }
}
