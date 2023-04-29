package swcapstone.batch.crawling;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CrawlingGitCommit {
    /**
     * 조회할 URL셋팅 및 Document 객체 로드하기
     */
    private static String url = "https://github.com/";

    public Long process(String nickname) {
        String tempUrl = url + nickname;

        Connection conn = Jsoup.connect(tempUrl);
        //Jsoup 커넥션 생성

        try {
            Document document = conn.get();
            Long commit = getCommitList(document);

            //log.info(nickname + " : " + commit);

            return commit;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    public static Long getCommitList(Document document) {
        String date = LocalDate.now().minusDays(1).toString();

        Elements commitTableBody = document.select("svg.js-calendar-graph-svg g");
        String text="-1";

        for(Element g : commitTableBody){
            if(g.attr("transform").equals("translate(832, 0)")){
                for(Element rect : g.select("rect")){
                    if(rect.attr("data-date").equals(date)){
                        text = rect.text().substring(0,1); //
                    }
                }
            }
        }

        if(text.equals("N")){
            return 0L;
        }

        return Long.parseLong(text);
    }
}
