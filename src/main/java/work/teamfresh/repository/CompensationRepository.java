package work.teamfresh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import work.teamfresh.domain.Compensation;
import work.teamfresh.domain.Voc;
import work.teamfresh.domain.enumrate.VocType;
import work.teamfresh.dto.FindAllCompensateDto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompensationRepository {
    private final EntityManager em;

    public void save(Compensation compensation) {
        em.persist(compensation);
    }

    public List<FindAllCompensateDto> findAllCompensationAndVoc() {
        return em.createQuery("select new work.teamfresh.dto.FindAllCompensateDto(v.id, v.vocType, v.content, ven.name, d.name, d.company, c.id, c.amount) from Compensation c join c.voc v join v.vendor ven join v.driver d", FindAllCompensateDto.class).getResultList();
    }
}
