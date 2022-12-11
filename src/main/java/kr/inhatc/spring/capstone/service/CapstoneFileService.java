package kr.inhatc.spring.capstone.service;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.capstone.dto.CapstoneFileResponseDto;
import kr.inhatc.spring.capstone.entity.CapstoneFile;
import kr.inhatc.spring.capstone.repository.CapstoneFileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CapstoneFileService {

    private final CapstoneFileRepository capstoneFileRepository;

    public CapstoneFileResponseDto findById(Long id) throws Exception {
        return new CapstoneFileResponseDto(capstoneFileRepository.findById(id).get());
    }

    public List<Long> findByBoardId(Long boardId) throws Exception {
        return capstoneFileRepository.findByBoardId(boardId);
    }

    public boolean uploadFile(MultipartHttpServletRequest multiRequest, Long boardId) throws Exception {

        if (boardId == null)
            throw new NullPointerException("Empty BOARD_ID.");

        // 파라미터 이름을 키로 파라미터에 해당하는 파일 정보를 값으로 하는 Map을 가져온다.
        Map<String, MultipartFile> files = multiRequest.getFileMap();

        // files.entrySet()의 요소를 읽어온다.
        Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();

        MultipartFile mFile;

        String savaFilePath = "", randomFileName = "";

        Calendar cal = Calendar.getInstance();

        List<Long> resultList = new ArrayList<Long>();

        while (itr.hasNext()) {

            Entry<String, MultipartFile> entry = itr.next();

            mFile = entry.getValue();

            int fileSize = (int) mFile.getSize();

            if (fileSize > 0) {

                String filePath = "D:\\capstone\\uploadFiles\\";

                // 파일 업로드 경로 + 현재 년월(월별관리)
                filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator
                        + String.valueOf(cal.get(Calendar.MONTH) + 1);
                randomFileName = "FILE_" + RandomStringUtils.random(8, 0, 0, false, true, null, new SecureRandom());

                String realFileName = mFile.getOriginalFilename();
                String fileExt = realFileName.substring(realFileName.lastIndexOf(".") + 1);
                String saveFileName = randomFileName + "." + fileExt;
                String saveFilePath = filePath + File.separator + saveFileName;

                File filePyhFolder = new File(filePath);

                if (!filePyhFolder.exists()) {
                    // 부모 폴더까지 포함하여 경로에 폴더를 만든다.
                    if (!filePyhFolder.mkdirs()) {
                        throw new Exception("File.mkdir() : Fail.");
                    }
                }

                File saveFile = new File(saveFilePath);

                // saveFile이 File이면 true, 아니면 false
                // 파일명이 중복일 경우 파일명(1).확장자, 파일명(2).확장자 와 같은 형태로 생성한다.
                if (saveFile.isFile()) {
                    boolean _exist = true;

                    int index = 0;

                    // 동일한 파일명이 존재하지 않을때까지 반복한다.
                    while (_exist) {
                        index++;

                        saveFileName = randomFileName + "(" + index + ")." + fileExt;

                        String dictFile = filePath + File.separator + saveFileName;

                        _exist = new File(dictFile).isFile();

                        if (!_exist) {
                            savaFilePath = dictFile;
                        }
                    }

                    mFile.transferTo(new File(savaFilePath));
                } else {
                    // 생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서드를
                    // 이용해서 업로드처리한다.
                    mFile.transferTo(saveFile);
                }

                CapstoneFile capstoneFile = CapstoneFile.builder()
                        .boardId(boardId)
                        .origFileName(realFileName)
                        .saveFileName(saveFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .filePath(filePath)
                        .deleteYn("N")
                        .build();

                resultList.add(capstoneFileRepository.save(capstoneFile).getId());
            }
        }

        return (files.size() == resultList.size()) ? true : false;
    }

    public int updateDeleteYn(Long[] deleteIdList) throws Exception {
        return capstoneFileRepository.updateDeleteYn(deleteIdList);
    }

    public int deleteCapstoneFileYn(Long[] boardIdList) throws Exception {
        return capstoneFileRepository.deleteCapstoneFileYn(boardIdList);
    }
}
