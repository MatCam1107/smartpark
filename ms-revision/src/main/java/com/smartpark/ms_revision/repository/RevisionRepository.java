package com.smartpark.ms_revision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartpark.ms_revision.model.Revision;

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {

}