package com.example.mugu.repository;

import com.example.mugu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByPhone(String phone);
}