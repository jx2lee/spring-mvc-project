package com.jx2lee.mvcproject.web.basic;

import com.jx2lee.mvcproject.domain.item.Item;
import com.jx2lee.mvcproject.domain.item.ItemParamDto;
import com.jx2lee.mvcproject.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    // @Autowired // 생성자가 1개인 경우 Autowired 생략이 가능하다.
    // @RequiredArgsConstructor 사용 시 생성자 생략 가능 (final 멤버 변수 존재시)
    // public BasicItemController(ItemRepository itemRepository) {
    //     this.itemRepository = itemRepository;
    // }

    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

        itemRepository.save(item);
        // model 은 자동 추가, 생략 가능
        // 단, item 이름이 변경되면 안된다. addForm.html 에 해당 모델 이름은 item!
        // model.addAttribute("item", item);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        // ModelAttribute 변수명 생략 가능
        // rule: 해당 클래스 첫 문자 -> 소문자로 변경 (ex. JaeJun -> jaeJun)
        itemRepository.save(item);
        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV4(Item item) {

        // ModelAttribute 생략 가능
        // 생략은 범위는 개발자 마음
        itemRepository.save(item);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV5(Item item) {

        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("message", "상품 등록이 완료되었습니다.");

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute Item item) {

        itemRepository.update(itemId, new ItemParamDto(item.getItemName(), item.getPrice(), item.getQuantity()));
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }

}
