package br.com.gubee.interview.entities;

import br.com.gubee.interview.enums.RaceEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "hero")
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HeroEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "race", nullable = false)
    private RaceEnum race;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "power_stats_id", referencedColumnName = "id")
    private PowerStatsEntity powerStats;

    @Column(name = "enabled", nullable = false)
    private boolean isEnabled = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    private void prePersist() {
        this.isEnabled = true;
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
