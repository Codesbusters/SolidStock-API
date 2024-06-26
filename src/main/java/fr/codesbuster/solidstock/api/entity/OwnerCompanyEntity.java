package fr.codesbuster.solidstock.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owner_company")
public class OwnerCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nullable
    private String companyName;

    @Nullable
    private String ownerName;

    @Nullable
    private String siret;

    @Nullable
    private String siren;

    @Nullable
    private int rcs;

    @Nullable
    private String streetNumber;

    @Nullable
    private String streetName;

    @Nullable
    private String zipCode;

    @Nullable
    private String city;

    @Nullable
    private String country;

    @Nullable
    private String email;

    @Nullable
    private String phone;

    @Nullable
    private String iban;

    @Lob
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "ownerCompany")
    private List<InvoiceEntity> invoices;
}
