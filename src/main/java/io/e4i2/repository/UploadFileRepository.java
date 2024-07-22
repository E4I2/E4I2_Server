package io.e4i2.repository;


import io.e4i2.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
    
    
    @Modifying
    @Query("delete from UploadFile uf where uf.fileUrl in :fileUrl")
    int deleteByFileUrlIn(List<String> fileUrl);
}
