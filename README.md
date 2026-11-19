[README.md](https://github.com/user-attachments/files/35064091/README.md)
# Katalog Produk

Aplikasi desktop sederhana untuk mengelola katalog produk, dibuat dengan **Java, JavaFX, dan SQLite**.

## Fitur
- **Tambah** produk (nama, harga, foto) lewat form
- **Tampil** produk sebagai kartu katalog
- **Edit** data produk yang sudah ada
- **Hapus** produk (dengan konfirmasi)
- Data tersimpan di database SQLite sehingga tetap ada saat aplikasi dibuka kembali

## Teknologi
- Java 17+
- JavaFX 21
- SQLite (`sqlite-jdbc`)
- Maven

## Cara menjalankan
1. Pastikan JDK dan Maven sudah terpasang (atau gunakan NetBeans).
2. Buka folder proyek, lalu jalankan:

   ```
   mvn clean javafx:run
   ```

   Di NetBeans: klik kanan proyek, pilih **Run Maven > Other Goals...**, isi `clean javafx:run`.
3. Database `data/katalog.db` dibuat otomatis saat pertama kali aplikasi berjalan.

## Cara menggunakan
1. Isi **Nama produk**, **Harga (Rp)**, dan pilih **Foto**, lalu klik **Simpan**.
2. Untuk mengubah produk, klik **Edit** pada kartu, ubah datanya, lalu klik **Simpan Perubahan** (atau **Batal**).
3. Untuk menghapus produk, klik **Hapus** pada kartu lalu konfirmasi.

## Screenshot

### 1. Tampilan awal (database masih kosong)
![Tampilan awal](screenshots/01-kosong.png)

### 2. Menambah produk
![Tambah produk](screenshots/02-tambah-produk.png)

### 3. Katalog dengan beberapa produk
Setiap kartu produk memiliki tombol **Edit** dan **Hapus**.

![Katalog](screenshots/03-katalog.png)

### 4. Edit produk
Setelah **Simpan Perubahan**, data pada kartu ikut berubah (contoh: produk ID 1 diubah menjadi "ayam" Rp12000).

![Edit produk](screenshots/04-edit-produk.png)

### 5. Validasi input
Jika harga kosong atau bukan angka bulat, muncul pesan kesalahan dan data tidak disimpan.

![Validasi](screenshots/05-validasi.png)
