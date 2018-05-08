package com.example.rpl.hobi_kita_rev;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Constant {
    //Firebase Database
    public final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public final static DatabaseReference refEvent = database.getReference("event");
    public final static DatabaseReference refKompetisi = database.getReference("kompetisi");
    public final static DatabaseReference refSewa = database.getReference("sewa");
    public final static DatabaseReference refPhoto = database.getReference("photo");

    //Firebase Auth
    public final static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser;

    //Firebase Storage
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference();
}
