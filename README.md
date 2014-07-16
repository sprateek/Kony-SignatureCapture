Kony-SignatureCapture
=====================

Android FFI Module for Signature Capture. Targetted for Tablets

<b>Support:</b><br/>
Supported on API Level 11(Honeycomb) and above
To support mobile devices, change below in CaptureSignature onCreate as per appropriate layout width/height
LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(900, 500);

<b>Integration steps:</b><br/>
<b>Step 1:</b> Import kony_ffi_export\export_ffi.zip in Kony IDE
<br/>
<b>Step 2:</b> Add below under "application" section at Project Properties -> Native -> Android -> Tags
<br/>
\<activity android:theme="@android:style/Theme.Holo.Dialog" android:name="com.kony.prateek.ffi.CaptureSignature" android:permission="com.testpackage.mypermission" android:launchMode="singleTop"><br/>
  \<intent-filter>                 
    \<action android:name="android.intent.action.VIEW" />                 
    \<category android:name="android.intent.category.DEFAULT" />             
  \</intent-filter>         
\</activity>

<b>Usage:</b><br/>
Signature.capture(handleCallback)

function handleCallback(signatureSring){
	kony.print(signatureSring.signatureByteArray);
}

<b>Future Enhancements:</b><br/>
Change the layout params based on device type/JS input (Currently hardcoded to 900,500)
