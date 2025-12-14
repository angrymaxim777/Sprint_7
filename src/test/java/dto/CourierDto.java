package dto;

public class CourierDto {
    private String login;
    private String password;
    private String firstName;

    public CourierDto(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierDto(String login, String password) {
        this.login = login;
        this.password = password;
        this.firstName = null;
    }

    // Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    // Сеттеры
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
