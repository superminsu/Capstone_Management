package kr.inhatc.spring.capstone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Capstone extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capstone_id")
    private Long id;
    
    private String title;
    
    private String content;
    
    private int readCnt;
    
    private String registerId;
    
    @Builder
    public Capstone(Long id, String title, String content, int readCnt, String registerId) {
        this.id = id; 
        this.title = title;
        this.content = content;
        this.readCnt = readCnt;
        this.registerId = registerId;
    }
}
