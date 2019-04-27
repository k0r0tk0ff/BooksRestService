package ru.k0r0tk0ff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.k0r0tk0ff.entity.Wishlist;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {
}
