package com.breno.wishlist.controller;

import com.breno.wishlist.exception.WishNotFoundException;
import com.breno.wishlist.model.Wish;
import com.breno.wishlist.repository.WishRepository;
import com.breno.wishlist.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/wishlist")
public class WishController {

    @Autowired
    private WishRepository wishRepository;

    public enum OrderEnum {
        ascending, descending;
    }

    public enum ValueEnum {
        wish, price;
    }

    @PostMapping
    public Wish create(@RequestBody MultipartFile file, String wish,  String site, Double price) {

        Optional<Wish> existWish = wishRepository.findByWish(wish);

        if(!file.isEmpty() && wish != null && site != null && price != null && existWish.isEmpty() ) {
            ImgService img = new ImgService();
            img.setImg(file, wish);


            Wish newWish = new Wish(wish, site, img.getImg(), price);
            return wishRepository.save(newWish);


        }

        return null;
    }

    @GetMapping
    public Page<Wish> getAll(@RequestParam ValueEnum value, OrderEnum order, Integer page) {

        Sort sort;

        if(order == OrderEnum.ascending) {
            System.out.println("if");
            sort = Sort.by(String.valueOf(value)).ascending();
            System.out.println(sort);
            Pageable pageable = PageRequest.of(page, 6, sort);
            return wishRepository.findAll(pageable);
        } else {
            System.out.println("else");
            sort = Sort.by(String.valueOf(value)).descending();
            System.out.println(sort);
            Pageable pageable = PageRequest.of(page, 6, sort);
            return wishRepository.findAll(pageable);
        }

    }


    @GetMapping("/search")
    public List<Wish> search(@RequestParam String value) {
        return wishRepository.findByWishIgnoreCaseContaining(value);
    }

    @GetMapping("/{id}")
    public Wish findById(@PathVariable Long id) {
        return wishRepository.findById(id)
                .orElseThrow(()->new WishNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Wish edit(@RequestBody Wish editWish, @PathVariable Long id) {

        return wishRepository.findById(id)
                .map(wish -> {
                    wish.setPrice(editWish.getPrice());
                    wish.setSite(editWish.getSite());
                    return wishRepository.save(wish);
                }).orElseThrow(()-> new WishNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public String destroy(@PathVariable Long id) throws IOException {
        if(!wishRepository.existsById(id)) {
            throw new WishNotFoundException(id);
        }
        Optional<Wish> wish = wishRepository.findById(id);
        ImgService imgService = new ImgService();
        imgService.delete(wish.get().getWish());
        wishRepository.deleteById(id);
        return "Wish with "+id+" has been deleted sucess.";
    }

}
