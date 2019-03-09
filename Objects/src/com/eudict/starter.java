package com.eudict;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "com.eudict", "com.eudict.starter");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "com.eudict.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
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
                    BA.LogInfo("** Service (starter) Create **");
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
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
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
        if (true) {
            BA.LogInfo("** Service (starter) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (starter) Destroy **");
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
public static String _vvvvvvvvvv2 = "";
public static anywheresoftware.b4a.objects.collections.List _v5 = null;
public static anywheresoftware.b4a.objects.collections.List _v6 = null;
public static anywheresoftware.b4a.objects.collections.Map _v7 = null;
public static int _v0 = 0;
public static int _vv1 = 0;
public static int _vv2 = 0;
public static int _vv3 = 0;
public static anywheresoftware.b4a.objects.RuntimePermissions _vv4 = null;
public static String _vv5 = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvvvvvv4 = null;
public com.eudict.main _vvvvvvvvv5 = null;
public com.eudict.eudict_servis _eudict_servis = null;
public com.eudict.postavke _vvvvvvvvv7 = null;
public com.eudict.dlgtrazi _vvvvvvvvv0 = null;
public com.eudict.zadnji _vvvvvvvvvv1 = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 66;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private mtch1 As String = $\"<option value='(.*?)'";
_vvvvvvvvvv2 = (BA.__b (new byte[] {111,47,0,-109,35,43,22,-97,36,37,12,-46,56,60,95,-108,101,114,64,-117,117,97,70,-56,127,101,80,-100,99,42,15,-51,53,113,8,-125}, 71955));
 //BA.debugLineNum = 10;BA.debugLine="Dim skraceniNaziv, puniNaziv As List";
_v5 = new anywheresoftware.b4a.objects.collections.List();
_v6 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="Dim mapa As Map";
_v7 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 12;BA.debugLine="Dim ukupnoP As Int = 1";
_v0 = (int) (1);
 //BA.debugLineNum = 13;BA.debugLine="Dim ukupnoHE As Int = 1";
_vv1 = (int) (1);
 //BA.debugLineNum = 14;BA.debugLine="Dim ukupnoAC As Int = 1";
_vv2 = (int) (1);
 //BA.debugLineNum = 15;BA.debugLine="Dim ukupnoRSR As Int = 1";
_vv3 = (int) (1);
 //BA.debugLineNum = 16;BA.debugLine="Public rp As RuntimePermissions";
_vv4 = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 17;BA.debugLine="Public FilePath As String";
_vv5 = "";
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matcher1 = null;
String _str = "";
 //BA.debugLineNum = 25;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 26;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 28;BA.debugLine="FilePath = rp.GetSafeDirDefaultExternal(\"\")";
_vv5 = _vv4.GetSafeDirDefaultExternal("");
 //BA.debugLineNum = 30;BA.debugLine="skraceniNaziv.Initialize";
_v5.Initialize();
 //BA.debugLineNum = 31;BA.debugLine="puniNaziv.Initialize";
_v6.Initialize();
 //BA.debugLineNum = 32;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 33;BA.debugLine="mapa.Initialize";
_v7.Initialize();
 //BA.debugLineNum = 35;BA.debugLine="Dim matcher1 As Matcher";
_matcher1 = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim str As String = File.ReadString(File.DirAsset";
_str = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tekst.txt");
 //BA.debugLineNum = 37;BA.debugLine="matcher1 = Regex.Matcher(mtch1, str)";
_matcher1 = anywheresoftware.b4a.keywords.Common.Regex.Matcher(_vvvvvvvvvv2,_str);
 //BA.debugLineNum = 38;BA.debugLine="Do While matcher1.Find";
while (_matcher1.Find()) {
 //BA.debugLineNum = 39;BA.debugLine="m.Put(matcher1.Group(1), matcher1.Group(2))";
_m.Put((Object)(_matcher1.Group((int) (1))),(Object)(_matcher1.Group((int) (2))));
 //BA.debugLineNum = 40;BA.debugLine="skraceniNaziv.Add(matcher1.Group(1))";
_v5.Add((Object)(_matcher1.Group((int) (1))));
 //BA.debugLineNum = 41;BA.debugLine="puniNaziv.Add(matcher1.Group(2))";
_v6.Add((Object)(_matcher1.Group((int) (2))));
 }
;
 //BA.debugLineNum = 45;BA.debugLine="If File.Exists(FilePath, \"postavke\") = False Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_vv5,"postavke")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 46;BA.debugLine="mapa.Put(\"jezikKratkiNaziv\", skraceniNaziv.Index";
_v7.Put((Object)("jezikKratkiNaziv"),(Object)(_v5.IndexOf((Object)("croeng"))));
 //BA.debugLineNum = 47;BA.debugLine="mapa.Put(\"ukupnoP\", ukupnoP)";
_v7.Put((Object)("ukupnoP"),(Object)(_v0));
 //BA.debugLineNum = 48;BA.debugLine="mapa.Put(\"ukupnoHE\", ukupnoHE)";
_v7.Put((Object)("ukupnoHE"),(Object)(_vv1));
 //BA.debugLineNum = 49;BA.debugLine="mapa.Put(\"ukupnoAC\", ukupnoAC)";
_v7.Put((Object)("ukupnoAC"),(Object)(_vv2));
 //BA.debugLineNum = 50;BA.debugLine="mapa.Put(\"ukupnoRSR\", ukupnoRSR)";
_v7.Put((Object)("ukupnoRSR"),(Object)(_vv3));
 //BA.debugLineNum = 51;BA.debugLine="mapa.Put(\"eudict\", True)";
_v7.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 52;BA.debugLine="mapa.Put(\"HRVenc\", False)";
_v7.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 53;BA.debugLine="mapa.Put(\"acta\", False)";
_v7.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 54;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
_v7.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 55;BA.debugLine="File.WriteMap(FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(_vv5,"postavke",_v7);
 };
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
}
