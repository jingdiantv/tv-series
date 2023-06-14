package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity,String> {
}
