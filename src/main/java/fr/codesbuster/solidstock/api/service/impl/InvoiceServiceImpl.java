package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.StockMovementEntity;
import fr.codesbuster.solidstock.api.entity.StockMovementType;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceRowDto;
import fr.codesbuster.solidstock.api.repository.*;
import fr.codesbuster.solidstock.api.service.InvoicePDFService;
import fr.codesbuster.solidstock.api.service.InvoiceService;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceRowRepository invoiceRowRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoicePDFService invoicePDFService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OwnerCompanyService ownerCompanyService;
    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Override
    public InvoiceEntity createInvoice(InvoiceDto invoiceDto) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setName(invoiceDto.getName());
        invoiceEntity.setDescription(invoiceDto.getDescription());
        CustomerEntity customerEntity = customerRepository.findById(invoiceDto.getCustomerId()).orElse(null);
        if (customerEntity == null) {
            throw new APIException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        invoiceEntity.setCustomer(customerEntity);

        invoiceEntity = invoiceRepository.save(invoiceEntity);
        return invoiceEntity;
    }

    @Override
    public InvoiceEntity getInvoice(long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Invoice not found"));
    }

    @Override
    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public void deleteInvoice(long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public InvoiceEntity updateInvoice(long id, InvoiceDto invoiceDto) {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Invoice not found"));
        invoiceEntity.setName(invoiceDto.getName());
        invoiceEntity.setDescription(invoiceDto.getDescription());
        CustomerEntity customerEntity = customerRepository.findById(invoiceDto.getCustomerId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Customer not found"));
        invoiceEntity.setCustomer(customerEntity);

        invoiceRepository.save(invoiceEntity);
        return invoiceEntity;
    }

    @Override
    public void addRow(long id, InvoiceRowDto invoiceRowDto) {
        InvoiceRowEntity invoiceRowEntity = new InvoiceRowEntity();
        invoiceRowEntity.setQuantity(invoiceRowDto.getQuantity());
        invoiceRowEntity.setSellPrice(invoiceRowDto.getSellPrice());
        InvoiceEntity invoiceEntity = invoiceRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Invoice not found"));
        invoiceRowEntity.setInvoice(invoiceEntity);
        invoiceRowEntity.setProduct(productRepository.findById(invoiceRowDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        invoiceRowRepository.save(invoiceRowEntity);
    }

    @Override
    public InvoiceRowEntity getRow(long id) {
        return invoiceRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "InvoiceRow not found"));
    }

    @Override
    public List<InvoiceRowEntity> getAllRows(long invoiceId) {
        return invoiceRowRepository.findByInvoice_Id(invoiceId);
    }

    @Override
    public void deleteRow(long id) {
        invoiceRowRepository.deleteById(id);
    }

    @Override
    public InvoiceRowEntity updateRow(long id, InvoiceRowDto invoiceRowDto) {
        InvoiceRowEntity invoiceRowEntity = invoiceRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "InvoiceRow not found"));
        invoiceRowEntity.setQuantity(invoiceRowDto.getQuantity());
        invoiceRowEntity.setSellPrice(invoiceRowDto.getSellPrice());
        invoiceRowEntity.setProduct(productRepository.findById(invoiceRowDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        invoiceRowRepository.save(invoiceRowEntity);
        return invoiceRowEntity;
    }

    @Override
    public File generatePDF(long id) throws IOException, ParseException {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Invoice not found"));
        invoiceEntity.setOwnerCompany(ownerCompanyService.getOwnerCompany());

        for (InvoiceRowEntity invoiceRowEntity : invoiceEntity.getInvoiceRows()) {
            StockMovementEntity stockMovementEntity = new StockMovementEntity();
            stockMovementEntity.setProduct(invoiceRowEntity.getProduct());
            stockMovementEntity.setQuantity(invoiceRowEntity.getQuantity());
            stockMovementEntity.setType(StockMovementType.OUT_SALE);
            stockMovementEntity.setBatchNumber("0");

            stockMovementRepository.save(stockMovementEntity);
        }


        return invoicePDFService.generateInvoicePDF(invoiceEntity);
    }
}
