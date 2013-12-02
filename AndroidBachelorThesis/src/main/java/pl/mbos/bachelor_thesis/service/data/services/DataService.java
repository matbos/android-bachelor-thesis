package pl.mbos.bachelor_thesis.service.data.services;

import android.content.Context;
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

    static long counter = 0;
    static long mCounter = 0;

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

    protected void retrieveData(DataSyncPackage pack) {
        DataPackage dataPackage = new DataPackage();
        SyncTimes req = pack.getRequested();
        dataPackage.attentions = getAttentionDataSince(req.attention);
        dataPackage.meditations = getMeditationDataSince(req.meditation);
        dataPackage.blinks = getBlinkDataSince(req.blink);
        dataPackage.powers = getPowerEEGDataSince(req.power);
        dataPackage.signals = getPoorSignalDataSince(req.poorSignal);
        pack.setData(dataPackage);
    }

    private void saveAttention(Attention data) {
        if (data != null) {
            counter++;
            Log.d("CTR", "ad " + counter);
            DaoSession daoSession = daoMaster.newSession();
            AttentionDao attentionDao = daoSession.getAttentionDao();
            attentionDao.insert(data);
        }
    }

    private void saveMeditation(Meditation data) {
        if (data != null) {
            mCounter ++;
            Log.d("CTR","md "+mCounter);
            DaoSession daoSession = daoMaster.newSession();
            MeditationDao dao = daoSession.getMeditationDao();
            dao.insert(data);
        }
    }

    private void saveBlink(Blink data) {
        if (data != null) {
            DaoSession daoSession = daoMaster.newSession();
            BlinkDao dao = daoSession.getBlinkDao();
            dao.insert(data);
        }
    }

    private void savePower(PowerEEG data) {
        if (data != null) {
            DaoSession daoSession = daoMaster.newSession();
            PowerEEGDao dao = daoSession.getPowerEEGDao();
            dao.insert(data);
        }
    }

    private void savePoorSignal(PoorSignal data) {
        if (data != null) {
            DaoSession daoSession = daoMaster.newSession();
            PoorSignalDao dao = daoSession.getPoorSignalDao();
            dao.insert(data);
        }
    }

    private void initDB() {
        dbHelper = new DaoMaster.DevOpenHelper(context, "data-db", null);
        daoMaster = new DaoMaster(dbHelper.getWritableDatabase());
    }

    private List<Attention> getAttentionDataSince(Date date) {

        DaoSession daoSession = daoMaster.newSession();
        AttentionDao attentionDao = daoSession.getAttentionDao();
        QueryBuilder<Attention> qb = attentionDao.queryBuilder().where(AttentionDao.Properties.CollectionDate.gt(date)).orderAsc(AttentionDao.Properties.CollectionDate);
        List<Attention> data = qb.list();
        return data;
    }

    private List<Meditation> getMeditationDataSince(Date date) {
        DaoSession daoSession = daoMaster.newSession();
        MeditationDao dao = daoSession.getMeditationDao();
        QueryBuilder<Meditation> qb = dao.queryBuilder().where(MeditationDao.Properties.CollectionDate.gt(date)).orderAsc(MeditationDao.Properties.CollectionDate);
        List<Meditation> data = qb.list();
        return data;
    }

    private List<Blink> getBlinkDataSince(Date date) {
        DaoSession daoSession = daoMaster.newSession();
        BlinkDao dao = daoSession.getBlinkDao();
        QueryBuilder<Blink> qb = dao.queryBuilder().where(BlinkDao.Properties.CollectionDate.gt(date)).orderAsc(BlinkDao.Properties.CollectionDate);
        List<Blink> data = qb.list();
        return data;
    }

    private List<PoorSignal> getPoorSignalDataSince(Date date) {
        DaoSession daoSession = daoMaster.newSession();
        PoorSignalDao dao = daoSession.getPoorSignalDao();
        QueryBuilder<PoorSignal> qb = dao.queryBuilder().where(PoorSignalDao.Properties.CollectionDate.gt(date)).orderAsc(PoorSignalDao.Properties.CollectionDate);
        List<PoorSignal> data = qb.list();
        return data;
    }

    private List<PowerEEG> getPowerEEGDataSince(Date date) {
        DaoSession daoSession = daoMaster.newSession();
        PowerEEGDao dao = daoSession.getPowerEEGDao();
        QueryBuilder<PowerEEG> qb = dao.queryBuilder().where(PowerEEGDao.Properties.CollectionDate.gt(date)).orderAsc(PowerEEGDao.Properties.CollectionDate);
        List<PowerEEG> data = qb.list();
        return data;
    }

}
