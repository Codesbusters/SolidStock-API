package fr.codesbuster.solidstock.api.entity.supplierOrder;

import fr.codesbuster.solidstock.api.entity.SupplierEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier_order")
public class SupplierOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String orderNumber;
    private Date orderDate;
    private Date deliveryDate;
    private SupplierOrderStatus status;
    private String note;

    @OneToMany(mappedBy = "supplierOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierOrderRowEntity> supplierOrderRows;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @Transient
    private double total;

    public void calculateTotal() {
        total = 0;
        for (SupplierOrderRowEntity supplierOrderRow : supplierOrderRows) {
            total += supplierOrderRow.getBuyPrice() * supplierOrderRow.getQuantity();
        }
    }

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
