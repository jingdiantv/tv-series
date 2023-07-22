package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity>  findByUsername(String username);

    void deleteByUsername(String username);
}
