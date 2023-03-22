package com.breno.wishlist.exception;

public class WishNotFoundException extends RuntimeException {

    public WishNotFoundException(Long id) {
        super("Could not found the wish with id"+id);
    }

}
