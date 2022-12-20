package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),

})
@ToString
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/* @Setter 도 @Getter 처럼 클래스 단위로 걸수있는데, 그렇게 하면 모든 필드에 접근이 가능해짐.
*  그런데 id와 메타데이터들 같은 경우에는 내가 부여하는게 아니라 JPA에서 자동으로 부여해주는 번호임. 메타데이터들도 자동으로 JPA 가
*  셋팅하게 만들어야함. 그래서 id와 메타데이터는 @Setter 가 필요없음. @Setter 의 경우는 지금처럼 필요한 필드에만 주는걸 연습하자
 *  */
    
    /* @Column - 해당 칼럼이 not null 인 경우 @Column(nullable=false) 써줌.
    *  기본값은 true라서 @Column을 아예 안쓰면 null 가능
    * @Column(nullable=false, length="숫자") 숫자 안쓰면 기본값 255 적용.
    *  */
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length=10000) private String content; // 본문
    @Setter private String hashtag; // 해시태그

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude /* 이거 중요. 맨 위에 @ToString 이 있는데 마우스 올려보면 '@ToString includes~ lazy load 어쩌고' 나온다.
     이건 퍼포먼스, 메모리 저하를 일으킬 수 있어서 성능적으로 안좋은 영향을 줄 수있음. 그래서 해당 필드를 가려주세요 하는 거.
     */
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
    /* 이건 더 중요 : @ToString.Exclude 이걸 안해주면 순환잠초 이슈가 생길 수 있음.
    *  여기서 ToString이 id, title, content, hashtag 다 찍고 Set<ArticleComent> 부분을 찍으려고
    *  ArticleComent.java 파일에 가서 거기 있는 @ToString 이 원소들 다 찍으려고 하면서 우너소들 중에 private Article article;
    *  을 보는 순간 다시 Article 의 @ToString 이 동작하면서 또 모든 원소들을 찍으려하고, 그러다가 다시 Set<ArticleComent>를 보고
    *  또 ArticleComent 로 가서 toString 돌리고.... 이런식으로 동작하면서 메모리가 터질 수 있음. 그래서 Set<ArticleComent> 에
    *  @ToString.Exclude을 달아준다.
    *
    * ArticleComent에 걸지않고 Article에 걸어주는 이유는 댓글이 글을 참조하는건 정삭적인 경우인데, 반대로 글이 댓글을 참조하는건
    * 일반적인 경우는 아니기 때문에 Article에 exclude를 걸어준다.
    *  */

    

    /* jpa auditing : jpa 에서 자동으로 세팅하게 해줄때 사용하는 기능
    *                 이거 하려면 config 파일이 별도로 있어야함
    *                 config 패키지 만들으서 JpaConfig 클래스 만들자.
    * 
    *  */


    // 메타데이터
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length=100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false) private
    LocalDateTime modifiedAt; // 생성일시

    @LastModifiedBy
    @Column(nullable = false, length=100)
    private String modifiedBy; // 수정자



    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


