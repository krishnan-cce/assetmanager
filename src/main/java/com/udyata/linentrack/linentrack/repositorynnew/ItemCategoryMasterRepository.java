package com.udyata.linentrack.linentrack.repositorynnew;

import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ItemCategoryMasterRepository extends JpaRepository<ItemCategoryMaster,Long> {

    @Query(value = "CALL GetItemCategoryMasterList(:itemTypeId);", nativeQuery = true)
    List<Map<String, Object>> getItemCategoryMaster(
            @Param("itemTypeId") Integer itemTypeId
    );



}
