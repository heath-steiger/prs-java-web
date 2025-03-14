package com.prs.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.RequestRepo;
import com.prs.model.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {

	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Request> getById(@PathVariable int id) {
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent()) {
			return r;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id" + id);
		}
	}
	
	@GetMapping("/list-review/{id}")
	public List<Request> getRequestByReview(@PathVariable int id){
		String Review = "Review";
	return requestRepo.findByStatusAndUserIdNot(Review, id);
	
	}

	@PostMapping("")
	public Request add(@RequestBody Request request) {
		request.setRequestNumber(getRequestNumber());
		request.setStatus("New");
		request.setTotal(0.0);
		request.setSubmittedDate(LocalDate.now());
		
		return requestRepo.save(request);
	}

	@PutMapping("/{id}")
	public void putRequest(@PathVariable int id, @RequestBody Request request) {
		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Request id mismatch vs URL. ");
			
		}
		else if (requestRepo.existsById(request.getId())) {
			requestRepo.save(request);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Request not found for id"+id);
		}
	}
	@PutMapping("/submit-review/{id}")
	public void putRequestReview(@PathVariable int id) {
		if (requestRepo.existsById(id)) {
		Request r = requestRepo.findById(id).get();
		r.setStatus("Review");
		requestRepo.save(r);
		} else

		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id" + id);
		}
	}
	
	@PutMapping("/approve/{id}")
	public void putRequestApproval(@PathVariable int id) {
		if (requestRepo.existsById(id)) {
		Request r = requestRepo.findById(id).get();
		r.setStatus("Approved");
		requestRepo.save(r);
		} else

		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id" + id);
		}
	}
	
	@PutMapping("/reject/{id}")
	public void putRequestRejection(@PathVariable int id) {
		if (requestRepo.existsById(id)) {
		Request r = requestRepo.findById(id).get();
		r.setStatus("Rejected");
		requestRepo.save(r);
		} else

		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id" + id);
		}
	}

	@DeleteMapping("/{id}")
	public void delRequest(@PathVariable int id) {
		if (requestRepo.existsById(id)) {
			requestRepo.deleteById(id);
		} else

		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id" + id);
		}
	}
	private String getRequestNumber() {
	    String requestNbr = "R";
	    
	    LocalDate today = LocalDate.now();
	    requestNbr += today.format(DateTimeFormatter.ofPattern("yyMMdd"));
	    
	    String maxReqNbr = requestRepo.findTopRequestNumber();
	    String reqNbr = "";
	    if (maxReqNbr != null) {
	        // get last 4 characters, convert to number
	        String tempNbr = maxReqNbr.substring(7);
	        int nbr = Integer.parseInt(tempNbr);
	        nbr++;
	        // pad w/ leading zeros
	        reqNbr += nbr;
	        reqNbr = String.format("%04d", nbr);
	    }
	    else {
	        reqNbr = "0001";
	    }
	    requestNbr += reqNbr;
	    return requestNbr;

	}
}
