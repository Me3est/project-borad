package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),

})
@ToString
@Getter
@Entity
public class Ex01_1_Article_엔티티로_변경 {

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



    protected Ex01_1_Article_엔티티로_변경() {}

    private Ex01_1_Article_엔티티로_변경(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Ex01_1_Article_엔티티로_변경 of(String title, String content, String hashtag) {
        return new Ex01_1_Article_엔티티로_변경(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex01_1_Article_엔티티로_변경 article = (Ex01_1_Article_엔티티로_변경) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


