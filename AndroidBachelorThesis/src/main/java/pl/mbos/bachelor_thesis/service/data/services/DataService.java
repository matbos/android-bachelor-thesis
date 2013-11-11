package pl.mbos.bachelor_thesis.service.data.services;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.dao.query.QueryBuilder;
import pl.mbos.bachelor_thesis.BaseApplication;
import pl.mbos.bachelor_thesis.dao.Attention;
import pl.mbos.bachelor_thesis.dao.AttentionDao;
import pl.mbos.bachelor_thesis.dao.Blink;
import pl.mbos.bachelor_thesis.dao.BlinkDao;
import pl.mbos.bachelor_thesis.dao.DaoMaster;
import pl.mbos.bachelor_thesis.dao.DaoSession;
import pl.mbos.bachelor_thesis.dao.Meditation;
import pl.mbos.bachelor_thesis.dao.MeditationDao;
import pl.mbos.bachelor_thesis.dao.PoorSignal;
import pl.mbos.bachelor_thesis.dao.PoorSignalDao;
import pl.mbos.bachelor_thesis.dao.PowerEEG;
import pl.mbos.bachelor_thesis.dao.PowerEEGDao;

/**
 * Service object that handles data manipulation
 */
public class DataService implements IDataService {

    @Inject
    Context context;
    private static final String TAG = DataService.class.getSimpleName();
    private IDataServiceParent parent;

    private static DaoMaster.DevOpenHelper dbHelper;
    private static DaoMaster daoMaster;

    public DataService(IDataServiceParent parent) {
        BaseApplication.getBaseGraph().inject(this);
        this.parent = parent;
        initDB();
    }


    @Override
    public ArrayList<Attention> getAllAttentionData() {
        DaoSession daoSession = daoMaster.newSession();
        AttentionDao attentionDao = daoSession.getAttentionDao();
        List<Attention> data = attentionDao.queryBuilder().orderAsc(AttentionDao.Properties.CollectionDate).list();
        return new ArrayList<Attention>(data);
    }

    @Override
    public ArrayList<Meditation> getAllMeditationData() {
        DaoSession daoSession = daoMaster.newSession();
        MeditationDao dao = daoSession.getMeditationDao();
        List<Meditation> data = dao.queryBuilder().orderAsc(MeditationDao.Properties.CollectionDate).list();
        return new ArrayList<Meditation>(data);
    }

    @Override
    public ArrayList<Blink> getAllBlinkData() {
        DaoSession daoSession = daoMaster.newSession();
        BlinkDao dao = daoSession.getBlinkDao();
        List<Blink> data = dao.queryBuilder().orderAsc(BlinkDao.Properties.CollectionDate).list();
        return new ArrayList<Blink>(data);
    }

    @Override
    public ArrayList<PowerEEG> getAllPowerData() {
        DaoSession daoSession = daoMaster.newSession();
        PowerEEGDao dao = daoSession.getPowerEEGDao();
        List<PowerEEG> data = dao.queryBuilder().orderAsc(PowerEEGDao.Properties.CollectionDate).list();
        return new ArrayList<PowerEEG>(data);
    }

    @Override
    public ArrayList<PoorSignal> getAllPoorSignalData() {
        DaoSession daoSession = daoMaster.newSession();
        PoorSignalDao dao = daoSession.getPoorSignalDao();
        List<PoorSignal> data = dao.queryBuilder().orderAsc(PoorSignalDao.Properties.CollectionDate).list();
        return new ArrayList<PoorSignal>(data);
    }

    @Override
    public void addAttentionData(Attention data) {
        saveAttention(data);
    }

    @Override
    public void addMeditationData(Meditation data) {
        saveMeditation(data);
    }

    @Override
    public void addBlinkData(Blink data) {
        saveBlink(data);
    }

    @Override
    public void addPowerData(PowerEEG data) {
        savePower(data);
    }

    @Override
    public void addPoorSignalData(PoorSignal data) {
        savePoorSignal(data);
    }

    private void saveAttention(Attention data){
        DaoSession daoSession = daoMaster.newSession();
        AttentionDao attentionDao = daoSession.getAttentionDao();
        attentionDao.insert(data);
    }

    private void saveMeditation(Meditation data){
        DaoSession daoSession = daoMaster.newSession();
        MeditationDao dao = daoSession.getMeditationDao();
        dao.insert(data);
    }

    private void saveBlink(Blink data){
        DaoSession daoSession = daoMaster.newSession();
        BlinkDao dao = daoSession.getBlinkDao();
        dao.insert(data);
    }

    public void savePower(PowerEEG data){
        DaoSession daoSession = daoMaster.newSession();
        PowerEEGDao dao = daoSession.getPowerEEGDao();
        dao.insert(data);
    }

    private void savePoorSignal(PoorSignal data){
        DaoSession daoSession = daoMaster.newSession();
        PoorSignalDao dao = daoSession.getPoorSignalDao();
        dao.insert(data);
    }

    private void initDB(){
        dbHelper = new DaoMaster.DevOpenHelper(context, "data-db",null);
        daoMaster = new DaoMaster(dbHelper.getWritableDatabase());
    }
}
