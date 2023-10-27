package equinton.dev.kata_horse.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Race {

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    private String name;

    @Column
    private Integer number;

    @Column
    private LocalDate onDate;

    @OneToMany(
            mappedBy = "race",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @Builder.Default
    private Set<Runner> runners = new HashSet<>();

    public void addRunner(Runner runner) {
        runners.add(runner);
        runner.setRace(this);
    }

    public void addRunners(Set<Runner> runners) {
     runners.forEach(this::addRunner);
    }
}
