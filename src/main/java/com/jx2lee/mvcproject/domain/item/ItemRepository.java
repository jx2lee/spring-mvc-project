package com.jx2lee.mvcproject.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    // private static final Map<Long, Item> store = new HashMap<>(); // static
    private static final Map<Long, Item> store = new ConcurrentHashMap<>(); // static
    private static long sequence = 0; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, ItemParamDto itemParamDto) {
        Item findItem = findById(itemId);
        findItem.setItemName(itemParamDto.getUpdatedName());
        findItem.setPrice(itemParamDto.getUpdatedPrice());
        findItem.setQuantity(itemParamDto.getUpdatedQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
