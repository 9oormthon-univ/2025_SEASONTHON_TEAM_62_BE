package com.kbsw.seasonthon.running.entity;

import com.kbsw.seasonthon.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recent_paths")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecentPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String waypoints; // 경로 좌표 데이터 (JSON 형태)

    @Column(columnDefinition = "TEXT")
    private String savedPolyline; // 저장된 폴리라인

    @Column(name = "used_at", nullable = false)
    private LocalDateTime usedAt; // 사용 시간
}
