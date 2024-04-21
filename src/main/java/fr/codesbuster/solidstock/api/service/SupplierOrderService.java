package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderEntity;
import fr.codesbuster.solidstock.api.entity.supplierOrder.SupplierOrderRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceRowDto;
import fr.codesbuster.solidstock.api.payload.dto.SupplierOrderDto;
import fr.codesbuster.solidstock.api.payload.dto.SupplierOrderRowDto;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface SupplierOrderService {
    SupplierOrderEntity createSupplierOrder(SupplierOrderDto supplierOrderDto) throws ParseException;

    SupplierOrderEntity getSupplierOrder(long id);

    List<SupplierOrderEntity> getAllSupplierOrders();

    void deleteSupplierOrder(long id);

    SupplierOrderEntity updateSupplierOrder(long id, SupplierOrderDto supplierOrderDto) throws ParseException;

    void addRow(long id, SupplierOrderRowDto supplierOrderRowDto);

    SupplierOrderRowEntity getRow(long id);

    List<SupplierOrderRowEntity> getAllRows(long supplierOrderId);

    void deleteRow(long id);

    SupplierOrderRowEntity updateRow(long id, SupplierOrderRowDto supplierOrderRowDto);
}
