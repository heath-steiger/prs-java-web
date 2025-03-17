package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineitemRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Request;


@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineitemController {

	@Autowired
	private LineitemRepo lineitemRepo;
	 @Autowired
	    private RequestRepo requestRepo;

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
	public List<LineItem> getByRequestId(@PathVariable int id) {
		List<LineItem> l = lineitemRepo.findByRequestId(id);
		if (l.isEmpty()) {
			return l;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id" + id);
		}
	}

	@PostMapping("")
	public LineItem add(@RequestBody LineItem lineitem) {
		recalculateCollectionValue(lineitem.getRequest().getId());
		return lineitemRepo.save(lineitem);
		//recalculateCollectionValue();
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
		recalculateCollectionValue(lineitem.getRequest().getId());
	}

	@DeleteMapping("/{id}")
	public void delLineItem(@PathVariable int id) {
		if (lineitemRepo.existsById(id)) {
			LineItem l = lineitemRepo.findById(id).get();
			lineitemRepo.deleteById(id);
			recalculateCollectionValue(l.getRequest().getId());
		} else

		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id" + id);
		}
	}
	
	private void recalculateCollectionValue(int reqId){
		
		Request request = requestRepo.findById(reqId).get();
		List<LineItem> lineItems = lineitemRepo.findByRequestId(reqId);
		double total = 0;
        for (LineItem li : lineItems) {
            total += li.getQuantity() * li.getProduct().getPrice();
        } 
		
        request.setTotal(total);
        requestRepo.save(request);
	    }
}








