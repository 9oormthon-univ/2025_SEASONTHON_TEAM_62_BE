package com.kbsw.seasonthon.crew.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbsw.seasonthon.crew.enums.CrewStatus;
import com.kbsw.seasonthon.crew.enums.SafetyLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CrewListResponse {
    private Long id;
    private String title;
    private String description;
    private CrewStatus status;
    private String hostName;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private String routeId;
    private String type;
    private Double distanceKmValue;
    
    // 원본 Double 값 반환 (비교 및 계산용)
    public Double getDistanceKmValue() {
        return distanceKmValue;
    }
    
    // 단위가 포함된 거리 문자열 반환 (표시용)
    @JsonProperty("distanceKm")
    public String getDistanceKm() {
        return distanceKmValue != null ? String.format("%.1fkm", distanceKmValue) : "0.0km";
    }
    private Integer safetyScore;
    private SafetyLevel safetyLevel;
    private Integer durationMin;
    private List<String> waypoints;
    private List<String> tags;
    private String startLocation;        // 시작 위치 (예: "경북대학교 정문")
    private String pace;                 // 페이스 (예: "6'30\"/km")
    private LocalDateTime startTime;     // 시작 시간 (예: "18:00")
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


