package kr.inhatc.spring.crawling.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class Notice {
    private String subject;
    private String date;
}
