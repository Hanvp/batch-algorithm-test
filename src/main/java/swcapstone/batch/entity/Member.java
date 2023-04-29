package swcapstone.batch.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swcapstone.batch.crawling.CrawlingGitCommit;

import javax.persistence.*;

import static swcapstone.batch.entity.Status.WITHDRAW;

@Entity
@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String gitNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @Column(name="today_commit_count")
    private Long todayCommitCount;
    @Column(name="commit_count")
    private Long commitCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Member(int i, Status status){
        username = "student"+i;

        switch (i %7){
            case 0 :
                gitNickname = "Hanvp";
                break;
            case 1:
                gitNickname = "YENA0512";
                break;
            case 2:
                gitNickname = "yahoo557";
            case 3:
                gitNickname = "jisupark123";
                break;
            case 4:
                gitNickname = "CYY1007";
                break;
            case 5:
                gitNickname = "kmkim2689";
                break;
            case 6:
                gitNickname= "Younkyum";
                break;
        }

        todayCommitCount = 0L;
        commitCount = 0L;
        this.status = status;
    }


    public void setUniversity(University university){
        this.university = university;
        university.getMembers().add(this);
    }

    //크롤링 후 커밋 수 저장하는 로직
    public void setTodayCommitCount(){
        CrawlingGitCommit crawling = new CrawlingGitCommit();
        todayCommitCount = crawling.process(this.gitNickname);
        commitCount += todayCommitCount;
    }

    //회원 탈퇴
    public void withdraw(){
        status = WITHDRAW;
    }
}
