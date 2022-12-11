package kr.inhatc.spring.capstone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CapstoneFileRequestDto {
    private Long id;
    
    private Long[] idArr;
    
    private String fileId;
}
