package com.belvopoc.belvopoc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BelvoLink {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String belvoId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull
    private Institution institution;
}
