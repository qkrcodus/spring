package hello.spring.hello.Spring;

import hello.spring.hello.Spring.repository.MemberRepository;
import hello.spring.hello.Spring.repository.MemoryMemberRepository;
import hello.spring.hello.Spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
