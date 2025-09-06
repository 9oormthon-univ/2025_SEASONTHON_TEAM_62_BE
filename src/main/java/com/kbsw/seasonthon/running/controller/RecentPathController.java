package com.kbsw.seasonthon.running.controller;

import com.kbsw.seasonthon.global.base.response.ResponseBody;
import com.kbsw.seasonthon.global.base.response.ResponseUtil;
import com.kbsw.seasonthon.global.base.response.exception.ExceptionType;
import com.kbsw.seasonthon.running.dto.request.CompleteRouteRequest;
import com.kbsw.seasonthon.running.dto.response.RecentPathResponse;
import com.kbsw.seasonthon.running.service.RecentPathService;
import com.kbsw.seasonthon.user.entity.User;
import com.kbsw.seasonthon.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recent-paths")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://seasonthon-alb-272154529.ap-northeast-2.elb.amazonaws.com"}, allowCredentials = "false")
@Tag(name = "Recent Paths API", description = "최근 경로 완주 기록 관련 API")
public class RecentPathController {

    private final RecentPathService recentPathService;
    private final UserRepository userRepository;

    /**
     * 경로 완주 - recent_paths에 저장
     */
    @PostMapping(value = "/complete", consumes = "application/json", produces = "application/json")
    @Operation(summary = "경로 완주", description = "프론트에서 경로 종료 버튼을 누르면 완주 정보를 recent_paths DB에 저장합니다.")
    public ResponseEntity<?> completeRoute(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "경로 완주 정보", required = true,
                    content = @Content(schema = @Schema(implementation = CompleteRouteRequest.class))
            )
            @Valid @RequestBody CompleteRouteRequest request) {

        log.info("경로 완주 API 호출 - waypoints: {}, savedPolyline: {}", request.getWaypoints(), request.getSavedPolyline());

        try {
            User user = getDummyUser();
            RecentPathResponse response = recentPathService.completeRoute(user, request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.createSuccessResponse(response));

        } catch (Exception e) {
            log.error("경로 완주 정보 저장 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.createFailureResponse(ExceptionType.UNEXPECTED_SERVER_ERROR, "경로 완주 정보 저장 중 오류가 발생했습니다."));
        }
    }

    /**
     * 최근 경로 목록 조회
     */
    @GetMapping
    @Operation(summary = "최근 경로 목록 조회", description = "사용자의 최근 완주한 경로 목록을 조회합니다.")
    public ResponseEntity<?> getAllRecentPaths() {
        log.info("최근 경로 목록 조회 API 호출 - 개발 모드: 더미 사용자 사용");

        try {
            User user = getDummyUser();
            List<RecentPathResponse> responses = recentPathService.getAllRecentPaths(user);

            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(responses));

        } catch (Exception e) {
            log.error("최근 경로 목록 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.createFailureResponse(ExceptionType.UNEXPECTED_SERVER_ERROR, "최근 경로 목록 조회 중 오류가 발생했습니다."));
        }
    }

    /**
     * 개발용 더미 사용자 조회 (DB에서 실제 User 엔티티 조회)
     */
    private User getDummyUser() {
        try {
            // 실제 DB에서 User를 조회하여 영속성 컨텍스트에 관리되는 엔티티 반환
            User user = userRepository.findById(1L)
                    .orElseGet(() -> {
                        log.warn("User ID 1이 존재하지 않습니다. 첫 번째 사용자를 조회합니다.");
                        // 만약 1번 사용자가 없다면 첫 번째 사용자를 조회
                        return userRepository.findAll().stream()
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
                    });
            
            log.info("더미 사용자 조회 성공 - ID: {}, Email: {}", user.getId(), user.getEmail());
            return user;
            
        } catch (Exception e) {
            log.error("더미 사용자 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("사용자 조회 중 오류가 발생했습니다.", e);
        }
    }
}