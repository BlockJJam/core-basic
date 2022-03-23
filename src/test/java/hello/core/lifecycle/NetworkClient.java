package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient { // 옛날 방식 implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자를 호출, url = "+ url);
    }

    public void setUrl(String url){
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect(){
        System.out.println("connect: " + url);
    }

    public void call(String message){
        System.out.println("call: "+ url + " message = "+ message);
    }

    // 서비스 종료 시 호출
    public void disconnect(){
        System.out.println("close: " + url);
    }

    // 빈 콜백 메서드 초기화 애너테이션
    @PostConstruct
    public void init() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }

    // 빈 콜백 메서드 소멸 애너테이션
    @PreDestroy
    public void close() throws Exception {
        disconnect();
    }
}
