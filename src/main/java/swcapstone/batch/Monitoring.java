package swcapstone.batch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
//import java.lang.management.OperatingSystemMXBean;

@Slf4j
@Component
@Getter
public class Monitoring {

    private double cpuUsage;
    private double memoryUsage;
    private int count;
    public static boolean stop;

    public Monitoring(){
        cpuUsage = 0d;
        memoryUsage = 0d;
        count = 0;
        stop = false;
    }

    public void clear(){
        cpuUsage = 0d;
        memoryUsage = 0d;
        count = 0;
        stop = false;
    }

    @Async("taskExecutor")
    public void monitoringInfos(){

        log.info("============== monitoringInfos() 실행 ===================");
        try{
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

            while(!stop) {
                log.info("============= 현재 CPU, Memory 사용량 ==============");
                double currentCpu = osBean.getSystemCpuLoad() * 100;
                log.info("CPU Usage : " +
                        String.format("%.2f", currentCpu));

                if(!Double.isNaN(currentCpu)) {
                    cpuUsage += (double) currentCpu;
                }

                double usingPhysicalMemorySize =
                        (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024
                        - (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024;
                log.info("Physical Memory Usage : " +
                        String.format("%.2f", usingPhysicalMemorySize));
                memoryUsage += usingPhysicalMemorySize;

                count += 1;
                Thread.sleep(200);
                }
            } catch(Exception e){
            e.printStackTrace();

        }
    }
}
