package work.teamfresh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import work.teamfresh.domain.Driver;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverRepository {
    private final EntityManager em;

    public void save(Driver driver) {
        em.persist(driver);
    }

    public Optional<Driver> findOne(Long id) {
        return Optional.ofNullable(em.find(Driver.class, id));
    }
}
