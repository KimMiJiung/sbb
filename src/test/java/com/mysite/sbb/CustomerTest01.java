package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Customer;
import com.mysite.sbb.repository.CustomerRepository01;

import lombok.extern.java.Log;

// [ 1. JpaRepository 인터페이스의 쿼리 메서드를 사용하는 방법 ]
@Log
@SpringBootTest
public class CustomerTest01 {
	
	// CustomerRepository01의 빈(Bean)을 등록
	@Autowired
	private CustomerRepository01 customerRepository;
	
	// id, customerId, name, gender, age, phone, email, address, regDate
	
	// [ 데이터를 추가하는 방법 ]
	// 1. 데이터 추가 1번 - 기본 생성자와 섹터를 활용하는 방법
	//@Test
	@DisplayName("1. 데이터 추가 1번")
	@Transactional
	@Commit
	public void testInsert01() {
		Customer c1 = new Customer();
		c1.setCustomerId("aaaa");
		c1.setName("오이영");
		c1.setGender("여성");
		c1.setAge(27);
		c1.setPhone("010-1111-1111");
		c1.setEmail("aaaa@naver.com");
		c1.setAddress("서울시 강남구 역삼동");		
		this.customerRepository.save(c1);
		
		Customer c2 = new Customer();
		c2.setCustomerId("bbbb");
		c2.setName("표남경");
		c2.setGender("여성");
		c2.setAge(28);
		c2.setPhone("010-2222-2222");
		c2.setEmail("bbbb@naver.com");
		c2.setAddress("서울시 강남구 압구정동");		
		this.customerRepository.save(c2);
		
		Customer c3 = new Customer();
		c3.setCustomerId("cccc");
		c3.setName("엄재일");
		c3.setGender("남성");
		c3.setAge(29);
		c3.setPhone("010-3333-3333");
		c3.setEmail("cccc@naver.com");
		c3.setAddress("인천시 북구 월미동");		
		this.customerRepository.save(c3);
		
		Customer c4 = new Customer();
		c4.setCustomerId("dddd");
		c4.setName("김사비");
		c4.setGender("여성");
		c4.setAge(32);
		c4.setPhone("010-4444-4444");
		c4.setEmail("dddd@naver.com");
		c4.setAddress("부천시 북구 가구동");		
		this.customerRepository.save(c4);
		
		Customer c5 = new Customer();
		c5.setCustomerId("eeee");
		c5.setName("구도원");
		c5.setGender("남성");
		c5.setAge(33);
		c5.setPhone("010-5555-5555");
		c5.setEmail("eeee@naver.com");
		c5.setAddress("인천시 수성구 성공동");		
		this.customerRepository.save(c5);	
	}
	
	// 2. 데이터 추가 2번 
	// -  모든 멤버변수를 매개변수로 전달하는  생성자를 활용하는 방법	(id와 regDate제외)
	//@Test
	@DisplayName("2. 데이터 추가 2번")
	@Transactional
	@Commit
	public void testInsert02() {
		Customer c1 = new Customer("aaaa", "오이영", "여성", 27, "010-1111-1111", "aaaa@naver.com", "서울시 강남구 역삼동");
		Customer c2 = new Customer("bbbb", "표남경", "여성", 28, "010-2222-2222", "bbbb@naver.com", "서울시 강남구 압구정동");
		Customer c3 = new Customer("cccc", "엄재일", "남성", 29, "010-3333-3333", "cccc@naver.com", "인천시 북구 월미동");
		Customer c4 = new Customer("dddd", "김사비", "여성", 30, "010-4444-4444", "dddd@naver.com", "부천시 동구 가구동");
		Customer c5 = new Customer("eeee", "구도원", "남성", 31, "010-5555-5555", "eeee@naver.com", "인천시 수성구 성공동");
		
		// 각각의 bean을 추가
		this.customerRepository.save(c1);	
		this.customerRepository.save(c2);	
		this.customerRepository.save(c3);	
		this.customerRepository.save(c4);	
		this.customerRepository.save(c5);	
	}
	
	// 3. 데이터 추가 3번 
	// -  Builder 디자인 패턴으로 데이터 추가하는 방법
	//@Test
	@DisplayName("3. 데이터 추가 3번")
	@Transactional
	@Commit	
	public void testInsert03() {
		Customer c1 = Customer.builder().customerId("aaaa").name("오이영").gender("여성").age(27)
					.phone("010-1111-1111").email("aaaa@naver.com").address("서울시 강남구 역삼동").build();
		Customer c2 = Customer.builder().customerId("bbbb").name("표남경").gender("여성").age(28)
					.phone("010-2222-2222").email("bbbb@naver.com").address("서울시 강남구 압구정동").build();
		Customer c3 = Customer.builder().customerId("cccc").name("엄재일").gender("남성").age(29)
					.phone("010-3333-3333").email("cccc@naver.com").address("인천시 북구 월미동").build();
		Customer c4 = Customer.builder().customerId("dddd").name("김사비").gender("여성").age(30)
					.phone("010-4444-4444").email("dddd@naver.com").address("부천시 동구 가구동").build();
		Customer c5 = Customer.builder().customerId("eeee").name("구도원").gender("남성").age(31)
					.phone("010-5555-5555").email("eeee@naver.com").address("인천시 수성구 성공동").build();	
		// 한꺼번에 추가
		List<Customer> customerList = List.of(c1, c2, c3, c4, c5);
		this.customerRepository.saveAll(customerList);	
	}
	
	// [ 데이터를 조회하는 방법 ]
	// 1. 전체데이터를 조회하는 방법 - @Id 변수를 기준으로 조회
	//@Test
	@DisplayName("1. 데이터 1건 조회")
	public void testSelect01() {	
		List<Customer> customerList = this.customerRepository.findAll();
		
		// 출력 1번 - 기본 for문
		/*
		for (int i=0; i < customerList.size(); i++) {
			//System.out.println(customerList.get(i));
			log.info(customerList.get(i).toString());
		}
		*/
		
		// 출력 2번 - for-in
		/*
		for(Customer c : customerList) {
			log.info(c.toString());
		}
		*/
		
		// 출력 3번 - forEach(), 람다식
		customerList.forEach(c -> log.info(c.toString()));		
	}
	// 2. 데이터 1건을 조회하는 방법 - @Id 변수를 기준으로 조회
	//@Test
	@DisplayName("2. 데이터 1건 조회")
	public void testSelect02() {
		Optional<Customer> customer = this.customerRepository.findById(3L);
		log.info(customer.get().toString());
	}
	
	// 이름으로 조회하는 메서드를 생성하여 사용
	// 3. 데이터 1건을 조회하는 방법 - name 변수를 기준으로 조회
	//@Test
	@DisplayName("3. 데이터 1건 조회")
	public void testSelect03() {
		Optional<Customer>  customer = this.customerRepository.findByName("김사비");
		log.info(customer.get().toString());
	}	
	
	// 고객아이디로 조회하는 메서드를 생성하여 사용
	// 4. 데이터 1건을 조회하는 방법 - customerId 변수를 기준으로 조회
	//@Test
	@DisplayName("4. 데이터 1건 조회")
	public void testSelect04() {
		Optional<Customer> customer = this.customerRepository.findByCustomerId("cccc");
		log.info(customer.get().toString());
	}
	
	// 성별로 조회하는 메서드를 생성하여 사용
	// 5. 데이터를 여러 건을 조회하는 방법 - gender 변수를 기준으로 조회
	//@Test
	@DisplayName("5. 데이터 여러 건을 조회")
	public void testSelect05() {
		List<Customer> customerList = this.customerRepository.findByGender("여성");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 이름과 나이가 모두 일치하는 데이터를 조회하는 메서드를 생성하여 사용
	// 6. 데이터 1건을 조회하는 방법 -> name, age 변수를 기준으로 조회
	//@Test
	@DisplayName("6. 데이터 여러 건을 조회(name, age)")
	public void testSelect06() {
		Optional<Customer> customer = this.customerRepository.findByNameAndAge("구도원", 31);
		
		if (customer.isPresent()) {
			log.info(customer.get().toString());
		} else {
			log.info("해당하는 데이터가 존재하지 않습니다.");
		}
	}

	// 성별 또는 나이가 일치하는 데이터를 조회하는 메서드를 생성하여 사용
	// 7. 데이터 여러건을 조회하는 방법 -> gender, age 변수를 기준으로 조회
	//@Test
	@DisplayName("7. 데이터 여러 건을 조회(gender, age)")
	public void testSelect07() {	
		List<Customer> customerList = this.customerRepository.findByGenderOrAge("", 28);
		if (customerList.size() == 0) {
			log.info("해당하는 데이터가 존재하지 않습니다.");
		} else {
			customerList.forEach(c -> log.info(c.toString()));
		}
	}
	
	// 나이가 30세 이상인 데이터를 조회하는 메서드를 생성하여 사용
	// 8. 데이터 여러건을 조회하는 방법 -> age 변수로 조회
	//@Test
	@DisplayName("8. 데이터 여러 건을 조회(age)")
	public void testSelect08() {	
		List<Customer> customerList = this.customerRepository.findByAgeGreaterThanEqual(30);
		// if (customerList.size() == 0) {
		if (customerList.isEmpty()) {
			log.info("조회하는 데이터가 없습니다.");
		} else {
			customerList.forEach(c -> log.info(c.toString()));
		}
	}
	
	// 성별이 일치하고, 나이가 30세 이상인 데이터를 조회하는 메서드를 생성하여 사용
	// 9. 데이터 여러건을 조회하는 방법 -> gender, age 변수로 조회
	//@Test
	@DisplayName("9. 데이터 여러 건을 조회(gender, age)")
	public void testSelect09() {		
		List<Customer> customerList = this.customerRepository.findByGenderAndAgeGreaterThanEqual("남성", 30);
		customerList.forEach(c -> log.info(c.toString()));
	}

	// 성별이 일치하지 않고, 나이가 30세 미만인 데이터를 조회하는 메서드를 생성하여 사용
	// 10. 데이터 여러건을 조회하는 방법 -> gender, age 변수로 조회	
	//@Test
	@DisplayName("10. 데이터 여러 건을 조회(gender, age)")
	public void testSelect10() {
		List<Customer> customerList = this.customerRepository.findByGenderNotAndAgeLessThan("여성", 30);
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 나이가 20대인 데이터를 조회하는 메서드를 생성하여 사용
	// 11. 데이터 여러건을 조회하는 방법 -> age 변수로 조회	
	@Test
	@DisplayName("12. 데이터 여러 건을 조회(age)")
	public void testSelect11() {
		//List<Customer> customerList = this.customerRepository.findByAgeGreaterThanEqualAndAgeLessThan(20, 30);
		List<Customer> customerList = this.customerRepository.findByAgeBetween(20, 29);
		
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 나이가 27, 29, 33세인 데이터를 조회하는 메서드를 생성하여 사용
	// 12. 데이터 여러건을 조회하는 방법 -> age 변수로 조회	
	//@Test
	@DisplayName("12. 데이터 여러 건을 조회(gender, age)")
	public void testSelect12() { 
		//List<Customer> costomerList = this.customerRepository.findByAgeOrAgeOrAge(27, 29, 33);
		List<Integer> ages = List.of(27, 29, 33);
		List<Customer> customerList = this.customerRepository.findByAgeIn(ages);
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 이름에 '재'를 포함하는 데이터를 조회하는 메서드를 생성하여 사용
	// 13. 데이터 여러건을 조회하는 방법 -> name 변수로 조회	
	//@Test
	@DisplayName("13. 데이터 여러 건을 조회(name)")
	public void testSelect13() { 
		//List<Customer> customerList = this.customerRepository.findByNameLike("%재%");
		List<Customer> customerList = this.customerRepository.findByNameContaining("재");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 이름에 '표'로 시작하는 데이터를 조회하는 메서드를 생성하여 사용
	// 14. 데이터 여러건을 조회하는 방법 -> name 변수로 조회	
	//@Test
	@DisplayName("14. 데이터 여러 건을 조회(name)")
	public void testSelect14() { 
		//List<Customer> customerList = this.customerRepository.findByNameLike("표%");
		List<Customer> customerList = this.customerRepository.findByNameStartingWith("표");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 이름에 '비'로 끝나는 데이터를 조회하는 메서드를 생성하여 사용
	// 15. 데이터 여러건을 조회하는 방법 -> name 변수로 조회	
	@Test
	@DisplayName("15. 데이터 여러 건을 조회(name)")
	public void testSelect15() { 
		//List<Customer> customerList = this.customerRepository.findByNameLike("%비");
		List<Customer> customerList = this.customerRepository.findByNameEndingWith("비");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 이름에 '재'를 포함하지 않는 데이터를 조회하는 메서드를 생성하여 사용
	// 16. 데이터 여러건을 조회하는 방법 -> name 변수로 조회	
	//@Test
	@DisplayName("16. 데이터 여러 건을 조회(name)")
	public void testSelect16() { 
		//List<Customer> customerList = this.customerRepository.findByNameNotLike("%재%");
		List<Customer> customerList = this.customerRepository.findByNameNotContaining("재");
		customerList.forEach(c -> log.info(c.toString()));		
	}
	
	// 이메일이 널인 데이터를 조회
	// 17. 데이터 여러건을 조회하는 방법 -> name 변수로 조회	
	//@Test
	@DisplayName("17. 데이터 여러 건을 조회(name)")
	public void testSelect17() {
		List<Customer> customerList = this.customerRepository.findByEmailIsNull();
		customerList.forEach(c -> log.info(c.toString()));		
	}
	
	// 이메일이 널인 데이터를 조회
	// 18. 데이터 여러건을 조회하는 방법 -> name 변수로 조회	
	//@Test
	@DisplayName("18. 데이터 여러 건을 조회(name)")
	public void testSelect18() {
		List<Customer> customerList = this.customerRepository.findByEmailIsNotNull();
		customerList.forEach(c -> log.info(c.toString()));			
	}
	
	// 성별에 대해 조회하여 나이에 대한 내림차순으로 출력
	// 19. 데이터 여러건을 조회하는 방법 -> gender, name 변수로 조회	
	//@Test
	@DisplayName("19. 데이터 여러 건을 조회(gender, name)")
	public void testSelect19() {
		List<Customer> customerList = this.customerRepository.findByGenderOrderByAgeDesc("남성");
		customerList.forEach(c -> log.info(c.toString()));					
	}
	
	// 나이에 대해서 아이디를 기준으로 내림차순하여 상위 3건의 데이터를 조회
	// 20. 데이터 여러 건을 조회하는 방법 -> gender, age
	//@Test
	@DisplayName("20. 데이터 여러 건을 조회(gender, age)")
	public void testSelect20() {	
		List<Customer> customerList = this.customerRepository.findFirst3ByAgeOrderByIdDesc(27);
		//List<Customer> customerList = this.customerRepository.findTop3ByAgeOrderByIdDesc(27);
		customerList.forEach(c -> log.info(c.toString()));							
	}
		
	// ########################################################################################
	// [데이터 삭제 ]
	// 객체로 삭제 -> 1건 삭제
	// 1. 데이터 1건을 삭제하는 방법 -> id 객체로 삭제
	//@Test
	@DisplayName("1. 데이터 여러 건을 조회(id)")
	@Transactional
	@Commit
	public void testDelete01() {
		this.customerRepository.deleteById(1L);
		
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 아이디를 기준으로 삭제 -> 1건 삭제
	// 2. 데이터 1건을 삭제하는 방법 -> id로 삭제
	//@Test
	@DisplayName("2. 데이터 여러 건을 조회(Customer)")
	@Transactional
	@Commit
	public void testDelete02() {
		Customer customer = Customer.builder().id(2L).build();	
		this.customerRepository.delete(customer);
		
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 모든 객체로 삭제
	// 3. 전체 데이터 삭제
	//@Test
	@DisplayName("3. 전체 데이터 삭제")
	@Transactional
	@Commit
	public void testDelete03() {
		this.customerRepository.deleteAll();
		List<Customer> customerList = this.customerRepository.findAll();
		if (customerList.isEmpty()) {
			log.info("전체 데이터를 삭제하였습니다.");
		}
	}
	
	// 4번 - 나이가 30세 이상인 데이터를 삭제
	//@Test
	@DisplayName("4. 나이가 30세 이상인 데이터를 삭제")
	@Transactional
	@Commit
	public void testDelete04() {
		this.customerRepository.deleteByAgeGreaterThanEqual(30);
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 5번 - 주소가 부천인 데이터를 삭제
	//@Test
	@DisplayName("5. 데이터 여러건 삭제")
	@Transactional
	@Commit
	public void testDelete05() {
//		this.customerRepository.deleteByAddressLike("%부천%");
		this.customerRepository.deleteByAddressContaining("부천");
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}	
	
	// 6번 - 주소가 인천이고, 이름이 엄재일인 데이터를 삭제
	//@Test
	@DisplayName("6. 데이터 여러건 삭제")
	@Transactional
	@Commit
	public void testDelete06() {
		//this.customerRepository.deleteByAddressLikeAndName("%인천%", "엄재일");
		this.customerRepository.deleteByAddressContainingAndNameEquals("인천", "엄재일");
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}	
	
	
	// 7번 - 2012년에 가입한 고객의 데이터를 삭제
	//@Test
	@DisplayName("7. 데이터 여러건 삭제")
	@Transactional
	@Commit
	public void testDelete07() {
		// 1번 방법
		/*
		LocalDateTime date1 = LocalDateTime.of(2012, 1, 1, 0, 0);
		LocalDateTime date2 = LocalDateTime.of(2012, 12, 31, 0, 0);		 
		this.customerRepository.deleteByRegDateBetween(date1, date2);
		*/
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}		
	
	// ###################################################################
	// [ 데이터 수정 ]
	// < 데이터를 수정하는 단계 >
	// 1단계: 객체의 정보를 획득
	// 2단계: 객체를 수정
	// 3단계: 수정하 객체를 다시 저장
	
	// 1. 아이디가 3인 데이터의 나이를 45로 수정
	//@Test
	@DisplayName("1. 아이디가 3인 데이터의 나이를 45로 수정")
	@Transactional
	@Commit
	public void testUpdate01() {
		// 1단계: 객체의 정보를 획득
		Optional<Customer> customer = this.customerRepository.findById(3L);
		log.info("수정 전:" + customer.toString());
		// 2단계: 객체의 정보를 수정
		Customer c = customer.get();
		c.setAge(45);
		// 3단계: 수정할 객체를 다시 테이블에 저장
		this.customerRepository.save(c);
		
		Optional<Customer> customer2 =  this.customerRepository.findById(3L);
		log.info("수정 후:" + customer2.toString());
	}
	
	// 2. 고객 아이디가 bbbb이고, 이름이 표남경인 데이터의 전화번호를 010-1004-1004로 수정
	//@Test
	@DisplayName("2. 고객 아이디가 bbbb이고, 이름이 표남경인 데이터의 전화번호를 010-1004-1004로 수정")
	@Transactional
	@Commit
	public void testUpdate02() {	
		// 1단계: 고객 아이디가 bbbb이고, 이름이 표남경인 데이터 획득
		Optional<Customer> customer = this.customerRepository.findByCustomerIdAndName("bbbb", "표남경");
		log.info("수정 전:" + customer.toString());
		// 2단계: 획득한 데이터의 전화번호를 수정
		Customer c = customer.get();
		c.setPhone("010-1004-1004");;
		// 3단계: 수정한 데이터를 저장
		this.customerRepository.save(c);
		
		Optional<Customer> customer2 = this.customerRepository.findByCustomerIdAndName("bbbb", "표남경");
		log.info("수정 후:" + customer2.toString());
	}
	
	// 3. 주소가 인천이고, 전화번호가 010-5555-5555인 고객의 이름을 장겨울, 성별을 여성으로 수정
	//@Test
	@DisplayName("3. 주소가 인천이고, 전화번호가 010-5555-5555인 고객의 이름을 장겨울, 성별을 여성으로 수정")
	@Transactional
	@Commit
	public void testUpdate03() {
		// 1단계: 주소가 인천이고, 전화번호가 010-5555-5555인 데이터를 획득 
		Optional<Customer> customer = this.customerRepository.findByAddressContainingAndPhoneEquals("인천", "010-5555-5555");
		log.info("수정 전:" + customer.toString());
		// 2단계: 획득한 데이터의 이름을 장겨울, 성별을 여성으로 수정
		Customer c = customer.get();
		c.setName("장겨울");
		c.setGender("여성");
		
		this.customerRepository.save(c);
		
		Optional<Customer> customer2 = this.customerRepository.findByAddressContainingAndPhoneEquals("인천", "010-5555-5555");
		log.info("수정 후:" + customer2.toString());
	}	
	
	// ###################################################################################################################
	// 1. count(), exists(), existsById 메서드 확인
	@Test
	@DisplayName("1. count()확인")
	public void textCount() {
		// 1. customer 테이블의 전체 건수
		long cnt = this.customerRepository.count();
		log.info("customer 테이블의 건수: " + cnt);
		
		// 2. 아이디가 5인 데이터의 존재 여부
		boolean yn = this.customerRepository.existsById(5L);
		if (yn) log.info("5L 아이디는 존재합니다.");
		else log.info("5L 아이디는 존재합니다.");
		
		boolean yn2 = this.customerRepository.existsById(6L);
		if (yn2) log.info("6L 아이디는 존재합니다.");
		else log.info("6L 아이디는 존재합니다.");		
		
		// 3. 
		Optional<Customer> customer = this.customerRepository.findByName("표남경");

		

	}
}
	
