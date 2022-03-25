package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	// MyLogger를 만들어도 처음엔 에러가 뜬다, 왜?
	// 스프링 컨테이너가 뜨는데, 의존관계 주입을 LogDemoController가 수행해도 "request-스코프"가 아닌 상황에서 싱글톤 주입처럼 썼으니 문제가 발생
	// 어떻게 해결할 것인가? <- Provider를 쓰면 됨!
	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
