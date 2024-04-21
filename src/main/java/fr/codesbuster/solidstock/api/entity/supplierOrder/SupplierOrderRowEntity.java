package fr.codesbuster.solidstock.api.entity.supplierOrder;

import fr.codesbuster.solidstock.api.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier_order_row")
public class SupplierOrderRowEntity {

    @Id
    private long id;
    private double quantity;
    private double buyPrice;
    private String note;
    private SupplierOrderStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "supplier_order_id")
    private SupplierOrderEntity supplierOrder;
}
