package kr.inhatc.spring.main.controller;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.inhatc.spring.crawling.service.CrawlingService;
import kr.inhatc.spring.crawling.service.Notice;

@Controller
public class MainController {
    
    @Autowired
    CrawlingService cs;

    @GetMapping("/")
    public String crawling(Model model) throws Exception{
        
        String URL = "https://weather.naver.com/today";
        Document doc = Jsoup.connect(URL).get();
        Elements els1 = doc.select(".current");
        Elements els2 = doc.select(".summary .weather");
        
        String tels = els1.text();
        tels = tels.replace("현재 온도", " ");
        model.addAttribute("weather", els2.text());
        model.addAttribute("temperature", tels);
        
        String we = els2.text();
        String weatherImg = "img/weather_anoter.png";
        
        if(we.equals("맑음")) {
            weatherImg = "img/weather_sunny.png";
        }else if(we.equals("구름조금")) {
            weatherImg = "img/weather_cloudy.png";
        }else if(we.equals("구름많음")) {
            weatherImg = "img/weather_cloudy.png";
        }else if(we.equals("흐림")) {
            weatherImg = "img/weather_cloudy.png";
        }else if(we.equals("약한비")) {
            weatherImg = "img/weather_rainy.png";
        }else if(we.equals("비")) {
            weatherImg = "img/weather_rainy.png";
        }else if(we.equals("강한비")) {
            weatherImg = "img/weather_shower.png";
        }else if(we.equals("약한눈")) {
            weatherImg = "img/weather_snow.png";
        }else if(we.equals("강한눈")) {
            weatherImg = "img/weather_snow.png";
        }else if(we.equals("진눈깨비")) {
            weatherImg = "img/weather_snow.png";
        }else if(we.equals("소나기")) {
            weatherImg = "img/weather_shower.png";
        }else if(we.equals("소낙눈")) {
            weatherImg = "img/weather_snow.png";
        }else if(we.equals("안개")) {
            weatherImg = "img/weather_fog.png";
        }else{
            weatherImg = "img/weather_anoter.png";
        }
        model.addAttribute("wimg", weatherImg);
        
        List<Notice> noticeList = cs.getnotice();
        model.addAttribute("noticestat", noticeList);
        model.addAttribute("link1", "https://www.inhatc.ac.kr/kr/461/subview.do");
        
        List<Notice> noticeList_e = cs.getnotice_e();
        model.addAttribute("noticeEvent", noticeList_e);
        model.addAttribute("link2", "https://www.inhatc.ac.kr/kr/464/subview.do");
        
        List<Notice> noticeList_s = cs.getnotice_s();
        model.addAttribute("noticeScholarship", noticeList_s);
        model.addAttribute("link3", "https://www.inhatc.ac.kr/kr/463/subview.do");
        
        List<Notice> noticeList_em = cs.getnotice_em();
        model.addAttribute("noticeEmployment", noticeList_em);
        model.addAttribute("link4", "https://www.inhatc.ac.kr/kr/465/subview.do");
        
        List<Notice> noticeList_n = cs.getnotice_n();
        model.addAttribute("noticeNormal", noticeList_n);
        model.addAttribute("link5", "https://www.inhatc.ac.kr/kr/466/subview.do");
        
        return "/index";
    }
}
