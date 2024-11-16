package su.foxogram.enums;

public class ValidationEnum {
    public static class Lengths {
        public static final int MIN = 4;
        public static final int PASSWORD = 128;
        public static final int USERNAME = 32;
        public static final int EMAIL = 64;
        public static final int CHANNEL_NAME = 16;
        public static final int MESSAGE_CONTENT = 5000;
    }

    public static class Messages {
        public static final String PASSWORD_WRONG_LENGTH = "Password must be between {min} and {max} characters long";
        public static final String USERNAME_WRONG_LENGTH = "Username must be between {min} and {max} characters long";
        public static final String USERNAME_INCORRECT = "Incorrect username format";
        public static final String EMAIL_WRONG_LENGTH = "Email must be between {min} and {max} characters long";
        public static final String EMAIL_INCORRECT = "Incorrect email format";
        public static final String CHANNEL_NAME_WRONG_LENGTH = "Channel name must be between {min} and {max} characters long";
        public static final String CHANNEL_NAME_INCORRECT = "Incorrect channel format";
        public static final String MESSAGE_WRONG_LENGTH = "Message length must be between {min} and {max} characters long";
    }

    public static class Regex {
        public static final String EMAIL_REGEX = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public static final String USERNAME_REGEX = "^[_A-Za-z0-9-.]+$";
        public static final String CHANNEL_NAME_REGEX = "^[_A-Za-z0-9-.]+$";
    }
}
