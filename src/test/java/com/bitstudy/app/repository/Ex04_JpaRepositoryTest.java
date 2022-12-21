package com.bitstudy.app.repository;



/* 슬라이드 테스트란 지난번 TDD 때 각 메서드들 다 남남으로 서로를 알아보지 못하게 만들었었다. 이것처럼 메서드들 각각이 테스트한
* 결과를 서로 못보게 잘라서 만드는 것 */

import com.bitstudy.app.config.JpaConfig;
import com.bitstudy.app.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // 슬라이드 테스트

@Import(JpaConfig.class) /** 원래대로라면 JPA 에서 모든정보를 컨트롤 해야되는데  JpaConfig의 경우는 읽어오지 못함.
 이유는 시스템에서 만든게 아니라 우리가 별도로 만든 파일이기 때문. 그래서 따로 import를 해줘야 함.
 안하면 config 안에 명시해놨던 JpaAuditing 기능이 동작하지 않음.
 **/

class Ex04_JpaRepositoryTest {


    private final Ex04_ArticleRepository_기본테스트용 articleRepository;
    private final Ex05_ArticleCommentRepository_기본테스트용 articleCommentRepository;
    /* 원래는 둘다 @Autowired 가 붙어있어야 하는데 JUnit5 버전과 최신버전의 스프링부트를 이용하면 Test 에서 주변 샛성자 주입패턴늘 사용할 수 있다.
    *
    *  */

    /* 생성자 만들기 - 여기서는 다른 파일에서 매개변수로 보내주는 걸 받는거라서 위에랑 상관없이 @Autowired 를 붙혀야함 */
    Ex04_JpaRepositoryTest(@Autowired Ex04_ArticleRepository_기본테스트용 articleRepository, @Autowired Ex05_ArticleCommentRepository_기본테스트용 articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    /* - 트랜잭션시 사용하는 매서드
         사용법 : repository명.메서드()
       1) findAll() - 모든 컬럼을 조회할 때 사용. 페이징(pageble) 가능
                      당연히 select 작업을 할 때 사용하지만, 잠깐 사이에 해당 테이블에 어떤 변화가 있었는지 알 수 없기 때문에 select 전에 먼저 최신 데이터를 잡기 위해서 update를 함.
                      동작 순서 : update -> select
       2) findById() - 한 건에 대한 조회시 사용
                       primary key 로 레코드 한검 조회.
                       () 안에 글번호를 넣어줘야 함.
       3) save() - 레코드 저장 시 사용(insert, update)
       4) count() - 레코드 개수 뽑을 때 사용
       5) delete() - 레코드 삭제
    *  */
    
    /* select 테스트 */
    @Test
    void selectTest() {
        /* 셀렉팅을 할거니까 articleRepository 를 기준으로 테스트 할거임
        *
        *  maven 방식 : dao -> mapper 로 정보 보내고 DB 갔다 와서 C까지 돌려보낼건데 dao 에서 DTO를 list 에 담아서 return
        * */

        List<Article> articles = articleRepository.findAll();


        /* assertJ 를 이용해서 테스트 할거임
        *  articles 가 NotNull 이고 사이즈가 ?? 개면 통과
        *  */
        assertThat(articles).isNotNull().hasSize(100); // 비어있지않고 사이즈가 100이면 통과.
    }
    
    /* insert 테스트 */
    @DisplayName("insert 테스트")
    @Test
    void insertTest() {
        // 기존 카운트 구하기
        long prevCount

        // insert 하기
        
        // 기존꺼랑 현재꺼랑 개수 차이 구하기
    }
}