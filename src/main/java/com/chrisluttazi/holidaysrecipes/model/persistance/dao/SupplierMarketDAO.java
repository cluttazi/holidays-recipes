package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.SupplierMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface SupplierMarketDAO extends JpaRepository<SupplierMarket, Long> {

    public SupplierMarket findBySupplierMarketId(Long Id);

    @SuppressWarnings("unchecked")
    public SupplierMarket save(SupplierMarket supplierMarket);

    public long count();

    List<SupplierMarket> findAll();

}