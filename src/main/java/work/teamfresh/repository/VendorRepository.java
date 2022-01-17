package work.teamfresh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import work.teamfresh.domain.Vendor;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VendorRepository {
    private final EntityManager em;

    public void save(Vendor vendor) {
        em.persist(vendor);
    }

    public Optional<Vendor> findOne(Long id) {
        return Optional.ofNullable(em.find(Vendor.class, id));
    }
}



