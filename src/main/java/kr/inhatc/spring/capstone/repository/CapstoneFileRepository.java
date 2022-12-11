package kr.inhatc.spring.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.capstone.entity.CapstoneFile;

public interface CapstoneFileRepository extends JpaRepository<CapstoneFile, Long>{

    static final String SELECT_FILE_ID = "SELECT ID FROM capstone_file " + "WHERE BOARD_ID = :boardId AND DELETE_YN != 'Y'";

    static final String UPDATE_DELETE_YN = "UPDATE capstone_file " + "SET DELETE_YN = 'Y' " + "WHERE ID IN (:deleteIdList)";

    static final String DELETE_BOARD_FILE_YN = "UPDATE capstone_file " + "SET DELETE_YN = 'Y' " + "WHERE BOARD_ID IN (:boardIdList)";


    @Query(value = SELECT_FILE_ID, nativeQuery = true)
    public List<Long> findByBoardId(@Param("boardId") Long boardId);
    
    @Transactional
    @Modifying
    @Query(value = UPDATE_DELETE_YN, nativeQuery = true)
    public int updateDeleteYn(@Param("deleteIdList") Long[] deleteIdList);
    
    @Transactional
    @Modifying
    @Query(value = DELETE_BOARD_FILE_YN, nativeQuery = true)
    public int deleteBoardFileYn(@Param("boardIdList") Long[] boardIdList);
}
