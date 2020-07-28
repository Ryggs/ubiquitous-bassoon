package com.hover.runner.ui.filter_pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hover.runner.ApplicationInstance;
import com.hover.runner.R;
import com.hover.runner.adapters.FilterSingleItemRecyclerAdapter;
import com.hover.runner.interfaces.CustomOnClickListener;
import com.hover.runner.models.SingleFilterInfoModel;
import com.hover.runner.states.ActionState;
import com.hover.runner.utils.UIHelper;

import java.util.ArrayList;

public class FilterByCategoriesActivity extends AppCompatActivity implements CustomOnClickListener {
    private ArrayList<SingleFilterInfoModel> categoryList = new ArrayList<>();
    private ArrayList<String> selectedCategories = new ArrayList<>();
    boolean saveStateChanged = false;
    private TextView saveText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_by_categories);
        if(getIntent().getExtras() !=null) categoryList = getIntent().getExtras().getParcelableArrayList("data");

        assert categoryList != null;
        if(categoryList.size() > 0 ) {
            for(int i=0; i<categoryList.size(); i++) {
                if(categoryList.get(i).isCheck()) selectedCategories.add(categoryList.get(i).getTitle());
            }
        }

        findViewById(R.id.category_title).setOnClickListener(v-> finish());
        saveText = findViewById(R.id.filter_save_id);
        saveText.setOnClickListener(v->{
            if(saveStateChanged) {
                ActionState.setCategoryFilter(selectedCategories);
                finish();
            }
        });

        RecyclerView itemsRecyclerView = findViewById(R.id.filter_recyclerView);
        itemsRecyclerView.setLayoutManager(UIHelper.setMainLinearManagers(this));
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setAdapter(new FilterSingleItemRecyclerAdapter(categoryList, this));



    }

    @Override
    public void customClickListener(Object... data) {
        if(!saveStateChanged) {
            saveText.setTextColor(getResources().getColor(R.color.colorHoverWhite));
            UIHelper.setTextUnderline(saveText, "Save");
            saveStateChanged = true;
        }
        String categoryClicked = (String) data[0];
        boolean checked = (boolean) data[1];
        if(checked) selectedCategories.add(categoryClicked);
        else if(selectedCategories.indexOf(categoryClicked) != -1) selectedCategories.remove(categoryClicked);
    }
}
