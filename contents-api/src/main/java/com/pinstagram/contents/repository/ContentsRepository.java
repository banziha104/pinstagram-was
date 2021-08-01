package com.pinstagram.contents.repository;

import com.pinstagram.domain.entity.contents.ContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsRepository extends JpaRepository<ContentsEntity,Long> {
    @Query(value = "select * from contents c where st_distance_sphere(POINT(:lng,:lat),POINT(c.lng,c.lat)) < :limit",nativeQuery = true)
    List<ContentsEntity> findAllByDistance(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("limit") double limit
    );
}
