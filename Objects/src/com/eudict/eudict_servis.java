package com.eudict;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class eudict_servis extends  android.app.Service{
	public static class eudict_servis_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (eudict_servis) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, eudict_servis.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static eudict_servis mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return eudict_servis.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "com.eudict", "com.eudict.eudict_servis");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "com.eudict.eudict_servis", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (eudict_servis) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (eudict_servis) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (eudict_servis) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (false) {
            BA.LogInfo("** Service (eudict_servis) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (eudict_servis) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.RemoteViewsWrapper _vv6 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvvvvvv4 = null;
public com.eudict.main _vvvvvvvvv5 = null;
public com.eudict.starter _vvvvvvvvv6 = null;
public com.eudict.postavke _vvvvvvvvv7 = null;
public com.eudict.dlgtrazi _vvvvvvvvv0 = null;
public com.eudict.zadnji _vvvvvvvvvv1 = null;
public static String  _vvvvvvvvvv3() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 299;BA.debugLine="Sub ApdejtajStatus1";
 //BA.debugLineNum = 300;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 302;BA.debugLine="If mapa.Get(\"eudict\") Then";
if (BA.ObjectToBoolean(_mapa.Get((Object)("eudict")))) { 
 //BA.debugLineNum = 303;BA.debugLine="rv.SetText(\"lblStatus\", \"Eudict riječnici\")";
_vv6.SetText(processBA,"lblStatus",BA.ObjectToCharSequence("Eudict riječnici"));
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("HRVenc")))) { 
 //BA.debugLineNum = 305;BA.debugLine="rv.SetText(\"lblStatus\", \"Hrvatska enciklopedija\"";
_vv6.SetText(processBA,"lblStatus",BA.ObjectToCharSequence("Hrvatska enciklopedija"));
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("acta")))) { 
 //BA.debugLineNum = 307;BA.debugLine="rv.SetText(\"lblStatus\", \"Acta Croatica\")";
_vv6.SetText(processBA,"lblStatus",BA.ObjectToCharSequence("Acta Croatica"));
 }else {
 //BA.debugLineNum = 309;BA.debugLine="rv.SetText(\"lblStatus\", \"Hrvatski jezični portal";
_vv6.SetText(processBA,"lblStatus",BA.ObjectToCharSequence("Hrvatski jezični portal"));
 };
 //BA.debugLineNum = 312;BA.debugLine="rv.UpdateWidget";
_vv6.UpdateWidget(processBA);
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static String  _apdejtajstatus2() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 283;BA.debugLine="Sub ApdejtajStatus2";
 //BA.debugLineNum = 284;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 286;BA.debugLine="If mapa.Get(\"eudict\") Then";
if (BA.ObjectToBoolean(_mapa.Get((Object)("eudict")))) { 
 //BA.debugLineNum = 287;BA.debugLine="PrikaziZadnjiPrijevodUWidgetu";
_vvvvvvvvvv4();
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("HRVenc")))) { 
 //BA.debugLineNum = 289;BA.debugLine="PrikaziZadnjeIzHRVEnciklopedije";
_vvvvvvvvvv5();
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("acta")))) { 
 //BA.debugLineNum = 291;BA.debugLine="PrikaziZadnjeActaCroatica";
_vvvvvvvvvv6();
 }else {
 //BA.debugLineNum = 293;BA.debugLine="PrikaziZadnjuStranuRijec";
_vvvvvvvvvv7();
 };
 //BA.debugLineNum = 296;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return "";
}
public static String  _btnacta_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 63;BA.debugLine="Sub btnActa_Click";
 //BA.debugLineNum = 64;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 66;BA.debugLine="mapa.Put(\"acta\", True)";
_mapa.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 67;BA.debugLine="mapa.Put(\"eudict\", False)";
_mapa.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 68;BA.debugLine="mapa.Put(\"HRVenc\", False)";
_mapa.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 69;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
_mapa.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 70;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 72;BA.debugLine="ApdejtajStatus2";
_apdejtajstatus2();
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _btneudict_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 75;BA.debugLine="Sub btnEudict_Click";
 //BA.debugLineNum = 76;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 78;BA.debugLine="mapa.Put(\"eudict\", True)";
_mapa.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 79;BA.debugLine="mapa.Put(\"HRVenc\", False)";
_mapa.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 80;BA.debugLine="mapa.Put(\"acta\", False)";
_mapa.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 81;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
_mapa.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 82;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 84;BA.debugLine="ApdejtajStatus2";
_apdejtajstatus2();
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _btnhrvenc_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 87;BA.debugLine="Sub btnHrvEnc_Click";
 //BA.debugLineNum = 88;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 90;BA.debugLine="mapa.Put(\"HRVenc\", True)";
_mapa.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 91;BA.debugLine="mapa.Put(\"eudict\", False)";
_mapa.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 92;BA.debugLine="mapa.Put(\"acta\", False)";
_mapa.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 93;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
_mapa.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 94;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 96;BA.debugLine="ApdejtajStatus2";
_apdejtajstatus2();
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static String  _btnstranerijeci_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 99;BA.debugLine="Sub btnStraneRijeci_Click";
 //BA.debugLineNum = 100;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 102;BA.debugLine="mapa.Put(\"stranaRijec\", True)";
_mapa.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 103;BA.debugLine="mapa.Put(\"HRVenc\", False)";
_mapa.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 104;BA.debugLine="mapa.Put(\"eudict\", False)";
_mapa.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 105;BA.debugLine="mapa.Put(\"acta\", False)";
_mapa.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 106;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 108;BA.debugLine="ApdejtajStatus2";
_apdejtajstatus2();
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _imglink_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
String _s = "";
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 45;BA.debugLine="Sub imgLink_Click";
 //BA.debugLineNum = 46;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 47;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 50;BA.debugLine="If mapa.Get(\"eudict\") Then";
if (BA.ObjectToBoolean(_mapa.Get((Object)("eudict")))) { 
 //BA.debugLineNum = 51;BA.debugLine="s = File.ReadString(Starter.FilePath, \"ze_link\")";
_s = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"ze_link");
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("HRVenc")))) { 
 //BA.debugLineNum = 53;BA.debugLine="s = File.ReadString(Starter.FilePath, \"zenc_link";
_s = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"zenc_link");
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("acta")))) { 
 //BA.debugLineNum = 55;BA.debugLine="s = File.ReadString(Starter.FilePath, \"za_link\")";
_s = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"za_link");
 }else {
 //BA.debugLineNum = 57;BA.debugLine="s = File.ReadString(Starter.FilePath, \"zsr_link\"";
_s = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"zsr_link");
 };
 //BA.debugLineNum = 60;BA.debugLine="StartActivity (p.OpenBrowser(s))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser(_s)));
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _imgopcije_click() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub imgOpcije_Click";
 //BA.debugLineNum = 123;BA.debugLine="StartActivity(postavke)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv7.getObject()));
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _imgtrazi_click() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub imgTrazi_Click";
 //BA.debugLineNum = 113;BA.debugLine="StartActivity(dlgTrazi)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvv0.getObject()));
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _imgzadnjizapisi_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub imgZadnjiZapisi_Click";
 //BA.debugLineNum = 118;BA.debugLine="StartActivity(zadnji)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._vvvvvvvvvv1.getObject()));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv6() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
String _text = "";
String _s = "";
 //BA.debugLineNum = 209;BA.debugLine="Sub PrikaziZadnjeActaCroatica";
 //BA.debugLineNum = 210;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 212;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoAC\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoAC"))));
 //BA.debugLineNum = 213;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 214;BA.debugLine="If File.Exists(Starter.FilePath, \"ac\" & x) Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._vvvvvvvvv6._vv5,"ac"+BA.NumberToString(_x))) { 
 //BA.debugLineNum = 215;BA.debugLine="Dim text As String = File.ReadString(Starter.Fil";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"ac"+BA.NumberToString(_x));
 //BA.debugLineNum = 216;BA.debugLine="Dim s As String = text.SubString2(0, text.IndexO";
_s = _text.substring((int) (0),_text.indexOf(";"));
 //BA.debugLineNum = 217;BA.debugLine="rv.SetText(\"lblTrazena\", s)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 218;BA.debugLine="s = text.SubString2(text.IndexOf(\";\")+1, text.Le";
_s = _text.substring((int) (_text.indexOf(";")+1),_text.length());
 //BA.debugLineNum = 219;BA.debugLine="rv.SetText(\"lblPrijevod\", s)";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(_s));
 }else {
 //BA.debugLineNum = 221;BA.debugLine="rv.SetText(\"lblTrazena\", \"Nema traženog pojma!\")";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence("Nema traženog pojma!"));
 //BA.debugLineNum = 222;BA.debugLine="rv.SetText(\"lblPrijevod\", \"Nema traženog pojma!\"";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence("Nema traženog pojma!"));
 };
 //BA.debugLineNum = 225;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv5() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
String _text = "";
String _s = "";
 //BA.debugLineNum = 172;BA.debugLine="Sub PrikaziZadnjeIzHRVEnciklopedije";
 //BA.debugLineNum = 173;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 175;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoHE\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoHE"))));
 //BA.debugLineNum = 176;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 177;BA.debugLine="If File.Exists(Starter.FilePath, \"he\" & x) Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._vvvvvvvvv6._vv5,"he"+BA.NumberToString(_x))) { 
 //BA.debugLineNum = 178;BA.debugLine="Dim text As String = File.ReadString(Starter.Fil";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"he"+BA.NumberToString(_x));
 //BA.debugLineNum = 179;BA.debugLine="Dim s As String = text.SubString2(0, text.IndexO";
_s = _text.substring((int) (0),_text.indexOf(";"));
 //BA.debugLineNum = 180;BA.debugLine="rv.SetText(\"lblTrazena\", s)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 181;BA.debugLine="s = text.SubString2(text.IndexOf(\";\")+1, text.Le";
_s = _text.substring((int) (_text.indexOf(";")+1),_text.length());
 //BA.debugLineNum = 182;BA.debugLine="rv.SetText(\"lblPrijevod\", s)";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(_s));
 }else {
 //BA.debugLineNum = 184;BA.debugLine="rv.SetText(\"lblTrazena\", \"Nema traženog pojma!\")";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence("Nema traženog pojma!"));
 //BA.debugLineNum = 185;BA.debugLine="rv.SetText(\"lblPrijevod\", \"Nema traženog pojma!\"";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence("Nema traženog pojma!"));
 };
 //BA.debugLineNum = 188;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv4() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
String _text = "";
String _s = "";
 //BA.debugLineNum = 126;BA.debugLine="Sub PrikaziZadnjiPrijevodUWidgetu";
 //BA.debugLineNum = 127;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 129;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoP\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoP"))));
 //BA.debugLineNum = 130;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 131;BA.debugLine="If File.Exists(Starter.FilePath, \"p\" & x) Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._vvvvvvvvv6._vv5,"p"+BA.NumberToString(_x))) { 
 //BA.debugLineNum = 132;BA.debugLine="Dim text As String = File.ReadString(Starter.Fil";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"p"+BA.NumberToString(_x));
 //BA.debugLineNum = 133;BA.debugLine="Dim s As String = text.SubString2(0, text.IndexO";
_s = _text.substring((int) (0),_text.indexOf(" "));
 //BA.debugLineNum = 134;BA.debugLine="rv.SetText(\"lblTrazena\", s)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 135;BA.debugLine="rv.SetText(\"lblPrijevod\", text)";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(_text));
 }else {
 //BA.debugLineNum = 137;BA.debugLine="rv.SetText(\"lblTrazena\", \"Nema tražene riječi!\")";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence("Nema tražene riječi!"));
 //BA.debugLineNum = 138;BA.debugLine="rv.SetText(\"lblPrijevod\", \"Nema tražene riječi!\"";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence("Nema tražene riječi!"));
 };
 //BA.debugLineNum = 141;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv7() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
String _text = "";
String _s = "";
 //BA.debugLineNum = 246;BA.debugLine="Sub PrikaziZadnjuStranuRijec";
 //BA.debugLineNum = 247;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 249;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoRSR\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoRSR"))));
 //BA.debugLineNum = 250;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 251;BA.debugLine="If File.Exists(Starter.FilePath, \"rsr\" & x) Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._vvvvvvvvv6._vv5,"rsr"+BA.NumberToString(_x))) { 
 //BA.debugLineNum = 252;BA.debugLine="Dim text As String = File.ReadString(Starter.Fil";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"rsr"+BA.NumberToString(_x));
 //BA.debugLineNum = 253;BA.debugLine="Dim s As String = text.SubString2(0, text.IndexO";
_s = _text.substring((int) (0),_text.indexOf(";"));
 //BA.debugLineNum = 254;BA.debugLine="rv.SetText(\"lblTrazena\", s)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 255;BA.debugLine="s = text.SubString2(text.IndexOf(\";\")+1, text.Le";
_s = _text.substring((int) (_text.indexOf(";")+1),_text.length());
 //BA.debugLineNum = 256;BA.debugLine="rv.SetText(\"lblPrijevod\", s)";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(_s));
 }else {
 //BA.debugLineNum = 258;BA.debugLine="rv.SetText(\"lblTrazena\", \"Nema traženog pojma!\")";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence("Nema traženog pojma!"));
 //BA.debugLineNum = 259;BA.debugLine="rv.SetText(\"lblPrijevod\", \"Nema traženog pojma!\"";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence("Nema traženog pojma!"));
 };
 //BA.debugLineNum = 262;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim rv As RemoteViews";
_vv6 = new anywheresoftware.b4a.objects.RemoteViewsWrapper();
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _rv_disabled() throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub rv_Disabled";
 //BA.debugLineNum = 42;BA.debugLine="StopService(\"\")";
anywheresoftware.b4a.keywords.Common.StopService(processBA,(Object)(""));
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _rv_requestupdate() throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub rv_RequestUpdate";
 //BA.debugLineNum = 38;BA.debugLine="rv.UpdateWidget";
_vv6.UpdateWidget(processBA);
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 12;BA.debugLine="rv = ConfigureHomeWidget(\"eudict\", \"rv\", 30, \"Rij";
_vv6 = anywheresoftware.b4a.objects.RemoteViewsWrapper.createRemoteViews(processBA, R.layout.eudict_servis_layout, "eudict","rv");
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static void  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
ResumableSub_Service_Start rsub = new ResumableSub_Service_Start(null,_startingintent);
rsub.resume(processBA, null);
}
public static class ResumableSub_Service_Start extends BA.ResumableSub {
public ResumableSub_Service_Start(com.eudict.eudict_servis parent,anywheresoftware.b4a.objects.IntentWrapper _startingintent) {
this.parent = parent;
this._startingintent = _startingintent;
}
com.eudict.eudict_servis parent;
anywheresoftware.b4a.objects.IntentWrapper _startingintent;
anywheresoftware.b4a.objects.collections.Map _mapa = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 16;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 18;BA.debugLine="If mapa.Get(\"eudict\") Then";
if (true) break;

case 1:
//if
this.state = 10;
if (BA.ObjectToBoolean(_mapa.Get((Object)("eudict")))) { 
this.state = 3;
}else if(BA.ObjectToBoolean(_mapa.Get((Object)("HRVenc")))) { 
this.state = 5;
}else if(BA.ObjectToBoolean(_mapa.Get((Object)("acta")))) { 
this.state = 7;
}else {
this.state = 9;
}if (true) break;

case 3:
//C
this.state = 10;
 //BA.debugLineNum = 19;BA.debugLine="PrikaziZadnjiPrijevodUWidgetu";
_vvvvvvvvvv4();
 if (true) break;

case 5:
//C
this.state = 10;
 //BA.debugLineNum = 21;BA.debugLine="PrikaziZadnjeIzHRVEnciklopedije";
_vvvvvvvvvv5();
 if (true) break;

case 7:
//C
this.state = 10;
 //BA.debugLineNum = 23;BA.debugLine="PrikaziZadnjeActaCroatica";
_vvvvvvvvvv6();
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 25;BA.debugLine="PrikaziZadnjuStranuRijec";
_vvvvvvvvvv7();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 28;BA.debugLine="rv.HandleWidgetEvents(StartingIntent)";
parent._vv6.HandleWidgetEvents(processBA,(android.content.Intent)(_startingintent.getObject()));
 //BA.debugLineNum = 29;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (0));
this.state = 11;
return;
case 11:
//C
this.state = -1;
;
 //BA.debugLineNum = 30;BA.debugLine="Service.StopAutomaticForeground";
parent.mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _ubaciactacroatica() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
 //BA.debugLineNum = 228;BA.debugLine="Sub UbaciActaCroatica";
 //BA.debugLineNum = 229;BA.debugLine="rv.SetText(\"lblTrazena\", dlgTrazi.pojamActa)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vvv3));
 //BA.debugLineNum = 230;BA.debugLine="rv.SetText(\"lblPrijevod\", dlgTrazi.pojamActaOpis)";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vvv4));
 //BA.debugLineNum = 232;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 233;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoAC\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoAC"))));
 //BA.debugLineNum = 234;BA.debugLine="File.WriteString(Starter.FilePath, \"ac\" & x, dlgT";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"ac"+BA.NumberToString(_x),mostCurrent._vvvvvvvvv0._vvv3+";"+mostCurrent._vvvvvvvvv0._vvv4);
 //BA.debugLineNum = 235;BA.debugLine="x = x + 1";
_x = (int) (_x+1);
 //BA.debugLineNum = 236;BA.debugLine="If x = 10 Then x = 1";
if (_x==10) { 
_x = (int) (1);};
 //BA.debugLineNum = 237;BA.debugLine="mapa.Put(\"ukupnoAC\", x)";
_mapa.Put((Object)("ukupnoAC"),(Object)(_x));
 //BA.debugLineNum = 238;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 239;BA.debugLine="File.WriteString(Starter.FilePath, \"za_link\", dlg";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"za_link",mostCurrent._vvvvvvvvv0._z_acta_link);
 //BA.debugLineNum = 241;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 243;BA.debugLine="rv_RequestUpdate";
_rv_requestupdate();
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _ubacihrvencpojam() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
 //BA.debugLineNum = 191;BA.debugLine="Sub UbaciHRVencPojam";
 //BA.debugLineNum = 192;BA.debugLine="rv.SetText(\"lblTrazena\", dlgTrazi.pojamHRVenc)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vvv1));
 //BA.debugLineNum = 193;BA.debugLine="rv.SetText(\"lblPrijevod\", dlgTrazi.pojamHRVencTek";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vvv2));
 //BA.debugLineNum = 195;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 196;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoHE\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoHE"))));
 //BA.debugLineNum = 197;BA.debugLine="File.WriteString(Starter.FilePath, \"he\" & x, dlgT";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"he"+BA.NumberToString(_x),mostCurrent._vvvvvvvvv0._vvv1+";"+mostCurrent._vvvvvvvvv0._vvv2);
 //BA.debugLineNum = 198;BA.debugLine="x = x + 1";
_x = (int) (_x+1);
 //BA.debugLineNum = 199;BA.debugLine="If x = 10 Then x = 1";
if (_x==10) { 
_x = (int) (1);};
 //BA.debugLineNum = 200;BA.debugLine="mapa.Put(\"ukupnoHE\", x)";
_mapa.Put((Object)("ukupnoHE"),(Object)(_x));
 //BA.debugLineNum = 201;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 202;BA.debugLine="File.WriteString(Starter.FilePath, \"zenc_link\", d";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"zenc_link",mostCurrent._vvvvvvvvv0._hrv_enc_link);
 //BA.debugLineNum = 204;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 206;BA.debugLine="rv_RequestUpdate";
_rv_requestupdate();
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _ubaciprijevod() throws Exception{
String _s = "";
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _i = 0;
int _x = 0;
 //BA.debugLineNum = 144;BA.debugLine="Sub UbaciPrijevod";
 //BA.debugLineNum = 145;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 146;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 148;BA.debugLine="If dlgTrazi.rijecG.Size > 0 Then";
if (mostCurrent._vvvvvvvvv0._vv7.getSize()>0) { 
 //BA.debugLineNum = 149;BA.debugLine="rv.SetText(\"lblTrazena\", dlgTrazi.rijecG.Get(0))";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vv7.Get((int) (0))));
 //BA.debugLineNum = 150;BA.debugLine="For i = 0 To dlgTrazi.prijevodG.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._vvvvvvvvv0._vv0.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 151;BA.debugLine="s = s & dlgTrazi.rijecG.Get(i) & \" → \" & dlgTraz";
_s = _s+BA.ObjectToString(mostCurrent._vvvvvvvvv0._vv7.Get(_i))+" → "+BA.ObjectToString(mostCurrent._vvvvvvvvv0._vv0.Get(_i))+anywheresoftware.b4a.keywords.Common.CRLF;
 }
};
 //BA.debugLineNum = 153;BA.debugLine="If dlgTrazi.prijevodG.Size > 0 Then";
if (mostCurrent._vvvvvvvvv0._vv0.getSize()>0) { 
 //BA.debugLineNum = 154;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoP\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoP"))));
 //BA.debugLineNum = 155;BA.debugLine="File.WriteString(Starter.FilePath, \"p\" & x, s)";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"p"+BA.NumberToString(_x),_s);
 //BA.debugLineNum = 156;BA.debugLine="x = x + 1";
_x = (int) (_x+1);
 //BA.debugLineNum = 157;BA.debugLine="If x = 10 Then x = 1";
if (_x==10) { 
_x = (int) (1);};
 //BA.debugLineNum = 158;BA.debugLine="mapa.Put(\"ukupnoP\", x)";
_mapa.Put((Object)("ukupnoP"),(Object)(_x));
 //BA.debugLineNum = 159;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 };
 //BA.debugLineNum = 162;BA.debugLine="File.writestring(Starter.FilePath, \"ze_link\", dlg";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"ze_link",mostCurrent._vvvvvvvvv0._z_eudict_link);
 //BA.debugLineNum = 164;BA.debugLine="rv.SetText(\"lblPrijevod\", s)";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 166;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 168;BA.debugLine="rv_RequestUpdate";
_rv_requestupdate();
 };
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _ubacistranurijec() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
 //BA.debugLineNum = 265;BA.debugLine="Sub UbaciStranuRijec";
 //BA.debugLineNum = 266;BA.debugLine="rv.SetText(\"lblTrazena\", dlgTrazi.stranaRijec)";
_vv6.SetText(processBA,"lblTrazena",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vvv5));
 //BA.debugLineNum = 267;BA.debugLine="rv.SetText(\"lblPrijevod\", dlgTrazi.stranaRijecOpi";
_vv6.SetText(processBA,"lblPrijevod",BA.ObjectToCharSequence(mostCurrent._vvvvvvvvv0._vvv6));
 //BA.debugLineNum = 269;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 270;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoRSR\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoRSR"))));
 //BA.debugLineNum = 271;BA.debugLine="File.WriteString(Starter.FilePath, \"rsr\" & x, dlg";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"rsr"+BA.NumberToString(_x),mostCurrent._vvvvvvvvv0._vvv5+";"+mostCurrent._vvvvvvvvv0._vvv6);
 //BA.debugLineNum = 272;BA.debugLine="x = x + 1";
_x = (int) (_x+1);
 //BA.debugLineNum = 273;BA.debugLine="If x = 10 Then x = 1";
if (_x==10) { 
_x = (int) (1);};
 //BA.debugLineNum = 274;BA.debugLine="mapa.Put(\"ukupnoRSR\", x)";
_mapa.Put((Object)("ukupnoRSR"),(Object)(_x));
 //BA.debugLineNum = 275;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",_mapa);
 //BA.debugLineNum = 276;BA.debugLine="File.WriteString(Starter.FilePath, \"zsr_link\", dl";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"zsr_link",mostCurrent._vvvvvvvvv0._strana_rijec_link);
 //BA.debugLineNum = 278;BA.debugLine="ApdejtajStatus1";
_vvvvvvvvvv3();
 //BA.debugLineNum = 280;BA.debugLine="rv_RequestUpdate";
_rv_requestupdate();
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
}
