package com.rock.jpetstore.service;

import com.rock.jpetstore.domain.Items;
import com.rock.jpetstore.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	// item
	public List<Items> getAll() {
		return itemRepository.findAll();
	}

	public Items getById(String itemid) {
		return itemRepository.findByItemId(itemid);
	}

	public void save(Items item) {
		itemRepository.save(item);
	}

	public void delete(String itemid) {
		itemRepository.deleteById(itemid);
	}
}