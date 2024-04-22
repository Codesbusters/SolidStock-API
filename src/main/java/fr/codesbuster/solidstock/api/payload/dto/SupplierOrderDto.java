package fr.codesbuster.solidstock.api.payload.dto;

import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderDto {
    private long id;
    private String orderNumber;
    private String orderDate;
    private String deliveryDate;
    private String status;
    private String note;
    private long supplierId;
}
