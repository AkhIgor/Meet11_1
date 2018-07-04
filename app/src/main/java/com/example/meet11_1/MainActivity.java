package com.example.meet11_1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meet11_2.IMyAidlInterface;

import java.util.List;

/**
 *  Класс является "клиентом". Класс биндится к сервису и по нажатию кнопки текст записываются в
 * переменную сервиса.
 *  Метод createExplicitIntent служит для поиска сервиса "приложения-сервера". С помошью PackageManager
 *  получаем все службы, соответствующие заданному intent. С помощью его получаем информацию о
 *  имени пакета и класса, далее на их основе создается компонет для создания нового интента.
 */

public class MainActivity extends AppCompatActivity {

    public static final String AIDL = "com.example.meet11_2.aidl.IMyAidlInterface";
    private EditText someData;
    private Button save;
    private IMyAidlInterface dataInterface;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            dataInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            dataInterface = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new  Intent(AIDL);
        Intent updatedIntent = createExplicitIntent(this, intent);
        if (updatedIntent != null) {
            bindService(updatedIntent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        someData = (EditText) findViewById(R.id.editText);
        save = (Button) findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();
            }
        });
    }

    private void getText() {
        try {
            dataInterface.putNecessaryInformation(someData.getText().toString());
        }
        catch (RemoteException ignored) {}
    }

    public Intent createExplicitIntent(Context context, Intent intent) {

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(intent, 0);

        if (resolveInfo == null || resolveInfo.size() != 1) {

            return null;
        }

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);


        Intent explicitIntent = new Intent(intent);

        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
