package com.kbsw.seasonthon.crew.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CrewCreateRequest {
    @NotBlank(message = "제목은 필수입니다")
    private String title;
    
    private String description;
    
    @NotNull(message = "최대 참여자 수는 필수입니다")
    @Positive(message = "최대 참여자 수는 양수여야 합니다")
    private Integer maxParticipants;
    
    @NotBlank(message = "경로 ID는 필수입니다")
    private String routeId;
    
    @NotBlank(message = "경로 타입은 필수입니다")
    private String type;                 // 경로 타입 (예: "safe", "fast", "scenic")
    
    @NotNull(message = "거리는 필수입니다")
    @Positive(message = "거리는 양수여야 합니다")
    private Double distanceKm;           // 거리 (km)
    
    @NotNull(message = "안전 점수는 필수입니다")
    @PositiveOrZero(message = "안전 점수는 0 이상이어야 합니다")
    private Integer safetyScore;         // 안전 점수
    
    @NotNull(message = "예상 소요 시간은 필수입니다")
    @Positive(message = "예상 소요 시간은 양수여야 합니다")
    private Integer durationMin;         // 예상 소요 시간 (분)
    
    private List<String> waypoints;      // 경로 좌표 리스트 (예: ["37.5665,126.9780", "37.5675,126.9790"])
    private List<String> tags;
    private String startLocation;        // 시작 위치 (예: "경북대학교 정문")
    private String pace;                 // 페이스 (예: "6'30\"/km")
    private LocalDateTime startTime;     // 시작 시간
}


