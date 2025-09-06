package com.kbsw.seasonthon.crew.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CrewCreateRequest {
    private String title;
    private String description;
    private Integer maxParticipants;
    private String routeId;
    private String type;                 // 경로 타입 (예: "safe", "fast", "scenic")
    private Double distanceKm;           // 거리 (km)
    private Integer safetyScore;         // 안전 점수
    private Integer durationMin;         // 예상 소요 시간 (분)
    private List<String> waypoints;      // 경로 좌표 리스트 (예: ["37.5665,126.9780", "37.5675,126.9790"])
    private List<String> tags;
    private String startLocation;        // 시작 위치 (예: "경북대학교 정문")
    private String pace;                 // 페이스 (예: "6'30\"/km")
    private LocalDateTime startTime;     // 시작 시간
}


