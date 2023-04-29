package swcapstone.batch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swcapstone.batch.entity.University;
import swcapstone.batch.entity.Member;
import swcapstone.batch.repository.UniversityRepository;
import swcapstone.batch.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UniversityService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final UniversityRepository univRepository;
    @Autowired
    private final EntityManager em;

    @Transactional
    public void changeRank(){
        try {
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("========== changeRank() 실행 =============");

        List<University> universities = univRepository.findAll();

        for(University univ : universities){
            List<Member> members = univ.getMembers();

            Long count =0L;

            for (Member member : members){
                count += member.getCommitCount();
            }

            univ.setTotalGitCount(count);
        }

        em.flush();
        em.clear();

        universities = univRepository.findAll(Sort.by(Sort.Direction.DESC, "totalGitCount"));

        Long beforeTotalCount = 0L;
        int rank = 0;

        for(University university : universities){
            if (university.getTotalGitCount() != beforeTotalCount){
                beforeTotalCount = university.getTotalGitCount();
                rank += 1;
            }
            university.setRank(rank);
        }
    }
}
