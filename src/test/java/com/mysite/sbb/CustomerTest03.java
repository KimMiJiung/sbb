package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Customer;
import com.mysite.sbb.entity.QCustomer;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.java.Log;

// 3. QueryDSL을 사용하는 방법
// - JpaRepository 인터페이스를 사용하지 않음 -> Q도메인 클래스를 사용
// - Q도메인 클래스 - 해당 테이블의 데이터를 가져와서 사용하는 클래스

@Log
@SpringBootTest
public class CustomerTest03 {
	// Entity를 관리하는 객체
	@PersistenceContext
	EntityManager em;
	
	// [ 1. 데이터 조회 (SELECT) ]
	
	// 1. 전체 데이터 조회
	//@Test
	@DisplayName("1. 전체 데이터 조회")
	public void testSelect01() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		List<Customer>  coustomerList = factory.selectFrom(qCustomer).fetch();
		coustomerList.forEach(c -> log.info(c.toString()));
	}
	
	// 2. 아이디로 1건 조회
	@Test
	@DisplayName("2. 아이디로 1건 조회")
	public void testSelect02() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		Customer customer = factory.selectFrom(qCustomer).where(qCustomer.id.eq(2L)).fetchOne();
		log.info(customer.toString());
	}	

	// 3. 고객아이디로 1건 조회
	//@Test
	@DisplayName("3. 고객아이디로 1건 조회")
	public void testSelect03() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		Customer customer = factory.selectFrom(qCustomer).where(qCustomer.customerId.eq("bbbb")).fetchOne();
		log.info(customer.toString());
	}	
	
	
	// 4. 성별로 여러 건 조회
	//@Test
	@DisplayName("4. 성별로 여러 건 조회")
	public void testSelect04() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.gender.eq("남성")).fetch();
		customerList.forEach(c -> log.info(c.toString()));
	}	
	
	// 5. 고객아이디와 성별이 일치하는 데이터 1건 조회
	//@Test
	@DisplayName("5. 고객아이디와 성별이 일치하는 데이터 1건 조회")
	public void testSelect05() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		Customer customer = factory.selectFrom(qCustomer)
				.where(qCustomer.customerId.eq("cccc").and(qCustomer.gender.eq("남성"))).fetchOne();
		log.info(customer.toString());
	}		
	
	// 6. 남성이고 나이가 30대인 데이터 여러건 조회
	//@Test
	@DisplayName("6. 남성이고 나이가 30대인 데이터 여러건 조회")
	public void testSelect06() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		List<Customer> customerList = factory.selectFrom(qCustomer)
				.where(qCustomer.gender.eq("남성").and(qCustomer.age.between(30, 39))).fetch();
		customerList.forEach(c -> log.info(c.toString()));
	}		
		
	// 7. 부천에 사는 남성을 여러건 조회
	//@Test
	@DisplayName("7. 부천에 사는 남성을 여러건 조회")
	public void testSelect07() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
//		List<Customer> customerList = factory.selectFrom(qCustomer)
//				.where(qCustomer.address.startsWith("부천").and(qCustomer.gender.eq("남성"))).fetch();
		// 1번 - like() 메서드
//		List<Customer> customerList = factory.selectFrom(qCustomer)
//				.where(qCustomer.address.like("%부천%").and(qCustomer.gender.eq("남성"))).fetch();
		// 2번 - contains() 메서드
		List<Customer> customerList = factory.selectFrom(qCustomer)
				.where(qCustomer.address.contains("부천").and(qCustomer.gender.eq("남성"))).fetch();		
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 8. 가입년도가 2025이고 남성인  데이터 여러 건 조회
	//@Test
	@DisplayName("8. 가입년도가 2025이고 남성인  데이터 여러 건 조회")
	public void testSelect08() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		// 1번 - between() 메서드
//		List<Customer> customerList = factory.selectFrom(qCustomer)
//				.where(qCustomer.regDate.between(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 12, 31, 0, 0))
//						.and(qCustomer.gender.eq("남성"))).fetch();
		
		// 2번 -year() 메서드
		List<Customer> customerList = factory.selectFrom(qCustomer)
				.where(qCustomer.regDate.year().eq(2025)
						.and(qCustomer.gender.eq("남성"))).fetch();		
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 9. 나이가 28, 29, 32, 33세인 데이터를 나이의 내림차순으로 정렬하여 조회
	//@Test
	@DisplayName("9. 나이가 28, 29, 32, 33세인 데이터를 나이의 내림차순으로 정렬하여 조회")
	public void testSelect09() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;

		// 1번 방법 - or() 메서드 사용
//		List<Customer> customerList = factory.selectFrom(qCustomer)
//				.where(qCustomer.age.eq(28).or(qCustomer.age.eq(29)).or(qCustomer.age.eq(32)).or(qCustomer.age.eq(33)))
//				.orderBy(qCustomer.age.desc())
//				.fetch();
		
		// 2번 방법 - in() 메서드 사용
		List<Customer> customerList = factory.selectFrom(qCustomer)
				.where(qCustomer.age.in(28, 29, 32, 33))
				.orderBy(qCustomer.age.desc()).fetch();
		
		customerList.forEach(c -> log.info(c.toString()));
	}	
	
	// 10. 성이 김씨 또는 이씨이고, 남성인 데이터를 최근 가입일 순으로 여러 건 조회
	//@Test
	@DisplayName("10. 성이 김씨 또는 이씨이고, 남성인 데이터를 최근 가입일 순으로 여러 건 조회")
	public void testSelect10() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
		List<Customer> customerList = factory.selectFrom(qCustomer)
				.where(qCustomer.name.startsWith("김").or(qCustomer.name.startsWith("이")).and(qCustomer.gender.eq("남성")))
				.orderBy(qCustomer.regDate.desc())
				.fetch();		
//		List<Customer> customerList = factory.selectFrom(qCustomer)
//				.where(qCustomer.name.like("김%").or(qCustomer.name.like("이%")).and(qCustomer.gender.eq("남성")))
//				.orderBy(qCustomer.regDate.desc()).fetch();
		customerList.forEach(c -> log.info(c.toString()));		
	}
	
	// 11. 이메일 또는 전화번호가 널인 데이터를 이름의 오름차순으로 여러 건 조회
	//@Test	
	@DisplayName("11. 이메일 또는 전화번호가 널인 데이터를 고객아이디의 오름차순으로 여러 건 조회")
	public void testSelect11() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		List<Customer> customerList = factory.selectFrom(qCustomer)
				.where(qCustomer.email.isNull().or(qCustomer.phone.isNull()))
				.orderBy(qCustomer.name.asc())
				.fetch();
		customerList.forEach(c -> log.info(c.toString()));		
	}
	
	// ################################################################
	
	// [ 2. 데이터 수정 (UPDATE) ]
	// update, delete는 @Transactional, @Commit 애너테이션을 사용해야 함
	
	// 1. 아이디가 2인 고객의 이름과 전화번호를 수정
	//@Test
	@DisplayName("1. 아이디가 2인 고객의 이름과 전화번호를 수정")
	@Transactional
	@Commit
	public void testUpdate01() {
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;
		
		long count = factory.update(qCustomer)
		.set(qCustomer.name, "기은미").set(qCustomer.phone, "010-1004-1004")
		.where(qCustomer.id.eq(2L)).execute();
		log.info("수정 건수: " + count);
		Customer customer = factory.selectFrom(qCustomer).where(qCustomer.id.eq(2L)).fetchOne();
		log.info(customer.toString());
	}
	
	// 2. 나이가 40대이고, 남성인 고객의 가입일을 '2022-10-10', 이메일을 'fourty@kakao.com'으로 수정
	//@Test
	@DisplayName("2. 나이가 40대이고, 남성인 고객의 가입일을 '2022-10-10', 이메일을 'fourty@kakao.com'으로 수정")
	@Transactional
	@Commit
	public void testUpdate02() {
		
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
		
		long count = factory.update(qCustomer)
		.set(qCustomer.gender, "남성").set(qCustomer.regDate, LocalDateTime.of(2022, 10, 10, 0, 0))
		.where(qCustomer.age.between(40, 49)).execute();
		log.info("수정 건수: " + count);
			
	}
	
		
	// 3. 주소가 서울인 고객의 전화번호를 '010-0000-0000'으로 수정
	//@Test
	@DisplayName("3. 주소가 서울인 고객의 전화번호를 '010-0000-0000'으로 수정")
	@Transactional
	@Commit
	public void testUpdate03() {
		
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
		
		long count = factory.update(qCustomer)
		.set(qCustomer.phone, "010-0000-0000")
		.where(qCustomer.address.contains("서울")).execute();
		log.info("수정 건수: " + count);
			
	}
	
	// ##################################################################
	
	// [ 3. 데이터 삭제 (DELETE)
		
	//@Test
	@DisplayName("1. 아이디로 데이터 1건 삭제")
	@Transactional
	@Commit
	public void testDelete01() {
		
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
	
		
		long count = factory.delete(qCustomer)
		.where(qCustomer.id.eq(1L)).execute();
		
		log.info("삭제 건수: " + count);
		
		// 확인
		Customer customer = factory.selectFrom(qCustomer).where(qCustomer.id.eq(1L)).fetchOne();
		log.info(customer.toString());
			
	}	
	
	// 2. 주소가 수원이고 남성이 데이터를 삭제
	//@Test
	@DisplayName("2. 주소가 수원이고 남성이 데이터를 삭제")
	@Transactional
	@Commit
	public void testDelete02() {
		
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
	
		
		long count = factory.delete(qCustomer)
		.where(qCustomer.address.contains("인천").and(qCustomer.gender.eq("남성"))).execute();
		
		log.info("삭제 건수: " + count);
		
		// 확인
		List<Customer> customer = factory.selectFrom(qCustomer).fetch();
		log.info(customer.toString());
			
	}		
	// 3. 전화번호가 널이고, 나이가 40대인 데이터를 삭제
	//@Test
	@DisplayName("3. 전화번호가 널이고, 나이가 40대인 데이터를 삭제")
	@Transactional
	@Commit
	public void testDelete03() {
		
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
	
		
		long count = factory.delete(qCustomer)
		.where(qCustomer.phone.isNull().and(qCustomer.age.between(40, 49))).execute();
		
		log.info("삭제 건수: " + count);
		
		// 확인
		List<Customer> customer = factory.selectFrom(qCustomer).fetch();
		log.info(customer.toString());
			
	}		
	// 4. 모든 데이터를 삭제
	//@Test
	@DisplayName("4. 모든 데이터를 삭제")
	@Transactional
	@Commit
	public void testDelete04() {
		
		// 쿼리를 동적으로 생성하는 객체
		JPAQueryFactory factory = new JPAQueryFactory(em);
		// Entity를 복제해서 가지고 있는 객체
		QCustomer qCustomer = QCustomer.customer;	
	
		
		long count = factory.delete(qCustomer).execute();
		
		log.info("삭제 건수: " + count);
		
		// 확인
		List<Customer> customer = factory.selectFrom(qCustomer).fetch();
		log.info(customer.toString());
			
	}		
	
	
	
	
	
	
}
