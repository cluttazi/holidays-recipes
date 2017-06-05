package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.SupplierMarket;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SupplierMarketService {
    public SupplierMarket findBySupplierMarketId(Long id);

    @PreAuthorize("hasAuthority('SUPPLIER')")
    public SupplierMarket update(SupplierMarket supplier);

    @PreAuthorize("hasAuthority('SUPPLIER')")
    public SupplierMarket create(SupplierMarket supplier);

    public List<SupplierMarket> findAll();

}
