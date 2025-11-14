package com.mysite.sbb;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Customer;
import com.mysite.sbb.repository.CustomerRepository02;

import lombok.extern.java.Log;

//[ 2.JpaRepository @Query, @Param 애너테이션을 사용하는 방법 ]

@Log
@SpringBootTest
public class CustomerTest02 {
	// CustomerRepository02의 빈을 등록
	@Autowired
	private CustomerRepository02 customerRepository;
	
	// [ 1. 데이터 조회 ]
	//@Test
	@DisplayName("1. 전체 데이터 조회")
	public void testSelect01() {
		List<Customer> custmerList = this.customerRepository.querySelectAll();
		custmerList.forEach(c -> log.info(c.toString()));
	}
	
	//@Test
	@DisplayName("2. 아이디로 1건 조회")
	public void testSelect02() {
		Optional<Customer> customer = this.customerRepository.querySelectById(4L);
		log.info(customer.get().toString());
	}
	
	//@Test
	@DisplayName("3. 고객아이디로 1건 조회")
	public void testSelect03() {
		Optional<Customer> customer = this.customerRepository.querySelectByCustomerId("cccc");
		log.info(customer.get().toString());
	}
	
	//@Test
	@DisplayName("4. 고객아이디와 이름이 모두 일치하는 데이터 1건 조회")
	public void testSelect04() {
		Optional<Customer> customer = this.customerRepository.querySelectByCustomerIdAndName("dddd", "김사비");
		log.info(customer.get().toString());
	}
	
	//@Test
	@DisplayName("5. 나이가 30세이상이고, 성별이 여성인 데이터 여러 건 조회")
	public void testSelect05() {
		List<Customer> customerList = this.customerRepository.querySelectByAgeAndGender(30, "여성");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 6. 주소가 인천이고, 나이가 30대인 데이터를 여러 건 조회
	// 7. 주소 또는 이메일이 또는 전화번호가 널이고, 이름이 김씨 또는 이씨인 데이터를 여러 건 조회
	// 8. 가입일이 2025년인 데이터를 여러 건 조회
	
	//@Test
	@DisplayName("6. 주소가 인천이고, 나이가 30대인 데이터를 여러 건 조회")
	public void testSelect06() {
		List<Customer> customerList = this.customerRepository.querySelectByAddesssAndAge("인천", 30, 39);
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	//@Test
	@DisplayName("7. 주소 또는 이메일이 또는 전화번호가 널이고, 이름이 김씨 또는 이씨인 데이터를 여러 건 조회")
	public void testSelect07() {
		List<Customer> customerList = this.customerRepository.querySelectByAddesssOrEmailOrPhoneAndName("김", "이");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	//@Test
	@DisplayName("8. 가입일이 2025년인 데이터를 여러 건 조회")
	public void testSelect08() {
		List<Customer> customerList = this.customerRepository.querySelectByRegDate("2025");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 9. 이메일이 naver를 사용하는 고객 중에서 서울에 사는 데이터를 여러 건 조회
	//@Test
	@DisplayName("9. 이메일이 naver를 사용하는 고객 중에서 서울에 사는 데이터를 여러 건 조회")
	public void testSelect09() {
		List<Customer> customerList = this.customerRepository.querySelectByEmailAndAddress("naver", "서울");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 10. 고객아이디가 cccc인 고객과 같은 성별인 데이터를 여러 건 조회
	//@Test
	@DisplayName("10. 고객아이디가 cccc인 고객과 같은 성별인 데이터를 여러 건 조회")
	public void testSelect10() {
		List<Customer> customerList = this.customerRepository.querySelectByCustomerId2("cccc");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 11. 2002년에 가입한 고객과 사는 도시가 같은 데이터를 여러 건 조회
	//@Test
	@DisplayName("11. 2002년에 가입한 고객과 사는 도시가 같은 데이터를 여러 건 조회")
	public void testSelect11() {
		List<Customer> customerList = this.customerRepository.querySelectByRegDate2("2002");
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 12. 나이가 27, 29, 33세인 데이터를 여러 건 조회
	//@Test
	@DisplayName("12. 나이가 27, 29, 33세인 데이터를 여러 건 조회")
	public void testSelect12() {
		List<Integer> ages = List.of(27, 29, 33);
		List<Customer> customerList = this.customerRepository.querySelectByAgeIn(ages);
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// #####################################################################################
	// [ 2. 데이터 수정 ]
	//@Test
	@DisplayName("1. 아이디에 해당하는 고객의 나이를 수정")
	@Transactional
	@Commit
	public void testUpdate01() {
		this.customerRepository.queryUpdateAgeId(1L, 48);
		// 확인
		Optional<Customer> customer = this.customerRepository.findById(1L);
		log.info(customer.get().toString());
	}
	
	// 2. 고객아이디와 이름이 일치하는 고객의 전화번호를 수정
	//@Test
	@DisplayName("2. 고객아이디와 이름이 일치하는 고객의 전화번호를 수정")
	@Transactional
	@Commit
	public void testUpdate02() {
		this.customerRepository.queryUpdateCustomerIdNamePhone("bbbb", "표남경", "010-1004-1004");
		// 확인
		Optional<Customer> customer = this.customerRepository.findById(2L);
		log.info(customer.get().toString());
	}
	
	// 3. 나이가 40대인 고객의 가입일자를 2024-10-10로 수정
	//@Test
	@DisplayName("3. 나이가 40대인 고객의 가입일자를 2024-10-10로 수정")
	@Transactional
	@Commit
	public void testUpdate03() {
		this.customerRepository.queryUpdateAgeRegDate(40, 49, "2024-10-10");
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 4. 부천에 사는 여성 고객의 나이를 10살을 더하여 수정
	//@Test
	@DisplayName("4. 부천에 사는 여성 고객의 나이를 10살을 더하여 수정")
	@Transactional
	@Commit
	public void testUpdate04() {
		this.customerRepository.queryUpdateAddressGenderAge("부천", "여성");
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 5. 이메일이 널이 고객의 이메일을 xxxx@korea.com으로 수정
	//@Test
	@DisplayName("5. 이메일이 널이 고객의 이메일을 xxxx@korea.com으로 수정")
	@Transactional
	@Commit
	public void testUpdate05() {
		this.customerRepository.queryUpdateEmail();
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// ★★★★★
	// 6. 객체 형식으로 매개변수를 받아서 수정하는 방법
	// - id로 나이를 수정
	//@Test
	@DisplayName("6. 객체 형식으로 매개변수를 받아서 수정하는 방법")
	@Transactional
	@Commit
	public void testUpdate06() {
		Customer customer = Customer.builder().id(2L).age(35).build();
		this.customerRepository.queryUpdateCustomer(customer);
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// ###################################################################################
	// [ 3. 데이터 삭제 ]
	
	// 1. 아이디로 데이터를 삭제
	//@Test
	@DisplayName("1. 아이디로 데이터를 삭제")
	@Transactional
	@Commit
	public void testDelete01() {
		this.customerRepository.queryDeleteId(1L);
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 2. 고객아이디와 이름이 일치할 때 삭제
	//@Test
	@DisplayName("2. 고객아이디와 이름이 일치할 때 삭제")
	@Transactional
	@Commit
	public void testDelete02() {
		this.customerRepository.queryDeleteCustomerIdName("bbbb", "표남경");
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 3. 나이가 20대인 고객을 삭제
	//@Test
	@DisplayName("3. 나이가 20대인 고객을 삭제")
	@Transactional
	@Commit
	public void testDelete03() {
		//this.customerRepository.queryDeleteAge(20,  29);
		this.customerRepository.queryDeleteAge2(20);
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 4. 부천에 사는 여성을 삭제
	//@Test
	@DisplayName("4. 부천에 사는 여성을 삭제")
	@Transactional
	@Commit
	public void testDelete04() {
		this.customerRepository.queryDeleteAddressGender("부천", "여성");
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// 5. 모든 고객을 삭제
	//@Test
	@DisplayName("5. 모든 고객을 삭제")
	@Transactional
	@Commit
	public void testDelete05() {
		this.customerRepository.queryDeleteAll();
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// ★★★★★
	// 6. 객체를 매개변수로 전달하여 삭제하는 방법
	//@Test
	@DisplayName("6. 객체를 매개변수로 전달하여 삭제하는 방법")
	@Transactional
	@Commit
	public void testDelete06() {
		Customer customer = Customer.builder().id(1L).build();
		this.customerRepository.queryDeleteCustomer(customer);
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	// ###################################################################################
	// [ 4. 데이터 추가 ]
	
	// 1. 객체를 매개변수로 전달하여 추가하는 방법
	//@Test
	@DisplayName("1. 객체를 매개변수로 전달하여 추가하는 방법")
	@Transactional
	@Commit
	public void testInsert01() {
		Customer customer = Customer.builder().customerId("kkkk").name("장겨울").gender("여성").age(35).phone("010-1234-1234")
				.email("kkkk@kakao.com").address("안양시 동구 홍문동").build();
		this.customerRepository.queryInsertCustomer(customer);
		// 확인
		List<Customer> customerList = this.customerRepository.findAll();
		customerList.forEach(c -> log.info(c.toString()));
	}
	
	
	
	
	
	
	
	
	
	
	
}
