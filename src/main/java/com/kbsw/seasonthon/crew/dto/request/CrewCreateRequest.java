package com.kbsw.seasonthon.crew.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "크루 생성 요청", example = """
{
  "title": "달서구 안전 러닝 크루",
  "description": "AI가 추천한 안전한 경로로 함께 러닝해요!",
  "maxParticipants": 8,
  "routeId": "ai_route_001",
  "type": "safe",
  "distanceKm": 5.2,
  "safetyScore": 85,
  "durationMin": 32,
  "waypoints": ["37.5665,126.9780", "37.5675,126.9790"],
  "tags": ["초보자", "안전", "AI추천"],
  "startLocation": "경북대학교 정문",
  "pace": "6'30\"/km",
  "startTime": "2024-01-15T07:00:00"
}
""")
public class CrewCreateRequest {
    
    @Schema(description = "크루 제목", example = "달서구 안전 러닝 크루", required = true)
    private String title;
    
    @Schema(description = "크루 설명", example = "AI가 추천한 안전한 경로로 함께 러닝해요!")
    private String description;
    
    @Schema(description = "최대 참여자 수", example = "8", required = true)
    private Integer maxParticipants;
    
    @Schema(description = "경로 ID", example = "ai_route_001", required = true)
    private String routeId;
    
    @Schema(description = "경로 타입", example = "safe", allowableValues = {"safe", "normal", "challenging"}, required = true)
    private String type;
    
    @Schema(description = "거리 (km)", example = "5.2", required = true)
    private Double distanceKm;
    
    @Schema(description = "안전 점수 (0-100)", example = "85", required = true)
    private Integer safetyScore;
    
    @Schema(description = "예상 소요 시간 (분)", example = "32", required = true)
    private Integer durationMin;
    
    @Schema(description = "경로 좌표 리스트", example = "[\"37.5665,126.9780\", \"37.5675,126.9790\"]")
    private List<String> waypoints;
    
    @Schema(description = "태그 리스트", example = "[\"초보자\", \"안전\", \"AI추천\"]")
    private List<String> tags;
    
    @Schema(description = "시작 위치", example = "경북대학교 정문")
    private String startLocation;
    
    @Schema(description = "페이스", example = "6'30\"/km")
    private String pace;
    
    @Schema(description = "시작 시간", example = "2024-01-15T07:00:00")
    private LocalDateTime startTime;
}


