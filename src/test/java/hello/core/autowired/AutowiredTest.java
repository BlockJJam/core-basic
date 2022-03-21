package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{

        // 의존관계가 없으면, 해당 메서드 자체가 호출이 안됨, bean을 가져오는 부분에서 생성 안된 것을 확인 가능
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            // 스프링 컨테이너가 관리 안하는 Member 객체이용
            System.out.println("noBean1 = " + noBean1);
        }

        // 호출은 되지만, 의존관계 주입 객체에 null이 들어감
        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            // 스프링 컨테이너가 관리 안하는 Member 객체이용
            System.out.println("noBean2 = " + noBean2);
        }

        // 호출은 되지만, 의존관계 주입 객체에 
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
