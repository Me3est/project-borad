package com.bitstudy.app.repository;


import com.bitstudy.app.config.JpaConfig;
import com.bitstudy.app.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

//import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 슬라이드 테스트
/** 슬라이드 테스트란 지난번 TDD 때 각 메서드들 다 남남으로 서로를 알아보지 못하게 만들었었다. 이것처럼 메서드들 각각 테스트한 결과를 서로 못보게 잘라서 만드는것 *
 *
 * */

@Import(JpaConfig.class)
/** 원래대로라면 JPA 에서 모든 정보를 컨트롤 해야되는데 JpaConfig 의 경우는 읽어오지 못한다. 이유는 이건 시스템에서 만든게 아니라 우리가 별도로 만든 파일이기 때문. 그래서 따로 import를 해줘야 한다.
 안하면 config 안에 명시해놨던 JpaAuditing 기능이 동작하지 않는다.
 * */

class Ex04_JpaRepositoryTest {
    private final Ex04_ArticleRepository_기본테스트용 articleRepository;
    private final Ex05_ArticleCommentRepository_기본테스트용 articleCommentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;
    /* 원래는 둘 다 @Autowired가 붙어야 하는데, JUnit5 버전과 최신 버전의 스프링 부트를 이용하면 Test에서 생성자 주입패턴을 사용할 수 있다.  */


    /* 생성자 만들기 - 여기서는 다른 파일에서 매개변수로 보내주는걸 받는거라서 위에랑 상관 없이 @Autowired 를 붙여야 함 */
    public Ex04_JpaRepositoryTest(@Autowired Ex04_ArticleRepository_기본테스트용 articleRepository, @Autowired Ex05_ArticleCommentRepository_기본테스트용 articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }


    /* - 트랜잭션시 사용하는 메서드
        사용법: repository명.메소드()
        1) findAll() - 모든 컬럼을 조회할때 사용. 페이징(pageable) 가능
                        당연히 select 작업을 하지만, 잠깐 사이에 해당 테이블에 어떤 변화가 있었는지 알 수 없기 때문에 select 전에 먼저 최신 데이터를 잡기 위해서 update를 한다.
                        동작 순서 : update -> select

        2) findById() - 한 건에 대한 데이터 조회시 사용
                        primary key로 레코드 한검 조회.
        3) save() - 레코드 저장할때 사용 (insert, update)
        4) count() - 레코드 개수 뽑을때 사용
        5) delete() - 레코드 삭제
        ------------------------------------------------------

        - 테스트용 데이터 가져오기
            1) mockaroo 사이트 접속

    * */

    /* select 테스트 */
    @Test
    void selectTest() {
        /** 셀렉팅을 할거니까 articleRepository 를 기준으로 테스트 할거임.
         maven방식: dao -> mapper 로 정보 보내고 DB 갔다 와서 C 까지 돌려보낼건데 dao에서 DTO를 list에 담아서 return
         * */

        List<Article> articles  =  articleRepository.findAll();

        /** assertJ 를 이용해서 테스트 할거임
         * articles 가 NotNull 이고 사이즈가 ?? 개면 통과
         *
         * * */
        assertThat(articles).isNotNull().hasSize(100);

    }

    /*  insert 테스트 */
    @DisplayName("insert 테스트")
    @Test
    void insertTest() {
        long prevCount = articleRepository.count();

        assertThat(articleRepository.count()).isEqualTo(prevCount + 1);
    }


    // update 테스트
    @DisplayName("update 테스트")
    @Test
    void updateTest() {
        Article article = articleRepository.findById(1L).orElseThrow();
//        articleRepository.update(article);

        String updateArticle = "#asdf"
    }

    @DisplayName("delete 테스트")
    @Test
    void deleteTest() {
        /* 기존의 데이터들이 있다고 치고, 그 중에 값을 하나 꺼내고, 지워야 함
        *
        *  1) 기존의 영속성 컨텍스트로부터 하나의 엔티티 객체를 가져옴 => findById
        *  2) 지우면 DB에서 하나 사라지기 때문에 count 를 구해놓고 => 레포지토리.count()
        *  3) delete 하고 (-1) => .delete();
        *  4) 2번에서 구한 count와 지금 순간의 개수 비교해서 1 차이나면 테스트 통과 => .isEqualsTo()
        * */

        /* 1) 기존의 영속성 컨텍스트로 부터 하나 엔티티 객체를 가져온다.
        *  - 순서
        *   articleRepository -> 기존의 영속성 컨텍스트로 부터
        *   findById(1L) -> 하나 엔티티 객체를 가져옴
        *   .orElseThrow() -> 없으면 throw 시켜서 일단 테스트가 끝나게 하자
        *  */
        Article article = articleRepository.findById(1L).orElseThrow();

        // 2) 지우면 DB에서 하나 사라지기 때문에 count 를 구해놓고
        long prevArticleCount = articleRepository.count();
        long prevArticleCommentCount = articleCommentRepository.count(); // 전체 댓글의 카운트
        int deletedCommentSize = article.getArticleComments().size();

        // 3) delete 하고 (전체 게시글 개수 -1 됨)
        articleRepository.delete(article);

        // 테스트 통과 조건 - 2에서 구한거랑 여기서 구하는 개수가 1 차이나는 경우
        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleCommentCount - deletedCommentSize);
    }
}










