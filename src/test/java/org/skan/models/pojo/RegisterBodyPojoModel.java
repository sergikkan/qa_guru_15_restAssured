package org.skan.models.pojo;

public class RegisterBodyPojoModel {

   // String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

    private String email, password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
