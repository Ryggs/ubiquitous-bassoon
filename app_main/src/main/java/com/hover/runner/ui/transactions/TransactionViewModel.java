package com.hover.runner.ui.transactions;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hover.runner.ApplicationInstance;
import com.hover.runner.api.Apis;
import com.hover.runner.enums.HomeEnums;
import com.hover.runner.enums.StatusEnums;
import com.hover.runner.models.FullTransactionResult;
import com.hover.runner.states.ActionState;
import com.hover.runner.states.TransactionState;


public class TransactionViewModel extends ViewModel {

	private MutableLiveData<HomeEnums> filterStatus;
	private MutableLiveData<FullTransactionResult> homeTransactions;


	public TransactionViewModel() {
		filterStatus = new MutableLiveData<>();
		homeTransactions = new MutableLiveData<>();

		filterStatus.setValue(HomeEnums.FILTER_OFF);
		homeTransactions.setValue(new FullTransactionResult(StatusEnums.LOADING, null));
	}

	LiveData<HomeEnums> getText() {
		return filterStatus;
	}
	LiveData<FullTransactionResult> loadTransactionsObs() {return homeTransactions;}

	void setFilterOn() {
		filterStatus.postValue(HomeEnums.FILTER_ON);
	}
	void getAllTransactions() {
		if(new TransactionState().isTransactionInDefaultState()) {
			Log.d("HOMER", "empty filter final result");

			filterStatus.postValue(HomeEnums.FILTER_OFF);
			homeTransactions.postValue(new Apis().doGetAllTransactionsWorkManager());
		}
		else {
			Log.d("HOMER", "has good filter results");
			filterStatus.postValue(HomeEnums.FILTER_ON);
			homeTransactions.postValue(new FullTransactionResult(StatusEnums.HAS_DATA, TransactionState.getResultFilter_Transactions_LOAD()));
		}

	}
	void getTransactionByActionId(String actionId) {homeTransactions.postValue(new Apis().doGetTransactionsByActionIdWorkManager(actionId)); }
}