package com.onjee.loginApi.repository;


import com.onjee.loginApi.domain.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<MemberEntity,Long> {

    boolean existsByUsername(String id);

    boolean existsByEmail(String email);


}
