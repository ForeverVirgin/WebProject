package repository;

import model.Confectionery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfectioneryRepository extends JpaRepository<Confectionery, Integer> {
    public Confectionery findByIdLike(Integer id);
}
