package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.LineItem;

public interface LineitemRepo extends JpaRepository<LineItem, Integer>{

}
