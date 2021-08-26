package com.jx2lee.mvcproject.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("test", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findById() {
        //given
        Item item = new Item("test", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem.getId()).isEqualTo(savedItem.getId());
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("test1", 10000, 10);
        Item item2 = new Item("test2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> findItemList = itemRepository.findAll();

        //then
        assertThat(findItemList.size()).isEqualTo(2);
        assertThat(findItemList).contains(item1, item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("test1", 10000, 10);

        itemRepository.save(item);
        Long itemId = item.getId();

        //when
        ItemParamDto itemParamDto = new ItemParamDto("test2", 10001, 11);
        itemRepository.update(itemId, itemParamDto);

        //then
        Item updatedItem = itemRepository.findById(itemId);
        assertThat(updatedItem.getItemName()).isEqualTo(itemParamDto.getUpdatedName());
        assertThat(updatedItem.getPrice()).isEqualTo(itemParamDto.getUpdatedPrice());
        assertThat(updatedItem.getQuantity()).isEqualTo(itemParamDto.getUpdatedQuantity());
    }

    @Test
    void clear() {
        //given
        Item item = new Item("test1", 10000, 10);
        itemRepository.save(item);

        //when
        itemRepository.clearStore();

        //then
        assertThat(itemRepository).isNotIn(item);
    }
}
