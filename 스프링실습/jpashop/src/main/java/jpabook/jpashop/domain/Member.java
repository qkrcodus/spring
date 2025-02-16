package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter // lombok 라이브러리로 getter와 setter 메서드를 자동으로 생성
public class Member {

    @Id @GeneratedValue // 기본 키를 데이터베이스가 자동 생성
    @Column(name="member_id") // 테이블의 해당 컬럼 이름 설정
    private Long id;
    private String name;

    @Embedded // 복합 타입 값을 하나의 엔티티에 넣을 때
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders= new ArrayList<>();

}


