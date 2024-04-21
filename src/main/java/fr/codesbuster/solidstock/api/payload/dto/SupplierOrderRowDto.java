package fr.codesbuster.solidstock.api.payload.dto;

import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderRowDto {

    @Id
    private long id;
    private double quantity;
    private double buyPrice;
    private String note;
    private String status;
    private long productId;
}
