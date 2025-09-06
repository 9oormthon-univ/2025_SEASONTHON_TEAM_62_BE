package com.kbsw.seasonthon.running.repository;

import com.kbsw.seasonthon.running.entity.RecentPath;
import com.kbsw.seasonthon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentPathRepository extends JpaRepository<RecentPath, Long> {
    List<RecentPath> findAllByUserOrderByUsedAtDesc(User user);
}
