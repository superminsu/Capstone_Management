package kr.inhatc.spring.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.capstone.dto.CapstoneRequestDto;
import kr.inhatc.spring.capstone.entity.Capstone;

public interface CapstoneRepository extends JpaRepository<Capstone, Long>{

    static final String UPDATE_CAPSTONE = "UPDATE Capstone "
            + "SET TITLE = :#{#capstoneRequestDto.title}, "
            + "CONTENT = :#{#capstoneRequestDto.content}, "
            + "REGISTER_ID = :#{#capstoneRequestDto.registerId}, "
            + "UPDATE_TIME = NOW() "
            + "WHERE ID = :#{#capstoneRequestDto.id}";
    
    static final String UPDATE_CAPSTONE_READ_CNT_INC = "UPDATE Capstone "
            + "SET READ_CNT = READ_CNT + 1 "
            + "WHERE ID = :id";
    
    static final String DELETE_CAPSTONE = "DELETE FROM Capstone "
            + "WHERE ID IN (:deleteIdList)";
    
    @Transactional
    @Modifying
    @Query(value = UPDATE_CAPSTONE, nativeQuery = true)
    public int updateCapstone(@Param("capstoneRequestDto") CapstoneRequestDto capstoneRequestDto);
    
    @Transactional
    @Modifying
    @Query(value = UPDATE_CAPSTONE_READ_CNT_INC, nativeQuery = true)
    public int updateCapstoneReadCntInc(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query(value = DELETE_CAPSTONE, nativeQuery = true)
    public int deleteCapstone(@Param("deleteIdList") Long[] deleteIdList);
}
