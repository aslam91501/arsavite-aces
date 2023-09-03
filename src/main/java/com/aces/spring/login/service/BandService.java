package com.aces.spring.login.service;

import com.aces.spring.login.models.Band;
import com.aces.spring.login.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aces.spring.login.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class BandService {
    @Autowired
    private BandRepository bandRepository;

    @Autowired
    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    public Band createBand(Band band) {
        return bandRepository.save(band);
    }

    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    public Optional<Band> getBandById(Long id) {
        return bandRepository.findById(id);
    }

    public Band updateBand(Long id, Band updatedBand) {
        Optional<Band> optionalBand = bandRepository.findById(id);
        if (optionalBand.isPresent()) {
            Band existingBand = optionalBand.get();
            existingBand.setName(updatedBand.getName());
            existingBand.setLogoUrl(updatedBand.getLogoUrl());
            // Update other properties as needed
            return bandRepository.save(existingBand);
        } else {
            return null; // or throw an exception
        }
    }

    public void deleteBand(Long id) {
        Optional<Band> optionalBand = bandRepository.findById(id);
        optionalBand.ifPresent(bandRepository::delete);
    }

    public Band followBand(Long bandId, User user) {
        Band band = bandRepository.findById(bandId).orElse(null);
        if (band != null) {
            user.followBand(band);
            return bandRepository.save(band);
        }
        return null;
    }

    public Band unfollowBand(Long bandId, User user) {
        Band band = bandRepository.findById(bandId).orElse(null);
        if (band != null) {
            user.unfollowBand(band);
            return bandRepository.save(band);
        }
        return null;
    }

}
