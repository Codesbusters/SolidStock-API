package fr.codesbuster.solidstock.api.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String name;
    private String address;
    private Boolean corporation;
    private String corporateName;
    private String siret;
    private String siren;
    private String rib;
    private Integer rcsInteger;
}