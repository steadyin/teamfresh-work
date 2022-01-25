package work.teamfresh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import work.teamfresh.domain.Voc;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VocRepository {

    private final EntityManager em;

    public void save(Voc voc) {
        em.persist(voc);
    }

    public Optional<Voc> findOne(Long id) {
        return Optional.ofNullable(em.find(Voc.class, id));
    }

    public List<Voc> findAllVocAndCompensationAndPenalty() {
        return em.createQuery("select v from Voc v left outer join fetch v.compensation left outer join fetch v.penalty join fetch v.driver join fetch v.vendor", Voc.class)
                .getResultList();
    }
}

