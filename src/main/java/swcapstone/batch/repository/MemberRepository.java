package swcapstone.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import swcapstone.batch.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @Modifying
    @Transactional
    @Query("delete from Member m where m.status = swcapstone.batch.entity.Status.WITHDRAW")
    public int deleteMember(List<Member> members);

}
