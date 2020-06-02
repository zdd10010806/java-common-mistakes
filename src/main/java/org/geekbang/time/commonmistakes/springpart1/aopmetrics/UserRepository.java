package org.geekbang.time.commonmistakes.springpart1.aopmetrics;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//@Repository
//public interface UserRepository extends JpaRepository<UserEntity, Long> {
public interface UserRepository {
    List<UserEntity> findByName(String name);

    void save(UserEntity entity);
}
