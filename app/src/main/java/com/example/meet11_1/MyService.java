//package com.example.meet11_1;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.os.RemoteException;
//
//public class MyService extends Service {
//
//    private String text;
//
//    public MyService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new IMyAidlInterface.Stub() {
//
//            @Override
//            public void putNecessaryInformation(String NecessaaryInformation) throws RemoteException {
//                text = NecessaaryInformation;
//            }
//
//            @Override
//            public String getNecessaryInformation() throws RemoteException {
//                return text;
//            }
//        };
//    }
//}
