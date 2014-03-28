Kony-SignatureCapture
=====================

Android FFI Module for Signature Capture. Targetted for Tablets

Support:
Supported on API Level 11(Honeycomb) and above
To support mobile devices, change below in CaptureSignature onCreate as per appropriate layout width/height
LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(900, 500);

Integration steps:
Step 1: Import kony_ffi_export\export_ffi.zip in Kony IDE
Step 2: Add below under <application> at Project Properties -> Native -> Android -> Tags
<activity android:theme="@android:style/Theme.Holo.Dialog" android:name="com.kony.prateek.ffi.CaptureSignature" android:permission="com.testpackage.mypermission" android:launchMode="singleTop">
  <intent-filter>                 
    <action android:name="android.intent.action.VIEW" />                 
    <category android:name="android.intent.category.DEFAULT" />             
  </intent-filter>         
</activity>
Step 3: 

Usage:
Signature.capture(handleCallback)

function handleCallback(signatureSring){
	kony.print(signatureSring.signatureByteArray);
}

Future Enhancements:
Change the layout params based on device type/JS input (Currently hardcoded to 900,500)
