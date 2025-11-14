
/*
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('aaaa', '오이영', '남성', 28, '010-1111-1111', 'aaaa@naver.com', '서울시 강남구 역삼동', '1992-07-20');
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date)  
values('bbbb', '표남경', '여성', 27, '010-2222-2222', 'bbbb@naver.com', '서울시 강남구 압구정동', '1997-11-25');
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('cccc', '엄재일', '남성', 29, '010-3333-3333', 'cccc@naver.com', '인천시 북구 월미동', '2002-06-11');
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('dddd', '김사비', '남성', 32, '010-4444-4444', 'dddd@naver.com', '부천시 동구 가구동', '2012-09-15');
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('eeee', '구도원', '남성', 33, '010-5555-5555', 'eeee@naver.com', '인천시 수성구 안양시 중구', '2012-11-11');
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('ffff', '이익준', '남성', 42, '010-6666-6666', null, '수원시 동구 중앙동', now());
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('gggg', '김준완', '남성', 42, null, 'gggg@kakao.com', '수원시 중구 대길동', now());
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('hhhh', '채송화', '여성', 43, '010-8888-8888', 'hhhh@kakao.com', null, now());
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('iiii', '안정원', '남성', 44, '010-9999-9999', null, '일산시 서구 일산동', now());
insert into customer(customer_id, name, gender, age, phone, email, address, reg_date) 
values('jjjj', '양석형', '남성', 45, null, 'jjjj@kakao.com', '부천시 북구 행복동', now());
commit;
*/

-- 질문등록
insert into question(subject, content, create_date) values('스프링부트는 무엇인가요?', '스프링부트에 대해 알고싶어오.', now());
insert into question(subject, content, create_date) values('JPA는 어떻게 사용하나요?', '이번에 회사에서 JPA를 사용하려고 해요', now());
insert into question(subject, content, create_date) values('엔터티는 테이블과 무슨 관련이 있나요?', '엔터티는 어떻게 자동으로 테이블을 만드는지 궁금해요.', now());
insert into question(subject, content, create_date) values('룸북은 무슨 기능이 있나요?', '룸북을 사용하면 장점이 있나요?', now());
insert into question(subject, content, create_date) values('게시판에는 어떤글을 남겨야 하나요?', 'sbb가 무엇인지 궁금해요.', now());
commit;

-- 답변등록
insert answer(content, create_date, question_id) values('자바 기반의 웹프레임워크입니다.', now(), 1);
insert answer(content, create_date, question_id) values('스프링부트는 JPA로 DB의 테이블을 관리합니다.', now(), 1);
insert answer(content, create_date, question_id) values('JpaRepository 인터페이스를 상속한 레파지토리를 생성합니다', now(), 2);
insert answer(content, create_date, question_id) values('JpaRepository 인터페이스에 있는 쿼리 메서드를 사용합니다.', now(), 2);
commit;


