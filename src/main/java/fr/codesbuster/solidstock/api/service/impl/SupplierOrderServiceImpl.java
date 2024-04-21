package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderRowEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderStatus;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.SupplierOrderDto;
import fr.codesbuster.solidstock.api.payload.dto.SupplierOrderRowDto;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import fr.codesbuster.solidstock.api.repository.SupplierOrderRepository;
import fr.codesbuster.solidstock.api.repository.SupplierOrderRowRepository;
import fr.codesbuster.solidstock.api.repository.SupplierRepository;
import fr.codesbuster.solidstock.api.service.SupplierOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierOrderRepository supplierOrderRepository;
    @Autowired
    private SupplierOrderRowRepository supplierOrderRowRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public SupplierOrderEntity createSupplierOrder(SupplierOrderDto supplierOrderDto) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SupplierOrderEntity supplierOrderEntity = new SupplierOrderEntity();
        supplierOrderEntity.setOrderNumber(supplierOrderDto.getOrderNumber());
        supplierOrderEntity.setOrderDate(sdf.parse(supplierOrderDto.getOrderDate()));
        supplierOrderEntity.setDeliveryDate(sdf.parse(supplierOrderDto.getDeliveryDate()));
        supplierOrderEntity.setStatus(SupplierOrderStatus.valueOf(supplierOrderDto.getStatus()));
        supplierOrderEntity.setNote(supplierOrderDto.getNote());
        supplierOrderEntity.setSupplier(supplierRepository.findById(supplierOrderDto.getSupplierId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Supplier not found")));

        supplierOrderEntity = supplierOrderRepository.save(supplierOrderEntity);
        return supplierOrderEntity;
    }

    @Override
    public SupplierOrderEntity getSupplierOrder(long id) {
        return supplierOrderRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "SupplierOrder not found"));
    }

    @Override
    public List<SupplierOrderEntity> getAllSupplierOrders() {
        return supplierOrderRepository.findAll();
    }

    @Override
    public void deleteSupplierOrder(long id) {
        supplierOrderRepository.deleteById(id);
    }

    @Override
    public SupplierOrderEntity updateSupplierOrder(long id, SupplierOrderDto supplierOrderDto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SupplierOrderEntity supplierOrderEntity = supplierOrderRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Invoice not found"));
        supplierOrderEntity.setOrderNumber(supplierOrderDto.getOrderNumber() != null ? supplierOrderDto.getOrderNumber() : supplierOrderEntity.getOrderNumber());
        supplierOrderEntity.setOrderDate(supplierOrderDto.getOrderDate() != null ? sdf.parse(supplierOrderDto.getOrderDate()) : supplierOrderEntity.getOrderDate());
        supplierOrderEntity.setDeliveryDate(supplierOrderDto.getDeliveryDate() != null ? sdf.parse(supplierOrderDto.getDeliveryDate()) : supplierOrderEntity.getDeliveryDate());
        supplierOrderEntity.setStatus(supplierOrderDto.getStatus() != null ? SupplierOrderStatus.valueOf(supplierOrderDto.getStatus()) : supplierOrderEntity.getStatus());
        supplierOrderEntity.setNote(supplierOrderDto.getNote() != null ? supplierOrderDto.getNote() : supplierOrderEntity.getNote());
        supplierOrderEntity.setSupplier(supplierOrderDto.getSupplierId() != 0 ? supplierRepository.findById(supplierOrderDto.getSupplierId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Supplier not found")) : supplierOrderEntity.getSupplier());

        supplierOrderEntity = supplierOrderRepository.save(supplierOrderEntity);
        return supplierOrderEntity;
    }

    @Override
    public void addRow(long id, SupplierOrderRowDto supplierOrderDto) {
        SupplierOrderRowEntity supplierOrderRowEntity = new SupplierOrderRowEntity();
        supplierOrderRowEntity.setQuantity(supplierOrderDto.getQuantity());
        supplierOrderRowEntity.setBuyPrice(supplierOrderDto.getBuyPrice());
        supplierOrderRowEntity.setProduct(productRepository.findById(supplierOrderDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        SupplierOrderEntity supplierOrderEntity = supplierOrderRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "SupplierOrder not found"));
        supplierOrderRowEntity.setSupplierOrder(supplierOrderEntity);

        supplierOrderRowEntity = supplierOrderRowRepository.save(supplierOrderRowEntity);

    }

    @Override
    public SupplierOrderRowEntity getRow(long id) {
        return supplierOrderRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "SupplierOrderRow not found"));
    }

    @Override
    public List<SupplierOrderRowEntity> getAllRows(long invoiceId) {
        return supplierOrderRowRepository.findBySupplierOrder_Id(invoiceId);
    }

    @Override
    public void deleteRow(long id) {
        supplierOrderRowRepository.deleteById(id);
    }

    @Override
    public SupplierOrderRowEntity updateRow(long id, SupplierOrderRowDto supplierOrderDto) {
        SupplierOrderRowEntity supplierOrderRowEntity = supplierOrderRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "InvoiceRow not found"));
        supplierOrderRowEntity.setQuantity(supplierOrderDto.getQuantity() != 0 ? supplierOrderDto.getQuantity() : supplierOrderRowEntity.getQuantity());
        supplierOrderRowEntity.setBuyPrice(supplierOrderDto.getBuyPrice() != 0 ? supplierOrderDto.getBuyPrice() : supplierOrderRowEntity.getBuyPrice());
        supplierOrderRowEntity.setProduct(supplierOrderDto.getProductId() != 0 ? productRepository.findById(supplierOrderDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")) : supplierOrderRowEntity.getProduct());
        supplierOrderRowEntity = supplierOrderRowRepository.save(supplierOrderRowEntity);
        return supplierOrderRowEntity;
    }

}
