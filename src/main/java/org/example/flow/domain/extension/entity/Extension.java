package org.example.flow.domain.extension.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Extension {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "is_on")
    private boolean isOn;

    @Column(nullable = false, name = "is_custom")
    private boolean isCustom;

    public void toggleOn(boolean isOn) {
        this.isOn = isOn;
    }

    public Extension(String extensionName) {
        this.name = extensionName;
        this.isOn = true;
        this.isCustom = true;
    }

}
