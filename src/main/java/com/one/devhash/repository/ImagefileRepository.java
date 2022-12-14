package com.one.devhash.repository;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Imagefile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImagefileRepository extends JpaRepository<Imagefile, Long> {
	@Query("SELECT i FROM Imagefile i WHERE i.imageTarget = :target AND i.targetId = :targetId")
	List<Imagefile> findAllByTargetId(@Param("target") ImageTarget target, @Param("targetId") Long targetId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Imagefile i WHERE i.imageTarget = :target AND i.targetId = :targetId")
	void deleteAllByTargetId(@Param("target") ImageTarget target, @Param("targetId") Long targetId);
}