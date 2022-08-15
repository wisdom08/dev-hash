package com.one.devhash.repository;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Imagefile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagefileRepository extends JpaRepository<Imagefile, Long> {
	@Query("SELECT i FROM Imagefile i WHERE i.imageTarget = :target AND i.targetId = :targetId")
	List<Imagefile> findAllByTargetId(ImageTarget target, Long targetId);
}