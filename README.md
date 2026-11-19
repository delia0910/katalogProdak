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
<img width="865" height="749" alt="01-kosong" src="https://github.com/user-attachments/assets/295c4ae9-92b1-4111-89c1-e1144cd4773e" />


### 2. Menambah produk
<img width="856" height="741" alt="02-tambah-produk" src="https://github.com/user-attachments/assets/cda1ae06-20b8-425a-bd3f-8efaefab9ec0" />


### 3. Katalog dengan beberapa produk
<img width="863" height="743" alt="03-katalog" src="https://github.com/user-attachments/assets/d0a8cd1e-c7ab-4e60-97f5-52e3a1bd9136" />


![Katalog](screenshots/03-katalog.png)

### 4. Edit produk
<img width="857" height="738" alt="04-edit-produk" src="https://github.com/user-attachments/assets/6fcd3cf4-d2d7-4b20-9767-381bcc3a8e29" />


![Edit produk](screenshots/04-edit-produk.png)

### 5. Validasi input
Jika harga kosong atau bukan angka bulat, muncul pesan kesalahan dan data tidak disimpan.

<img width="862" height="741" alt="05-validasi" src="https://github.com/user-attachments/assets/8e2c961b-86bd-4c1a-8c1a-5f1ee21de89d" />

