package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineitemRepo;
import com.prs.model.LineItem;


@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineitemController {

	@Autowired
	private LineitemRepo lineitemRepo;

	@GetMapping("/")
	public List<LineItem> getAllLineitem() {
		return lineitemRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<LineItem> getById(@PathVariable int id) {
		Optional<LineItem> l = lineitemRepo.findById(id);
		if (l.isPresent()) {
			return l;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id" + id);
		}
	}
	
	@GetMapping("/lines-for-req/{id}")
	public Optional<LineItem> getByRequestId(@PathVariable int id) {
		Optional<LineItem> l = lineitemRepo.findByRequestId(id);
		if (l.isPresent()) {
			return l;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id" + id);
		}
	}

	@PostMapping("")
	public LineItem add(@RequestBody LineItem lineitem) {

		return lineitemRepo.save(lineitem);
	}

	@PutMapping("/{id}")
	public void putLineItem(@PathVariable int id, @RequestBody LineItem lineitem) {
		if (id != lineitem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL. ");

		} else if (lineitemRepo.existsById(lineitem.getId())) {
			lineitemRepo.save(lineitem);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id" + id);
		}
	}

	@DeleteMapping("/{id}")
	public void delLineItem(@PathVariable int id) {
		if (lineitemRepo.existsById(id)) {
			lineitemRepo.deleteById(id);
		} else

		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id" + id);
		}
	}
}
