package org.skan.models.lombok;

import lombok.Data;

@Data
public class RegisterBodyLombokModel {

   // String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

    private String email, password;


}
