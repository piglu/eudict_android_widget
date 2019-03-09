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

public class postavke extends Activity implements B4AActivity{
	public static postavke mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.eudict", "com.eudict.postavke");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (postavke).");
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
		activityBA = new BA(this, layout, processBA, "com.eudict", "com.eudict.postavke");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.eudict.postavke", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (postavke) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (postavke) Resume **");
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
		return postavke.class;
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
        BA.LogInfo("** Activity (postavke) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            postavke mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (postavke) Resume **");
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
public anywheresoftware.b4a.objects.SpinnerWrapper _spnrijecnici = null;
public anywheresoftware.b4a.objects.collections.Map _v7 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbeudict = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbhrvenc = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbactacroatica = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbstranarijec = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvvvvvv4 = null;
public com.eudict.main _vvvvvvvvv5 = null;
public com.eudict.starter _vvvvvvvvv6 = null;
public com.eudict.eudict_servis _eudict_servis = null;
public com.eudict.dlgtrazi _vvvvvvvvv0 = null;
public com.eudict.zadnji _vvvvvvvvvv1 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 24;BA.debugLine="Activity.LoadLayout(\"postavke\")";
mostCurrent._activity.LoadLayout("postavke",mostCurrent.activityBA);
 //BA.debugLineNum = 26;BA.debugLine="mapa.Initialize";
mostCurrent._v7.Initialize();
 //BA.debugLineNum = 28;BA.debugLine="spnRijecnici.AddAll(Starter.puniNaziv)";
mostCurrent._spnrijecnici.AddAll(mostCurrent._vvvvvvvvv6._v6);
 //BA.debugLineNum = 29;BA.debugLine="mapa = File.ReadMap(Starter.FilePath, \"postavke\")";
mostCurrent._v7 = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 31;BA.debugLine="spnRijecnici.SelectedIndex = mapa.GetValueAt(0)";
mostCurrent._spnrijecnici.setSelectedIndex((int)(BA.ObjectToNumber(mostCurrent._v7.GetValueAt((int) (0)))));
 //BA.debugLineNum = 32;BA.debugLine="rbEudict.Checked = mapa.Get(\"eudict\")";
mostCurrent._rbeudict.setChecked(BA.ObjectToBoolean(mostCurrent._v7.Get((Object)("eudict"))));
 //BA.debugLineNum = 33;BA.debugLine="rbHrvEnc.Checked = mapa.Get(\"HRVenc\")";
mostCurrent._rbhrvenc.setChecked(BA.ObjectToBoolean(mostCurrent._v7.Get((Object)("HRVenc"))));
 //BA.debugLineNum = 34;BA.debugLine="rbActaCroatica.Checked = mapa.Get(\"acta\")";
mostCurrent._rbactacroatica.setChecked(BA.ObjectToBoolean(mostCurrent._v7.Get((Object)("acta"))));
 //BA.debugLineNum = 35;BA.debugLine="rbStranaRijec.Checked = mapa.Get(\"stranaRijec\")";
mostCurrent._rbstranarijec.setChecked(BA.ObjectToBoolean(mostCurrent._v7.Get((Object)("stranaRijec"))));
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="CallSubDelayed(eudict_servis, \"ApdejtajStatus2\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._eudict_servis.getObject()),"ApdejtajStatus2");
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 44;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 46;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private spnRijecnici As Spinner";
mostCurrent._spnrijecnici = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim mapa As Map";
mostCurrent._v7 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 16;BA.debugLine="Private rbEudict As RadioButton";
mostCurrent._rbeudict = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private rbHrvEnc As RadioButton";
mostCurrent._rbhrvenc = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private rbActaCroatica As RadioButton";
mostCurrent._rbactacroatica = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private rbStranaRijec As RadioButton";
mostCurrent._rbstranarijec = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _rbactacroatica_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub rbActaCroatica_CheckedChange(Checked As Boolea";
 //BA.debugLineNum = 77;BA.debugLine="spnRijecnici.Enabled = False";
mostCurrent._spnrijecnici.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 78;BA.debugLine="mapa.Put(\"acta\", Checked)";
mostCurrent._v7.Put((Object)("acta"),(Object)(_checked));
 //BA.debugLineNum = 79;BA.debugLine="mapa.Put(\"eudict\", False)";
mostCurrent._v7.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 80;BA.debugLine="mapa.Put(\"HRVenc\", False)";
mostCurrent._v7.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 81;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
mostCurrent._v7.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 82;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",mostCurrent._v7);
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _rbeudict_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub rbEudict_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 59;BA.debugLine="spnRijecnici.Enabled = True";
mostCurrent._spnrijecnici.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 60;BA.debugLine="mapa.Put(\"eudict\", Checked)";
mostCurrent._v7.Put((Object)("eudict"),(Object)(_checked));
 //BA.debugLineNum = 61;BA.debugLine="mapa.Put(\"HRVenc\", False)";
mostCurrent._v7.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 62;BA.debugLine="mapa.Put(\"acta\", False)";
mostCurrent._v7.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 63;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
mostCurrent._v7.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 64;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",mostCurrent._v7);
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _rbhrvenc_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub rbHrvEnc_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 68;BA.debugLine="spnRijecnici.Enabled = False";
mostCurrent._spnrijecnici.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="mapa.Put(\"HRVenc\", Checked)";
mostCurrent._v7.Put((Object)("HRVenc"),(Object)(_checked));
 //BA.debugLineNum = 70;BA.debugLine="mapa.Put(\"eudict\", False)";
mostCurrent._v7.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 71;BA.debugLine="mapa.Put(\"acta\", False)";
mostCurrent._v7.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 72;BA.debugLine="mapa.Put(\"stranaRijec\", False)";
mostCurrent._v7.Put((Object)("stranaRijec"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 73;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",mostCurrent._v7);
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _rbstranarijec_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub rbStranaRijec_CheckedChange(Checked As Boolean";
 //BA.debugLineNum = 86;BA.debugLine="spnRijecnici.Enabled = False";
mostCurrent._spnrijecnici.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 87;BA.debugLine="mapa.Put(\"stranaRijec\", Checked)";
mostCurrent._v7.Put((Object)("stranaRijec"),(Object)(_checked));
 //BA.debugLineNum = 88;BA.debugLine="mapa.Put(\"HRVenc\", False)";
mostCurrent._v7.Put((Object)("HRVenc"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 89;BA.debugLine="mapa.Put(\"eudict\", False)";
mostCurrent._v7.Put((Object)("eudict"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 90;BA.debugLine="mapa.Put(\"acta\", False)";
mostCurrent._v7.Put((Object)("acta"),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 91;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",mostCurrent._v7);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _spnrijecnici_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub spnRijecnici_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 54;BA.debugLine="mapa.Put(\"jezikKratkiNaziv\", Position)";
mostCurrent._v7.Put((Object)("jezikKratkiNaziv"),(Object)(_position));
 //BA.debugLineNum = 55;BA.debugLine="File.WriteMap(Starter.FilePath, \"postavke\", mapa)";
anywheresoftware.b4a.keywords.Common.File.WriteMap(mostCurrent._vvvvvvvvv6._vv5,"postavke",mostCurrent._v7);
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
}
