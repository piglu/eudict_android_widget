package com.eudict;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class dlgtrazi extends Activity implements B4AActivity{
	public static dlgtrazi mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.eudict", "com.eudict.dlgtrazi");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (dlgtrazi).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.eudict", "com.eudict.dlgtrazi");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.eudict.dlgtrazi", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (dlgtrazi) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (dlgtrazi) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return dlgtrazi.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (dlgtrazi) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            dlgtrazi mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (dlgtrazi) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.collections.List _vv7 = null;
public static anywheresoftware.b4a.objects.collections.List _vv0 = null;
public static String _vvv1 = "";
public static String _vvv2 = "";
public static String _vvv3 = "";
public static String _vvv4 = "";
public static String _vvv5 = "";
public static String _vvv6 = "";
public static String _strana_rijec_link = "";
public static String _hrv_enc_link = "";
public static String _z_acta_link = "";
public static String _z_eudict_link = "";
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomLayoutDialog _vvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.FloatLabeledEditTextWrapper _edtrijec = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpromijeni = null;
public uk.co.gingerscrack.jSoup.jSoup _vvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.collections.List _vvvvvvvvvvv6 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvvvvvv4 = null;
public com.eudict.main _vvvvvvvvv5 = null;
public com.eudict.starter _vvvvvvvvv6 = null;
public com.eudict.eudict_servis _eudict_servis = null;
public com.eudict.postavke _vvvvvvvvv7 = null;
public com.eudict.zadnji _vvvvvvvvvv1 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 45;BA.debugLine="rijecG.Initialize";
_vv7.Initialize();
 //BA.debugLineNum = 46;BA.debugLine="prijevodG.Initialize";
_vv0.Initialize();
 //BA.debugLineNum = 48;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 49;BA.debugLine="If mapa.Get(\"eudict\") Then";
if (BA.ObjectToBoolean(_mapa.Get((Object)("eudict")))) { 
 //BA.debugLineNum = 50;BA.debugLine="PokaziDlgTrazi";
_vvvvvvvvvv0();
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("HRVenc")))) { 
 //BA.debugLineNum = 52;BA.debugLine="PokaziDlgTraziHRVEnc";
_vvvvvvvvvvv1();
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("acta")))) { 
 //BA.debugLineNum = 54;BA.debugLine="PokaziDlgTraziActa";
_vvvvvvvvvvv2();
 }else {
 //BA.debugLineNum = 56;BA.debugLine="PokaziDlgTraziStranuRijec";
_vvvvvvvvvvv3();
 };
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 61;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 63;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 31;BA.debugLine="Private DetailsDialog As CustomLayoutDialog";
mostCurrent._vvvvvvvvvvv4 = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomLayoutDialog();
 //BA.debugLineNum = 33;BA.debugLine="Dim edtRijec As FloatLabeledEditText'EditText";
mostCurrent._edtrijec = new anywheresoftware.b4a.objects.FloatLabeledEditTextWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private imgPromijeni As ImageView";
mostCurrent._imgpromijeni = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim js As jSoup";
mostCurrent._vvvvvvvvvvv5 = new uk.co.gingerscrack.jSoup.jSoup();
 //BA.debugLineNum = 39;BA.debugLine="Dim Extract1 As List";
mostCurrent._vvvvvvvvvvv6 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv7(String _str) throws Exception{
anywheresoftware.b4a.objects.collections.List _l1 = null;
String _ss = "";
anywheresoftware.b4a.objects.collections.List _l = null;
int _i = 0;
String _ss1 = "";
String _s1 = "";
 //BA.debugLineNum = 311;BA.debugLine="Sub ParsajStranicuActaCroatica(str As String)";
 //BA.debugLineNum = 312;BA.debugLine="pojamActaOpis = \"\"";
_vvv4 = "";
 //BA.debugLineNum = 315;BA.debugLine="Dim l1 As List";
_l1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 316;BA.debugLine="l1.Initialize";
_l1.Initialize();
 //BA.debugLineNum = 317;BA.debugLine="str = str.Replace(\"&hellip;\", \"...\")";
_str = _str.replace("&hellip;","...");
 //BA.debugLineNum = 320;BA.debugLine="Dim ss As String";
_ss = "";
 //BA.debugLineNum = 321;BA.debugLine="Dim l As List = Regex.Split($\"<div class=\"feature";
_l = new anywheresoftware.b4a.objects.collections.List();
_l = anywheresoftware.b4a.keywords.Common.ArrayToList(anywheresoftware.b4a.keywords.Common.Regex.Split(("<div class=\"featured-list-icon text-primary\">"),_str));
 //BA.debugLineNum = 322;BA.debugLine="For i = 1 To l.Size - 1";
{
final int step7 = 1;
final int limit7 = (int) (_l.getSize()-1);
_i = (int) (1) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 323;BA.debugLine="If i = l.Size - 1 Then";
if (_i==_l.getSize()-1) { 
 //BA.debugLineNum = 326;BA.debugLine="ss = Regex.Replace($\"<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:";
_ss = anywheresoftware.b4a.keywords.Common.Regex.Replace(("<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:\\\".*?\"|'.*?'|[^'\\\">\\s]+))?)+\\s*|\\s*)\\/?>"),BA.ObjectToString(_l.Get(_i)),"");
 //BA.debugLineNum = 328;BA.debugLine="Dim ss1 As String = ss.SubString2(0, ss.IndexOf";
_ss1 = _ss.substring((int) (0),_ss.indexOf("Impresum"));
 //BA.debugLineNum = 329;BA.debugLine="Log(i & \":\" & ss1)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_i)+":"+_ss1);
 //BA.debugLineNum = 330;BA.debugLine="l1.Add(ss1)";
_l1.Add((Object)(_ss1));
 }else {
 //BA.debugLineNum = 332;BA.debugLine="Log(i & \":\" & Regex.Replace($\"<\\/?\\w+((\\s+\\w+(\\";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_i)+":"+anywheresoftware.b4a.keywords.Common.Regex.Replace(("<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:\\\".*?\"|'.*?'|[^'\\\">\\s]+))?)+\\s*|\\s*)\\/?>"),BA.ObjectToString(_l.Get(_i)),""));
 //BA.debugLineNum = 333;BA.debugLine="l1.Add(Regex.Replace($\"<\\/?\\w+((\\s+\\w+(\\s*=\\s*(";
_l1.Add((Object)(anywheresoftware.b4a.keywords.Common.Regex.Replace(("<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:\\\".*?\"|'.*?'|[^'\\\">\\s]+))?)+\\s*|\\s*)\\/?>"),BA.ObjectToString(_l.Get(_i)),"")));
 };
 }
};
 //BA.debugLineNum = 337;BA.debugLine="For i = 0 To l1.Size - 1";
{
final int step18 = 1;
final int limit18 = (int) (_l1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit18 ;_i = _i + step18 ) {
 //BA.debugLineNum = 338;BA.debugLine="Dim s1 As String = l1.Get(i)";
_s1 = BA.ObjectToString(_l1.Get(_i));
 //BA.debugLineNum = 339;BA.debugLine="s1 = s1.Trim";
_s1 = _s1.trim();
 //BA.debugLineNum = 341;BA.debugLine="pojamActaOpis = pojamActaOpis & s1 & CRLF";
_vvv4 = _vvv4+_s1+anywheresoftware.b4a.keywords.Common.CRLF;
 }
};
 //BA.debugLineNum = 344;BA.debugLine="CallSubDelayed(eudict_servis, \"UbaciActaCroatica\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._eudict_servis.getObject()),"UbaciActaCroatica");
 //BA.debugLineNum = 345;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 346;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv0(String _str) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matcher1 = null;
 //BA.debugLineNum = 404;BA.debugLine="Sub ParsajStranicuEudict(str As String)";
 //BA.debugLineNum = 405;BA.debugLine="Dim matcher1 As Matcher";
_matcher1 = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 406;BA.debugLine="rijecG.Clear";
_vv7.Clear();
 //BA.debugLineNum = 407;BA.debugLine="prijevodG.Clear";
_vv0.Clear();
 //BA.debugLineNum = 409;BA.debugLine="matcher1 = Regex.Matcher($\"class='uk-table-link u";
_matcher1 = anywheresoftware.b4a.keywords.Common.Regex.Matcher(("class='uk-table-link uk-link-reset'><a href='https://eudict.com/\\?lang=.*?&amp;word=.*?'>(.*?)</a></td><td class='uk-table-link uk-link-reset'><a href='https://eudict.com/\\?lang=.*?&amp;word=.*?'>(.*?)</a>"),_str);
 //BA.debugLineNum = 410;BA.debugLine="Do While matcher1.Find";
while (_matcher1.Find()) {
 //BA.debugLineNum = 412;BA.debugLine="rijecG.Add(matcher1.Group(1))";
_vv7.Add((Object)(_matcher1.Group((int) (1))));
 //BA.debugLineNum = 414;BA.debugLine="prijevodG.Add(matcher1.Group(2))";
_vv0.Add((Object)(_matcher1.Group((int) (2))));
 }
;
 //BA.debugLineNum = 417;BA.debugLine="CallSubDelayed(eudict_servis, \"UbaciPrijevod\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._eudict_servis.getObject()),"UbaciPrijevod");
 //BA.debugLineNum = 418;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvvvvvv1(String _str) throws Exception{
ResumableSub_ParsajStranicuHRVencZaLink rsub = new ResumableSub_ParsajStranicuHRVencZaLink(null,_str);
rsub.resume(processBA, null);
}
public static class ResumableSub_ParsajStranicuHRVencZaLink extends BA.ResumableSub {
public ResumableSub_ParsajStranicuHRVencZaLink(com.eudict.dlgtrazi parent,String _str) {
this.parent = parent;
this._str = _str;
}
com.eudict.dlgtrazi parent;
String _str;
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matcher1 = null;
String _linkhrvenc = "";
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 164;BA.debugLine="Dim matcher1 As Matcher";
_matcher1 = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Dim linkHRVenc As String";
_linkhrvenc = "";
 //BA.debugLineNum = 168;BA.debugLine="matcher1 = Regex.Matcher($\"<div id=\"ContentPlaceH";
_matcher1 = anywheresoftware.b4a.keywords.Common.Regex.Matcher(("<div id=\"ContentPlaceHolder1_divListaPogodaka\"><p class=\"pogodak\"><a class=\".*?\" href=\"(.*?)\"><b>.*?</b></a>"),_str);
 //BA.debugLineNum = 169;BA.debugLine="Do While matcher1.Find";
if (true) break;

case 1:
//do while
this.state = 4;
while (_matcher1.Find()) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 1;
 //BA.debugLineNum = 170;BA.debugLine="linkHRVenc = \"http://www.enciklopedija.hr/\" & ma";
_linkhrvenc = "http://www.enciklopedija.hr/"+_matcher1.Group((int) (1));
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 175;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 176;BA.debugLine="ProgressDialogShow(\"Šaljem upit...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Šaljem upit..."));
 //BA.debugLineNum = 177;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",dlgtrazi.getObject());
 //BA.debugLineNum = 179;BA.debugLine="j.Download(linkHRVenc)";
_j._download(_linkhrvenc);
 //BA.debugLineNum = 180;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 5;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 181;BA.debugLine="If j.Success Then";
if (true) break;

case 5:
//if
this.state = 10;
if (_j._success) { 
this.state = 7;
}else {
this.state = 9;
}if (true) break;

case 7:
//C
this.state = 10;
 //BA.debugLineNum = 182;BA.debugLine="ParsajStranicuHRVencZaPojam(j.GetString)";
_vvvvvvvvvvvv2(_j._getstring());
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 184;BA.debugLine="ToastMessageShow(\"Neka greška!!\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Neka greška!!"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 187;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 188;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _j) throws Exception{
}
public static String  _vvvvvvvvvvvv2(String _str) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matcher1 = null;
String _local_html = "";
String _s = "";
int _i = 0;
 //BA.debugLineNum = 191;BA.debugLine="Sub ParsajStranicuHRVencZaPojam(str As String)";
 //BA.debugLineNum = 192;BA.debugLine="Dim matcher1 As Matcher";
_matcher1 = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 193;BA.debugLine="Dim local_html As String";
_local_html = "";
 //BA.debugLineNum = 195;BA.debugLine="pojamHRVenc = \"\"";
_vvv1 = "";
 //BA.debugLineNum = 196;BA.debugLine="pojamHRVencTekst = \"\"";
_vvv2 = "";
 //BA.debugLineNum = 198;BA.debugLine="matcher1 = Regex.Matcher($\"<div id=\"ContentPlaceH";
_matcher1 = anywheresoftware.b4a.keywords.Common.Regex.Matcher(("<div id=\"ContentPlaceHolder1_Naslov\"><h1>(.*?)</h1></div>"),_str);
 //BA.debugLineNum = 199;BA.debugLine="Do While matcher1.Find";
while (_matcher1.Find()) {
 //BA.debugLineNum = 200;BA.debugLine="pojamHRVenc = matcher1.Group(1)";
_vvv1 = _matcher1.Group((int) (1));
 }
;
 //BA.debugLineNum = 209;BA.debugLine="str = str.Replace(\"&scaron;\", \"š\").Replace(\"&ndas";
_str = _str.replace("&scaron;","š").replace("&ndash;","-").replace("&Scaron;","Š").replace("&raquo;","»").replace("&laquo;","«").replace(("&Ouml;"),"Ö").replace(("&icirc;"),"î");
 //BA.debugLineNum = 214;BA.debugLine="File.WriteString(Starter.FilePath, \"tmp.html\", st";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._vvvvvvvvv6._vv5,"tmp.html",_str);
 //BA.debugLineNum = 217;BA.debugLine="local_html = File.ReadString(Starter.FilePath, \"t";
_local_html = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._vvvvvvvvv6._vv5,"tmp.html");
 //BA.debugLineNum = 233;BA.debugLine="Extract1.Initialize";
mostCurrent._vvvvvvvvvvv6.Initialize();
 //BA.debugLineNum = 234;BA.debugLine="Extract1 = js.selectorElementText(local_html, \"p\"";
mostCurrent._vvvvvvvvvvv6 = mostCurrent._vvvvvvvvvvv5.selectorElementText(_local_html,"p");
 //BA.debugLineNum = 235;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 236;BA.debugLine="For i = 0 To Extract1.Size - 1";
{
final int step15 = 1;
final int limit15 = (int) (mostCurrent._vvvvvvvvvvv6.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit15 ;_i = _i + step15 ) {
 //BA.debugLineNum = 238;BA.debugLine="s = s & Extract1.Get(i)";
_s = _s+BA.ObjectToString(mostCurrent._vvvvvvvvvvv6.Get(_i));
 }
};
 //BA.debugLineNum = 251;BA.debugLine="pojamHRVencTekst = s";
_vvv2 = _s;
 //BA.debugLineNum = 254;BA.debugLine="CallSubDelayed(eudict_servis, \"UbaciHRVencPojam\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._eudict_servis.getObject()),"UbaciHRVencPojam");
 //BA.debugLineNum = 255;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv3(String _str) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matcher1 = null;
anywheresoftware.b4a.objects.collections.List _l = null;
int _i = 0;
String _ss = "";
 //BA.debugLineNum = 108;BA.debugLine="Sub ParsajStranicuStranaRijec(str As String)";
 //BA.debugLineNum = 109;BA.debugLine="Dim matcher1 As Matcher";
_matcher1 = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 111;BA.debugLine="stranaRijecOpis = \"\"";
_vvv6 = "";
 //BA.debugLineNum = 113;BA.debugLine="str = str.Replace(\"&#x005B;\", \"[\").Replace(\"&#x00";
_str = _str.replace("&#x005B;","[").replace("&#x005D;","]").replace("<i>","").replace("</i>","").replace("&#x2329;","〈").replace("&#x232A;","〉").replace("&#x012B;","ī").replace("&#x00E8;","è").replace("&#x00EC;","ì").replace("&#x017E;","ž").replace("&#x0161;","š").replace("&#x010D;","č").replace("&#x0107;","ć").replace("&#x00E9;","é");
 //BA.debugLineNum = 116;BA.debugLine="matcher1 = Regex.Matcher($\"<meta name=\"descriptio";
_matcher1 = anywheresoftware.b4a.keywords.Common.Regex.Matcher(("<meta name=\"description\" content=\".*?: (.*?) : .*?\" />"),_str);
 //BA.debugLineNum = 117;BA.debugLine="Do While matcher1.Find";
while (_matcher1.Find()) {
 //BA.debugLineNum = 118;BA.debugLine="stranaRijec = matcher1.Group(1)";
_vvv5 = _matcher1.Group((int) (1));
 }
;
 //BA.debugLineNum = 121;BA.debugLine="Dim l As List = Regex.Split(\"<!-- definicija -->\"";
_l = new anywheresoftware.b4a.objects.collections.List();
_l = anywheresoftware.b4a.keywords.Common.ArrayToList(anywheresoftware.b4a.keywords.Common.Regex.Split("<!-- definicija -->",_str));
 //BA.debugLineNum = 122;BA.debugLine="For i = 1 To l.Size - 1";
{
final int step9 = 1;
final int limit9 = (int) (_l.getSize()-1);
_i = (int) (1) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 123;BA.debugLine="Dim ss As String = Regex.Replace($\"<\\/?\\w+((\\s+\\";
_ss = anywheresoftware.b4a.keywords.Common.Regex.Replace(("<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:\\\".*?\"|'.*?'|[^'\\\">\\s]+))?)+\\s*|\\s*)\\/?>"),BA.ObjectToString(_l.Get(_i)),"");
 //BA.debugLineNum = 124;BA.debugLine="stranaRijecOpis = ss.SubString2(0, ss.IndexOf(\"<";
_vvv6 = _ss.substring((int) (0),_ss.indexOf("<!-- sintagma -->"));
 }
};
 //BA.debugLineNum = 127;BA.debugLine="CallSubDelayed(eudict_servis, \"UbaciStranuRijec\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._eudict_servis.getObject()),"UbaciStranuRijec");
 //BA.debugLineNum = 128;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvvvv0() throws Exception{
ResumableSub_PokaziDlgTrazi rsub = new ResumableSub_PokaziDlgTrazi(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgTrazi extends BA.ResumableSub {
public ResumableSub_PokaziDlgTrazi(com.eudict.dlgtrazi parent) {
this.parent = parent;
}
com.eudict.dlgtrazi parent;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _res = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 349;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 350;BA.debugLine="Dim x As Int = mapa.GetValueAt(0)";
_x = (int)(BA.ObjectToNumber(_mapa.GetValueAt((int) (0))));
 //BA.debugLineNum = 352;BA.debugLine="Dim sf As Object = DetailsDialog.ShowAsync(\"Upit";
_sf = parent.mostCurrent._vvvvvvvvvvv4.ShowAsync("Upit za "+BA.ObjectToString(parent.mostCurrent._vvvvvvvvv6._v6.Get(_x)),"U redu","Odbaci","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 353;BA.debugLine="DetailsDialog.SetSize(100%x, 50%y)";
parent.mostCurrent._vvvvvvvvvvv4.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 354;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 7;
return;
case 7:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 355;BA.debugLine="pnl.LoadLayout(\"dlg_trazi\")";
_pnl.LoadLayout("dlg_trazi",mostCurrent.activityBA);
 //BA.debugLineNum = 364;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 8;
return;
case 8:
//C
this.state = 1;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 365;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 367;BA.debugLine="PosaljiUpitEudict(edtRijec.Text)";
_vvvvvvvvvvvv4(parent.mostCurrent._edtrijec.getText());
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 370;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 372;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _dialog_ready(anywheresoftware.b4a.objects.PanelWrapper _pnl) throws Exception{
}
public static void  _dialog_result(int _res) throws Exception{
}
public static void  _vvvvvvvvvvv2() throws Exception{
ResumableSub_PokaziDlgTraziActa rsub = new ResumableSub_PokaziDlgTraziActa(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgTraziActa extends BA.ResumableSub {
public ResumableSub_PokaziDlgTraziActa(com.eudict.dlgtrazi parent) {
this.parent = parent;
}
com.eudict.dlgtrazi parent;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _res = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 259;BA.debugLine="Log(\"PokaziDlgTraziActa\")";
anywheresoftware.b4a.keywords.Common.Log("PokaziDlgTraziActa");
 //BA.debugLineNum = 260;BA.debugLine="Dim sf As Object = DetailsDialog.ShowAsync(\"Acta";
_sf = parent.mostCurrent._vvvvvvvvvvv4.ShowAsync("Acta Croatica","U redu","Odbaci","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 261;BA.debugLine="DetailsDialog.SetSize(100%x, 50%y)";
parent.mostCurrent._vvvvvvvvvvv4.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 262;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 7;
return;
case 7:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 263;BA.debugLine="pnl.LoadLayout(\"dlg_trazi\")";
_pnl.LoadLayout("dlg_trazi",mostCurrent.activityBA);
 //BA.debugLineNum = 272;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 8;
return;
case 8:
//C
this.state = 1;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 273;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 275;BA.debugLine="PosaljiUpitActaCroatica(edtRijec.Text)";
_vvvvvvvvvvvv5(parent.mostCurrent._edtrijec.getText());
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 278;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvv1() throws Exception{
ResumableSub_PokaziDlgTraziHRVEnc rsub = new ResumableSub_PokaziDlgTraziHRVEnc(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgTraziHRVEnc extends BA.ResumableSub {
public ResumableSub_PokaziDlgTraziHRVEnc(com.eudict.dlgtrazi parent) {
this.parent = parent;
}
com.eudict.dlgtrazi parent;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _res = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 134;BA.debugLine="Dim sf As Object = DetailsDialog.ShowAsync(\"Hrvat";
_sf = parent.mostCurrent._vvvvvvvvvvv4.ShowAsync("Hrvatska enciklopedija","U redu","Odbaci","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 135;BA.debugLine="DetailsDialog.SetSize(100%x, 50%y)";
parent.mostCurrent._vvvvvvvvvvv4.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 136;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 7;
return;
case 7:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 137;BA.debugLine="pnl.LoadLayout(\"dlg_trazi\")";
_pnl.LoadLayout("dlg_trazi",mostCurrent.activityBA);
 //BA.debugLineNum = 138;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 8;
return;
case 8:
//C
this.state = 1;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 139;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 140;BA.debugLine="PosaljiUpitHRVEnc(edtRijec.Text)";
_vvvvvvvvvvvv6(parent.mostCurrent._edtrijec.getText());
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 142;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvv3() throws Exception{
ResumableSub_PokaziDlgTraziStranuRijec rsub = new ResumableSub_PokaziDlgTraziStranuRijec(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgTraziStranuRijec extends BA.ResumableSub {
public ResumableSub_PokaziDlgTraziStranuRijec(com.eudict.dlgtrazi parent) {
this.parent = parent;
}
com.eudict.dlgtrazi parent;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _res = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 71;BA.debugLine="Dim sf As Object = DetailsDialog.ShowAsync(\"Riječ";
_sf = parent.mostCurrent._vvvvvvvvvvv4.ShowAsync("Riječnik stranih riječi","U redu","Odbaci","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 72;BA.debugLine="DetailsDialog.SetSize(100%x, 50%y)";
parent.mostCurrent._vvvvvvvvvvv4.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 73;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 7;
return;
case 7:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 74;BA.debugLine="pnl.LoadLayout(\"dlg_trazi\")";
_pnl.LoadLayout("dlg_trazi",mostCurrent.activityBA);
 //BA.debugLineNum = 75;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 8;
return;
case 8:
//C
this.state = 1;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 76;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 77;BA.debugLine="PosaljiUpitStranaRijec(edtRijec.Text)";
_vvvvvvvvvvvv7(parent.mostCurrent._edtrijec.getText());
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 79;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvvv5(String _rijec) throws Exception{
ResumableSub_PosaljiUpitActaCroatica rsub = new ResumableSub_PosaljiUpitActaCroatica(null,_rijec);
rsub.resume(processBA, null);
}
public static class ResumableSub_PosaljiUpitActaCroatica extends BA.ResumableSub {
public ResumableSub_PosaljiUpitActaCroatica(com.eudict.dlgtrazi parent,String _rijec) {
this.parent = parent;
this._rijec = _rijec;
}
com.eudict.dlgtrazi parent;
String _rijec;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 283;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 285;BA.debugLine="pojamActa = rijec";
parent._vvv3 = _rijec;
 //BA.debugLineNum = 287;BA.debugLine="ProgressDialogShow(\"Šaljem upit...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Šaljem upit..."));
 //BA.debugLineNum = 288;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",dlgtrazi.getObject());
 //BA.debugLineNum = 289;BA.debugLine="j.Download(\"https://actacroatica.com/hr/dosearch?";
_j._download("https://actacroatica.com/hr/dosearch?q="+_rijec);
 //BA.debugLineNum = 290;BA.debugLine="z_acta_link = \"https://actacroatica.com/hr/dosear";
parent._z_acta_link = "https://actacroatica.com/hr/dosearch?q="+_rijec;
 //BA.debugLineNum = 291;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 17;
return;
case 17:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 292;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_j._success) { 
this.state = 3;
}else {
this.state = 15;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 293;BA.debugLine="If j.GetString.Contains(\"Još nema spominjanja u";
if (true) break;

case 4:
//if
this.state = 13;
if (_j._getstring().contains("Još nema spominjanja u izvorima")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 13;
 //BA.debugLineNum = 295;BA.debugLine="ParsajStranicuActaCroatica(j.GetString)";
_vvvvvvvvvvv7(_j._getstring());
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 297;BA.debugLine="Msgbox2Async(\"Još nema spominjanja u izvorima!\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Još nema spominjanja u izvorima!"),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 18;
return;
case 18:
//C
this.state = 9;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 299;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 9:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 300;BA.debugLine="PokaziDlgTraziActa";
_vvvvvvvvvvv2();
 if (true) break;

case 12:
//C
this.state = 13;
;
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 304;BA.debugLine="Log(\"neka greška!\")";
anywheresoftware.b4a.keywords.Common.Log("neka greška!");
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 307;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 308;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _vvvvvvvvvvvv4(String _rijec) throws Exception{
ResumableSub_PosaljiUpitEudict rsub = new ResumableSub_PosaljiUpitEudict(null,_rijec);
rsub.resume(processBA, null);
}
public static class ResumableSub_PosaljiUpitEudict extends BA.ResumableSub {
public ResumableSub_PosaljiUpitEudict(com.eudict.dlgtrazi parent,String _rijec) {
this.parent = parent;
this._rijec = _rijec;
}
com.eudict.dlgtrazi parent;
String _rijec;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _x = 0;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 375;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 376;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 377;BA.debugLine="Dim x As Int = mapa.GetValueAt(0)";
_x = (int)(BA.ObjectToNumber(_mapa.GetValueAt((int) (0))));
 //BA.debugLineNum = 379;BA.debugLine="ProgressDialogShow(\"Šaljem upit...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Šaljem upit..."));
 //BA.debugLineNum = 380;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",dlgtrazi.getObject());
 //BA.debugLineNum = 382;BA.debugLine="j.Download(\"https://eudict.com/?lang=\" & Starter.";
_j._download("https://eudict.com/?lang="+BA.ObjectToString(parent.mostCurrent._vvvvvvvvv6._v5.Get(_x))+"&word="+_rijec);
 //BA.debugLineNum = 383;BA.debugLine="z_eudict_link = \"https://eudict.com/?lang=\" & Sta";
parent._z_eudict_link = "https://eudict.com/?lang="+BA.ObjectToString(parent.mostCurrent._vvvvvvvvv6._v5.Get(_x))+"&word="+_rijec;
 //BA.debugLineNum = 384;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 17;
return;
case 17:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 385;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_j._success) { 
this.state = 3;
}else {
this.state = 15;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 386;BA.debugLine="If j.GetString.Contains(\"Word is not found.\") =";
if (true) break;

case 4:
//if
this.state = 13;
if (_j._getstring().contains("Word is not found.")==anywheresoftware.b4a.keywords.Common.False || _j._getstring().contains("WORD IS NOT FOUND.")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 13;
 //BA.debugLineNum = 388;BA.debugLine="ParsajStranicuEudict(j.GetString)";
_vvvvvvvvvvv0(_j._getstring());
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 390;BA.debugLine="Msgbox2Async(\"Riječ nije pronađena!\", \"Info\", \"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Riječ nije pronađena!"),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 391;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 18;
return;
case 18:
//C
this.state = 9;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 392;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 9:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 393;BA.debugLine="PokaziDlgTrazi";
_vvvvvvvvvv0();
 if (true) break;

case 12:
//C
this.state = 13;
;
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 397;BA.debugLine="Log(\"neka greška!\")";
anywheresoftware.b4a.keywords.Common.Log("neka greška!");
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 400;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 401;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvvv6(String _pojam) throws Exception{
ResumableSub_PosaljiUpitHRVEnc rsub = new ResumableSub_PosaljiUpitHRVEnc(null,_pojam);
rsub.resume(processBA, null);
}
public static class ResumableSub_PosaljiUpitHRVEnc extends BA.ResumableSub {
public ResumableSub_PosaljiUpitHRVEnc(com.eudict.dlgtrazi parent,String _pojam) {
this.parent = parent;
this._pojam = _pojam;
}
com.eudict.dlgtrazi parent;
String _pojam;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 147;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 148;BA.debugLine="ProgressDialogShow(\"Šaljem upit...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Šaljem upit..."));
 //BA.debugLineNum = 149;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",dlgtrazi.getObject());
 //BA.debugLineNum = 150;BA.debugLine="j.Download2(\"http://www.enciklopedija.hr/Trazi.as";
_j._download2("http://www.enciklopedija.hr/Trazi.aspx",new String[]{"t",_pojam,"s","0","k","10"});
 //BA.debugLineNum = 151;BA.debugLine="hrv_enc_link = \"http://www.enciklopedija.hr/Trazi";
parent._hrv_enc_link = "http://www.enciklopedija.hr/Trazi.aspx&t="+_pojam+"&t=0&k=10";
 //BA.debugLineNum = 152;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 7;
return;
case 7:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 153;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_j._success) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 154;BA.debugLine="ParsajStranicuHRVencZaLink(j.GetString)";
_vvvvvvvvvvvv1(_j._getstring());
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 156;BA.debugLine="Log(\"neka greška\")";
anywheresoftware.b4a.keywords.Common.Log("neka greška");
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 159;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 160;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvvv7(String _rijec) throws Exception{
ResumableSub_PosaljiUpitStranaRijec rsub = new ResumableSub_PosaljiUpitStranaRijec(null,_rijec);
rsub.resume(processBA, null);
}
public static class ResumableSub_PosaljiUpitStranaRijec extends BA.ResumableSub {
public ResumableSub_PosaljiUpitStranaRijec(com.eudict.dlgtrazi parent,String _rijec) {
this.parent = parent;
this._rijec = _rijec;
}
com.eudict.dlgtrazi parent;
String _rijec;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 84;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 85;BA.debugLine="ProgressDialogShow(\"Šaljem upit...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Šaljem upit..."));
 //BA.debugLineNum = 86;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",dlgtrazi.getObject());
 //BA.debugLineNum = 87;BA.debugLine="j.PostString(\"http://hjp.znanje.hr/index.php?show";
_j._poststring("http://hjp.znanje.hr/index.php?show=search","word="+_rijec);
 //BA.debugLineNum = 88;BA.debugLine="strana_rijec_link = \"http://hjp.znanje.hr/index.p";
parent._strana_rijec_link = "http://hjp.znanje.hr/index.php?show=search?word="+_rijec;
 //BA.debugLineNum = 89;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 17;
return;
case 17:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 90;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_j._success) { 
this.state = 3;
}else {
this.state = 15;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 91;BA.debugLine="If j.GetString.Contains(\"Nijedna natuknica u rje";
if (true) break;

case 4:
//if
this.state = 13;
if (_j._getstring().contains("Nijedna natuknica u rječničkoj bazi ne zadovoljava zadane uvjete.")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 13;
 //BA.debugLineNum = 92;BA.debugLine="ParsajStranicuStranaRijec(j.GetString)";
_vvvvvvvvvvvv3(_j._getstring());
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 94;BA.debugLine="Msgbox2Async(\"Nijedna natuknica u rječničkoj ba";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Nijedna natuknica u rječničkoj bazi ne zadovoljava zadane uvjete."),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 95;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 18;
return;
case 18:
//C
this.state = 9;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 96;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 9:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 97;BA.debugLine="PokaziDlgTraziStranuRijec";
_vvvvvvvvvvv3();
 if (true) break;

case 12:
//C
this.state = 13;
;
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 101;BA.debugLine="Log(\"neka greška!\")";
anywheresoftware.b4a.keywords.Common.Log("neka greška!");
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 104;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 105;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim rijecG As List";
_vv7 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 10;BA.debugLine="Dim prijevodG As List";
_vv0 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="Dim pojamHRVenc, pojamHRVencTekst As String";
_vvv1 = "";
_vvv2 = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim pojamActa As String";
_vvv3 = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim pojamActaOpis As String";
_vvv4 = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim stranaRijec As String";
_vvv5 = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim stranaRijecOpis As String";
_vvv6 = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim strana_rijec_link As String";
_strana_rijec_link = "";
 //BA.debugLineNum = 17;BA.debugLine="Dim hrv_enc_link As String";
_hrv_enc_link = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim z_acta_link As String";
_z_acta_link = "";
 //BA.debugLineNum = 19;BA.debugLine="Dim z_eudict_link As String";
_z_eudict_link = "";
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
}
