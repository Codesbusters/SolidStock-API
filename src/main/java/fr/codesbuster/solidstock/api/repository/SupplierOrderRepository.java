package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierOrderRepository extends JpaRepository<SupplierOrderEntity, Long> {
}
