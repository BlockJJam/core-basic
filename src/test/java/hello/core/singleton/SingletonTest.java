package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();

        // 1. 조회: 호출할 때마다 객체를 생성 ( memberService 안에 memberRepository도 계속 생성됨)
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회: 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 만약 memberservice에 접근하는 user 5만명이 들어오면 어떻게 될까?
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 메모리 낭비가 되는 모습을 볼 수 있다
        // isSameAs 부류와 isEqualTo 부류의 차이
        // isSameAs = 참조를 비교하는 녀석
        // isEqualTo = 값을 비교하는 녀석
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void singletonContainer(){
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회: 호출할 때마다 객체를 생성 ( memberService 안에 memberRepository도 계속 생성됨)
        MemberService memberService1 = ac.getBean("memberService",MemberService.class);
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);

        // 만약 memberservice에 접근하는 user 5만명이 들어오면 어떻게 될까?
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);


        assertThat(memberService1).isSameAs(memberService2);
    }
}
