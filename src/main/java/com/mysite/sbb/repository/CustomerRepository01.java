package com.mysite.sbb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.entity.Customer;

/*
 * < Repository 인터페이스 >
 * - 엔터티로 생성한 테이블에 DML 명령을 실행할 수 있도록 하는 역할
 * - JpaRepository 인터페이스를 상속해야 함.
 * - JpaRepository<엔터티 클래스명, @Id로 설정된 컬럼의 타입>
 */
@Repository
public interface CustomerRepository01 extends JpaRepository<Customer, Long> {
	// ############################################################
	// [ 조회 메서드 테스트 ]
	// 3. 이름으로 조회하는 메서드 -> 1건을 리턴
	Optional<Customer> findByName(String name);
	
	// 4. 고객아이디로 조회하는 메서드 -> 1건을 리턴
	Optional<Customer> findByCustomerId(String customerId);
	
	// 5. 성별로 조회하는 방법 -> 여러건을 리턴
	List<Customer> findByGender(String gender);
	
	// 6. 이름과 나이를 모두 만족할 때 조회하는 방법 -> 1건을 리턴
	Optional<Customer> findByNameAndAge(String name, Integer age);
	
	// 7. 성별 또는 나이가 일치할 때  조회하는 방법 -> 여러 건을 리턴
	List<Customer> findByGenderOrAge(String gender, Integer age);
	
	// 8. 특정 나이 이상인 데이터를 조회하는 방법 -> 여러 건을 리턴
	List<Customer> findByAgeGreaterThanEqual(Integer age);
	
	// 9.성별이 일치하고, 나이가 30세 이상인 데이터를 조회하는 방법 -> 여러 건을 리턴
	List<Customer> findByGenderAndAgeGreaterThanEqual(String gender, Integer age);

	// 10. 성별이 일치하지 않고, 나이가 30세 미만인 데이터를 조회하는 메서드를 생성하여 사용
	List<Customer> findByGenderNotAndAgeLessThan(String gender, Integer age);
	
	// 11. 나이가 20대인 데이터를 조회하는 메서드를 생성하여 사용	-> 여러 건을 리턴
	// 20보다 크거나 같고, 30보다 작은 데이터
	List<Customer> findByAgeGreaterThanEqualAndAgeLessThan(Integer age1, Integer age2);
	List<Customer> findByAgeBetween(Integer age1, Integer age2);
	
	// 12. 나이가 27, 29, 33세인 데이터를 조회하는 메서드를 생성하여 사용 -> 여러 건을 리턴
	List<Customer> findByAgeOrAgeOrAge(Integer age1, Integer age2, Integer age3);
	List<Customer> findByAgeIn(List<Integer> ages);
	
	// 13 이름에 '재'를 포함하는 데이터를 조회하는 메서드를 생성하여 사용 -> 여러 건을 리턴
	List<Customer> findByNameLike(String name);
	List<Customer> findByNameContaining(String name);
	// 14 이름에 '표'로 시작하는 데이터를 조회하는 메서드를 생성하여 사용 -> 여러 건을 리턴
	List<Customer> findByNameStartingWith(String name);
	// 15.이름에 '비'로 끝나는 데이터를 조회하는 메서드를 생성하여 사용 -> 여러 건을 리턴	
	List<Customer> findByNameEndingWith(String name); 
	
	// 16. 이름에 '재'를 포함하지 않는 데이터를 조회하는 메서드를 생성하여 사용
	List<Customer> findByNameNotLike(String name);
	List<Customer> findByNameNotContaining(String name);
	
	// 17. 이메일이 널인 데이터를 조회
	List<Customer> findByEmailIsNull();
	// 18. 이메일이 널이 아닌 데이터를 조회 
	List<Customer> findByEmailIsNotNull();
	
	// 19. 성별에 대해 조회하여 나이에 대한 내림차순으로 출력 -> 여러건을 리턴
	List<Customer> findByGenderOrderByAgeDesc(String gender);
	
	// 20. 성별에 대해 조회하여 나이에 대한 내림차순으로 하여 상위 3건의 데이터를 조회
	List<Customer> findFirst3ByAgeOrderByIdDesc(Integer age);
	List<Customer> findTop3ByAgeOrderByIdDesc(Integer age);
	
	// #############################################################
	// [ 삭제 메서드 ]
	
	// 4번 - 나이가 30세 이상인 데이터를 삭제
	void deleteByAgeGreaterThanEqual(Integer age);
	
	// 5번 - 주소가 부천인 데이터를 삭제
	void deleteByAddressLike(String address);
	void deleteByAddressContaining(String address);
	
	// 6번 - 주소가 인천이고, 이름이 엄재일인 데이터를 삭제
	void deleteByAddressLikeAndName(String address, String name);
	void deleteByAddressContainingAndNameEquals(String address, String name);
	
	// 7번 - 2012년에 가입한 고객의 데이터를 삭제
	void deleteByRegDateBetween(LocalDateTime date1, LocalDateTime date2);

	// #############################################################
	// [ 수정 메서드 테스트 ]
	//  save() 메서드가 추가, 수정하는 역할을 함.

	// 2. 고객 아이디가 bbbb이고, 이름이 표남경인 데이터의 전화번호를 010-1004-1004로 수정
	// 고객 아이디가 bbbb이고, 이름이 표남경인 데이터 조회
	Optional<Customer> findByCustomerIdAndName(String customerId, String name);
	
	// 3. 주소가 인천이고, 전화번호가 010-5555-5555인 고객의 이름을 장겨울, 성별을 여성으로 수정
	// 주소가 인천이고, 전화번호가 010-5555-5555인 고객 조회
	Optional<Customer> findByAddressContainingAndPhoneEquals(String address, String phone);
	
	
}
