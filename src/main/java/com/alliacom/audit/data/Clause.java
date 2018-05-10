package com.alliacom.audit.data;

import com.alliacom.audit.repository.ExigenceRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CLAUSE")
@EntityListeners(AuditingEntityListener.class)
public class Clause implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
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
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "norme_id")
    private Norme norme;

    @OneToMany(mappedBy = "clause", orphanRemoval=true)
    @JsonIgnore
    private List<Exigence> exigences;

    public void delete(ExigenceRepository exigenceRepository) {
        for(Exigence exigence : exigences) {
            exigence.delete(exigenceRepository);
        }
        exigences.clear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
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

    public Norme getNorme() {
        return norme;
    }

    public void setNorme(Norme norme) {
        this.norme = norme;
    }

    public List<Exigence> getExigences() {
        return exigences;
    }

    public void setExigences(List<Exigence> exigences) {
        this.exigences = exigences;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
