package com.kbsw.seasonthon.route.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteRequest {
    private String routeId;
    private String type;
    private Double distanceKm;
    private Integer safetyScore;
    private Integer durationMin;
    private List<List<Double>> waypoints;
}




<<<<<<< HEAD

=======
>>>>>>> e04b319b397ad1c42354c7007afb3daa7e0bd33b
