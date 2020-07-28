package com.hover.runner.ui.transactionDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hover.runner.api.Apis;
import com.hover.runner.models.TransactionDetailsInfoModels;
import com.hover.runner.models.TransactionDetailsMessagesModel;

import java.util.ArrayList;

public class TransactionDetailsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TransactionDetailsInfoModels>> aboutInfoModels;
    private MutableLiveData<ArrayList<TransactionDetailsInfoModels>> deviceModels;
    private MutableLiveData<ArrayList<TransactionDetailsInfoModels>> debugInfoModels;
    private MutableLiveData<ArrayList<TransactionDetailsMessagesModel>> messagesModel;

    public TransactionDetailsViewModel() {
        aboutInfoModels = new MutableLiveData<>();
        deviceModels = new MutableLiveData<>();
        debugInfoModels = new MutableLiveData<>();
        messagesModel = new MutableLiveData<>();

        aboutInfoModels.setValue(null);
        deviceModels.setValue(null);
        debugInfoModels.setValue(null);
        messagesModel.setValue(null);
    }

    LiveData<ArrayList<TransactionDetailsInfoModels>> loadAboutInfoModelsObs() {return aboutInfoModels; }
    LiveData<ArrayList<TransactionDetailsInfoModels>> loadDeviceModelsObs() {return deviceModels; }
    LiveData<ArrayList<TransactionDetailsInfoModels>> loadDebugInfoModelsObs() {return debugInfoModels; }
    LiveData<ArrayList<TransactionDetailsMessagesModel>> loadMessagesModelObs() {return messagesModel; }

    void getAboutInfoModels(String transactionId) {aboutInfoModels.postValue
            (new Apis().getTransactionDetailsAboutById(transactionId));}

    void getDeviceModels(String transactionId) {deviceModels.postValue
            (new Apis().getTransactionDetailsDeviceById(transactionId));}

    void getDebugInfoModels(String transactionId) {debugInfoModels.postValue
            (new Apis().getTransactionDetailsDebugInfoById(transactionId));}

    void getMessagesModels(String transactionId) {
        messagesModel.postValue(new Apis().getMessagesOfTransactionById(transactionId));
    }
}
