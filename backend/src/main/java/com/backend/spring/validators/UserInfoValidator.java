package com.backend.spring.validators;

import com.backend.spring.entities.UserInfo;
import com.backend.spring.exceptions.ViolatedConstraintException;

class UserInfoValidator implements Validator {
    private final UserInfo userInfo;

    UserInfoValidator(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Validates the first name, last name, username, email, and password of the shop owner.
     */
    @Override
    public void validate() {
        if (userInfo == null)
            throw new ViolatedConstraintException("User info cannot be null.");
        validateFirstName();
        validateLastName();
        validateEmail();
        validatePhoneNumber();
    }

    private void validatePhoneNumber() {
        if (userInfo.getPhoneNumber() == null || userInfo.getPhoneNumber().length() == 0)
            throw new ViolatedConstraintException("User phone number must be provided.");

        String phoneNumber = userInfo.getPhoneNumber().replaceAll("\\s", "").replaceAll("-", "");
        userInfo.setPhoneNumber(phoneNumber);

        if (phoneNumber.length() > 15)
            throw new ViolatedConstraintException("User phone number must be at most 15 characters.");

        if (!phoneNumber.matches("^\\+[0-9]{11}$"))
            throw new ViolatedConstraintException("User phone number must be in the form +14161239876.");
    }

    private void validateEmail() {
        String email = this.userInfo.getEmail();

        if (email == null || email.length() == 0)
            throw new ViolatedConstraintException("Email must be provided.");

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new ViolatedConstraintException("Email must be a valid email address.");

    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.length() == 0)
            throw new ViolatedConstraintException(fieldName + " must be provided.");

        if (name.length() > 20)
            throw new ViolatedConstraintException(fieldName + " must be at most 20 characters.");

        if (!name.matches("^[a-zA-Z]+$"))
            throw new ViolatedConstraintException(fieldName + " must only contain letters.");

    }

    private void validateFirstName() {
        validateName(this.userInfo.getFirstName(), "First name");
    }

    private void validateLastName() {
        validateName(this.userInfo.getLastName(), "Last name");
    }
}
