package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.LineItem;

public interface LineitemRepo extends JpaRepository<LineItem, Integer>{
	Optional<LineItem> findByRequestId(int requestId);
}
