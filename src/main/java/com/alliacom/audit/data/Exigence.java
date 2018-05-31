package com.alliacom.audit.data;

import com.alliacom.audit.repository.ExigenceRepository;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EXIGENCE")
@EntityListeners(AuditingEntityListener.class)
public class Exigence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String reference;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String libelle;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @ManyToOne(
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "clause_id")
    private Clause clause;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "EXIGENCE_RESPONSABLE",
            joinColumns = @JoinColumn(name = "exigence_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "responsable_id", referencedColumnName = "id")
    )
    private List<Responsable> responsables;

    public void delete(ExigenceRepository exigenceRepository) {
        for(Responsable responsable : responsables) {
            exigenceRepository.supprimerExigenceResponsableRelation(id, responsable.getId());
        }
        responsables.clear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Clause getClause() {
        return clause;
    }

    public void setClause(Clause clause) {
        this.clause = clause;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }


}
