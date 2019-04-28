package pl.coderslab.Cracow_Scrooge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Cracow_Scrooge2.entity.User;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
