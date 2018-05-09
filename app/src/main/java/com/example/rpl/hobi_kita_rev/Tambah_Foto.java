package com.example.rpl.hobi_kita_rev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.rpl.hobi_kita_rev.R;
import com.example.rpl.hobi_kita_rev.Constant;
import com.example.rpl.hobi_kita_rev.FotoModel;

public class Tambah_Foto extends AppCompatActivity implements View.OnClickListener{

    //Deklarasi View
    @BindView(R.id.btnPost) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            FloatingActionButton btnPost;
    @BindView(R.id.tvTitle) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvTitle;
    @BindView(R.id.tvPost) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvPost;
    @BindView(R.id.imgPhoto) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            ImageView imgPhoto;
    @BindView(R.id.btnChoose) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            Button btnChoose;
    @BindView(R.id.tvLoc)
    TextInputEditText tvLoc;
    @BindView(R.id.cari)
    Button cari;
    private StorageReference refPhotoProfile;
    private Uri photoUrl;
    private ProgressDialog pbDialog;
    private String kondisi;
    private Spinner spin;
    private String[] kategori = new String[]{
            "Pilih Kategori",
            "Event",
            "Kompetisi",
            "Sewa"
    };

    private int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_foto);
        ButterKnife.bind(this); //Binding ButterKnife pada activity
        btnChoose.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        pbDialog = new ProgressDialog(this);
        cari.setOnClickListener(this);
        spin = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kondisi = String.valueOf(parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin.setAdapter(adapter);
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        kondisi = String.valueOf(parent.getSelectedItem());
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    //method handling onClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChoose: //tombol choose (pilih gambar)
                //ImagePicker library untuk menampilkan dialog memilih gambar pada gallery/camera
                ImagePicker.create(this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .single() // single mode
                        .limit(1) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
                break;
            case R.id.cari:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(Tambah_Foto.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            case R.id.btnPost:
                //validasi kosong
                if(tvTitle.getText().toString().isEmpty()) {
                    tvTitle.setError("Judul Harus diisi");
                    return;
                }
                //validasi kosong
                if(tvPost.getText().toString().isEmpty()) {
                    tvPost.setError("Deskripsi harus diisi");
                    return;
                }
                if (tvLoc.getText().toString().isEmpty()) {
                    tvLoc.setError("harus diisi");
                    return;
                }
                //validasi gambar sudah dipilih
                if(!isPicChange) {
                    Toast.makeText(this, "Silahkan Masukkan Foto/Gambar", Toast.LENGTH_SHORT).show();
                    return;
                }

                pbDialog.setMessage("Uploading..");
                pbDialog.setIndeterminate(true);
                pbDialog.show();

                //melakukan proses update foto
                if (kondisi.equals("Event")) {
                    refPhotoProfile = Constant.storageRef.child("gambar/Event/" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
                    StorageReference photoImagesRef = Constant.storageRef.child("gambar/Event/" + System.currentTimeMillis() + ".jpg");
                    refPhotoProfile.getName().equals(photoImagesRef.getName());
                    refPhotoProfile.getPath().equals(photoImagesRef.getPath());
                } else if (kondisi.equals("Kompetisi")) {
                    refPhotoProfile = Constant.storageRef.child("gambar/Kompetisi/" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
                    StorageReference photoImagesRef = Constant.storageRef.child("gambar/Kompetisi/" + System.currentTimeMillis() + ".jpg");
                    refPhotoProfile.getName().equals(photoImagesRef.getName());
                    refPhotoProfile.getPath().equals(photoImagesRef.getPath());
                } else if (kondisi.equals("Sewa")) {
                    refPhotoProfile = Constant.storageRef.child("gambar/Sewa" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
                    StorageReference photoImagesRef = Constant.storageRef.child("gambar/Sewa/" + System.currentTimeMillis() + ".jpg");
                    refPhotoProfile.getName().equals(photoImagesRef.getName());
                    refPhotoProfile.getPath().equals(photoImagesRef.getPath());
                } else {
                    Toast.makeText(this, "Pilih Kategori", Toast.LENGTH_SHORT).show();

                }

                //mengambil gambar dari imageview yang sudah di set menjadi selected image sebelumnya
                imgPhoto.setDrawingCacheEnabled(true);
                imgPhoto.buildDrawingCache();
                Bitmap bitmap = imgPhoto.getDrawingCache(); //convert imageview ke bitmap
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //convert bitmap ke bytearray
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = refPhotoProfile.putBytes(data); //upload image yang sudah dalam bentuk bytearray ke firebase storage
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (kondisi.equals("Event")) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            photoUrl = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                            String key = Constant.refEvent.push().getKey(); //ambil key dari node firebase

                            //push atau insert data ke firebase database
                            Constant.refEvent.child(key).setValue(new FotoModel(
                                    key,
                                    photoUrl.toString(),
                                    tvTitle.getText().toString(),
                                    tvLoc.getText().toString(),
                                    Constant.currentUser.getEmail().split("@")[0],
                                    Constant.currentUser.getEmail(),
                                    tvPost.getText().toString()
                            ));
                            pbDialog.dismiss();
                            Toast.makeText(Tambah_Foto.this, "Post Berhasil!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (kondisi.equals("Kompetisi")) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            photoUrl = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                            String key = Constant.refKompetisi.push().getKey(); //ambil key dari node firebase

                            //push atau insert data ke firebase database
                            Constant.refKompetisi.child(key).setValue(new FotoModel(
                                    key,
                                    photoUrl.toString(),
                                    tvTitle.getText().toString(),
                                    tvLoc.getText().toString(),
                                    Constant.currentUser.getEmail().split("@")[0],
                                    Constant.currentUser.getEmail(),
                                    tvPost.getText().toString()));
                            pbDialog.dismiss();
                            Toast.makeText(Tambah_Foto.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (kondisi.equals("Sewa")) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            photoUrl = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                            String key = Constant.refSewa.push().getKey(); //ambil key dari node firebase

                            //push atau insert data ke firebase database
                            Constant.refSewa.child(key).setValue(new FotoModel(
                                    key,
                                    photoUrl.toString(),
                                    tvTitle.getText().toString(),
                                    tvLoc.getText().toString(),
                                    Constant.currentUser.getEmail().split("@")[0],
                                    Constant.currentUser.getEmail(),
                                    tvPost.getText().toString()));
                            pbDialog.dismiss();
                            Toast.makeText(Tambah_Foto.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {

                        }
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        photoUrl = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                        String key = Constant.refPhoto.push().getKey(); //ambil key dari node firebase

                        //push atau insert data ke firebase database
                        Constant.refPhoto.child(key).setValue(new FotoModel(
                                key,
                                photoUrl.toString(),
                                tvTitle.getText().toString(),
                                tvLoc.getText().toString(),
                                Constant.currentUser.getEmail().split("@")[0],
                                Constant.currentUser.getEmail(),
                                tvPost.getText().toString()));
                        pbDialog.dismiss();
                        Toast.makeText(Tambah_Foto.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;
        }
    }

    boolean isPicChange = false;

    //method untuk handling result dari activity lain contoh disini adalah imagepicker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) { // jika ada data dipilih
            Image image = ImagePicker.getFirstImageOrNull(data); //ambil first image
            File imgFile = new File(image.getPath()); // dapatkan lokasi gambar yang dipilih
            if(imgFile.exists()){ //jika ditemukan
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath()); //convert file ke bitmap
                imgPhoto.setImageBitmap(myBitmap); //set imageview dengan gambar yang dipilih
                isPicChange = true; // ubah state menjadi true untuk menandakan gambar telah dipilih
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format(
                        ""+place.getName());
                tvLoc.setText(toastMsg);
                tvLoc.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}