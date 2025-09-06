package com.kbsw.seasonthon.running.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompleteRouteRequest {
    private List<List<Double>> waypoints;  // 경로 좌표 데이터
    private String savedPolyline;  // 저장된 폴리라인
}
