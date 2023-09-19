package repository;

import model.AutoUser;
import model.Cart;
import model.Confectionery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Cart findByUserLike(AutoUser user);
}
