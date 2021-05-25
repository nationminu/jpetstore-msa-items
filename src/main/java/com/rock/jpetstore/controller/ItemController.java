package com.rock.jpetstore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rock.jpetstore.domain.Inventories;
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

	@Value("${inventory.server.url}")
	private String inventoryServer;
	
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


	@RequestMapping(value = "/items/{itemid}/inventories", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Object> get_inventories(@PathVariable String itemid) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");
 
  		CloseableHttpClient httpClient = HttpClients.createDefault();

        
		try {
 		    //HttpGet request = new HttpGet("http://127.0.0.1:8082/items");
			HttpGet request = new HttpGet(inventoryServer);

		    request.addHeader("api-key", "change-key");
	        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
	  
	        
	        CloseableHttpResponse response = httpClient.execute(request);
	        
	        try { 

                HttpEntity entity = response.getEntity(); 
	                
	            if (entity != null) {

	                String result = EntityUtils.toString(entity);
	                //System.out.println(result); 
	                
	            	ObjectMapper mapper = new ObjectMapper(); 
	            	Inventories[] inventories = mapper.readValue(result, Inventories[].class);
	            	ArrayList<Inventories> new_inventories = new ArrayList<Inventories>();
	            	for (Inventories i : inventories) {
	            		if(i.getItemId().equals(itemid)) {
	            			//System.out.println(i);
	            			new_inventories.add(i);
	            		}
	                }

	                //System.out.println(new_items);
	            	
	                // return it as a String
	    			return new ResponseEntity<>(new_inventories, headers, HttpStatus.OK);
	                
	            }
	        } finally {
                response.close();
            }
		} finally {
			httpClient.close();
        }
     
		// return new ResponseEntity<>("{}", headers, HttpStatus.OK);
		return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);		
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
