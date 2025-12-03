package com.botofholding.api.Domain.Entity;

import com.botofholding.api.Domain.Entity.Auditing.AuditableEntity;
import com.botofholding.api.Domain.Entity.Auditing.CreatableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Theme extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "THEME_ID")
    private Long themeId;

    @EqualsAndHashCode.Include
    @Column(name = "THEME_NME", length = 75)
    private String themeName;

    @Column(name = "THEME_DESC", length = 350)
    private String themeDescription;
}
