package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierOrderRowRepository extends JpaRepository<SupplierOrderRowEntity, Long> {
    List<SupplierOrderRowEntity> findBySupplierOrder_Id(long id);
}
