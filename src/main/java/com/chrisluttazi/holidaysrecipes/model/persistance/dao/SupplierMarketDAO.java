package com.chrisluttazi.holidaysrecipes.model.persistance.dao;

import com.chrisluttazi.holidaysrecipes.model.SupplierMarket;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SupplierMarketDAO extends PagingAndSortingRepository<SupplierMarket, Long> {

    public SupplierMarket findBySupplierMarketId(Long Id);

    @SuppressWarnings("unchecked")
    public SupplierMarket save(SupplierMarket supplierMarket);

    public long count();

    List<SupplierMarket> findAll();

}