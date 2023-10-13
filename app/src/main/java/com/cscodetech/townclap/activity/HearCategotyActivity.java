package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.CategoryAdapter;
import com.cscodetech.townclap.model.CatlistItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HearCategotyActivity extends BasicActivity implements CategoryAdapter.RecyclerTouchListener {

    @BindView(R.id.recycler_category)
    RecyclerView recyclerCategory;
    ArrayList<CatlistItem> cats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear_categoty);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(R.string.directhire);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = this.getIntent().getExtras();
        cats = b.getParcelableArrayList("cat_list");
        recyclerCategory.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerCategory.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, cats, this, "hairviewall");
        recyclerCategory.setAdapter(categoryAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickCategoryItem(CatlistItem item, int position) {


        startActivity(new Intent(this, HearExportListActivity.class)
                .putExtra("vurl", item.getCatVideo())
                .putExtra("name", item.getCatName())
                .putExtra("named", item.getCatSubtitle())
                .putExtra("cid", item.getCatId())
                .putExtra("sid", String.valueOf(item.getTotalSubcat())));
    }
}