package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 스프링 부트로 테스트한다.
@Transactional // pk 즉 같은 id 값이면 엔티티가 딱 1개만 생긴다. 테스트 케이스에선 rollback 한다.
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    // rollback 은 하지만 영속성 컨텍스트에서 flush해서 db 에 반영되게 insert 로그 보고 싶다면
    // em.flush()
    // @Autowired
    // EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("park");
        //when
        Long savedId=memberService.join(member);
        //then
        //em.flush();
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when then
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "예외가 발생해야 한다.");
    }
    }
