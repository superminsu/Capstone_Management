package kr.inhatc.spring.capstone.dto;

import kr.inhatc.spring.capstone.entity.Capstone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CapstoneRequestDto {

    private Long id;
    
    private String title;
    
    private String content;
    
    private String registerId;
    
    public Capstone toEntity() {
        return Capstone.builder()
            .title(title)
            .content(content)
            .registerId(registerId)
            .build();
    }

    @Override
    public String toString() {
        return "CapstoneRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
                + "]";
    }
}
