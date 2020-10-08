package shop.ptrowinda.myhome;

public class MyBooking {

    String nama_graha, type_rumah, tanggal, jam;
    String jumlah_booking, id_booking;

    public MyBooking() {
    }

    public MyBooking(String nama_graha, String type_rumah, String tanggal, String jam, String jumlah_booking, String id_booking) {
        this.nama_graha = nama_graha;
        this.type_rumah = type_rumah;
        this.tanggal = tanggal;
        this.jam = jam;
        this.jumlah_booking = jumlah_booking;
        this.id_booking = id_booking;
    }

    public String getId_booking() {
        return id_booking;
    }

    public void setId_booking(String id_booking) {
        this.id_booking = id_booking;
    }


    public String getNama_graha() {
        return nama_graha;
    }

    public void setNama_graha(String nama_graha) {
        this.nama_graha = nama_graha;
    }

    public String getType_rumah() {
        return type_rumah;
    }

    public void setType_rumah(String type_rumah) {
        this.type_rumah = type_rumah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getJumlah_booking() {
        return jumlah_booking;
    }

    public void setJumlah_booking(String jumlah_booking) {
        this.jumlah_booking = jumlah_booking;
    }
}
