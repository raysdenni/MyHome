package shop.ptrowinda.myhome;

public class MPListGraha {

    String nama_graha, type_rumah, detail_graha, informasi,
    jenis_sertifikat, ketentuan, lokasi, url_thumbnail;
    int harga_graha;

    public MPListGraha(){}

    public MPListGraha(String nama_graha, String type_rumah, String detail_graha,
                       String informasi, String jenis_sertifikat, String ketentuan,
                       String lokasi, String url_thumbnail, int harga_graha) {
        this.nama_graha = nama_graha;
        this.type_rumah = type_rumah;
        this.detail_graha = detail_graha;
        this.informasi = informasi;
        this.jenis_sertifikat = jenis_sertifikat;
        this.ketentuan = ketentuan;
        this.lokasi = lokasi;
        this.url_thumbnail = url_thumbnail;
        this.harga_graha = harga_graha;
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

    public String getDetail_graha() {
        return detail_graha;
    }

    public void setDetail_graha(String detail_graha) {
        this.detail_graha = detail_graha;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public String getJenis_sertifikat() {
        return jenis_sertifikat;
    }

    public void setJenis_sertifikat(String jenis_sertifikat) {
        this.jenis_sertifikat = jenis_sertifikat;
    }

    public String getKetentuan() {
        return ketentuan;
    }

    public void setKetentuan(String ketentuan) {
        this.ketentuan = ketentuan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public int getHarga_graha() {
        return harga_graha;
    }

    public void setHarga_graha(int harga_graha) {
        this.harga_graha = harga_graha;
    }
}
