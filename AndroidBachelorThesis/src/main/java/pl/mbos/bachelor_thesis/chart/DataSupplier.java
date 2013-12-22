package pl.mbos.bachelor_thesis.chart;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.service.data.connector.data.DataServiceClient;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnection;
import pl.mbos.bachelor_thesis.service.data.contract.IDataServiceConnectionListener;
import pl.mbos.bachelor_thesis.service.data.services.IDataService;
import zeitdata.charts.model.TimeData;
import zeitdata.charts.model.TimePoint;
import zeitdata.charts.model.TimeSeries;

/**
 * Created by Mateusz on 18.12.13.
 */
public class DataSupplier implements IDataSupplier, IDataServiceConnectionListener {

    @Inject
    IDataServiceConnection serviceClient;

    List<Attention> attention;
    boolean await;

    public DataSupplier(){
        BaseApplication.getBaseGraph().inject(this);
        serviceClient.registerListener(this);
    }

    @Override
    public TimeData getAttentionData() {
        await = true;
        serviceClient.requestAllAttentionData();
        while(await){
            try {
                Thread.sleep(100,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TimeSeries.Builder seriesBuilder = new TimeSeries.Builder().withTitle("Attention");
        for(Attention a : attention){
            seriesBuilder.addPoint(new TimePoint.Builder(a.getCollectionDate(),a.getValue()).build());
        }
        TimeData td = new TimeData.Builder().addSeries(seriesBuilder.build()).build();
        return td;
    }

    @Override
    public void receivedAttentionData(List<Attention> data) {
        attention = data;
        await = false;
    }

    @Override
    public void receivedMeditationData(List<Meditation> data) {

    }

    @Override
    public void receivedBlinkData(List<Blink> data) {

    }

    @Override
    public void receivedPowerData(List<PowerEEG> data) {

    }

    @Override
    public void receivedPoorSignalData(List<PoorSignal> data) {

    }
}
