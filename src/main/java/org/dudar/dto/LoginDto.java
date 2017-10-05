package org.dudar.dto;

import org.dudar.model.entity.IBuilder;

public class LoginDto {

    private String phoneNumber;
    private String password;

    public LoginDto(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginDto)) return false;

        LoginDto loginDto = (LoginDto) o;

        if (phoneNumber != null ? !phoneNumber.equals(loginDto.phoneNumber) : loginDto.phoneNumber != null)
            return false;
        return password != null ? password.equals(loginDto.password) : loginDto.password == null;
    }

    @Override
    public int hashCode() {
        int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginDto{");
        sb.append("phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
