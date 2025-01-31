package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {

        // memberService 만들고
        MemberService memberService = new MemberServiceImpl();

        // join할 객체 생성
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // 객체가 잘 join 되었나 확인
        Member findMember=memberService.findMember(1L);
        System.out.println("new member: " + member.getName());
        System.out.println("find member: "+ findMember.getName());
    }
}
