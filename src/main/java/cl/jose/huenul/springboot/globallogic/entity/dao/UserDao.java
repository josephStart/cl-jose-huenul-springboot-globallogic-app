package cl.jose.huenul.springboot.globallogic.entity.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.jose.huenul.springboot.globallogic.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, UUID> {

	public boolean existsByEmail(String email);

	public Optional<UserEntity> findByEmail(String email);

}