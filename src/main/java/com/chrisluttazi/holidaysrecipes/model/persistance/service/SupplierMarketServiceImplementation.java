package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.SupplierMarket;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.SupplierMarketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierMarketServiceImplementation implements SupplierMarketService {

    @Autowired
    private SupplierMarketDAO supplierMarketDAO;

    @Override
    public SupplierMarket findBySupplierMarketId(Long id) {
        return supplierMarketDAO.findBySupplierMarketId(id);
    }

    @Override
    public SupplierMarket create(SupplierMarket supplierMarket) {

        if (supplierMarketDAO.findBySupplierMarketId(supplierMarket.getSupplierMarketId()) != null) {
            return null;
        }
        return supplierMarketDAO.save(supplierMarket);
    }

    @Override
    public SupplierMarket update(SupplierMarket supplierMarket) {

        if (supplierMarketDAO.findBySupplierMarketId(supplierMarket.getSupplierMarketId()) != null) {
            return supplierMarketDAO.save(supplierMarket);
        }
        return null;
    }

    @Override
    public List<SupplierMarket> findAll() {
        return supplierMarketDAO.findAll();
    }

}
