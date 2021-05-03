package com.rock.jpetstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rock.jpetstore.domain.Items;
import com.rock.jpetstore.service.ItemService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemservice;

	// @ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Object> all() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		List<Items> items = itemservice.getAll();

		if (items == null) {
			return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(items, headers, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/items/{itemid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable String itemid) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Items items = itemservice.getById(itemid);

		if (items == null) {
			// return new ResponseEntity<>("{}", headers, HttpStatus.OK);
			return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(items, headers, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/items/{itemid}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<Object, Object>> delete(@PathVariable String itemid) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<Object, Object> map = new HashMap<>();

		try {
			itemservice.delete(itemid);

			map.put("result", "OK");
			map.put("itemid", itemid);
			return new ResponseEntity<>(map, headers, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			map.put("result", "NOK");
			return new ResponseEntity<>(map, headers, HttpStatus.NO_CONTENT);
		} 
	}

	@RequestMapping(value = "/items", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Map<Object, Object>> insert(@RequestBody Items items) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<Object, Object> map = new HashMap<>();

		try {
			itemservice.save(items);

			map.put("result", "OK");
			map.put("items", items);
			return new ResponseEntity<>(map, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			map.put("result", "NOK");
			return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
		} 
	}

	@RequestMapping(value = "/items/{itemid}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Map<Object, Object>> update(@PathVariable String itemid, @RequestBody Items items) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<Object, Object> map = new HashMap<>();

		try {
			itemservice.save(items);

			map.put("result", "OK");
			map.put("items", items);
			return new ResponseEntity<>(map, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			map.put("result", "NOK");
			return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
		} 
	}
}
