package kr.inhatc.spring.capstone.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.capstone.dto.CapstoneRequestDto;
import kr.inhatc.spring.capstone.dto.CapstoneResponseDto;
import kr.inhatc.spring.capstone.entity.Capstone;
import kr.inhatc.spring.capstone.repository.CapstoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CapstoneService {

    private final CapstoneRepository capstoneRepository;
    private final CapstoneFileService capstoneFileService;
    
    @Transactional
    public boolean save(CapstoneRequestDto capstoneRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
        
        Capstone result = capstoneRepository.save(capstoneRequestDto.toEntity());
        
        boolean resultFlag = false;
        
        if (result != null) {
            capstoneFileService.uploadFile(multiRequest, result.getId());
            resultFlag = true;
        }
        
        return resultFlag;
    }
    
    /*
        트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드를 MANUAL로 설정한다.
        이렇게 하면 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다.
        따라서 트랜잭션을 커밋하더라도 영속성 컨텍스트가 플러시 되지 않아서 엔티티의 등록, 수정, 삭제이 동작하지 않고,
        또한 읽기 전용으로, 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향상된다.
     */
    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {
        
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        
        Page<Capstone> list = capstoneRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")));
        
        resultMap.put("list", list.stream().map(CapstoneResponseDto::new).collect(Collectors.toList()));
        resultMap.put("paging", list.getPageable());
        resultMap.put("totalCnt", list.getTotalElements());
        resultMap.put("totalPage", list.getTotalPages());
        
        return resultMap;
    }
    
    public HashMap<String, Object> findById(Long id) throws Exception {
        
        HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
        
        capstoneRepository.updateCapstoneReadCntInc(id);
        
        CapstoneResponseDto info = new CapstoneResponseDto(capstoneRepository.findById(id).get());
        
        resultMap.put("info", info);
        resultMap.put("fileList", capstoneFileService.findByBoardId(info.getId()));
        
        return resultMap;
    }
    
    public boolean updateCapstone(CapstoneRequestDto capstoneRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
        
        int result = capstoneRepository.updateCapstone(capstoneRequestDto);
        
        boolean resultFlag = false;
        
        if (result > 0) {
            capstoneFileService.uploadFile(multiRequest, capstoneRequestDto.getId());
            resultFlag = true;
        }
        
        return resultFlag;
    }
    
    public void deleteById(Long id) throws Exception {
        Long[] idArr = {id};
        capstoneFileService.deleteCapstoneFileYn(idArr);
        capstoneRepository.deleteById(id);
    }
    
    public void deleteAll(Long[] deleteIdList) throws Exception {
        capstoneFileService.deleteCapstoneFileYn(deleteIdList);
        capstoneRepository.deleteCapstone(deleteIdList);
    }
}
