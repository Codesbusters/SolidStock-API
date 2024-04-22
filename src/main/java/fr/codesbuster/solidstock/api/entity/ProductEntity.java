package fr.codesbuster.solidstock.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderRowEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String barCode;
    private double buyPrice;
    private double sellPrice;
    private int minimumStockQuantity;
    private double inStock;
    private double selled;
    @JsonIgnore
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "vat_id")
    private VATEntity vat;

    @ManyToOne
    @JoinColumn(name = "quantity_type_id")
    private QuantityTypeEntity quantityType;

    @ManyToOne
    @JoinColumn(name = "product_family_id")
    private ProductFamilyEntity productFamily;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<StockMovementEntity> stockMovements;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<EstimateRowEntity> estimateRows;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<InvoiceRowEntity> invoiceRows;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderFormRowEntity> orderFormRows;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<SupplierOrderRowEntity> supplierOrderRows;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}