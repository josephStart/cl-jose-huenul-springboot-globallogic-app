package cl.jose.huenul.springboot.globallogic.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.jose.huenul.springboot.globallogic.entity.PhoneEntity;

@Repository
public interface PhoneDao extends JpaRepository<PhoneEntity, Long> {

}
