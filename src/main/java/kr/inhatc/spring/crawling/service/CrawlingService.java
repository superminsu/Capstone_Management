package kr.inhatc.spring.crawling.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class CrawlingService {

    @PostConstruct
    public List<Notice> getnotice() throws IOException{
        String noticeURL = "https://www.inhatc.ac.kr/kr/461/subview.do";
        Document noticeDoc = Jsoup.connect(noticeURL).get();
        List<Notice> noticeList = new ArrayList<>();
        Elements contents = noticeDoc.select("table tbody tr");
        for(Element content : contents) {
            Notice notice = Notice.builder()
                    .subject(content.select("strong").text())
                    .date(content.select(".td-date").text())
                    .build();
            noticeList.add(notice);
        }
        return noticeList;
    }
    
    @PostConstruct
    public List<Notice> getnotice_e() throws IOException{
        String noticeURL = "https://www.inhatc.ac.kr/kr/464/subview.do";
        Document noticeDoc = Jsoup.connect(noticeURL).get();
        List<Notice> noticeList_e = new ArrayList<>();
        Elements contents = noticeDoc.select("table tbody tr");
        for(Element content : contents) {
            Notice notice = Notice.builder()
                    .subject(content.select("strong").text())
                    .date(content.select(".td-date").text())
                    .build();
            noticeList_e.add(notice);
        }
        return noticeList_e;
    }
    
    @PostConstruct
    public List<Notice> getnotice_s() throws IOException{
        String noticeURL = "https://www.inhatc.ac.kr/kr/463/subview.do";
        Document noticeDoc = Jsoup.connect(noticeURL).get();
        List<Notice> noticeList_s = new ArrayList<>();
        Elements contents = noticeDoc.select("table tbody tr");
        for(Element content : contents) {
            Notice notice = Notice.builder()
                    .subject(content.select("strong").text())
                    .date(content.select(".td-date").text())
                    .build();
            noticeList_s.add(notice);
        }
        return noticeList_s;
    }
    
    @PostConstruct
    public List<Notice> getnotice_em() throws IOException{
        String noticeURL = "https://www.inhatc.ac.kr/kr/465/subview.do";
        Document noticeDoc = Jsoup.connect(noticeURL).get();
        List<Notice> noticeList_em = new ArrayList<>();
        Elements contents = noticeDoc.select("table tbody tr");
        for(Element content : contents) {
            Notice notice = Notice.builder()
                    .subject(content.select("strong").text())
                    .date(content.select(".td-date").text())
                    .build();
            noticeList_em.add(notice);
        }
        return noticeList_em;
    }
    
    @PostConstruct
    public List<Notice> getnotice_n() throws IOException{
        String noticeURL = "https://www.inhatc.ac.kr/kr/466/subview.do";
        Document noticeDoc = Jsoup.connect(noticeURL).get();
        List<Notice> noticeList_n = new ArrayList<>();
        Elements contents = noticeDoc.select("table tbody tr");
        for(Element content : contents) {
            Notice notice = Notice.builder()
                    .subject(content.select("strong").text())
                    .date(content.select(".td-date").text())
                    .build();
            noticeList_n.add(notice);
        }
        return noticeList_n;
    }
}
