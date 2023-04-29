package swcapstone.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swcapstone.batch.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
