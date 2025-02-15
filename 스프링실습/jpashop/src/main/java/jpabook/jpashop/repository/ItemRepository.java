package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            // 처음엔 item 은 jpa 에 저장하기 전에 id가 없다.
            // 새로운 객체 만들때
            em.persist(item);
        }else{
            // 이미 만들어진 아이템
            em.merge(item);
        }
    }

    // 1개 찾는건 find
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 여러개 찾는 건 jpql
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}



