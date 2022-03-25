package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class ,PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    // 싱글톤 - 프로토타입 문제의 코드
//    @Scope("singleton")
//    static class ClientBean{
//        private final PrototypeBean prototypeBean;
//
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//
//        public int logic(){
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }

    // Object Provider로 싱글톤 - 프로토타입 문제 해결법
//    @Scope("singleton")
//    static class ClientBean{
//
//        // Object Provider 사용, DL역할
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider; // ObjectProvider가 ObjectFactory보다 좀더 편의기능 제공
////        private ObjectFactory<PrototypeBean> prototypeBeanProvider;
//
//        public int logic(){
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }

    // javax의 Provider로 싱글톤 - 프로토타입 문제 해결법
    @Scope("singleton")
    static class ClientBean{

        // Object Provider 사용, DL역할
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider; // ObjectProvider가 ObjectFactory보다 좀더 편의기능 제공
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }


    @Scope("singleton")
    static class ClientBean2{
        private final PrototypeBean prototypeBean; // 위 ClientBean과는 다른 프로토타입 빈이 생성되어 주입됨

        @Autowired
        public ClientBean2(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
