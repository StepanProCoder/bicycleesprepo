package com.staple.probkaesp.fetcher.interactor;

import android.content.Context;
import android.util.Log;
import android.util.MutableBoolean;

import androidx.lifecycle.MutableLiveData;

import com.staple.probkaesp.handlers.DBHandler;
import com.staple.probkaesp.iretrofits.Esp8266Api;
import com.staple.probkaesp.utils.mdns.NsdDiscovery;
import com.staple.probkaesp.R;
import com.staple.probkaesp.handlers.ResponseGetHandler;
import com.staple.probkaesp.handlers.ResponsePostHandler;
import com.staple.probkaesp.utils.saveloadprefs.SaveLoadResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetcherInteractor
{
    private HashMap<String, Esp8266Api> ipIdMap = new HashMap<>();
    private NsdDiscovery nsdDiscovery;
    private MapInteractor mapInteractor = new MapInteractor();
    private String curUUId;
    private File jsonFile;
    private Boolean eraseFlag = false;
    private MutableLiveData<Boolean> isGetOrPost = new MutableLiveData(false);
    private Timer timer = new Timer();
    private DBHandler dbHandler;
    private Context fetcherContext;

    public FetcherInteractor(Context context, Runnable shifting)
    {
        fetcherContext = context;
        onInit(isGetOrPost, shifting);
    }

    public void initializeEsp8266Api(String hostName, String ipAddress)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ipIdMap.put(hostName, retrofit.create(Esp8266Api.class));
    }

    public void onInit(MutableLiveData<Boolean> isGetOrPost, Runnable shifting)
    {
        curUUId = SaveLoadResult.loadResult("SystemSensors", "uuid", fetcherContext);
        jsonFile = new File(fetcherContext.getCacheDir(), fetcherContext.getString(R.string.json_path));
        if (jsonFile.exists())
        {
            Log.d("JSONFILE", loadJsonFromCache(jsonFile));

            dbHandler = new DBHandler(fetcherContext);
            dbHandler.updateDB();

            nsdDiscovery = new NsdDiscovery(this, fetcherContext);
            nsdDiscovery.startDiscovery();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run()
                {
                    updateState(isGetOrPost, shifting);
                }
            }, 0, 3000);

        }
        else
        {
            deleteFile();
            shifting.run();
        }

    }

    private Boolean updateState(MutableLiveData<Boolean> isGetOrPost, Runnable shifting)
    {
        nsdDiscovery.stopDiscovery();
        nsdDiscovery.startDiscovery();

        for (Map.Entry<String, Esp8266Api> entry : ipIdMap.entrySet())
        {
            Log.d("ENTRY", entry.getKey());
            if (entry.getValue() != null)
            {
                fetchSwitchStatus(isGetOrPost, entry, shifting);
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    public Boolean deleteFile()
    {
        return jsonFile.delete();
    }

    public void stopTimer()
    {
        timer.cancel();
    }

    public void setIsGetOrPost(boolean value)
    {
        this.isGetOrPost.setValue(value);
    }

    public void setEraseFlag(boolean value)
    {
        this.eraseFlag = value;
    }

    private void fetchSwitchStatus(MutableLiveData<Boolean> mode, Map.Entry<String, Esp8266Api> entry, Runnable shifting)
    {
        if(mode.getValue())
        {
            Log.d("RETROFIT","GET");
            entry.getValue().getSensorData(mapInteractor.currentGeoPoint.getLatitude(), mapInteractor.currentGeoPoint.getLongitude()).enqueue(new ResponseGetHandler(entry.getKey(), mode, dbHandler));
        }
        else
        {
            Log.d("FETCH",curUUId);
            if(entry.getKey().equals(curUUId))
            {
                Log.d("RETROFIT", "POST");
                RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), eraseFlag? "ERASE": loadJsonFromCache(jsonFile));
                entry.getValue().
                        postConfig(requestBody).
                        enqueue(new ResponsePostHandler(mode, () -> {
                            timer.cancel();
                            deleteFile();
                            shifting.run();
                        }));
                eraseFlag = false;
            }
        }
    }

    private String loadJsonFromCache(File file)
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
