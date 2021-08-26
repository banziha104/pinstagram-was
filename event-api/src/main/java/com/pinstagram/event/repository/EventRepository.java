package com.pinstagram.event.repository;

import com.pinstagram.domain.entity.contents.ContentsEntity;
import com.pinstagram.domain.entity.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value = "select * from event c where st_distance_sphere(POINT(:lng,:lat),POINT(c.lng,c.lat)) < :limit",nativeQuery = true)
    List<EventEntity> findAllByDistance(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("limit") double limit
    );
}
