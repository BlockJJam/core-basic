package hello.core.scan.filter;


import java.lang.annotation.*;

// MyIncludeComponent 애너테이션이 붙은 코드는 컴포넌트 스캔에 추가할 것
@Target(ElementType.TYPE) // class, method 어디에 붙일거야?
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
