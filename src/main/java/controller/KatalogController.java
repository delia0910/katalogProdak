package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import data.ProdukRepository;
import model.Produk;

public class KatalogController {

    @FXML
    private TextField namaField;
    @FXML
    private TextField hargaField;
    @FXML
    private ComboBox<String> fotoBox;
    @FXML
    private Label pesanLabel;
    @FXML
    private Label jumlahLabel;
    @FXML
    private TilePane katalogPane;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;

    private final ProdukRepository repository = new ProdukRepository();

    // 0 = mode tambah. Selain 0 = mode edit untuk produk dengan ID tersebut.
    private long idDiedit = 0;

    @FXML
    private void initialize() {
        fotoBox.getItems().add("roti.png");
        fotoBox.getItems().add("susu.png");
        fotoBox.getItems().add("kopi.png");
        fotoBox.setValue("roti.png");
        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        ArrayList<Produk> daftar = repository.getAll();
        katalogPane.getChildren().clear();
        for (Produk produk : daftar) {
            VBox kartu = createProductCard(produk);
            katalogPane.getChildren().add(kartu);
        }
        jumlahLabel.setText("Jumlah produk: " + daftar.size());
    }

    private VBox createProductCard(Produk produk) {
        ImageView fotoView = new ImageView();
        fotoView.setFitWidth(170);
        fotoView.setFitHeight(110);
        fotoView.setPreserveRatio(true);
        StackPane bingkai = new StackPane();
        bingkai.setPrefSize(180, 120);
        bingkai.setMinSize(180, 120);
        bingkai.setMaxSize(180, 120);

        URL lokasi = getClass().getResource("/image/" + produk.getFoto());
        if (lokasi != null) {
            Image gambar = new Image(lokasi.toExternalForm(),
                    170, 110, true, true);
            if (!gambar.isError()) {
                fotoView.setImage(gambar);
                bingkai.getChildren().add(fotoView);
            } else {
                bingkai.getChildren().add(new Label("Gambar tidak terbaca"));
            }
        } else {
            bingkai.getChildren().add(new Label("Gambar tidak tersedia"));
        }

        Label namaLabel = new Label(produk.getNama());
        namaLabel.setWrapText(true);
        namaLabel.getStyleClass().add("nama-produk");
        Label hargaLabel = new Label("Rp" + produk.getHarga());
        Label idLabel = new Label("ID: " + produk.getId());
        idLabel.getStyleClass().add("muted");

        // Tombol Edit dan Hapus untuk produk ini
        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("button-edit");
        editButton.setOnAction(e -> startEdit(produk));

        Button hapusButton = new Button("Hapus");
        hapusButton.getStyleClass().add("button-danger");
        hapusButton.setOnAction(e -> deleteProduct(produk));

        HBox aksi = new HBox(8);
        aksi.getChildren().add(editButton);
        aksi.getChildren().add(hapusButton);

        VBox kartu = new VBox();
        kartu.setSpacing(8);
        kartu.setMinWidth(220);
        kartu.setPrefWidth(220);
        kartu.setMaxWidth(220);
        kartu.getStyleClass().add("kartu-produk");
        if (produk.getId() == idDiedit) {
            kartu.getStyleClass().add("kartu-diedit");
        }
        kartu.getChildren().add(bingkai);
        kartu.getChildren().add(namaLabel);
        kartu.getChildren().add(hargaLabel);
        kartu.getChildren().add(idLabel);
        kartu.getChildren().add(aksi);
        return kartu;
    }

    // ---------- EDIT ----------

    private void startEdit(Produk produk) {
        idDiedit = produk.getId();
        namaField.setText(produk.getNama());
        hargaField.setText(String.valueOf(produk.getHarga()));
        fotoBox.setValue(produk.getFoto());
        simpanButton.setText("Simpan Perubahan");
        batalButton.setVisible(true);
        batalButton.setManaged(true);
        pesanLabel.setText("Mengedit produk ID " + produk.getId()
                + ". Ubah data lalu klik Simpan Perubahan.");
        refreshKatalog();
    }

    @FXML
    private void cancelEdit() {
        keluarModeEdit();
        pesanLabel.setText("Edit dibatalkan.");
        refreshKatalog();
    }

    private void keluarModeEdit() {
        idDiedit = 0;
        namaField.clear();
        hargaField.clear();
        fotoBox.setValue("roti.png");
        simpanButton.setText("Simpan");
        batalButton.setVisible(false);
        batalButton.setManaged(false);
    }

    // ---------- HAPUS ----------

    private void deleteProduct(Produk produk) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus produk \"" + produk.getNama() + "\" (ID " + produk.getId() + ")?",
                ButtonType.YES, ButtonType.NO);
        dialog.setTitle("Konfirmasi hapus");
        dialog.setHeaderText(null);
        dialog.initOwner(katalogPane.getScene().getWindow());
        Optional<ButtonType> jawaban = dialog.showAndWait();
        if (jawaban.isEmpty() || jawaban.get() != ButtonType.YES) {
            return; // dibatalkan, data tidak berubah
        }

        try {
            repository.delete(produk.getId());
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menghapus. Lihat Output.");
            e.printStackTrace();
            return;
        }

        // Jika produk yang sedang diedit dihapus, form dikembalikan ke mode tambah
        if (produk.getId() == idDiedit) {
            keluarModeEdit();
        }
        try {
            showData();
            pesanLabel.setText("Produk berhasil dihapus.");
        } catch (SQLException e) {
            pesanLabel.setText("Data terhapus, tetapi katalog gagal dimuat ulang.");
            e.printStackTrace();
        }
    }

    private void refreshKatalog() {
        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Katalog gagal dimuat ulang. Lihat Output.");
            e.printStackTrace();
        }
    }

    // ---------- SIMPAN (tambah baru atau simpan perubahan) ----------

    @FXML
    private void saveProduct() {
        boolean modeEdit = idDiedit != 0;
        Produk produk;
        try {
            String nama = namaField.getText();
            long harga = Long.parseLong(hargaField.getText().trim());
            String foto = fotoBox.getValue();
            // modeEdit: pakai ID lama supaya UPDATE mengenai baris yang benar
            produk = new Produk(idDiedit, nama, harga, foto);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus angka bulat, contoh 12000.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }
        try {
            if (modeEdit) {
                repository.update(produk);
            } else {
                repository.add(produk);
            }
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menyimpan. Lihat Output.");
            e.printStackTrace();
            return;
        }

        keluarModeEdit();
        try {
            showData();
            pesanLabel.setText(modeEdit
                    ? "Perubahan produk berhasil disimpan."
                    : "Produk berhasil disimpan.");
        } catch (SQLException e) {
            pesanLabel.setText("Data tersimpan, tetapi katalog gagal dimuat ulang.");
            e.printStackTrace();
        }
    }
}
