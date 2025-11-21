package com.botofholding.api.Domain.Entity;

import com.botofholding.api.Domain.Entity.Auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "ITEM")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Item extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    @EqualsAndHashCode.Include
    private Long itemId;

    // [BEST PRACTICE] Explicitly define the column length.
    // This serves as documentation and is crucial for schema validation.
    @Column(name = "ITEM_NME", length = 75)
    @EqualsAndHashCode.Include
    private String itemName;

    @Column(name = "ITEM_DESC")
    @Lob // As discussed, @Lob is best for very long or unknown-length text.
    private String itemDescription;

    @Column(name = "WEIGHT")
    private Float weight;

    @Column(name = "WEIGHT_UNIT", length = 25)
    private String weightUnit;

    @Column(name = "VALUE")
    private Float value;

    @Column(name = "VALUE_UNIT", length = 25)
    private String valueUnit;

    @Column(name = "IS_PARENT")
    private boolean parent;

    @Override
    public String toString() {
        return "Item: " + itemName + " Description: " + itemDescription;
    }

    
}
