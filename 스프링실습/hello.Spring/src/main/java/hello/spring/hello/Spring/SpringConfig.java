package hello.spring.hello.Spring;

import hello.spring.hello.Spring.repository.*;
import hello.spring.hello.Spring.repository.MemberRepository;
import hello.spring.hello.Spring.repository.MemoryMemberRepository;
import hello.spring.hello.Spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

}
