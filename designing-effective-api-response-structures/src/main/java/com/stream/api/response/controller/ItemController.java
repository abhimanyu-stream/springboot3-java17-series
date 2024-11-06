package com.stream.api.response.controller;

import com.stream.api.response.exception.ItemNotFoundException;
import com.stream.api.response.model.Item;
import com.stream.api.response.model.MessageLog;
import com.stream.api.response.service.ItemService;
import com.stream.api.response.transfer.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {


    private ItemService itemService;
    /*@Autowired
    private MessageLogRepository messageLogRepository;*/


    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> getItemById(@PathVariable Long id, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        Optional<Item> itemOptional = itemService.findById(id);

        if (itemOptional != null && itemOptional.isPresent()) {
            Item item = itemOptional.get();

            // Log the message to MySQL database
            MessageLog log = new MessageLog();
            log.setMessage("Item fetched successfully");
            log.setLanguage(locale.getLanguage());
            log.setRecipient("username");
            log.setSuccess(Boolean.TRUE);
            //messageLogRepository.save(log);


            ApiResponse<Item> apiResponse = new ApiResponse<>(item, log);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {

            // Log the message to MySQL database
            MessageLog log = new MessageLog();
            log.setMessage("Item not found");
            log.setLanguage(locale.getLanguage());
            log.setRecipient("username");
            log.setSuccess(Boolean.FALSE);
            //messageLogRepository.save(log);


            ApiResponse<Item> apiResponse = new ApiResponse<>(null, log);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

}