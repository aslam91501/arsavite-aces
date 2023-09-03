package com.aces.spring.login.controllers;

import com.aces.spring.login.models.Band;
import com.aces.spring.login.repository.BandRepository;
import com.aces.spring.login.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.aces.spring.login.models.User;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bands")
public class BandController {
    @Autowired
    private BandRepository bandRepository;
    private final BandService bandService;

    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @PostMapping("/create")
    public ResponseEntity<Band> createBand(@RequestBody Band band) {
        Band savedBand = bandRepository.save(band);
        return ResponseEntity.ok(savedBand);
    }

    @PostMapping("/{bandId}/follow")
    public Band followBand(@PathVariable Long bandId, @AuthenticationPrincipal User user) {
        return bandService.followBand(bandId, user);
    }

    @PostMapping("/{bandId}/unfollow")
    public Band unfollowBand(@PathVariable Long bandId, @AuthenticationPrincipal User user) {
        return bandService.unfollowBand(bandId, user);
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
