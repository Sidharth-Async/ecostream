package com.ecostream.common.dto;

public record AddressDTO(
        String street,
        String city,
        String zipCode,
        String country
) {}
