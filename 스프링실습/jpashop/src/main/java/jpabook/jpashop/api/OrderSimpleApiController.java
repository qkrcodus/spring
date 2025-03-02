package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders=orderRepository.findAll(new OrderSearch());
        List <SimpleOrderDto> result=orders.stream()
                .map(o->new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @Data
    public static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        // order 조회시 2개가 반환한다. dto 도 2개가 생긴다.
        // dto엔 order 와 연관된 member 와 delivery가 있다.
        // 즉, 모든 Order 를 조회하는 쿼리 1개에
        // member 와 delivery , member 와 delivery 까지 추가되어 5개의 쿼리가 나간다.
        public SimpleOrderDto(Order order) {
            orderId=order.getId();
            orderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            name=order.getMember().getName();
            address=order.getDelivery().getAddress();
        }
    }
}
