package com.rock.jpetstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.rock.jpetstore.domain.Items; 

@RepositoryRestResource(exported = false) 
public interface ItemRepository extends JpaRepository<Items, String> { 
	Items findByItemId(String itemid);
}
