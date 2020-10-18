package shop.ptrowinda.myhome;

public class ListUser {

    String nama_lengkap, password, username, bio, email_address, level, url_photo_profile;
    int user_balance;

    public ListUser(){ }

    public ListUser(String nama_lengkap, String password, String username, String bio, String email_address, String level, int user_balance, String url_photo_profile) {
        this.nama_lengkap = nama_lengkap;
        this.password = password;
        this.username = username;
        this.bio = bio;
        this.email_address = email_address;
        this.level = level;
        this.user_balance = user_balance;
        this.url_photo_profile= url_photo_profile;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(int user_balance) {
        this.user_balance = user_balance;
    }

    public String getUrl_photo_profile() {
        return url_photo_profile;
    }

    public void setUrl_photo_profile(String url_photo_profile) {
        this.url_photo_profile = url_photo_profile;
    }
}
