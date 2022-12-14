package org.skan.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RegisterResponseModel {

    private String token, id, error;
}
