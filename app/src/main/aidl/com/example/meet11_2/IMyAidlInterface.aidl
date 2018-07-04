// IMyAidlInterface.aidl
package com.example.meet11_2;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void putNecessaryInformation(String NecessaaryInformation);

   String getNecessaryInformation();
}
