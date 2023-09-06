package com.aces.spring.login.controllers;

import com.aces.spring.login.models.Band;
import com.aces.spring.login.payload.request.BandFollowRequest;
import com.aces.spring.login.payload.request.BandUnfollowRequest;
import com.aces.spring.login.repository.BandRepository;
import com.aces.spring.login.repository.UserRepository;
import com.aces.spring.login.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.aces.spring.login.models.User;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bands")
public class BandController {
    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private UserRepository userRepository;
    private final BandService bandService;

    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @PostMapping("/create")
    public ResponseEntity<Band> createBand(@RequestBody Band band, Principal principal) {

        String username = principal.getName();

        User user = userRepository.findByUsername(username).orElse(null);

        band.setCreatorId(user.getId());

        Band savedBand = bandRepository.save(band);
        return ResponseEntity.ok(savedBand);
    }

    @PostMapping("/follow")
    public User followBand(@RequestBody BandFollowRequest request) {
        return bandService.followBand(request.getBandId(), request.getUserId());
    }

    @PostMapping("/unfollow")
    public User followBand(@RequestBody BandUnfollowRequest request) {
        return bandService.unfollowBand(request.getBandId(), request.getUserId());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Band>> getAllBands() {
        List<Band> bands = bandRepository.findAll();
        return ResponseEntity.ok(bands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Band> getBandById(@PathVariable Long id) {
        Optional<Band> optionalBand = bandRepository.findById(id);
        if (optionalBand.isPresent()) {
            Band band = optionalBand.get();
            return ResponseEntity.ok(band);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getBandFollowers(@PathVariable Long id) {
        List<User> followers = bandService.getFollowers(id);

        return new ResponseEntity<>(followers, HttpStatus.OK);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<Band> updateBand(@PathVariable Long id, @RequestBody Band band) {
        Optional<Band> optionalBand = bandRepository.findById(id);
        if (optionalBand.isPresent()) {
            Band existingBand = optionalBand.get();
            existingBand.setName(band.getName());
            existingBand.setLogoUrl(band.getLogoUrl());
            // Update other properties as needed
            Band updatedBand = bandRepository.save(existingBand);
            return ResponseEntity.ok(updatedBand);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBand(@PathVariable Long id) {
        Optional<Band> optionalBand = bandRepository.findById(id);
        if (optionalBand.isPresent()) {
            bandRepository.delete(optionalBand.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

