package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.AutoUser;


@Repository
public interface AutoUserRepository extends JpaRepository<AutoUser, Integer> {

    public AutoUser findByUsername(String username);
}
