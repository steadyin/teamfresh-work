package work.teamfresh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import work.teamfresh.domain.Penalty;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PenaltyRepository {
    private final EntityManager em;

    public void save(Penalty penalty) {
        em.persist(penalty);
    }

    public Optional<Penalty> findOne(Long id) {
        return Optional.ofNullable(em.find(Penalty.class, id));
    }
}

