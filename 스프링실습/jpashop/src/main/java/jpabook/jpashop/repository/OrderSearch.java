package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    // 검색 조건 파라미터
    // 이름으로 검색하거나, 주문상태로 검색하거나
    private String memberName;
    private OrderStatus orderStatus;
}

