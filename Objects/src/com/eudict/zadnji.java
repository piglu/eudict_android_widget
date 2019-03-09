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

public class zadnji extends Activity implements B4AActivity{
	public static zadnji mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.eudict", "com.eudict.zadnji");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (zadnji).");
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
		activityBA = new BA(this, layout, processBA, "com.eudict", "com.eudict.zadnji");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.eudict.zadnji", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (zadnji) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (zadnji) Resume **");
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
		return zadnji.class;
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
        BA.LogInfo("** Activity (zadnji) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            zadnji mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (zadnji) Resume **");
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
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomLayoutDialog _vvvvvvvvvvvvvv2 = null;
public com.eudict.customlistview _clv1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _lbltitle1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _pnltitle1 = null;
public static int _vvvvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvvvv7 = 0;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _vvvvvvvv0 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvvvvvv4 = null;
public com.eudict.main _vvvvvvvvv5 = null;
public com.eudict.starter _vvvvvvvvv6 = null;
public com.eudict.eudict_servis _eudict_servis = null;
public com.eudict.postavke _vvvvvvvvv7 = null;
public com.eudict.dlgtrazi _vvvvvvvvv0 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
 //BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 29;BA.debugLine="If mapa.Get(\"eudict\") Then";
if (BA.ObjectToBoolean(_mapa.Get((Object)("eudict")))) { 
 //BA.debugLineNum = 30;BA.debugLine="PokaziDlgZadnjih10Eudict";
_vvvvvvvvvvvv0();
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("HRVenc")))) { 
 //BA.debugLineNum = 32;BA.debugLine="PokaziDlgZadnjih10HRVEnc";
_vvvvvvvvvvvvv1();
 }else if(BA.ObjectToBoolean(_mapa.Get((Object)("acta")))) { 
 //BA.debugLineNum = 34;BA.debugLine="PokaziDlgZadnjih10Acta";
_vvvvvvvvvvvvv2();
 }else {
 //BA.debugLineNum = 36;BA.debugLine="PokaziDlgZadnjih10StranaRijec";
_vvvvvvvvvvvvv3();
 };
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 41;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 43;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv4(int _index,int _from,int _todegree) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _iv = null;
 //BA.debugLineNum = 195;BA.debugLine="Sub AnimatedArrowEudict(index As Int, From As Int,";
 //BA.debugLineNum = 196;BA.debugLine="pnlTitle1 = clv1.GetPanel(index).GetView(0) 'pnlT";
mostCurrent._pnltitle1 = mostCurrent._clv1._vvvvv5(_index).GetView((int) (0));
 //BA.debugLineNum = 197;BA.debugLine="Dim iv As B4XView = pnlTitle1.GetView(1) 'ImageVi";
_iv = new anywheresoftware.b4a.objects.B4XViewWrapper();
_iv = mostCurrent._pnltitle1.GetView((int) (1));
 //BA.debugLineNum = 198;BA.debugLine="iv.SetRotationAnimated(0, From)";
_iv.SetRotationAnimated((int) (0),(float) (_from));
 //BA.debugLineNum = 199;BA.debugLine="iv.SetRotationAnimated(clv1.AnimationDuration, To";
_iv.SetRotationAnimated(mostCurrent._clv1._vvvvvvvv5,(float) (_todegree));
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _clv1_itemclick(int _index,Object _value) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 208;BA.debugLine="Sub clv1_ItemClick (Index As Int, Value As Object)";
 //BA.debugLineNum = 209;BA.debugLine="Dim p As B4XView = clv1.GetPanel(Index)";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._clv1._vvvvv5(_index);
 //BA.debugLineNum = 210;BA.debugLine="If p.Tag = True Then";
if ((_p.getTag()).equals((Object)(anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 211;BA.debugLine="CollapseItemEudict(Index)";
_vvvvvvvvvvvvv5(_index);
 }else {
 //BA.debugLineNum = 213;BA.debugLine="ExpandItemEudict(Index)";
_vvvvvvvvvvvvv6(_index);
 };
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv5(int _index) throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub CollapseItemEudict(index As Int)";
 //BA.debugLineNum = 203;BA.debugLine="clv1.ResizeItem(index, CollapsedHeightEudict)";
mostCurrent._clv1._vvvvvv7(_index,_vvvvvvvvvvvvv7);
 //BA.debugLineNum = 204;BA.debugLine="clv1.GetPanel(index).Tag = False";
mostCurrent._clv1._vvvvv5(_index).setTag((Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 205;BA.debugLine="AnimatedArrowEudict(index, 180, 0)";
_vvvvvvvvvvvvv4(_index,(int) (180),(int) (0));
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.B4XViewWrapper  _vvvvvvvvvvvvv0(String _title,String _txt) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 175;BA.debugLine="Sub CreateItemEudict(Title As String, txt As Strin";
 //BA.debugLineNum = 176;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._vvvvvvvv0.CreatePanel(processBA,"");
 //BA.debugLineNum = 177;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, clv1.AsView.Width, E";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),mostCurrent._clv1._vvvv1().getWidth(),_vvvvvvvvvvvvvv1);
 //BA.debugLineNum = 178;BA.debugLine="p.LoadLayout(\"stavka_zadnjih_10\")";
_p.LoadLayout("stavka_zadnjih_10",mostCurrent.activityBA);
 //BA.debugLineNum = 179;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, p.Width, CollapsedHe";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_p.getWidth(),_vvvvvvvvvvvvv7);
 //BA.debugLineNum = 180;BA.debugLine="lblTitle1.Text = Title";
mostCurrent._lbltitle1.setText(BA.ObjectToCharSequence(_title));
 //BA.debugLineNum = 181;BA.debugLine="EditText1.Text = txt";
mostCurrent._edittext1.setText(BA.ObjectToCharSequence(_txt));
 //BA.debugLineNum = 184;BA.debugLine="p.Tag = False 'collapsed";
_p.setTag((Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 186;BA.debugLine="Return p";
if (true) return _p;
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return null;
}
public static String  _vvvvvvvvvvvvv6(int _index) throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Sub ExpandItemEudict(index As Int)";
 //BA.debugLineNum = 190;BA.debugLine="clv1.ResizeItem(index, ExpandedHeightEudict)";
mostCurrent._clv1._vvvvvv7(_index,_vvvvvvvvvvvvvv1);
 //BA.debugLineNum = 191;BA.debugLine="clv1.GetPanel(index).Tag = True";
mostCurrent._clv1._vvvvv5(_index).setTag((Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 192;BA.debugLine="AnimatedArrowEudict(index, 0, 180)";
_vvvvvvvvvvvvv4(_index,(int) (0),(int) (180));
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private DetailsDialogEudict As CustomLayoutDialog";
mostCurrent._vvvvvvvvvvvvvv2 = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomLayoutDialog();
 //BA.debugLineNum = 15;BA.debugLine="Private clv1 As CustomListView";
mostCurrent._clv1 = new com.eudict.customlistview();
 //BA.debugLineNum = 16;BA.debugLine="Private lblTitle1 As B4XView";
mostCurrent._lbltitle1 = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private pnlTitle1 As B4XView";
mostCurrent._pnltitle1 = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private ExpandedHeightEudict As Int = 240dip";
_vvvvvvvvvvvvvv1 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240));
 //BA.debugLineNum = 19;BA.debugLine="Private CollapsedHeightEudict As Int = 60dip";
_vvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 21;BA.debugLine="Private xui As XUI";
mostCurrent._vvvvvvvv0 = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 22;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static void  _vvvvvvvvvvvvv2() throws Exception{
ResumableSub_PokaziDlgZadnjih10Acta rsub = new ResumableSub_PokaziDlgZadnjih10Acta(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgZadnjih10Acta extends BA.ResumableSub {
public ResumableSub_PokaziDlgZadnjih10Acta(com.eudict.zadnji parent) {
this.parent = parent;
}
com.eudict.zadnji parent;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _x = 0;
int _i = 0;
String _text = "";
String _s = "";
int _res = 0;
int _result = 0;
int step11;
int limit11;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 113;BA.debugLine="Log(\"PokaziDlgZadnjih10Acta\")";
anywheresoftware.b4a.keywords.Common.Log("PokaziDlgZadnjih10Acta");
 //BA.debugLineNum = 114;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 116;BA.debugLine="Dim sf As Object = DetailsDialogEudict.ShowAsync(";
_sf = parent.mostCurrent._vvvvvvvvvvvvvv2.ShowAsync("Zadnjih 10 pretraživanja","U redu","","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="DetailsDialogEudict.SetSize(100%x, 80%y)";
parent.mostCurrent._vvvvvvvvvvvvvv2.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 118;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 19;
return;
case 19:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 119;BA.debugLine="pnl.LoadLayout(\"dlg_zadnjih_10\")";
_pnl.LoadLayout("dlg_zadnjih_10",mostCurrent.activityBA);
 //BA.debugLineNum = 120;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoAC\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoAC"))));
 //BA.debugLineNum = 121;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 122;BA.debugLine="clv1.Clear";
parent.mostCurrent._clv1._vvvv3();
 //BA.debugLineNum = 123;BA.debugLine="If File.Exists(Starter.FilePath, \"ac\" & x) Then";
if (true) break;

case 1:
//if
this.state = 18;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._vvvvvvvvv6._vv5,"ac"+BA.NumberToString(_x))) { 
this.state = 3;
}else {
this.state = 13;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 124;BA.debugLine="For i = 0 To x - 1";
if (true) break;

case 4:
//for
this.state = 7;
step11 = 1;
limit11 = (int) (_x-1);
_i = (int) (0) ;
this.state = 20;
if (true) break;

case 20:
//C
this.state = 7;
if ((step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11)) this.state = 6;
if (true) break;

case 21:
//C
this.state = 20;
_i = ((int)(0 + _i + step11)) ;
if (true) break;

case 6:
//C
this.state = 21;
 //BA.debugLineNum = 125;BA.debugLine="Dim text As String = File.ReadString(Starter.Fi";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(parent.mostCurrent._vvvvvvvvv6._vv5,"ac"+BA.NumberToString(_x));
 //BA.debugLineNum = 126;BA.debugLine="Dim s As String = text.SubString2(0, text.Index";
_s = _text.substring((int) (0),_text.indexOf(";"));
 //BA.debugLineNum = 127;BA.debugLine="s = text.SubString2(text.IndexOf(\";\")+1, text.L";
_s = _text.substring((int) (_text.indexOf(";")+1),_text.length());
 //BA.debugLineNum = 128;BA.debugLine="clv1.Add(CreateItemEudict(s, text), \"\")";
parent.mostCurrent._clv1._vvv7(_vvvvvvvvvvvvv0(_s,_text),(Object)(""));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 130;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 22;
return;
case 22:
//C
this.state = 8;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 131;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 8:
//if
this.state = 11;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 132;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 11:
//C
this.state = 18;
;
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 135;BA.debugLine="Msgbox2Async(\"Ništa za prikazati!!\", \"Info\", \"U";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Ništa za prikazati!!"),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 136;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 23;
return;
case 23:
//C
this.state = 14;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 137;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 138;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _dialog_ready(anywheresoftware.b4a.objects.PanelWrapper _pnl) throws Exception{
}
public static void  _dialog_result(int _res) throws Exception{
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _vvvvvvvvvvvv0() throws Exception{
ResumableSub_PokaziDlgZadnjih10Eudict rsub = new ResumableSub_PokaziDlgZadnjih10Eudict(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgZadnjih10Eudict extends BA.ResumableSub {
public ResumableSub_PokaziDlgZadnjih10Eudict(com.eudict.zadnji parent) {
this.parent = parent;
}
com.eudict.zadnji parent;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _x = 0;
int _i = 0;
String _text = "";
String _s = "";
int _res = 0;
int _result = 0;
int step10;
int limit10;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 144;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 146;BA.debugLine="Dim sf As Object = DetailsDialogEudict.ShowAsync(";
_sf = parent.mostCurrent._vvvvvvvvvvvvvv2.ShowAsync("Zadnjih 10 pretraživanja","U redu","","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 147;BA.debugLine="DetailsDialogEudict.SetSize(100%x, 80%y)";
parent.mostCurrent._vvvvvvvvvvvvvv2.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 148;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 19;
return;
case 19:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 149;BA.debugLine="pnl.LoadLayout(\"dlg_zadnjih_10\")";
_pnl.LoadLayout("dlg_zadnjih_10",mostCurrent.activityBA);
 //BA.debugLineNum = 150;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoP\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoP"))));
 //BA.debugLineNum = 151;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 152;BA.debugLine="clv1.Clear";
parent.mostCurrent._clv1._vvvv3();
 //BA.debugLineNum = 153;BA.debugLine="If File.Exists(Starter.FilePath, \"p\" & x) Then";
if (true) break;

case 1:
//if
this.state = 18;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._vvvvvvvvv6._vv5,"p"+BA.NumberToString(_x))) { 
this.state = 3;
}else {
this.state = 13;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 154;BA.debugLine="For i = 0 To x - 1";
if (true) break;

case 4:
//for
this.state = 7;
step10 = 1;
limit10 = (int) (_x-1);
_i = (int) (0) ;
this.state = 20;
if (true) break;

case 20:
//C
this.state = 7;
if ((step10 > 0 && _i <= limit10) || (step10 < 0 && _i >= limit10)) this.state = 6;
if (true) break;

case 21:
//C
this.state = 20;
_i = ((int)(0 + _i + step10)) ;
if (true) break;

case 6:
//C
this.state = 21;
 //BA.debugLineNum = 155;BA.debugLine="Dim text As String = File.ReadString(Starter.Fi";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(parent.mostCurrent._vvvvvvvvv6._vv5,"p"+BA.NumberToString((_i+1)));
 //BA.debugLineNum = 156;BA.debugLine="Log(\"text: \" & text)";
anywheresoftware.b4a.keywords.Common.Log("text: "+_text);
 //BA.debugLineNum = 157;BA.debugLine="Dim s As String = text.SubString2(0, text.Index";
_s = _text.substring((int) (0),_text.indexOf(" "));
 //BA.debugLineNum = 158;BA.debugLine="Log(\"s: \" & s)";
anywheresoftware.b4a.keywords.Common.Log("s: "+_s);
 //BA.debugLineNum = 159;BA.debugLine="clv1.Add(CreateItemEudict(s, text), \"\")";
parent.mostCurrent._clv1._vvv7(_vvvvvvvvvvvvv0(_s,_text),(Object)(""));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 161;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 22;
return;
case 22:
//C
this.state = 8;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 162;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 8:
//if
this.state = 11;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 163;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 11:
//C
this.state = 18;
;
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 166;BA.debugLine="Msgbox2Async(\"Ništa za prikazati!!\", \"Info\", \"U";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Ništa za prikazati!!"),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 23;
return;
case 23:
//C
this.state = 14;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 168;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 169;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvvvv1() throws Exception{
ResumableSub_PokaziDlgZadnjih10HRVEnc rsub = new ResumableSub_PokaziDlgZadnjih10HRVEnc(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgZadnjih10HRVEnc extends BA.ResumableSub {
public ResumableSub_PokaziDlgZadnjih10HRVEnc(com.eudict.zadnji parent) {
this.parent = parent;
}
com.eudict.zadnji parent;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _x = 0;
int _i = 0;
String _text = "";
String _s = "";
int _res = 0;
int _result = 0;
int step11;
int limit11;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 82;BA.debugLine="Log(\"PokaziDlgZadnjih10HRVEnc\")";
anywheresoftware.b4a.keywords.Common.Log("PokaziDlgZadnjih10HRVEnc");
 //BA.debugLineNum = 83;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 85;BA.debugLine="Dim sf As Object = DetailsDialogEudict.ShowAsync(";
_sf = parent.mostCurrent._vvvvvvvvvvvvvv2.ShowAsync("Zadnjih 10 pretraživanja","U redu","","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 86;BA.debugLine="DetailsDialogEudict.SetSize(100%x, 80%y)";
parent.mostCurrent._vvvvvvvvvvvvvv2.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 87;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 19;
return;
case 19:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 88;BA.debugLine="pnl.LoadLayout(\"dlg_zadnjih_10\")";
_pnl.LoadLayout("dlg_zadnjih_10",mostCurrent.activityBA);
 //BA.debugLineNum = 89;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoHE\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoHE"))));
 //BA.debugLineNum = 90;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 91;BA.debugLine="clv1.Clear";
parent.mostCurrent._clv1._vvvv3();
 //BA.debugLineNum = 92;BA.debugLine="If File.Exists(Starter.FilePath, \"he\" & x) Then";
if (true) break;

case 1:
//if
this.state = 18;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._vvvvvvvvv6._vv5,"he"+BA.NumberToString(_x))) { 
this.state = 3;
}else {
this.state = 13;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 93;BA.debugLine="For i = 0 To x - 1";
if (true) break;

case 4:
//for
this.state = 7;
step11 = 1;
limit11 = (int) (_x-1);
_i = (int) (0) ;
this.state = 20;
if (true) break;

case 20:
//C
this.state = 7;
if ((step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11)) this.state = 6;
if (true) break;

case 21:
//C
this.state = 20;
_i = ((int)(0 + _i + step11)) ;
if (true) break;

case 6:
//C
this.state = 21;
 //BA.debugLineNum = 94;BA.debugLine="Dim text As String = File.ReadString(Starter.Fi";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(parent.mostCurrent._vvvvvvvvv6._vv5,"he"+BA.NumberToString(_x));
 //BA.debugLineNum = 95;BA.debugLine="Dim s As String = text.SubString2(0, text.Index";
_s = _text.substring((int) (0),_text.indexOf(";"));
 //BA.debugLineNum = 96;BA.debugLine="s = text.SubString2(text.IndexOf(\";\")+1, text.L";
_s = _text.substring((int) (_text.indexOf(";")+1),_text.length());
 //BA.debugLineNum = 97;BA.debugLine="clv1.Add(CreateItemEudict(s, text), \"\")";
parent.mostCurrent._clv1._vvv7(_vvvvvvvvvvvvv0(_s,_text),(Object)(""));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 99;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 22;
return;
case 22:
//C
this.state = 8;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 100;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 8:
//if
this.state = 11;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 101;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 11:
//C
this.state = 18;
;
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 104;BA.debugLine="Msgbox2Async(\"Ništa za prikazati!!\", \"Info\", \"U";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Ništa za prikazati!!"),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 23;
return;
case 23:
//C
this.state = 14;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 106;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 107;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _vvvvvvvvvvvvv3() throws Exception{
ResumableSub_PokaziDlgZadnjih10StranaRijec rsub = new ResumableSub_PokaziDlgZadnjih10StranaRijec(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_PokaziDlgZadnjih10StranaRijec extends BA.ResumableSub {
public ResumableSub_PokaziDlgZadnjih10StranaRijec(com.eudict.zadnji parent) {
this.parent = parent;
}
com.eudict.zadnji parent;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
Object _sf = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _x = 0;
int _i = 0;
String _text = "";
String _s = "";
int _res = 0;
int _result = 0;
int step11;
int limit11;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 51;BA.debugLine="Log(\"PokaziDlgZadnjih10StranaRijec\")";
anywheresoftware.b4a.keywords.Common.Log("PokaziDlgZadnjih10StranaRijec");
 //BA.debugLineNum = 52;BA.debugLine="Dim mapa As Map = File.ReadMap(Starter.FilePath,";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
_mapa = anywheresoftware.b4a.keywords.Common.File.ReadMap(parent.mostCurrent._vvvvvvvvv6._vv5,"postavke");
 //BA.debugLineNum = 54;BA.debugLine="Dim sf As Object = DetailsDialogEudict.ShowAsync(";
_sf = parent.mostCurrent._vvvvvvvvvvvvvv2.ShowAsync("Zadnjih 10 pretraživanja","U redu","","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"form.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 55;BA.debugLine="DetailsDialogEudict.SetSize(100%x, 80%y)";
parent.mostCurrent._vvvvvvvvvvvvvv2.SetSize(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 56;BA.debugLine="Wait For (sf) Dialog_Ready(pnl As Panel)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_ready", processBA, this, _sf);
this.state = 19;
return;
case 19:
//C
this.state = 1;
_pnl = (anywheresoftware.b4a.objects.PanelWrapper) result[0];
;
 //BA.debugLineNum = 57;BA.debugLine="pnl.LoadLayout(\"dlg_zadnjih_10\")";
_pnl.LoadLayout("dlg_zadnjih_10",mostCurrent.activityBA);
 //BA.debugLineNum = 58;BA.debugLine="Dim x As Int = mapa.Get(\"ukupnoRSR\")";
_x = (int)(BA.ObjectToNumber(_mapa.Get((Object)("ukupnoRSR"))));
 //BA.debugLineNum = 59;BA.debugLine="x = x - 1";
_x = (int) (_x-1);
 //BA.debugLineNum = 60;BA.debugLine="clv1.Clear";
parent.mostCurrent._clv1._vvvv3();
 //BA.debugLineNum = 61;BA.debugLine="If File.Exists(Starter.FilePath, \"rsr\" & x) Then";
if (true) break;

case 1:
//if
this.state = 18;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._vvvvvvvvv6._vv5,"rsr"+BA.NumberToString(_x))) { 
this.state = 3;
}else {
this.state = 13;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 62;BA.debugLine="For i = 0 To x - 1";
if (true) break;

case 4:
//for
this.state = 7;
step11 = 1;
limit11 = (int) (_x-1);
_i = (int) (0) ;
this.state = 20;
if (true) break;

case 20:
//C
this.state = 7;
if ((step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11)) this.state = 6;
if (true) break;

case 21:
//C
this.state = 20;
_i = ((int)(0 + _i + step11)) ;
if (true) break;

case 6:
//C
this.state = 21;
 //BA.debugLineNum = 63;BA.debugLine="Dim text As String = File.ReadString(Starter.Fi";
_text = anywheresoftware.b4a.keywords.Common.File.ReadString(parent.mostCurrent._vvvvvvvvv6._vv5,"rsr"+BA.NumberToString(_x));
 //BA.debugLineNum = 64;BA.debugLine="Dim s As String = text.SubString2(0, text.Index";
_s = _text.substring((int) (0),_text.indexOf(";"));
 //BA.debugLineNum = 65;BA.debugLine="s = text.SubString2(text.IndexOf(\";\")+1, text.L";
_s = _text.substring((int) (_text.indexOf(";")+1),_text.length());
 //BA.debugLineNum = 66;BA.debugLine="clv1.Add(CreateItemEudict(s, text), \"\")";
parent.mostCurrent._clv1._vvv7(_vvvvvvvvvvvvv0(_s,_text),(Object)(""));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 68;BA.debugLine="Wait For (sf) Dialog_Result(res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("dialog_result", processBA, this, _sf);
this.state = 22;
return;
case 22:
//C
this.state = 8;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 69;BA.debugLine="If res = DialogResponse.POSITIVE Then";
if (true) break;

case 8:
//if
this.state = 11;
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 70;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 11:
//C
this.state = 18;
;
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 73;BA.debugLine="Msgbox2Async(\"Ništa za prikazati!!\", \"Info\", \"U";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Ništa za prikazati!!"),BA.ObjectToCharSequence("Info"),"U redu","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 74;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 23;
return;
case 23:
//C
this.state = 14;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 75;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 76;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
}
