package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A 사용자 10000원 주문
        int userAPrice = statefulService1.order("UserA", 10000);
        // ThreadB: B 사용자 20000원 주문
        int userBPrice = statefulService1.order("UserB", 20000);

        // ThreadA: 사용자 A 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("user A price = " + userAPrice); // 엥 statefulService1에 10000원 주문한건데 왜 20000원? 당연히 statefulService1과 statefulService2는 하나의 주소에 있는 인스턴스니까

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}
