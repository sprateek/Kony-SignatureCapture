package com.kony.prateek.ffi;

import android.content.Intent;

import com.konylabs.android.KonyMain;
import com.konylabs.vm.Function;

public class SignatureWrapper
{
  public static void captureSignature(Function paramFunction)
  {
    System.out.println("****captureSignature****");
    KonyMain localKonyMain = KonyMain.getActivityContext();
    Intent localIntent = new Intent(localKonyMain, CaptureSignature.class);
    localIntent.putExtra("callback", paramFunction);
    localKonyMain.startActivity(localIntent);
  }
}