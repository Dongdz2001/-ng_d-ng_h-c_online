package com.dmd.studyonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.*;

import com.dmd.studyonline.adater.OnItemClickListener;
import com.dmd.studyonline.adater.RyclerViewAdapter;
import com.dmd.studyonline.fragment.FragmentPlus;
import com.dmd.studyonline.model.ClassLink;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewList;
    private RyclerViewAdapter ryclerViewAdapter;
    private List<ClassLink> listClassLink = new ArrayList<>();
    private ImageView addPlus;
    private FragmentContainerView fragmentContainerView;
    public static final String TAG_FRAGEMENT_PLUS = "TAG_FRAGEMENT_PLUS";
    private ConstraintLayout constraintLayout;
    public static final String EXTRA_CLASSLINK_INFO = "EXTRA_CLASSLINK_INFO";
    private static MainActivity instance;
    private int position;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        initData();
        recyclerViewList = findViewById(R.id.rycMainActivity);
        recyclerViewList.bringToFront();
        addPlus = findViewById(R.id.tvAdd);
        addPlus.bringToFront();
        fragmentContainerView = findViewById(R.id.fargmentContainer);
        fragmentContainerView.bringToFront();
        constraintLayout = findViewById(R.id.Layout_Contrain);
        ryclerViewAdapter = new RyclerViewAdapter(this, setOnItemClick(), (ArrayList<ClassLink>) listClassLink, R.layout.layout_item_link_resource);
        recyclerViewList.setAdapter(ryclerViewAdapter);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewList.addItemDecoration(itemDecoration);
        recyclerViewList.setItemAnimator(new SlideInLeftAnimator());

    }

    private OnItemClickListener setOnItemClick() {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void ItemOnClick(ClassLink i) {
                Log.d("dmd", "Ok Click");
                int index = listClassLink.indexOf(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    intent.setData(Uri.parse(listClassLink.get(index).getLink()));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Liên kết không khả dụng", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void ItemOnLongClick(ClassLink i) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Bạn có chắc muốn xóa không ?");
                builder.setMessage("Bạn sẽ không thể khôi phục lại item này sau khi đã xóa .");
                builder.setPositiveButton("xác nhận",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("long", "clicked");
                                int index = listClassLink.indexOf(i);
                                listClassLink.remove(index);
                                saveData();
                                ryclerViewAdapter.notifyItemRemoved(index);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        };
        return onItemClickListener;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initData() {
//        listClassLink.add(new ClassLink("Meet Room    ", "https://meet.google.com/jry-gmkj-xzk?authuser=0"));

        SharedPreferences mPrefs = getSharedPreferences("share preference", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("ListClassLink", null);
        Type type = new TypeToken<ArrayList<ClassLink>>() {
        }.getType();
        listClassLink = gson.fromJson(json, type);
//        if (listClassLink.size() != 0){
//            Log.d("dmd",listClassLink.get(0).getTitle());
//            ryclerViewAdapter.notifyDataSetChanged();
//        }
    }

    public void addItem(View view) {

        FragmentPlus fragment = (FragmentPlus) getSupportFragmentManager().findFragmentByTag("Fr1");
        if (fragment == null) {
            Log.d("tt", "null");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // add frangment  //args add bundle
            fragmentTransaction
                    // Bỏ qua load Frangment nếu add thêm 1 frangment khác cùng lúc nó sẽ bỏ Fr ban đầu
                    .setReorderingAllowed(true)
                    .add(R.id.fargmentContainer, FragmentPlus.class, null, "Fr1");
            // add data vaof frangment (xac nhan data truyen vao!)
            fragmentTransaction.commit();
        } else {
            fragment.getView().setVisibility(View.VISIBLE);
        }
        if(listClassLink != null){
            recyclerViewList.smoothScrollToPosition(listClassLink.size());
        }
    }

    public void addDataSubmit(ClassLink classLink) {
        EditText edtTitle = findViewById(R.id.edtTitle);
        EditText edtLink = findViewById(R.id.edtLink);
        if (!(edtLink.getText().toString().isEmpty()
                || edtTitle.getText().toString().isEmpty()
        )) {
            if (isValid(edtLink.getText().toString())) {
                listClassLink.add(classLink);
                saveData();
                ryclerViewAdapter.notifyItemInserted(listClassLink.size());
                recyclerViewList.smoothScrollToPosition(listClassLink.size());
            } else {
                Toast.makeText(MainActivity.this, "Link không hợp lệ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(MainActivity.this, "không được để trống Title hoặc Link", Toast.LENGTH_SHORT).show();
        }
        edtLink.setText("");
        edtTitle.setText("");
    }

    public boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

    private void saveData() {
        SharedPreferences mPrefs = getSharedPreferences("share preference", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listClassLink);
        prefsEditor.putString("ListClassLink", json);
        prefsEditor.apply();
    }

    @Override
    public void onBackPressed() {
        Log.d("br", "clicked1");
        FragmentPlus fragment = (FragmentPlus) getSupportFragmentManager().findFragmentByTag("Fr1");
        if (fragment != null) {
            if (fragment.isVisible()) {
                fragment.getView().setVisibility(View.GONE);
                hideKeyboard(MainActivity.this);
                Log.d("br", "clicked");
            } else {
                finish();
            }
        }
    }

    public void changeScreen(ClassLink classLink) {
        Intent intent = new Intent(MainActivity.this, DetailEditInformation.class);
        intent.putExtra(EXTRA_CLASSLINK_INFO, (Serializable) classLink);
        startActivity(intent);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void setSaveDataChangefromActivityDetail(ClassLink classLink) {
        Log.d("cs", "clicked= " + position);
        listClassLink.set(position, classLink);
        ryclerViewAdapter.notifyItemChanged(position);
        saveData();
    }

    public void scrollReyclerView() {
        recyclerViewList.smoothScrollToPosition(listClassLink.size());
    }
}