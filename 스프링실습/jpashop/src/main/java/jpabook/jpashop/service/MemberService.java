package jpabook.jpashop.service;

import jakarta.persistence.Table;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa의 데이터 변경과 로직은 한 트랜잭션안에서 이루어져야 한다.
@RequiredArgsConstructor
public class MemberService {
    // final 어노테이션으로 생성자 주입 안 만들때 컴파일 에러 경고 메시지 뜨게 한다.
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // 따로 설정하면 디폴트인 readOnly=false
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        // 중복이면 예외
        List<Member> findMembers=memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    // 회원 하나 조회
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
