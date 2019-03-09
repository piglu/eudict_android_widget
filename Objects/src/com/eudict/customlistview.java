package com.eudict;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class customlistview extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.eudict.customlistview");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.eudict.customlistview.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _vvvvvvv5 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _vvvvvvv6 = null;
public anywheresoftware.b4a.objects.collections.List _vvvvvvv7 = null;
public float _vvvvvvv0 = 0f;
public String _vvvvvvvv1 = "";
public Object _vvvvvvvv2 = null;
public int _vvvvvvvv3 = 0;
public int _vvvvvvvv4 = 0;
public int _vvvvvvvv5 = 0;
public long _vvvvvvvv6 = 0L;
public int _vvvvvvvv7 = 0;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _vvvvvvvv0 = null;
public int _vvvvvvvvv1 = 0;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.StringUtils _vvvvvvvvv3 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvvvvvv4 = null;
public com.eudict.main _vvvvvvvvv5 = null;
public com.eudict.starter _vvvvvvvvv6 = null;
public com.eudict.eudict_servis _eudict_servis = null;
public com.eudict.postavke _vvvvvvvvv7 = null;
public com.eudict.dlgtrazi _vvvvvvvvv0 = null;
public com.eudict.zadnji _vvvvvvvvvv1 = null;
public static class _clvitem{
public boolean IsInitialized;
public anywheresoftware.b4a.objects.B4XViewWrapper Panel;
public int Height;
public Object Value;
public int Color;
public boolean TextItem;
public void Initialize() {
IsInitialized = true;
Panel = new anywheresoftware.b4a.objects.B4XViewWrapper();
Height = 0;
Value = new Object();
Color = 0;
TextItem = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _vvv7(anywheresoftware.b4a.objects.B4XViewWrapper _pnl,Object _value) throws Exception{
 //BA.debugLineNum = 249;BA.debugLine="Public Sub Add(Pnl As B4XView, Value As Object)";
 //BA.debugLineNum = 250;BA.debugLine="InsertAt(items.Size, Pnl, Value)";
_vvvvv0(_vvvvvvv7.getSize(),_pnl,_value);
 //BA.debugLineNum = 251;BA.debugLine="End Sub";
return "";
}
public String  _vvv0(Object _text,Object _value) throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Public Sub AddTextItem(Text As Object, Value As Ob";
 //BA.debugLineNum = 128;BA.debugLine="InsertAtTextItem(items.Size, Text, Value)";
_vvvvvv2(_vvvvvvv7.getSize(),_text,_value);
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _vvvv1() throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Public Sub AsView As B4XView";
 //BA.debugLineNum = 72;BA.debugLine="Return mBase";
if (true) return _vvvvvvv5;
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return null;
}
public String  _base_resize(double _width,double _height) throws Exception{
com.eudict.customlistview._clvitem _it = null;
anywheresoftware.b4a.objects.B4XViewWrapper _lbl = null;
 //BA.debugLineNum = 55;BA.debugLine="Private Sub Base_Resize (Width As Double, Height A";
 //BA.debugLineNum = 56;BA.debugLine="sv.SetLayoutAnimated(0, 0, 0, Width, Height)";
_vvvvvvv6.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height));
 //BA.debugLineNum = 57;BA.debugLine="sv.ScrollViewContentWidth = Width";
_vvvvvvv6.setScrollViewContentWidth((int) (_width));
 //BA.debugLineNum = 61;BA.debugLine="For Each it As CLVItem In items";
{
final anywheresoftware.b4a.BA.IterableList group3 = _vvvvvvv7;
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_it = (com.eudict.customlistview._clvitem)(group3.Get(index3));
 //BA.debugLineNum = 62;BA.debugLine="it.Panel.Width = sv.ScrollViewContentWidth";
_it.Panel.setWidth(_vvvvvvv6.getScrollViewContentWidth());
 //BA.debugLineNum = 63;BA.debugLine="it.Panel.GetView(0).Width = it.Panel.Width";
_it.Panel.GetView((int) (0)).setWidth(_it.Panel.getWidth());
 //BA.debugLineNum = 64;BA.debugLine="If it.TextItem Then";
if (_it.TextItem) { 
 //BA.debugLineNum = 65;BA.debugLine="Dim lbl As B4XView = it.Panel.GetView(0).GetVie";
_lbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_lbl = _it.Panel.GetView((int) (0)).GetView((int) (0));
 //BA.debugLineNum = 66;BA.debugLine="lbl.SetLayoutAnimated(0, lbl.Left, lbl.Top, it.";
_lbl.SetLayoutAnimated((int) (0),_lbl.getLeft(),_lbl.getTop(),(int) (_it.Panel.getWidth()-_lbl.getLeft()),_lbl.getHeight());
 };
 }
};
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private mBase As B4XView";
_vvvvvvv5 = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Public sv As B4XView";
_vvvvvvv6 = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Type CLVItem(Panel As B4XView, Height As Int, Val";
;
 //BA.debugLineNum = 12;BA.debugLine="Private items As List";
_vvvvvvv7 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 13;BA.debugLine="Private dividerHeight As Float";
_vvvvvvv0 = 0f;
 //BA.debugLineNum = 14;BA.debugLine="Private EventName As String";
_vvvvvvvv1 = "";
 //BA.debugLineNum = 15;BA.debugLine="Private CallBack As Object";
_vvvvvvvv2 = new Object();
 //BA.debugLineNum = 16;BA.debugLine="Public DefaultTextColor As Int";
_vvvvvvvv3 = 0;
 //BA.debugLineNum = 17;BA.debugLine="Public DefaultTextBackgroundColor As Int";
_vvvvvvvv4 = 0;
 //BA.debugLineNum = 18;BA.debugLine="Public AnimationDuration As Int = 300";
_vvvvvvvv5 = (int) (300);
 //BA.debugLineNum = 19;BA.debugLine="Private LastReachEndEvent As Long";
_vvvvvvvv6 = 0L;
 //BA.debugLineNum = 20;BA.debugLine="Private PressedColor As Int";
_vvvvvvvv7 = 0;
 //BA.debugLineNum = 21;BA.debugLine="Private xui As XUI";
_vvvvvvvv0 = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 22;BA.debugLine="Private LastAnimatedAction As Int";
_vvvvvvvvv1 = 0;
 //BA.debugLineNum = 23;BA.debugLine="Private DesignerLabel As Label";
_vvvvvvvvv2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private su As StringUtils";
_vvvvvvvvv3 = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public void  _vvvv2() throws Exception{
ResumableSub_CleanAfterAnimation rsub = new ResumableSub_CleanAfterAnimation(this);
rsub.resume(ba, null);
}
public static class ResumableSub_CleanAfterAnimation extends BA.ResumableSub {
public ResumableSub_CleanAfterAnimation(com.eudict.customlistview parent) {
this.parent = parent;
}
com.eudict.customlistview parent;
int _myindex = 0;
int _top = 0;
com.eudict.customlistview._clvitem _item = null;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 215;BA.debugLine="LastAnimatedAction = LastAnimatedAction + 1";
parent._vvvvvvvvv1 = (int) (parent._vvvvvvvvv1+1);
 //BA.debugLineNum = 216;BA.debugLine="Dim MyIndex As Int = LastAnimatedAction";
_myindex = parent._vvvvvvvvv1;
 //BA.debugLineNum = 217;BA.debugLine="Sleep(AnimationDuration + 50)";
parent.__c.Sleep(ba,this,(int) (parent._vvvvvvvv5+50));
this.state = 11;
return;
case 11:
//C
this.state = 1;
;
 //BA.debugLineNum = 218;BA.debugLine="If MyIndex <> LastAnimatedAction Then Return";
if (true) break;

case 1:
//if
this.state = 6;
if (_myindex!=parent._vvvvvvvvv1) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) return ;
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 219;BA.debugLine="Dim Top As Int = dividerHeight";
_top = (int) (parent._vvvvvvv0);
 //BA.debugLineNum = 220;BA.debugLine="For Each item As CLVItem In items";
if (true) break;

case 7:
//for
this.state = 10;
group6 = parent._vvvvvvv7;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 12;
if (true) break;

case 12:
//C
this.state = 10;
if (index6 < groupLen6) {
this.state = 9;
_item = (com.eudict.customlistview._clvitem)(group6.Get(index6));}
if (true) break;

case 13:
//C
this.state = 12;
index6++;
if (true) break;

case 9:
//C
this.state = 13;
 //BA.debugLineNum = 221;BA.debugLine="item.Panel.Top = Top";
_item.Panel.setTop(_top);
 //BA.debugLineNum = 222;BA.debugLine="Top = Top + item.Height + dividerHeight";
_top = (int) (_top+_item.Height+parent._vvvvvvv0);
 if (true) break;
if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _vvvv3() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Public Sub Clear";
 //BA.debugLineNum = 77;BA.debugLine="items.Clear";
_vvvvvvv7.Clear();
 //BA.debugLineNum = 78;BA.debugLine="sv.ScrollViewInnerPanel.RemoveAllViews";
_vvvvvvv6.getScrollViewInnerPanel().RemoveAllViews();
 //BA.debugLineNum = 79;BA.debugLine="sv.ScrollViewContentHeight = 0";
_vvvvvvv6.setScrollViewContentHeight((int) (0));
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _vvvv4(String _txt) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 355;BA.debugLine="Private Sub CreateLabel(txt As String) As B4XView";
 //BA.debugLineNum = 356;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 357;BA.debugLine="lbl.Initialize(\"\")";
_lbl.Initialize(ba,"");
 //BA.debugLineNum = 358;BA.debugLine="lbl.Gravity = DesignerLabel.Gravity";
_lbl.setGravity(_vvvvvvvvv2.getGravity());
 //BA.debugLineNum = 359;BA.debugLine="lbl.TextSize = DesignerLabel.TextSize";
_lbl.setTextSize(_vvvvvvvvv2.getTextSize());
 //BA.debugLineNum = 360;BA.debugLine="lbl.SingleLine = False";
_lbl.setSingleLine(__c.False);
 //BA.debugLineNum = 361;BA.debugLine="lbl.Text = txt";
_lbl.setText(BA.ObjectToCharSequence(_txt));
 //BA.debugLineNum = 362;BA.debugLine="lbl.Width = sv.ScrollViewContentWidth - 10dip";
_lbl.setWidth((int) (_vvvvvvv6.getScrollViewContentWidth()-__c.DipToCurrent((int) (10))));
 //BA.debugLineNum = 363;BA.debugLine="lbl.Height = su.MeasureMultilineTextHeight(lbl, t";
_lbl.setHeight(_vvvvvvvvv3.MeasureMultilineTextHeight((android.widget.TextView)(_lbl.getObject()),BA.ObjectToCharSequence(_txt)));
 //BA.debugLineNum = 364;BA.debugLine="Return lbl";
if (true) return (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_lbl.getObject()));
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _vvvv5(String _paneleventname) throws Exception{
 //BA.debugLineNum = 331;BA.debugLine="Private Sub CreatePanel (PanelEventName As String)";
 //BA.debugLineNum = 332;BA.debugLine="Return xui.CreatePanel(PanelEventName)";
if (true) return _vvvvvvvv0.CreatePanel(ba,_paneleventname);
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _vvvv6() throws Exception{
anywheresoftware.b4a.objects.ScrollViewWrapper _nsv = null;
 //BA.debugLineNum = 340;BA.debugLine="Private Sub CreateScrollView As B4XView";
 //BA.debugLineNum = 341;BA.debugLine="Dim nsv As ScrollView";
_nsv = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 342;BA.debugLine="nsv.Initialize2(0, \"sv\")";
_nsv.Initialize2(ba,(int) (0),"sv");
 //BA.debugLineNum = 343;BA.debugLine="Return nsv";
if (true) return (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_nsv.getObject()));
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return null;
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 43;BA.debugLine="mBase = Base";
_vvvvvvv5.setObject((java.lang.Object)(_base));
 //BA.debugLineNum = 44;BA.debugLine="mBase.AddView(sv, 0, 0, mBase.Width, mBase.Height";
_vvvvvvv5.AddView((android.view.View)(_vvvvvvv6.getObject()),(int) (0),(int) (0),_vvvvvvv5.getWidth(),_vvvvvvv5.getHeight());
 //BA.debugLineNum = 45;BA.debugLine="sv.ScrollViewInnerPanel.Color = xui.PaintOrColorT";
_vvvvvvv6.getScrollViewInnerPanel().setColor(_vvvvvvvv0.PaintOrColorToColor(_props.Get((Object)("DividerColor"))));
 //BA.debugLineNum = 46;BA.debugLine="dividerHeight = DipToCurrent(Props.Get(\"DividerHe";
_vvvvvvv0 = (float) (__c.DipToCurrent((int)(BA.ObjectToNumber(_props.Get((Object)("DividerHeight"))))));
 //BA.debugLineNum = 47;BA.debugLine="PressedColor = xui.PaintOrColorToColor(Props.Get(";
_vvvvvvvv7 = _vvvvvvvv0.PaintOrColorToColor(_props.Get((Object)("PressedColor")));
 //BA.debugLineNum = 48;BA.debugLine="AnimationDuration = Props.GetDefault(\"InsertAnima";
_vvvvvvvv5 = (int)(BA.ObjectToNumber(_props.GetDefault((Object)("InsertAnimationDuration"),(Object)(_vvvvvvvv5))));
 //BA.debugLineNum = 49;BA.debugLine="DefaultTextColor = xui.PaintOrColorToColor(Lbl.Te";
_vvvvvvvv3 = _vvvvvvvv0.PaintOrColorToColor((Object)(_lbl.getTextColor()));
 //BA.debugLineNum = 50;BA.debugLine="DefaultTextBackgroundColor = mBase.Color";
_vvvvvvvv4 = _vvvvvvv5.getColor();
 //BA.debugLineNum = 51;BA.debugLine="DesignerLabel = Lbl";
_vvvvvvvvv2 = _lbl;
 //BA.debugLineNum = 52;BA.debugLine="Base_Resize(mBase.Width, mBase.Height)";
_base_resize(_vvvvvvv5.getWidth(),_vvvvvvv5.getHeight());
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public int  _vvvv7(int _index) throws Exception{
int _top = 0;
int _i = 0;
 //BA.debugLineNum = 268;BA.debugLine="Private Sub FindItemTop(Index As Int) As Int";
 //BA.debugLineNum = 269;BA.debugLine="Dim top As Int";
_top = 0;
 //BA.debugLineNum = 270;BA.debugLine="For i = 0 To Min(Index - 1, items.Size - 1)";
{
final int step2 = 1;
final int limit2 = (int) (__c.Min(_index-1,_vvvvvvv7.getSize()-1));
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 271;BA.debugLine="top = top + GetItem(i).Height + dividerHeight";
_top = (int) (_top+_vvvvv2(_i).Height+_vvvvvvv0);
 }
};
 //BA.debugLineNum = 273;BA.debugLine="Return top";
if (true) return _top;
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _vvvv0() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Public Sub GetBase As B4XView";
 //BA.debugLineNum = 83;BA.debugLine="Return mBase";
if (true) return _vvvvvvv5;
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return null;
}
public int  _getvvvvvvv2() throws Exception{
int _bottom = 0;
int _i = 0;
 //BA.debugLineNum = 278;BA.debugLine="Public Sub getFirstVisibleIndex As Int";
 //BA.debugLineNum = 279;BA.debugLine="Dim bottom As Int";
_bottom = 0;
 //BA.debugLineNum = 280;BA.debugLine="For i = 0 To items.Size - 1";
{
final int step2 = 1;
final int limit2 = (int) (_vvvvvvv7.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 281;BA.debugLine="bottom = bottom +  GetItem(i).Height + dividerHe";
_bottom = (int) (_bottom+_vvvvv2(_i).Height+_vvvvvvv0);
 //BA.debugLineNum = 282;BA.debugLine="If bottom > sv.ScrollViewOffsetY Then Return i";
if (_bottom>_vvvvvvv6.getScrollViewOffsetY()) { 
if (true) return _i;};
 }
};
 //BA.debugLineNum = 284;BA.debugLine="Return items.Size - 1";
if (true) return (int) (_vvvvvvv7.getSize()-1);
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return 0;
}
public com.eudict.customlistview._clvitem  _vvvvv2(int _index) throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Private Sub GetItem(Index As Int) As CLVItem";
 //BA.debugLineNum = 92;BA.debugLine="Return items.Get(Index)";
if (true) return (com.eudict.customlistview._clvitem)(_vvvvvvv7.Get(_index));
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return null;
}
public int  _vvvvv3(anywheresoftware.b4a.objects.B4XViewWrapper _v) throws Exception{
Object _parent = null;
anywheresoftware.b4a.objects.B4XViewWrapper _current = null;
 //BA.debugLineNum = 309;BA.debugLine="Public Sub GetItemFromView(v As B4XView) As Int";
 //BA.debugLineNum = 310;BA.debugLine="Dim parent = v As Object, current As B4XView";
_parent = (Object)(_v.getObject());
_current = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 311;BA.debugLine="Do While sv.ScrollViewInnerPanel <> parent";
while ((_vvvvvvv6.getScrollViewInnerPanel()).equals((java.lang.Object)(_parent)) == false) {
 //BA.debugLineNum = 312;BA.debugLine="current = parent";
_current.setObject((java.lang.Object)(_parent));
 //BA.debugLineNum = 313;BA.debugLine="parent = current.Parent";
_parent = (Object)(_current.getParent().getObject());
 }
;
 //BA.debugLineNum = 315;BA.debugLine="v = current";
_v = _current;
 //BA.debugLineNum = 316;BA.debugLine="Return v.Tag";
if (true) return (int)(BA.ObjectToNumber(_v.getTag()));
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return 0;
}
public int  _getvvvvvvv3() throws Exception{
int _first = 0;
int _bottom = 0;
int _i = 0;
 //BA.debugLineNum = 288;BA.debugLine="Public Sub getLastVisibleIndex As Int";
 //BA.debugLineNum = 289;BA.debugLine="Dim first As Int = getFirstVisibleIndex";
_first = _getvvvvvvv2();
 //BA.debugLineNum = 290;BA.debugLine="Dim bottom As Int";
_bottom = 0;
 //BA.debugLineNum = 291;BA.debugLine="For i = 0 To items.Size - 1";
{
final int step3 = 1;
final int limit3 = (int) (_vvvvvvv7.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 292;BA.debugLine="bottom = bottom +  GetItem(i).Height + dividerHe";
_bottom = (int) (_bottom+_vvvvv2(_i).Height+_vvvvvvv0);
 //BA.debugLineNum = 293;BA.debugLine="If i >= first And bottom >= sv.ScrollViewOffsetY";
if (_i>=_first && _bottom>=_vvvvvvv6.getScrollViewOffsetY()+_vvvvvvv6.getHeight()) { 
if (true) return (int) (__c.Max(_i-1,_first));};
 }
};
 //BA.debugLineNum = 295;BA.debugLine="Return items.Size - 1";
if (true) return (int) (_vvvvvvv7.getSize()-1);
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _vvvvv5(int _index) throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Public Sub GetPanel(Index As Int) As B4XView";
 //BA.debugLineNum = 97;BA.debugLine="Return GetItem(Index).Panel.GetView(0)";
if (true) return _vvvvv2(_index).Panel.GetView((int) (0));
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return null;
}
public int  _getvvvvvvv4() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Public Sub getSize As Int";
 //BA.debugLineNum = 88;BA.debugLine="Return items.Size";
if (true) return _vvvvvvv7.getSize();
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return 0;
}
public Object  _vvvvv7(int _index) throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Public Sub GetValue(Index As Int) As Object";
 //BA.debugLineNum = 102;BA.debugLine="Return GetItem(Index).Value";
if (true) return _vvvvv2(_index).Value;
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _vcallback,String _veventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 33;BA.debugLine="Public Sub Initialize (vCallBack As Object, vEvent";
 //BA.debugLineNum = 34;BA.debugLine="EventName = vEventName";
_vvvvvvvv1 = _veventname;
 //BA.debugLineNum = 35;BA.debugLine="CallBack = vCallBack";
_vvvvvvvv2 = _vcallback;
 //BA.debugLineNum = 36;BA.debugLine="sv = CreateScrollView";
_vvvvvvv6 = _vvvv6();
 //BA.debugLineNum = 37;BA.debugLine="items.Initialize";
_vvvvvvv7.Initialize();
 //BA.debugLineNum = 38;BA.debugLine="DefaultTextBackgroundColor = xui.Color_White";
_vvvvvvvv4 = _vvvvvvvv0.Color_White;
 //BA.debugLineNum = 39;BA.debugLine="DefaultTextColor = xui.Color_Black";
_vvvvvvvv3 = _vvvvvvvv0.Color_Black;
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public String  _vvvvv0(int _index,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,Object _value) throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Public Sub InsertAt(Index As Int, pnl As B4XView,";
 //BA.debugLineNum = 147;BA.debugLine="InsertAtImpl(Index, pnl, pnl.Height, Value, 0)";
_vvvvvv1(_index,_pnl,_pnl.getHeight(),_value,(int) (0));
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public void  _vvvvvv1(int _index,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,int _itemheight,Object _value,int _initialheight) throws Exception{
ResumableSub_InsertAtImpl rsub = new ResumableSub_InsertAtImpl(this,_index,_pnl,_itemheight,_value,_initialheight);
rsub.resume(ba, null);
}
public static class ResumableSub_InsertAtImpl extends BA.ResumableSub {
public ResumableSub_InsertAtImpl(com.eudict.customlistview parent,int _index,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,int _itemheight,Object _value,int _initialheight) {
this.parent = parent;
this._index = _index;
this._pnl = _pnl;
this._itemheight = _itemheight;
this._value = _value;
this._initialheight = _initialheight;
}
com.eudict.customlistview parent;
int _index;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl;
int _itemheight;
Object _value;
int _initialheight;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _includedividierheight = 0;
com.eudict.customlistview._clvitem _newitem = null;
int _top = 0;
com.eudict.customlistview._clvitem _previtem = null;
int _i = 0;
com.eudict.customlistview._clvitem _it = null;
int _newtop = 0;
boolean _shouldsetpanelheight = false;
int step27;
int limit27;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 153;BA.debugLine="Dim p As B4XView = CreatePanel(\"Panel\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = parent._vvvv5("Panel");
 //BA.debugLineNum = 154;BA.debugLine="p.Color = Pnl.Color";
_p.setColor(_pnl.getColor());
 //BA.debugLineNum = 155;BA.debugLine="Pnl.Color = xui.Color_Transparent";
_pnl.setColor(parent._vvvvvvvv0.Color_Transparent);
 //BA.debugLineNum = 156;BA.debugLine="p.AddView(Pnl, 0, 0, sv.ScrollViewContentWidth, I";
_p.AddView((android.view.View)(_pnl.getObject()),(int) (0),(int) (0),parent._vvvvvvv6.getScrollViewContentWidth(),_itemheight);
 //BA.debugLineNum = 157;BA.debugLine="p.Tag = Index";
_p.setTag((Object)(_index));
 //BA.debugLineNum = 158;BA.debugLine="Dim IncludeDividierHeight As Int";
_includedividierheight = 0;
 //BA.debugLineNum = 159;BA.debugLine="If InitialHeight = 0 Then IncludeDividierHeight =";
if (true) break;

case 1:
//if
this.state = 8;
if (_initialheight==0) { 
this.state = 3;
;}
else {
this.state = 5;
;}if (true) break;

case 3:
//C
this.state = 8;
_includedividierheight = (int) (parent._vvvvvvv0);
if (true) break;

case 5:
//C
this.state = 8;
_includedividierheight = (int) (0);
if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 160;BA.debugLine="Dim NewItem As CLVItem";
_newitem = new com.eudict.customlistview._clvitem();
 //BA.debugLineNum = 161;BA.debugLine="NewItem.Panel = p";
_newitem.Panel = _p;
 //BA.debugLineNum = 162;BA.debugLine="NewItem.Height = ItemHeight";
_newitem.Height = _itemheight;
 //BA.debugLineNum = 163;BA.debugLine="NewItem.Value = Value";
_newitem.Value = _value;
 //BA.debugLineNum = 164;BA.debugLine="NewItem.Color = p.Color";
_newitem.Color = _p.getColor();
 //BA.debugLineNum = 165;BA.debugLine="If Index = items.Size And InitialHeight = 0 Then";
if (true) break;

case 9:
//if
this.state = 43;
if (_index==parent._vvvvvvv7.getSize() && _initialheight==0) { 
this.state = 11;
}else {
this.state = 21;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 166;BA.debugLine="items.Add(NewItem)";
parent._vvvvvvv7.Add((Object)(_newitem));
 //BA.debugLineNum = 167;BA.debugLine="Dim top As Int";
_top = 0;
 //BA.debugLineNum = 168;BA.debugLine="If Index = 0 Then top = dividerHeight Else top =";
if (true) break;

case 12:
//if
this.state = 19;
if (_index==0) { 
this.state = 14;
;}
else {
this.state = 16;
;}if (true) break;

case 14:
//C
this.state = 19;
_top = (int) (parent._vvvvvvv0);
if (true) break;

case 16:
//C
this.state = 19;
_top = parent._vvvvvvv6.getScrollViewInnerPanel().getHeight();
if (true) break;

case 19:
//C
this.state = 43;
;
 //BA.debugLineNum = 169;BA.debugLine="sv.ScrollViewInnerPanel.AddView(p, 0, top, sv.Wi";
parent._vvvvvvv6.getScrollViewInnerPanel().AddView((android.view.View)(_p.getObject()),(int) (0),_top,parent._vvvvvvv6.getWidth(),_itemheight);
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 171;BA.debugLine="CleanAfterAnimation";
parent._vvvv2();
 //BA.debugLineNum = 172;BA.debugLine="Dim top As Int";
_top = 0;
 //BA.debugLineNum = 173;BA.debugLine="If Index = 0 Then";
if (true) break;

case 22:
//if
this.state = 27;
if (_index==0) { 
this.state = 24;
}else {
this.state = 26;
}if (true) break;

case 24:
//C
this.state = 27;
 //BA.debugLineNum = 174;BA.debugLine="top = dividerHeight";
_top = (int) (parent._vvvvvvv0);
 if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 176;BA.debugLine="Dim PrevItem As CLVItem = items.Get(Index - 1)";
_previtem = (com.eudict.customlistview._clvitem)(parent._vvvvvvv7.Get((int) (_index-1)));
 //BA.debugLineNum = 177;BA.debugLine="top = PrevItem.Panel.top + PrevItem.Height + di";
_top = (int) (_previtem.Panel.getTop()+_previtem.Height+parent._vvvvvvv0);
 if (true) break;
;
 //BA.debugLineNum = 179;BA.debugLine="For i = Index To items.Size - 1";

case 27:
//for
this.state = 36;
step27 = 1;
limit27 = (int) (parent._vvvvvvv7.getSize()-1);
_i = _index ;
this.state = 58;
if (true) break;

case 58:
//C
this.state = 36;
if ((step27 > 0 && _i <= limit27) || (step27 < 0 && _i >= limit27)) this.state = 29;
if (true) break;

case 59:
//C
this.state = 58;
_i = ((int)(0 + _i + step27)) ;
if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 180;BA.debugLine="Dim it As CLVItem = items.Get(i)";
_it = (com.eudict.customlistview._clvitem)(parent._vvvvvvv7.Get(_i));
 //BA.debugLineNum = 181;BA.debugLine="Dim NewTop As Int = it.Panel.top + ItemHeight -";
_newtop = (int) (_it.Panel.getTop()+_itemheight-_initialheight+_includedividierheight);
 //BA.debugLineNum = 182;BA.debugLine="If  Min(NewTop, it.Panel.Top) - sv.ScrollViewOf";
if (true) break;

case 30:
//if
this.state = 35;
if (parent.__c.Min(_newtop,_it.Panel.getTop())-parent._vvvvvvv6.getScrollViewOffsetY()<parent._vvvvvvv6.getHeight()) { 
this.state = 32;
}else {
this.state = 34;
}if (true) break;

case 32:
//C
this.state = 35;
 //BA.debugLineNum = 183;BA.debugLine="it.Panel.SetLayoutAnimated(AnimationDuration,";
_it.Panel.SetLayoutAnimated(parent._vvvvvvvv5,(int) (0),_newtop,_it.Panel.getWidth(),_it.Height);
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 185;BA.debugLine="it.Panel.top = NewTop";
_it.Panel.setTop(_newtop);
 if (true) break;

case 35:
//C
this.state = 59;
;
 //BA.debugLineNum = 187;BA.debugLine="it.Panel.Tag = i + 1";
_it.Panel.setTag((Object)(_i+1));
 if (true) break;
if (true) break;

case 36:
//C
this.state = 37;
;
 //BA.debugLineNum = 189;BA.debugLine="items.InsertAt(Index, NewItem)";
parent._vvvvvvv7.InsertAt(_index,(Object)(_newitem));
 //BA.debugLineNum = 190;BA.debugLine="sv.ScrollViewInnerPanel.AddView(p, 0, top, sv.Wi";
parent._vvvvvvv6.getScrollViewInnerPanel().AddView((android.view.View)(_p.getObject()),(int) (0),_top,parent._vvvvvvv6.getWidth(),_initialheight);
 //BA.debugLineNum = 192;BA.debugLine="Dim ShouldSetPanelHeight As Boolean = InitialHei";
_shouldsetpanelheight = _initialheight>_itemheight;
 //BA.debugLineNum = 193;BA.debugLine="If ShouldSetPanelHeight = True Then 'collapse";
if (true) break;

case 37:
//if
this.state = 42;
if (_shouldsetpanelheight==parent.__c.True) { 
this.state = 39;
}else {
this.state = 41;
}if (true) break;

case 39:
//C
this.state = 42;
 //BA.debugLineNum = 194;BA.debugLine="Pnl.Height = InitialHeight";
_pnl.setHeight(_initialheight);
 if (true) break;

case 41:
//C
this.state = 42;
 //BA.debugLineNum = 196;BA.debugLine="p.SetLayoutAnimated(0, 0, top, sv.Width, ItemHe";
_p.SetLayoutAnimated((int) (0),(int) (0),_top,parent._vvvvvvv6.getWidth(),_itemheight);
 if (true) break;

case 42:
//C
this.state = 43;
;
 //BA.debugLineNum = 198;BA.debugLine="p.SendToBack";
_p.SendToBack();
 if (true) break;

case 43:
//C
this.state = 44;
;
 //BA.debugLineNum = 201;BA.debugLine="sv.ScrollViewContentHeight = sv.ScrollViewContent";
parent._vvvvvvv6.setScrollViewContentHeight((int) (parent._vvvvvvv6.getScrollViewContentHeight()+_itemheight-_initialheight+_includedividierheight));
 //BA.debugLineNum = 202;BA.debugLine="If items.Size = 1 Then sv.ScrollViewContentHeight";
if (true) break;

case 44:
//if
this.state = 49;
if (parent._vvvvvvv7.getSize()==1) { 
this.state = 46;
;}if (true) break;

case 46:
//C
this.state = 49;
parent._vvvvvvv6.setScrollViewContentHeight((int) (parent._vvvvvvv6.getScrollViewContentHeight()+_includedividierheight));
if (true) break;

case 49:
//C
this.state = 50;
;
 //BA.debugLineNum = 204;BA.debugLine="If ShouldSetPanelHeight Then";
if (true) break;

case 50:
//if
this.state = 57;
if (_shouldsetpanelheight) { 
this.state = 52;
}if (true) break;

case 52:
//C
this.state = 53;
 //BA.debugLineNum = 205;BA.debugLine="Sleep(AnimationDuration)";
parent.__c.Sleep(ba,this,parent._vvvvvvvv5);
this.state = 60;
return;
case 60:
//C
this.state = 53;
;
 //BA.debugLineNum = 206;BA.debugLine="If p.Parent.IsInitialized Then";
if (true) break;

case 53:
//if
this.state = 56;
if (_p.getParent().IsInitialized()) { 
this.state = 55;
}if (true) break;

case 55:
//C
this.state = 56;
 //BA.debugLineNum = 208;BA.debugLine="p.Height = ItemHeight";
_p.setHeight(_itemheight);
 //BA.debugLineNum = 209;BA.debugLine="Pnl.Height = ItemHeight";
_pnl.setHeight(_itemheight);
 if (true) break;

case 56:
//C
this.state = 57;
;
 if (true) break;

case 57:
//C
this.state = -1;
;
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _vvvvvv2(int _index,Object _text,Object _value) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
anywheresoftware.b4a.objects.B4XViewWrapper _lbl = null;
com.eudict.customlistview._clvitem _item = null;
 //BA.debugLineNum = 132;BA.debugLine="Public Sub InsertAtTextItem(Index As Int, Text As";
 //BA.debugLineNum = 133;BA.debugLine="Dim pnl As B4XView = CreatePanel(\"\")";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = _vvvv5("");
 //BA.debugLineNum = 134;BA.debugLine="Dim lbl As B4XView = CreateLabel(Text)";
_lbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_lbl = _vvvv4(BA.ObjectToString(_text));
 //BA.debugLineNum = 135;BA.debugLine="lbl.Height = Max(50dip, lbl.Height)";
_lbl.setHeight((int) (__c.Max(__c.DipToCurrent((int) (50)),_lbl.getHeight())));
 //BA.debugLineNum = 136;BA.debugLine="pnl.AddView(lbl, 5dip, 2dip, sv.ScrollViewContent";
_pnl.AddView((android.view.View)(_lbl.getObject()),__c.DipToCurrent((int) (5)),__c.DipToCurrent((int) (2)),(int) (_vvvvvvv6.getScrollViewContentWidth()-__c.DipToCurrent((int) (10))),_lbl.getHeight());
 //BA.debugLineNum = 137;BA.debugLine="lbl.TextColor = DefaultTextColor";
_lbl.setTextColor(_vvvvvvvv3);
 //BA.debugLineNum = 138;BA.debugLine="pnl.Color = DefaultTextBackgroundColor";
_pnl.setColor(_vvvvvvvv4);
 //BA.debugLineNum = 139;BA.debugLine="pnl.Height = lbl.Height + 2dip";
_pnl.setHeight((int) (_lbl.getHeight()+__c.DipToCurrent((int) (2))));
 //BA.debugLineNum = 140;BA.debugLine="InsertAt(Index, pnl, Value)";
_vvvvv0(_index,_pnl,_value);
 //BA.debugLineNum = 141;BA.debugLine="Dim item As CLVItem = GetItem(Index)";
_item = _vvvvv2(_index);
 //BA.debugLineNum = 142;BA.debugLine="item.TextItem = True";
_item.TextItem = __c.True;
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv3(int _index) throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Public Sub JumpToItem(Index As Int)";
 //BA.debugLineNum = 255;BA.debugLine="sv.ScrollViewOffsetY = FindItemTop(Index)";
_vvvvvvv6.setScrollViewOffsetY(_vvvv7(_index));
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public String  _panel_click() throws Exception{
 //BA.debugLineNum = 346;BA.debugLine="Private Sub Panel_Click";
 //BA.debugLineNum = 347;BA.debugLine="PanelClickHandler(Sender)";
_vvvvvv4((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(__c.Sender(ba))));
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public void  _vvvvvv4(anywheresoftware.b4a.objects.B4XViewWrapper _senderpanel) throws Exception{
ResumableSub_PanelClickHandler rsub = new ResumableSub_PanelClickHandler(this,_senderpanel);
rsub.resume(ba, null);
}
public static class ResumableSub_PanelClickHandler extends BA.ResumableSub {
public ResumableSub_PanelClickHandler(com.eudict.customlistview parent,anywheresoftware.b4a.objects.B4XViewWrapper _senderpanel) {
this.parent = parent;
this._senderpanel = _senderpanel;
}
com.eudict.customlistview parent;
anywheresoftware.b4a.objects.B4XViewWrapper _senderpanel;
int _clr = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 299;BA.debugLine="Dim clr As Int = GetItem(SenderPanel.Tag).Color";
_clr = parent._vvvvv2((int)(BA.ObjectToNumber(_senderpanel.getTag()))).Color;
 //BA.debugLineNum = 300;BA.debugLine="SenderPanel.SetColorAnimated(50, clr, PressedColo";
_senderpanel.SetColorAnimated((int) (50),_clr,parent._vvvvvvvv7);
 //BA.debugLineNum = 301;BA.debugLine="If xui.SubExists(CallBack, EventName & \"_ItemClic";
if (true) break;

case 1:
//if
this.state = 4;
if (parent._vvvvvvvv0.SubExists(ba,parent._vvvvvvvv2,parent._vvvvvvvv1+"_ItemClick",(int) (2))) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 302;BA.debugLine="CallSub3(CallBack, EventName & \"_ItemClick\", Sen";
parent.__c.CallSubNew3(ba,parent._vvvvvvvv2,parent._vvvvvvvv1+"_ItemClick",_senderpanel.getTag(),parent._vvvvv2((int)(BA.ObjectToNumber(_senderpanel.getTag()))).Value);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 304;BA.debugLine="Sleep(200)";
parent.__c.Sleep(ba,this,(int) (200));
this.state = 5;
return;
case 5:
//C
this.state = -1;
;
 //BA.debugLineNum = 305;BA.debugLine="SenderPanel.SetColorAnimated(200, PressedColor, c";
_senderpanel.SetColorAnimated((int) (200),parent._vvvvvvvv7,_clr);
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _vvvvvv5(int _index) throws Exception{
com.eudict.customlistview._clvitem _removeitem = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _i = 0;
com.eudict.customlistview._clvitem _item = null;
int _newtop = 0;
 //BA.debugLineNum = 106;BA.debugLine="Public Sub RemoveAt(Index As Int)";
 //BA.debugLineNum = 107;BA.debugLine="If getSize <= 1 Then";
if (_getvvvvvvv4()<=1) { 
 //BA.debugLineNum = 108;BA.debugLine="Clear";
_vvvv3();
 //BA.debugLineNum = 109;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 111;BA.debugLine="Dim RemoveItem As CLVItem = items.Get(Index)";
_removeitem = (com.eudict.customlistview._clvitem)(_vvvvvvv7.Get(_index));
 //BA.debugLineNum = 112;BA.debugLine="Dim p As B4XView";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 113;BA.debugLine="For i = Index + 1 To items.Size - 1";
{
final int step7 = 1;
final int limit7 = (int) (_vvvvvvv7.getSize()-1);
_i = (int) (_index+1) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 114;BA.debugLine="Dim item As CLVItem = items.Get(i)";
_item = (com.eudict.customlistview._clvitem)(_vvvvvvv7.Get(_i));
 //BA.debugLineNum = 115;BA.debugLine="p = item.Panel";
_p = _item.Panel;
 //BA.debugLineNum = 116;BA.debugLine="p.Tag = i - 1";
_p.setTag((Object)(_i-1));
 //BA.debugLineNum = 117;BA.debugLine="Dim NewTop As Int = p.Top - item.Height - divide";
_newtop = (int) (_p.getTop()-_item.Height-_vvvvvvv0);
 //BA.debugLineNum = 118;BA.debugLine="p.top = NewTop";
_p.setTop(_newtop);
 }
};
 //BA.debugLineNum = 120;BA.debugLine="sv.ScrollViewContentHeight = sv.ScrollViewContent";
_vvvvvvv6.setScrollViewContentHeight((int) (_vvvvvvv6.getScrollViewContentHeight()-_removeitem.Height-_vvvvvvv0));
 //BA.debugLineNum = 121;BA.debugLine="items.RemoveAt(Index)";
_vvvvvvv7.RemoveAt(_index);
 //BA.debugLineNum = 122;BA.debugLine="RemoveItem.Panel.RemoveViewFromParent";
_removeitem.Panel.RemoveViewFromParent();
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv6(int _index,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,int _itemheight,Object _value) throws Exception{
com.eudict.customlistview._clvitem _removeitem = null;
 //BA.debugLineNum = 239;BA.debugLine="Public Sub ReplaceAt(Index As Int, pnl As B4XView,";
 //BA.debugLineNum = 240;BA.debugLine="Dim RemoveItem As CLVItem = items.Get(Index)";
_removeitem = (com.eudict.customlistview._clvitem)(_vvvvvvv7.Get(_index));
 //BA.debugLineNum = 241;BA.debugLine="items.RemoveAt(Index)";
_vvvvvvv7.RemoveAt(_index);
 //BA.debugLineNum = 242;BA.debugLine="RemoveItem.Panel.RemoveViewFromParent";
_removeitem.Panel.RemoveViewFromParent();
 //BA.debugLineNum = 243;BA.debugLine="InsertAtImpl(Index, pnl, ItemHeight, Value, Remov";
_vvvvvv1(_index,_pnl,_itemheight,_value,_removeitem.Height);
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv7(int _index,int _itemheight) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
Object _value = null;
anywheresoftware.b4a.objects.B4XViewWrapper _parent = null;
 //BA.debugLineNum = 228;BA.debugLine="Public Sub ResizeItem(Index As Int, ItemHeight As";
 //BA.debugLineNum = 229;BA.debugLine="Dim p As B4XView = GetPanel(Index)";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _vvvvv5(_index);
 //BA.debugLineNum = 230;BA.debugLine="Dim value As Object = items.Get(Index)";
_value = _vvvvvvv7.Get(_index);
 //BA.debugLineNum = 231;BA.debugLine="Dim parent As B4XView = p.Parent";
_parent = new anywheresoftware.b4a.objects.B4XViewWrapper();
_parent = _p.getParent();
 //BA.debugLineNum = 232;BA.debugLine="p.Color = parent.Color";
_p.setColor(_parent.getColor());
 //BA.debugLineNum = 233;BA.debugLine="p.RemoveViewFromParent";
_p.RemoveViewFromParent();
 //BA.debugLineNum = 234;BA.debugLine="ReplaceAt(Index, p, ItemHeight, value)";
_vvvvvv6(_index,_p,_itemheight,_value);
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv0() throws Exception{
int _position = 0;
 //BA.debugLineNum = 319;BA.debugLine="Private Sub ScrollHandler";
 //BA.debugLineNum = 320;BA.debugLine="Dim position As Int = sv.ScrollViewOffsetY";
_position = _vvvvvvv6.getScrollViewOffsetY();
 //BA.debugLineNum = 321;BA.debugLine="If position + sv.Height >= sv.ScrollViewContentHe";
if (_position+_vvvvvvv6.getHeight()>=_vvvvvvv6.getScrollViewContentHeight()-__c.DipToCurrent((int) (5)) && __c.DateTime.getNow()>_vvvvvvvv6+100) { 
 //BA.debugLineNum = 322;BA.debugLine="If xui.SubExists(CallBack, EventName & \"_ReachEn";
if (_vvvvvvvv0.SubExists(ba,_vvvvvvvv2,_vvvvvvvv1+"_ReachEnd",(int) (0))) { 
 //BA.debugLineNum = 323;BA.debugLine="LastReachEndEvent = DateTime.Now";
_vvvvvvvv6 = __c.DateTime.getNow();
 //BA.debugLineNum = 324;BA.debugLine="CallSubDelayed(CallBack, EventName & \"_ReachEnd";
__c.CallSubDelayed(ba,_vvvvvvvv2,_vvvvvvvv1+"_ReachEnd");
 }else {
 //BA.debugLineNum = 326;BA.debugLine="LastReachEndEvent = DateTime.Now + 1000 * DateT";
_vvvvvvvv6 = (long) (__c.DateTime.getNow()+1000*__c.DateTime.TicksPerDay);
 };
 };
 //BA.debugLineNum = 329;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvv1(int _index) throws Exception{
anywheresoftware.b4a.objects.ScrollViewWrapper _nsv = null;
 //BA.debugLineNum = 259;BA.debugLine="Public Sub ScrollToItem(Index As Int)";
 //BA.debugLineNum = 263;BA.debugLine="Dim nsv As ScrollView = sv";
_nsv = new anywheresoftware.b4a.objects.ScrollViewWrapper();
_nsv.setObject((android.widget.ScrollView)(_vvvvvvv6.getObject()));
 //BA.debugLineNum = 264;BA.debugLine="nsv.ScrollPosition = FindItemTop(Index) 'smooth s";
_nsv.setScrollPosition(_vvvv7(_index));
 //BA.debugLineNum = 266;BA.debugLine="End Sub";
return "";
}
public String  _sv_scrollchanged(int _position) throws Exception{
 //BA.debugLineNum = 350;BA.debugLine="Private Sub sv_ScrollChanged(Position As Int)";
 //BA.debugLineNum = 351;BA.debugLine="ScrollHandler";
_vvvvvv0();
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
