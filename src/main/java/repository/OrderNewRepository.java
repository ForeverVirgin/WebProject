package repository;

import model.AutoUser;
import model.OrderNew;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderNewRepository extends JpaRepository<OrderNew, Integer> {
    List<OrderNew> findByUserLike(AutoUser user);
}
