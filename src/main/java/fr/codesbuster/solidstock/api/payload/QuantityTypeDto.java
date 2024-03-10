package fr.codesbuster.solidstock.api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityTypeDto {
    private String name;
    private String description;
    private String unit;
}