package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.DetailBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DetailBillRepository extends JpaRepository<DetailBill, String> {

    @Query("SELECT db FROM DetailBill db WHERE db.bill.id = :billId")
    List<DetailBill> findBybillId(String billId);

    @Query("SELECT COALESCE(SUM(db.total), 0) FROM DetailBill db WHERE db.bill.id = :billId")
    BigDecimal sumTotalByBillId(@Param("billId") String billId);
}
