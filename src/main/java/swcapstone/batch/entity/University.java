package swcapstone.batch.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static swcapstone.batch.entity.Status.ALIVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class University extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "university_id")
    private Long id;

    @Column(name = "university_name")
    private String name;

    @OneToMany(mappedBy = "university")
    private List<Member> members = new ArrayList<Member>();

    private Long totalGitCount;

    private int rank;
    @Enumerated(EnumType.STRING)
    private Status status;

    public University(String name){
        this.name = name;
        totalGitCount = 0L;
        rank = 0;
        status = ALIVE;
    }

    public void setTotalGitCount(Long totalGitCount) {
        this.totalGitCount = 0L;
        this.totalGitCount = totalGitCount;
    }

    public void setRank(int rank){
        this.rank = rank;
    }
}
