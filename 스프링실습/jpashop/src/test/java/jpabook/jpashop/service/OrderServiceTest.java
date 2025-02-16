package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest // 스프링 부트로 테스트한다.
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    // 상품 주문
    @Test
    public void 상품주문( ) throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("jpajap", 10000, 10);
        int orderCount=2;
        //when
        Long orderId=orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder=orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 수는 1개");
        assertEquals(10000*orderCount,getOrder.getTotalPrice(),"주문 가격은 가격 * 수다");
        assertEquals(8,book.getStockQuantity(),"주문한 수량만큼 재고가 줄어야한다");
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","23423"));
        em.persist(member);
        return member;
    }

    // 주문 취소
    @Test
    public void 주문취소( )throws Exception{
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //When
        orderService.cancleOrder(orderId);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCLE, getOrder.getStatus(),
                "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    // 주문 재고 확인
    @Test
    public void 상품주문_재고수령초과( ) throws Exception{
        //given
        Member member = createMember();
        Item item=createBook("jpajpa",10000,10);
        int orderCount=11;
        // when & then
        try {
            orderService.order(member.getId(), item.getId(), orderCount);
            fail("재고부족 에러가 떠야 합니다");
        } catch (NotEnoughStockException e) {
            assertThat(e.getMessage()).isEqualTo("need more stock");
        }
        //then
        fail("재고부족 에러가 떠야 합니다");
    }
}
