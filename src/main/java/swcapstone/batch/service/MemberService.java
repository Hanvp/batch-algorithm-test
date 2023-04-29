package swcapstone.batch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swcapstone.batch.entity.Member;
import swcapstone.batch.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public void deleteWithdrawUser(){

        try {
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("========== deleteWithdrawUser() 실행 =============");
        List<Member> members = memberRepository.findAll();
        int effectedCount = memberRepository.deleteMember(members);
    }

    @Transactional
    public void getCommitCount(){
        try {
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("========== getCommitCount() 실행 =============");
        List<Member> members = memberRepository.findAll();
        for(Member member : members){
            member.setTodayCommitCount();
        }
    }
}
