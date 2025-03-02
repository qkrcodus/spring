package hello.spring.hello.Spring.repository;

import hello.spring.hello.Spring.domain.Member;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}

