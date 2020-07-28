package com.hover.runner.ui.filter_pages;

import android.os.Bundle;
import android.view.View;
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
import com.hover.runner.states.TransactionState;
import com.hover.runner.utils.UIHelper;

import java.text.MessageFormat;
import java.util.ArrayList;

public class FilterByNetworksActivity extends AppCompatActivity implements CustomOnClickListener {
    private ArrayList<SingleFilterInfoModel> networkList = new ArrayList<>();
    private ArrayList<SingleFilterInfoModel> networkInselectedCountriesList = new ArrayList<>();
    private ArrayList<SingleFilterInfoModel> networkOutsideSelectedCountriesList = new ArrayList<>();
    private boolean saveStateChanged = false;
    private ArrayList<String> selectedNetworks = new ArrayList<>();
    private TextView saveText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterbynetworks);

        if(getIntent().getExtras() !=null) networkList = getIntent().getExtras().getParcelableArrayList("data");
        assert networkList != null;

        if(networkList.size() > 0 ) {
            for(int i=0; i<networkList.size(); i++) {
                if(networkList.get(i).isCheck()) selectedNetworks.add(networkList.get(i).getTitle());
            }

            if(ActionState.getCountriesFilter().size() > 0) {
                for(SingleFilterInfoModel infoModel : networkList) {
                    if(ActionState.getCountriesFilter().contains(infoModel.getCountry())) {
                        networkInselectedCountriesList.add(infoModel);
                    }
                    else networkOutsideSelectedCountriesList.add(infoModel);
                }
            }
        }

        findViewById(R.id.networks_title).setOnClickListener(v-> finish());
        saveText = findViewById(R.id.filter_save_id);
        saveText.setOnClickListener(v->{
            if(saveStateChanged) {
                int filterType = getIntent().getExtras().getInt("filter_type", 0);
                if(filterType == 0) ActionState.setNetworksFilter(selectedNetworks);
                else TransactionState.setTransactionNetworksFilter(selectedNetworks);
                finish();
            }
        });

        TextView networksInCountries = findViewById(R.id.networks_in_countries);
        TextView networkInOtherCountries = findViewById(R.id.networks_in_other_countries);

        RecyclerView networksInCountryRecyclerView = findViewById(R.id.filter_recyclerView_1);
        RecyclerView networksInOtherCountryRecyclerView = findViewById(R.id.filter_recyclerView_2);

        networksInCountryRecyclerView.setLayoutManager(UIHelper.setMainLinearManagers(this));
        networksInOtherCountryRecyclerView.setLayoutManager(UIHelper.setMainLinearManagers(this));
        networksInCountryRecyclerView.setHasFixedSize(true);
        networksInOtherCountryRecyclerView.setHasFixedSize(true);


        int countryListSize = ActionState.getCountriesFilter().size();
        if(countryListSize>0) {
            for(int i=0; i <countryListSize; i++) {
                if(i == countryListSize-1) {
                    networksInCountries.append(ActionState.getCountriesFilter().get(i));
                }
                else networksInCountries.append(ActionState.getCountriesFilter().get(i)+", ");
            }
            FilterSingleItemRecyclerAdapter filterSingleItemRecyclerAdapter = new FilterSingleItemRecyclerAdapter(networkInselectedCountriesList, this);
            networksInCountryRecyclerView.setAdapter(filterSingleItemRecyclerAdapter);
        }
        else {
            networksInCountries.setVisibility(View.GONE);
            FilterSingleItemRecyclerAdapter filterSingleItemRecyclerAdapter = new FilterSingleItemRecyclerAdapter(networkList, this);
            networksInCountryRecyclerView.setAdapter(filterSingleItemRecyclerAdapter);
        }

        if(networkOutsideSelectedCountriesList.size()>0) {
            networksInOtherCountryRecyclerView.setVisibility(View.VISIBLE);
            networkInOtherCountries.setVisibility(View.VISIBLE);

            networkInOtherCountries.setText(MessageFormat.format("+ {0} in other countries", networkOutsideSelectedCountriesList.size()));
            FilterSingleItemRecyclerAdapter filterSingleItemRecyclerAdapter = new FilterSingleItemRecyclerAdapter(networkInselectedCountriesList, this, true);
            networksInOtherCountryRecyclerView.setAdapter(filterSingleItemRecyclerAdapter);
        }


    }

    @Override
    public void customClickListener(Object... data) {
        if(!saveStateChanged) {
            saveText.setTextColor(getResources().getColor(R.color.colorHoverWhite));
            UIHelper.setTextUnderline(saveText, "Save");
            saveStateChanged = true;
        }
        String networkClicked = (String) data[0];
        boolean checked = (boolean) data[1];

        if(checked) selectedNetworks.add(networkClicked);
        else if(selectedNetworks.indexOf(networkClicked) != -1) selectedNetworks.remove(networkClicked);
    }
}
