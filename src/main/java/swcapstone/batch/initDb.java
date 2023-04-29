package swcapstone.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swcapstone.batch.entity.University;
import swcapstone.batch.entity.Member;
import swcapstone.batch.repository.UniversityRepository;
import swcapstone.batch.repository.MemberRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static swcapstone.batch.entity.Status.ALIVE;
import static swcapstone.batch.entity.Status.WITHDRAW;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.setUniv();
        initService.setMember();
    }

    @Slf4j
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        private final MemberRepository memberRepository;

        private final UniversityRepository univRepository;

        public void setUniv(){
            List<University> universities = new ArrayList<University>();
            for(int i = 1; i < 31; i++){
                universities.add(new University("univ"+i));
            }
            univRepository.saveAll(universities);

        }

        public void setMember(){
            log.info("---------------- setMember() 동작 시작 ------------------");
            List<Member> members = new ArrayList<Member>();
            for(int i = 0; i < 250; i++){
                Member member = new Member(i, ALIVE);
                Long univId = i%30L +1;
                University university = univRepository.findById(univId)
                        .orElseThrow(IllegalAccessError::new);
                member.setUniversity(university);
                members.add(member);

                member = new Member(i+5000, WITHDRAW);
                member.setUniversity(university);
                members.add(member);
            }
            memberRepository.saveAll(members);

            log.info("---------------- setMember() 동작 끝 ------------------");
        }
    }
}
