package com.mysite.sbb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Customer;

@Repository
public interface CustomerRepository02 extends JpaRepository<Customer, Long> {
	// [ 1. 데이터 조회 ]
	
	// 1. 전체 데이터 조회
	public final String SELECT_ALL = "select * from customer";
	@Query(value = SELECT_ALL, nativeQuery = true)
	List<Customer> querySelectAll();
	
	// 2. 아이디로 1건 조회
	public final String SELECT_ID = "select * from customer where id = :id";
	@Query(value = SELECT_ID, nativeQuery = true)
	Optional<Customer> querySelectById(@Param("id") Long id);
	
	// 3. 고객아이디로 1건 조회
	public final String SELECT_CUSTOMER_ID = "select * from customer where customer_id = :customerId";
	@Query(value = SELECT_CUSTOMER_ID, nativeQuery = true)
	Optional<Customer> querySelectByCustomerId(@Param("customerId") String customerId);
	
	// 4. 고객아이디와 이름으로 1건 조회
	// 4-1. nativeQuery = true -> SQL 문법을 사용
	//public final String SELECT_CUSTOMER_ID_NAME = "select * from customer where customer_id = :customerId and name = :name";
	//@Query(value = SELECT_CUSTOMER_ID_NAME, nativeQuery = true)
	
	// 4-2. nativeQuery = false -> JPQL 문법을 사용
	public final String SELECT_CUSTOMER_ID_NAME = "select c1 from Customer c1 where customerId = :customerId and name = :name";
	@Query(value = SELECT_CUSTOMER_ID_NAME, nativeQuery = false)
	Optional<Customer> querySelectByCustomerIdAndName(@Param("customerId") String customerId, @Param("name") String name);
	
	// 5. 나이와 성별로 여러 건 조회
	// 5-1. nativeQuery = true -> SQL 문법을 사용
	//public final String SELECT_AGE_GENDER = "select * from customer where age >= :age and gender = :gender";
	//@Query(value = SELECT_AGE_GENDER, nativeQuery = true)
	
	// 5-2. nativeQuery = false -> JPQL 문법을 사용
	public final String SELECT_AGE_GENDER = "select c1 from Customer c1 where age >= :age and gender = :gender";
	@Query(value = SELECT_AGE_GENDER, nativeQuery = false)
	List<Customer> querySelectByAgeAndGender(@Param("age") Integer age, @Param("gender") String gender);
	
	// 6. 주소가 인천이고, 나이가 30대인 데이터를 여러 건 조회
	public final String SELECT_ADDRESS_AGE = "select * from customer where address like concat('%',:address,'%') and age between :age1 and :age2";
	@Query(value = SELECT_ADDRESS_AGE, nativeQuery = true)
	List<Customer> querySelectByAddesssAndAge(@Param("address") String address, @Param("age1") Integer age1, @Param("age2") Integer age2);
	
	// 7. 주소 또는 이메일이 또는 전화번호가 널이고, 이름이 김씨 또는 이씨인 데이터를 여러 건 조회
	// 7-1번
	/*
	public final String SELECT_ADDRESS_EMAIL_PHONE_NAME = "select * from customer "
			+ " where (address is null or email is null or phone is null) "
			+ " and name like concat(:name1, '%') or name like concat(:name2, '%')";
	*/
	
	// 7-2번 - 인라인 뷰
	public final String SELECT_ADDRESS_EMAIL_PHONE_NAME = "select c.* from (select * from customer where address is null or email is null or phone is null) c "
			+ " where name like concat(:name1, '%') or name like concat(:name2, '%')";
	@Query(value = SELECT_ADDRESS_EMAIL_PHONE_NAME, nativeQuery = true)
	List<Customer> querySelectByAddesssOrEmailOrPhoneAndName(@Param("name1") String name1, @Param("name2") String name2);
	
	// 8. 가입일이 2025년인 데이터를 여러 건 조회
	// 8-1. date() 함수, between A and B
	//public final String SELECT_REG_DATE = "select * from customer where reg_date between date(concat(:year,'-01-01')) and date(concat(:year,'-12-31'))";
	// 8-2. substr() 함수
	//public final String SELECT_REG_DATE = "select * from customer where substr(reg_date,1,4) = :year";
	// 8-3. year() 함수
	public final String SELECT_REG_DATE = "select * from customer where year(reg_date) = :year";
	@Query(value = SELECT_REG_DATE, nativeQuery = true)
	List<Customer> querySelectByRegDate(@Param("year") String year);
	
	// 9. 이메일이 naver를 사용하는 고객 중에서 서울에 사는 데이터를 여러 건 조회
	public final String SELECT_EMAIL_ADDRESS = "select * from customer where email like concat('%',:email,'%') and address like concat('%',:address,'%')";
	@Query(value = SELECT_EMAIL_ADDRESS, nativeQuery = true)
	List<Customer> querySelectByEmailAndAddress(@Param("email") String email, @Param("address") String address);
	
	// 서브쿼리
	// 10. 고객아이디가 cccc인 고객과 같은 성별인 데이터를 여러 건 조회
	public final String SELECT_CUSTOMER_ID2 = "select * from customer where gender = (select gender from customer where customer_id = :customerId)";
	@Query(value = SELECT_CUSTOMER_ID2, nativeQuery = true)
	List<Customer> querySelectByCustomerId2(@Param("customerId") String customerId);
	
	// 서브쿼리
	// 11. 2002년에 가입한 고객과 사는 도시가 같은 데이터를 여러 건 조회
	public final String SELECT_REG_DATE2 = "select * from customer where substr(address,1,2) = (select substr(address,1,2) from customer where year(reg_date) = :year)";
	@Query(value = SELECT_REG_DATE2, nativeQuery = true)
	List<Customer> querySelectByRegDate2(@Param("year") String year);
	
	// 12. 나이가 27, 29, 33세인 데이터를 여러 건 조회
	public final String SELECT_AGE_IN = "select * from customer where age in :ages";
	@Query(value = SELECT_AGE_IN, nativeQuery = true)
	List<Customer> querySelectByAgeIn(@Param("ages") List<Integer> ages);
	
	// ###################################################################################
	// [ 2. 데이터 수정 ]
	
	// 1. 아이디에 해당하는 고객의 나이를 수정
	public final String UPDATE_AGE_ID = "update customer set age = :age where id = :id";
	@Query(value = UPDATE_AGE_ID, nativeQuery = true)
	@Transactional
	@Modifying
	void queryUpdateAgeId(@Param("id") Long id, @Param("age") Integer age);
	
	// 2. 고객아이디와 이름이 일치하는 고객의 전화번호를 수정
	public final String UPDATE_CUSTOMERID_NAME_PHONE = "update customer set phone = :phone where customer_id = :customerId and name = :name";
	@Query(value = UPDATE_CUSTOMERID_NAME_PHONE, nativeQuery = true)
	@Transactional
	@Modifying
	void queryUpdateCustomerIdNamePhone(@Param("customerId") String customerId, @Param("name") String name, @Param("phone") String phone);
	
	// 3. 나이가 40대인 고객의 가입일자를 2024-10-10로 수정
	public final String UPDATE_AGE_REGDATE = "update customer set reg_date = date(:regDate) where age between :age1 and :age2";
	@Query(value = UPDATE_AGE_REGDATE, nativeQuery = true)
	@Transactional
	@Modifying
	void queryUpdateAgeRegDate(@Param("age1") Integer age1, @Param("age2") Integer age2, @Param("regDate") String regDate);
	
	// 4. 부천에 사는 여성 고객의 나이를 10살을 더하여 수정
	public final String UPDATE_ADDRESS_GENDER_AGE = "update customer set age = age + 10 where address like concat('%',:address,'%') and gender = :gender";
	@Query(value = UPDATE_ADDRESS_GENDER_AGE, nativeQuery = true)
	@Transactional
	@Modifying
	void queryUpdateAddressGenderAge(@Param("address") String address, @Param("gender") String gender);
	
	
	// 5. 이메일이 널이 고객의 이메일을 xxxx@korea.com으로 수정
	public final String UPDATE_EMAIL = "update customer set email = 'xxxx@korea.com' where email is null";
	@Query(value = UPDATE_EMAIL, nativeQuery = true)
	@Transactional
	@Modifying
	void queryUpdateEmail();
	
	// 6. 객체 형식으로 매개변수를 받아서 수정하는 방법
	// - id로 나이를 수정 -> 객체에 담겨 있는 멤버 변수의 값을 꺼내서 비교
	public final String UPDATE_CUSTOMER = "update customer set age = :#{#customer.age} where id = :#{#customer.id}";
	@Query(value = UPDATE_CUSTOMER, nativeQuery = true)
	@Transactional
	@Modifying
	void queryUpdateCustomer(@Param("customer") Customer customer);
	
	// ###################################################################################
	// [ 3. 데이터 삭제 ]
	
	// 1. 아이디로 삭제
	public final String DELETE_ID = "delete from customer where id = :id";
	@Query(value = DELETE_ID, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteId(@Param("id") Long id);
	
	// 2. 고객아이디와 이름이 일치할 때 삭제
	public final String DELETE_CUSTOMERID_NAME = "delete from customer where customer_id = :customerId and name = :name";
	@Query(value = DELETE_CUSTOMERID_NAME, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteCustomerIdName(@Param("customerId") String customerId, @Param("name") String name);
	
	// 3. 나이가 20대인 고객을 삭제
	// 3-1.
	public final String DELETE_AGE = "delete from customer where age between :age1 and :age2";
	@Query(value = DELETE_AGE, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteAge(@Param("age1") Integer age1, @Param("age2") Integer age2);
	
	// 3-2.
	public final String DELETE_AGE2 = "delete from customer where age between :age and :age+9";
	@Query(value = DELETE_AGE2, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteAge2(@Param("age") Integer age);
	
	// 4. 부천에 사는 여성을 삭제
	public final String DELETE_ADDRESS_GENDER = "delete from customer where address like concat('%',:address,'%') and gender = :gender";
	@Query(value = DELETE_ADDRESS_GENDER, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteAddressGender(@Param("address") String address, @Param("gender") String gender);
	
	// 5. 모든 고객을 삭제
	public final String DELETE_ALL = "delete from customer";
	@Query(value = DELETE_ALL, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteAll();
	
	//★★★★★
	// 6. 객체를 매개변수로 받아서 삭제하는 방법 -> 객체 안의 멤버변수 값을 꺼내서 비교
	public final String DELETE_CUSTOMER = "delete from customer where id = :#{#customer.id}";
	@Query(value = DELETE_CUSTOMER, nativeQuery = true)
	@Transactional
	@Modifying
	void queryDeleteCustomer(@Param("customer") Customer customer);
	
	// ###################################################################################
	// [ 4. 데이터 추가 ]
	
	// 1. 객체를 매개변수로 전달하여 추가하는 방법
	public final String INSERT_CUSTOMER = "insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) "
			+ " values(:#{#customer.customerId}, :#{#customer.name}, :#{#customer.gender}, :#{#customer.age}, :#{#customer.phone}, :#{#customer.email}, :#{#customer.address}, now())";
	@Query(value = INSERT_CUSTOMER, nativeQuery = true)
	@Transactional
	@Modifying
	void queryInsertCustomer(@Param("customer") Customer customer);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
