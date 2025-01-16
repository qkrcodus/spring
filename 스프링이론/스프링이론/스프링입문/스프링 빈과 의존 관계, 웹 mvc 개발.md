#### 의존 관계란?
- 어떠한 객체가 다른 객체를 사용하는 관계
#### 스프링이 하는 일?
- 에노테이션 @Controller @Repository @Service가 붙은 클래스를 스캔
- 스캔한 클래스 기반 객체를 생성하고, 객체(BEAN)을 컨테이너에 등록
- @Autowired로 의존 관계에 놓인 객체들을 자동으로 연결 (By DI)

#### 멤버 컨트롤러는 멤버 서비스 객체를 사용해 일을 처리한다
- member controller : 사용자 회원 가입 요청을 처리
- member service : 회원 가입하려면 중복 조회 후 db에 저장하는 등 세부적 로직 처리

그럼 @Autowired로 멤버 컨트롤러에 멤버 서비스 객체를 DI 해보자

`@Controller`
`public class MemberController {`

    `private final MemberService memberService;`

    `@Autowired`
    `public MemberController(MemberService memberService){`
        `this.memberService = memberService;`
    `}`
`}`

이후 repository 와 service 에도 @Repository @Service 를 붙여 자동으로 객체(BEAN)이 컨테이너에 등록되게 하자.

#### 에노테이션을 통해 컨테이너에 등록된 객체(빈)을 의존 관계가 있는 객체의 클래스 정의 시 필드로 정의하고 생성자에 주입하여 DI 해준다




