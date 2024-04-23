package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.StockMovementEntity;
import fr.codesbuster.solidstock.api.entity.StockMovementType;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderRowEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderStatus;
import fr.codesbuster.solidstock.api.payload.dto.SupplierOrderDto;
import fr.codesbuster.solidstock.api.payload.dto.SupplierOrderRowDto;
import fr.codesbuster.solidstock.api.repository.StockMovementRepository;
import fr.codesbuster.solidstock.api.repository.SupplierOrderRepository;
import fr.codesbuster.solidstock.api.repository.SupplierOrderRowRepository;
import fr.codesbuster.solidstock.api.service.SupplierOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/supplier-order")
public class SupplierOrderController {

    @Autowired
    private SupplierOrderService supplierOrderService;
    @Autowired
    private SupplierOrderRepository supplierOrderRepository;
    @Autowired
    private SupplierOrderRowRepository supplierOrderRowRepository;
    @Autowired
    private StockMovementRepository stockMovementRepository;

    @PostMapping("/add")
    public void createSupplierOrder(@RequestBody SupplierOrderDto supplierOrderDto) throws ParseException {
        supplierOrderService.createSupplierOrder(supplierOrderDto);
    }

    @GetMapping("/{id}")
    public SupplierOrderEntity getSupplierOrder(@PathVariable long id) {
        SupplierOrderEntity supplierOrder = supplierOrderService.getSupplierOrder(id);
        supplierOrder.calculateTotal();
        return supplierOrder;
    }

    @GetMapping("/all")
    public List<SupplierOrderEntity> getAllSupplierOrders() {
        List<SupplierOrderEntity> supplierOrders = supplierOrderService.getAllSupplierOrders();
        supplierOrders.forEach(SupplierOrderEntity::calculateTotal);
        Collections.reverse(supplierOrders);
        return supplierOrders;
    }

    @DeleteMapping("/{id}")
    public void deleteSupplierOrder(@PathVariable long id) {
        supplierOrderService.deleteSupplierOrder(id);
    }

    @PutMapping("/{id}")
    public void updateSupplierOrder(@PathVariable long id, @RequestBody SupplierOrderDto supplierOrderDto) throws ParseException {
        supplierOrderService.updateSupplierOrder(id, supplierOrderDto);
    }

    @PostMapping("/{id}/row/add")
    public void addRow(@PathVariable long id, @RequestBody SupplierOrderRowDto supplierOrderRowDto) {
        supplierOrderService.addRow(id, supplierOrderRowDto);
    }

    @GetMapping("/{id}/row/{rowId}")
    public SupplierOrderRowEntity getRow(@PathVariable long id, @PathVariable long rowId) {
        return supplierOrderService.getRow(rowId);
    }

    @GetMapping("/{id}/row/all")
    public List<SupplierOrderRowEntity> getAllRows(@PathVariable long id) {
        return supplierOrderService.getAllRows(id);
    }

    @DeleteMapping("/{id}/row/{rowId}")
    public void deleteRow(@PathVariable long id, @PathVariable long rowId) {
        supplierOrderService.deleteRow(rowId);
    }

    @PutMapping("/{id}/row/{rowId}")
    public void updateRow(@PathVariable long id, @PathVariable long rowId, @RequestBody SupplierOrderRowDto supplierOrderRowDto) {
        supplierOrderService.updateRow(rowId, supplierOrderRowDto);
    }

    @PutMapping("/{id}/validate")
    public void validateSupplierOrder(@PathVariable long id) {
        SupplierOrderEntity supplierOrderEntity = supplierOrderService.getSupplierOrder(id);
        supplierOrderEntity.setStatus(SupplierOrderStatus.VALIDATED);

        supplierOrderRepository.save(supplierOrderEntity);

        for (SupplierOrderRowEntity supplierOrderRowEntity : supplierOrderEntity.getSupplierOrderRows()) {
            StockMovementEntity stockMovementEntity = new StockMovementEntity();
            stockMovementEntity.setProduct(supplierOrderRowEntity.getProduct());
            stockMovementEntity.setQuantity(supplierOrderRowEntity.getQuantity());
            stockMovementEntity.setType(StockMovementType.OUT_SALE);
            stockMovementEntity.setBatchNumber("0");

            stockMovementRepository.save(stockMovementEntity);
        }


    }

}
