package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 밑의 방식은 할인정책이 바뀌면 -> OrderSErviceImpl이 변경된
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    private DiscountPolicy discountPolicy; // 이렇게만 선언해도 이용가능하도록 할 순 없을까?

    // 생성자 주입 방식에 쓰이는 필드
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // @Autowired 로 여러 빈 중에 필드명과 같은 빈을 가져오려고 하기 위해서
//    private  DiscountPolicy fixDiscountPolicy;
 /*
    // 필드 주입 방식
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

    // Setter 주입 방식 (field에 final 키워드 제거 필요)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }*/

    // 생성자 주입방식
    public OrderServiceImpl(MemberRepository memberRepository,/*@Qualifier("fixDiscountPolicy") @MainDiscountPolicy*/ DiscountPolicy discountPolicy) { //DiscountPolicy fixDiscountPolicy
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy; // fixDiscountPolicy
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 싱글톤이 깨지는 지 여부 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
