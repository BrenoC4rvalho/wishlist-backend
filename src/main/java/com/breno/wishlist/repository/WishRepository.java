package com.breno.wishlist.repository;

import com.breno.wishlist.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByWishIgnoreCaseContaining(@Param("wish") String wish);
    Optional<Wish> findByWish(String wish);
}
