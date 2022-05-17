package com.dmd.studyonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmd.studyonline.model.ClassLink;

import java.util.ArrayList;
import java.util.List;

public class DetailEditInformation extends AppCompatActivity {
    List<ClassLink> listClassLink = new ArrayList<>();
    ClassLink classLink;
    private ImageView imvAvatar;
    private TextView txtName;
    private EditText edtNameCourse;
    private EditText edtLink;
    private EditText edtTime;
    private EditText edtNameTeacher;
    private EditText edtNoteShort;
    private ScrollView scrollView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit_information);
        // set id
        imvAvatar = findViewById(R.id.imvAvatar);
        txtName = findViewById(R.id.txtName);
        edtNameCourse = findViewById(R.id.edtNameCourse);
        edtNameCourse.bringToFront();
        edtLink = findViewById(R.id.edtLink);
        edtLink.bringToFront();
        edtTime = findViewById(R.id.edtTime);
        edtTime.bringToFront();
        edtNameTeacher = findViewById(R.id.edtNameTeacher);
        edtNameTeacher.bringToFront();
        edtNoteShort = findViewById(R.id.edtNote);
        scrollView = findViewById(R.id.scrollView_Detail);
        edtNoteShort.bringToFront();
        edtNoteShort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scrollView.smoothScrollTo(scrollView.getScrollX(),scrollView.getBottom());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // get Bundle
        Intent intent = getIntent();
        classLink = (ClassLink) intent.getSerializableExtra(MainActivity.EXTRA_CLASSLINK_INFO);
        // set info
        setImage();
        txtName.setText(classLink.getTitle());
        edtNameCourse.setText(classLink.getTitle());
        edtLink.setText(classLink.getLink());
        if (!classLink.getTime().equals("chưa xác định")) {
            edtTime.setText(classLink.getTime());
        }
        if (!classLink.getNameTeacher().equals("none")) {
            edtNameTeacher.setText(classLink.getNameTeacher());
        }
        if (!classLink.getNoteShort().equals("none")) {
            edtNoteShort.setText(classLink.getNoteShort());
        }
    }

    private void setImage() {
        int x = classLink.getImage();
        if (x % 4 == 0) {
            imvAvatar.setImageResource(R.drawable.meet);
        } else if (x % 4 == 1) {
            imvAvatar.setImageResource(R.drawable.zoom);
        } else if (x % 4 == 2) {
            imvAvatar.setImageResource(R.drawable.team);
        } else {
            imvAvatar.setImageResource(R.drawable.web);
        }
    }

    public void buttonCheck(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        findViewById(R.id.ibCheck).startAnimation(animation);
        if (!edtNoteShort.getText().toString().isEmpty()) {
            classLink.setNoteShort(edtNoteShort.getText().toString());
        }
        classLink.setTitle(edtNameCourse.getText().toString());
        classLink.setLink(edtLink.getText().toString());
        if (!edtTime.getText().toString().isEmpty()) {
            classLink.setTime(edtTime.getText().toString());
        }
        if (!edtNameTeacher.getText().toString().isEmpty()) {
            classLink.setNameTeacher(edtNameTeacher.getText().toString());
        }
        if (!(classLink.getLink().isEmpty() || classLink.getTitle().isEmpty())) {
            if (MainActivity.getInstance().isValid(edtLink.getText().toString())) {
                MainActivity.getInstance().setSaveDataChangefromActivityDetail(classLink);
                finish();
            } else {
                Toast.makeText(this, "Link không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DetailEditInformation.this, "Tên môn học và Link không được bỏ trống", Toast.LENGTH_SHORT).show();
        }

    }
}