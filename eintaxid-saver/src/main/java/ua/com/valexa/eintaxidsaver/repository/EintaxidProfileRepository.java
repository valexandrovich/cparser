package ua.com.valexa.eintaxidsaver.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.valexa.eintaxidsaver.entity.EintaxidProfile;

@Repository
public interface EintaxidProfileRepository extends JpaRepository<EintaxidProfile, Long> {

    EintaxidProfile findByIrsEin(String irsEin);
    Page<EintaxidProfile> findAll(Pageable pageable);

}
