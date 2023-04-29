package swcapstone.batch.crawling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlingGitCommitTest {

    @Test
    void process() {
        CrawlingGitCommit crawling = new CrawlingGitCommit();
        crawling.process("CYY1007");
    }
}