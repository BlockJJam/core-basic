package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    // java 방식은 Factory method를 이용해서 우회해서 스프링 빈을 박는 방식
    //AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // ApplicationContext 인터페이스로 선언하면, getBeanDefinition를 사용할 수 없어서 구현체로 선언
    // GenericXmlApplicationContext는 xml을 통해 직접 스프링 빈을 박는 방식
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = " + beanDefinitionName + ", beanDefinition = "+beanDefinition);
            }
        }
    }
}
