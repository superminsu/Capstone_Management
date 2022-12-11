package kr.inhatc.spring.capstone.dto;

import kr.inhatc.spring.capstone.entity.CapstoneFile;
import lombok.Getter;

@Getter
public class CapstoneFileResponseDto {

    private String origFileName;
    
    private String saveFileName;
    
    private String filePath;
    
    public CapstoneFileResponseDto(CapstoneFile entity) {
        this.origFileName = entity.getOrigFileName();
        this.saveFileName = entity.getSaveFileName();
        this.filePath = entity.getFilePath();
    }

    @Override
    public String toString() {
        return "FileMstResponseDto [origFileName=" + origFileName + ", saveFileName=" + saveFileName + ", filePath="
                + filePath + "]";
    }
}
