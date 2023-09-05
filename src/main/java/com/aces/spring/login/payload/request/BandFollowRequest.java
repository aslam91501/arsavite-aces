package com.aces.spring.login.payload.request;

import jakarta.validation.constraints.NotBlank;

public class BandFollowRequest {
    @NotBlank
    private long userId;

    @NotBlank
    private long bandId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBandId() {
        return bandId;
    }

    public void setBandId(long bandId) {
        this.bandId = bandId;
    }
}

