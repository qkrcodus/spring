package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 새로운 엔티티이면, 레포지토리에서 persist 호출되고, ( 엔티티 비영속 상태 )
    // 기존 존재했다면, 레포지토리에서 merge 가 호출된다.  ( 엔티티 영속 상태 )
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 준영속 엔티티를 다시 조회하여 jpa 가 관리하게 하자
    @Transactional
    public void updateItem(Long itemId, String name,int price,int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId); // ( 엔티티 영속 상태 )
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        // itemRepository.save(findItem) 할 필요가 없다.
        // jpa는 트랜잭션 뒤에 변경 감지
    }
}


