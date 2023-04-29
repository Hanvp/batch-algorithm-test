package swcapstone.batch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swcapstone.batch.Monitoring;
import swcapstone.batch.service.UniversityService;
import swcapstone.batch.service.MemberService;

import javax.persistence.EntityManager;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private final EntityManager em;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final UniversityService univService;
    @Autowired
    Monitoring monitoring = new Monitoring();

    @GetMapping("/delete")
    public void deleteUser(){

        monitoring.clear();
        monitoring.monitoringInfos();

        Long start = System.currentTimeMillis();

        memberService.deleteWithdrawUser();
        log.info("============= deleteWithdrawUser() 종료 ==============");
        Monitoring.stop = true;

        Long end = System.currentTimeMillis();
        log.info("=============== 작업량 ================");
        log.info("Duration : "+ (end - start) + "ms");
        log.info("Average CPU Usage : " + String.format("%.2f",monitoring.getCpuUsage()/ (monitoring.getCount()-1)));
        log.info("Average Physical Memory Usage : "+ String.format("%.2f",monitoring.getMemoryUsage()/monitoring.getCount()));
    }

    @GetMapping("/crawling")
    public void checkCommitCount(){

        monitoring.clear();
        monitoring.monitoringInfos();

        Long start = System.currentTimeMillis();

        memberService.getCommitCount();
        log.info("============= getCommitCount() 종료 ==============");
        Monitoring.stop = true;

        Long end = System.currentTimeMillis();
        log.info("=============== 작업량 ================");
        log.info("Duration : "+ (end - start) + "ms");
        log.info("Average CPU Usage : " + String.format("%.2f",monitoring.getCpuUsage()/ (monitoring.getCount()-1)));
        log.info("Average Physical Memory Usage : "+ String.format("%.2f",monitoring.getMemoryUsage()/monitoring.getCount()));
    }

    @GetMapping("/new-rank")
    public void changeNewRank(){

        monitoring.clear();
        monitoring.monitoringInfos();

        Long start = System.currentTimeMillis();

        univService.changeRank();
        log.info("============= changeRank() 종료 ==============");
        Monitoring.stop = true;

        Long end = System.currentTimeMillis();
        log.info("=============== 작업량 ================");
        log.info("Duration : "+ (end - start) + "ms");
        log.info("Average CPU Usage : " + String.format("%.2f",monitoring.getCpuUsage()/ (monitoring.getCount()-1)));
        log.info("Average Physical Memory Usage : "+ String.format("%.2f",monitoring.getMemoryUsage()/monitoring.getCount()));
    }
}
