package com.bitstudy.app.repository;

import com.bitstudy.app.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/*
* TDD를 위해서 임시로 만들어놓은 저장소 (이거로 DB에 접근할거임)
*
* */
public interface Ex04_ArticleRepository_기본테스트용 extends JpaRepository<Article, Long> {

}
