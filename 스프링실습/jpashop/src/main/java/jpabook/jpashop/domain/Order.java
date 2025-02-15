package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id") // 외래키 등록
    private Member member;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();

    // cascade = CascadeType.ALL을 주지 않을 경우엔
    // 리스트 객체를 만들고 컬렉션에 담아서
    // persist(orderItemA)
    // persist(orderItemB)
    // persist(orderItemC)
    // persist(order) 해줘야 하지만

    // cascade = CascadeType.ALL로
    // persist(order) 만 해도 된다.

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id") // 외래키 등록
    private Delivery delivery;

    // 마찬가지로 원래 모든 엔티티는 기본적으로 저장하고 싶으면 persist 해야 하는데
    // persist(order)
    // persist(delivery)를 // persist(order)만으로 영속성 주입 가능하다.

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 멤버가 주문하면 오더에 값을 넣어야하고, 오더에서도 멤버가 필요하다. 디비에 저장하는 건 연관관계 주인인 멤버만 있으면 되긴 함
    // 연관관계 메서드를 굳이 넣어준다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // 원래라면
    //public static void main(String[] args) {
        //Member member = new Member();
        //Order order = new Order();
        //member.getOrders().add(order);
        //order.setMember(member);
        // 근데 오더랑 멤버를 엮는 작업을 까먹을 수 있기 때문에
    //}

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(Member member,Delivery delivery,OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // == 비즈니스 로직 == //
    // 주문 취소
    public void cancle(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능합니다");
        }
        this.setStatus(OrderStatus.CANCLE);
        for (OrderItem orderItem : orderItems){
            orderItem.cancle();
        }
    }

    // == 조회 로직 == //
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem:orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}


