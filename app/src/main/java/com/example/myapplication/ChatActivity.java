package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String recieverId;
    DatabaseReference databaseReference;
    String senderRoom, recieverRoom;
    MessageAdapter messAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       recieverId = getIntent().getStringExtra("id");
       senderRoom = FirebaseAuth.getInstance().getUid()+recieverId;
       recieverRoom = recieverId+FirebaseAuth.getInstance().getUid();

       messAdapter = new MessageAdapter(this);
        binding.recycler.setAdapter(messAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
       databaseReference = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               messAdapter.clear();
               for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    MessageModel messageModel= dataSnapshot.getValue(MessageModel.class);

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       binding.sendMessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String message = binding.messageEd.getText().toString();
           }
       });

    }
}