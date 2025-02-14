package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링은 컴포넌트 스캔에 의해 자동으로 스프링 빈으로 관리됨
public class MemberRepository {

    @PersistenceContext // Jpa의 em을 스프링이 주입해준다.
    private EntityManager em;

    // 생성
    public void save(Member member) {
        // persist 는
        em.persist(member);
    }

    // 아이디로 찾기
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 이름으로 찾기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name", name).getResultList();
    }

    // read
    public List<Member> findAll() {
        // jpql문, 반환 타입
        // jpql 은 결국 sql 로 바뀌지만
        // 다른 점은
        // sql 은 테이블 기준으로 jpql 은 엔티티 기준으로 찾는다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }


}
