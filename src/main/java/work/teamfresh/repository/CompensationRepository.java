package work.teamfresh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import work.teamfresh.domain.Compensation;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompensationRepository {
    private final EntityManager em;

    public void save(Compensation compensation) {
        em.persist(compensation);
    }

    public List<Compensation> findAllCompensationAndVoc() {
        return em.createQuery("select c from Compensation c join fetch c.voc", Compensation.class)
                .getResultList();
    }
}

