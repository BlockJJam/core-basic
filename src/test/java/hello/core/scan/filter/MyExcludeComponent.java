package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // class, method 어디에 붙일거야?
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
