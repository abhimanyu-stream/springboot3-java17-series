package com.stream.api.response.service;

import com.stream.api.response.exception.ItemNotFoundException;
import com.stream.api.response.model.Item;
import com.stream.api.response.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> findById(Long id) {
      return   itemRepository.findById(id);
    }
}
